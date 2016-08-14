package com.twistedequations.reddit.rsvp.screens.home.mvl;

import com.twistedequations.mvl.rx.AndroidRxSchedulers;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditItem;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class HomePresenter {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private final HomeView homeView;
    private final HomeModel homeModel;
    private final AndroidRxSchedulers androidRxSchedulers;

    public HomePresenter(HomeView homeView, HomeModel homeModel, AndroidRxSchedulers androidRxSchedulers) {
        this.homeView = homeView;
        this.homeModel = homeModel;
        this.androidRxSchedulers = androidRxSchedulers;
    }

    public void onCreate() {
        compositeSubscription.add(loadPostsSubscribtion());
    }

    public void onDestroy() {
        compositeSubscription.clear();
    }

    //Loads posts from both the saved state and then the network
    private Observable<List<RedditItem>> loadPostsObservable() {
        return Observable.just(null)
                .doOnNext(notification -> homeView.setLoading(true))

                .switchMap(aVoid -> homeModel.getSavedRedditListing()
                        .concatWith(homeModel.postsForAll()
                                .subscribeOn(androidRxSchedulers.network())
                                .observeOn(androidRxSchedulers.mainThread())
                                .map(homeModel::saveRedditListing)))

                .map(redditData -> redditData.data.children)
                .flatMap(redditItems -> Observable.from(redditItems)
                        .map(child -> child.data)
                        .filter(redditItem -> !redditItem.over18)
                        .toList())

                .observeOn(androidRxSchedulers.mainThread())
                .doOnNext(notification -> homeView.setLoading(false));
    }

    private Subscription loadPostsSubscribtion() {
        return Observable.just(null)
                .mergeWith(homeView.refreshMenuClick())
                .mergeWith(homeView.errorRetryClick())
                .flatMap(aVoid -> loadPostsObservable())
                .doOnError(Throwable::printStackTrace)
                .subscribe(homeView::setRedditItems, throwable -> homeView.showError());
    }

}


