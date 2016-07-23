package com.twistedequations.reddit.rsvp.screens.detail.mvl;

import com.twistedequations.mvl.rx.AndroidSchedulers;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditItem;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditListing;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func3;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public class PostLifecycle {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private final PostModel postModel;
    private final PostActivityView postActivityView;
    private final AndroidSchedulers androidSchedulers;

    public PostLifecycle(PostActivityView postActivityView, PostModel postModel, AndroidSchedulers androidSchedulers) {
        this.postModel = postModel;
        this.postActivityView = postActivityView;
        this.androidSchedulers = androidSchedulers;
    }

    public void onCreate() {
        compositeSubscription.add(loadCommentsFunc.call(postActivityView, postModel, androidSchedulers));
    }

    public void onDestroy() {
        compositeSubscription.clear();
    }

    private static final Func3<PostActivityView, PostModel, AndroidSchedulers, Subscription> loadCommentsFunc = (postActivityView, postModel, mvlSchedulers) -> {

        final CompositeSubscription compositeSubscription = new CompositeSubscription();
        final PublishSubject<List<RedditListing>> listPublishSubject = PublishSubject.create();

        //get the posts from the network request
        final Subscription postSub = listPublishSubject.map(redditListings -> redditListings.get(0))
                .map(redditListing -> redditListing.data.children.get(0).data)
                .onErrorResumeNext(throwable -> Observable.empty())
                .observeOn(mvlSchedulers.mainThread())
                .subscribe(postActivityView::setRedditItem);

        //get the commentns from the network request
        final Observable<List<RedditItem>> networkItems = listPublishSubject.map(redditListings -> redditListings.get(1))
                .map(redditListing -> redditListing.data.children)
                .onErrorResumeNext(throwable -> Observable.just(Collections.emptyList()))
                .flatMap(children -> Observable.from(children)
                        .map(child -> child.data)
                        .toList());

        //get the comments from the saved state
        final Subscription commentSub = Observable.concat(postModel.getCommentsFromState(), networkItems)
                .observeOn(mvlSchedulers.mainThread())
                .doOnNext(postModel::saveComentsState)
                .subscribe(postActivityView::setCommentList);

        final Subscription subscription = Observable.just(postModel.getIntentRedditItem())
                .flatMap(redditItem -> postModel.getCommentsForPost(redditItem.subreddit, redditItem.id))
                .subscribeOn(mvlSchedulers.network())
                .subscribe(listPublishSubject);

        compositeSubscription.add(subscription);
        compositeSubscription.add(postSub);
        compositeSubscription.add(commentSub);
        return compositeSubscription;
    };
}
