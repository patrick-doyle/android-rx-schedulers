package com.twistedequations.reddit.rsvp.screens.detail.mvp;

import com.twistedequations.mvl.lifecycle.CreateLifecycle;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditItem;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditListing;
import com.twistedequations.mvl.rx.RxSchedulers;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public class PostLifecycle implements CreateLifecycle {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private final PostModel postModel;
    private final PostActivityView postActivityView;
    private final RxSchedulers rxSchedulers;

    public PostLifecycle(PostActivityView postActivityView, PostModel postModel, RxSchedulers rxSchedulers) {
        this.postModel = postModel;
        this.postActivityView = postActivityView;
        this.rxSchedulers = rxSchedulers;
    }

    @Override
    public void onCreate() {
        compositeSubscription.add(subscribeToComments());
    }

    @Override
    public void onDestroy() {
        compositeSubscription.clear();
    }

    private Subscription subscribeToComments() {

        final CompositeSubscription compositeSubscription = new CompositeSubscription();
        final PublishSubject<List<RedditListing>> listPublishSubject = PublishSubject.create();

        final Subscription postSub = listPublishSubject.map((redditListings -> redditListings.get(0)))
                .map(redditListing -> redditListing.data.children.get(0).data)
                .onErrorResumeNext(throwable -> Observable.empty())
                .observeOn(rxSchedulers.mainThread())
                .subscribe(postActivityView::setRedditItem);

        final Observable<List<RedditItem>> networkItems = listPublishSubject.map((redditListings -> redditListings.get(1)))
                .map(redditListing -> redditListing.data.children)
                .onErrorResumeNext(throwable -> Observable.just(Collections.emptyList()))
                .flatMap(children -> Observable.from(children)
                        .map(child -> child.data)
                        .toList());

        final Subscription commentSub = Observable.concat(postModel.getCommentsFromState(), networkItems)
                .observeOn(rxSchedulers.mainThread())
                .doOnNext(postModel::saveComentsState)
                .subscribe(postActivityView::setCommentList);

        final Subscription subscription = Observable.just(postModel.getIntentRedditItem())
                .flatMap(redditItem -> postModel.getCommentsForPost(redditItem.subreddit, redditItem.id))
                .subscribeOn(rxSchedulers.network())
                .subscribe(listPublishSubject);

        compositeSubscription.add(subscription);
        compositeSubscription.add(postSub);
        compositeSubscription.add(commentSub);
        return compositeSubscription;
    }
}
