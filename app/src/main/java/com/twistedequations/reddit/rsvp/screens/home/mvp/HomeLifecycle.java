package com.twistedequations.reddit.rsvp.screens.home.mvp;

import com.twistedequations.mvl.lifecycle.CreateLifecycle;
import com.twistedequations.mvl.rx.RxSchedulers;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditItem;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class HomeLifecycle implements CreateLifecycle {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private final HomeView homeView;
    private final HomeModel homeModel;
    private final RxSchedulers rxSchedulers;

    public HomeLifecycle(HomeView homeView, HomeModel homeModel, RxSchedulers rxSchedulers) {
        this.homeView = homeView;
        this.homeModel = homeModel;
        this.rxSchedulers = rxSchedulers;
    }

    @Override
    public void onCreate() {
        compositeSubscription.add(loadPosts());
        compositeSubscription.add(observableItemClicks());
    }

    @Override
    public void onDestroy() {
        compositeSubscription.clear();
    }

    private Subscription loadPosts() {
        return Observable.just(null)
                .mergeWith(homeView.refreshMenuClick())
                .mergeWith(homeView.errorRetryClick())
                .flatMap(aVoid -> loadRedditItems())
                .doOnError(Throwable::printStackTrace)
                .subscribe(homeView::setRedditItems, throwable -> homeView.showError());
    }

    private Observable<List<RedditItem>> loadRedditItems() {
        return Observable.just(null)
                .doOnEach(notification -> homeView.setLoading(true))
                .observeOn(rxSchedulers.network())
                .switchMap(aVoid -> homeModel.getSavedRedditListing())
                .concatWith(homeModel.postsForAll())
                .map(redditData -> redditData.data.children)
                .flatMap(Observable::from)
                .map(child -> child.data)
                .filter(redditItem -> !redditItem.over18)
                .toList()
                .observeOn(rxSchedulers.mainThread())
                .doOnEach(notification -> homeView.setLoading(false));
    }

    private Subscription observableItemClicks() {
        return homeView.listItemClicks()
                .subscribe(homeModel::startDetailActivity);
    }
}
