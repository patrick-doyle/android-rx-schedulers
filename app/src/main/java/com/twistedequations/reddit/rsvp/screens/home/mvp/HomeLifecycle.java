package com.twistedequations.reddit.rsvp.screens.home.mvp;

import com.twistedequations.mvl.lifecycle.CreateLifecycle;
import com.twistedequations.mvl.rx.MVLSchedulers;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditItem;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.subscriptions.CompositeSubscription;

public class HomeLifecycle implements CreateLifecycle {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private final HomeView homeView;
    private final HomeModel homeModel;
    private final MVLSchedulers mvlSchedulers;

    public HomeLifecycle(HomeView homeView, HomeModel homeModel, MVLSchedulers mvlSchedulers) {
        this.homeView = homeView;
        this.homeModel = homeModel;
        this.mvlSchedulers = mvlSchedulers;
    }

    @Override
    public void onCreate() {
        compositeSubscription.add(loadPostsFunc.call(homeView, homeModel, mvlSchedulers));
        compositeSubscription.add(listItemItemClicksFunc.call(homeView, homeModel));
    }

    @Override
    public void onDestroy() {
        compositeSubscription.clear();
    }

    private static final Func3<HomeView, HomeModel, MVLSchedulers, Observable<List<RedditItem>>> loadRedditItemsFunc
            = (homeView, homeModel, mvlSchedulers) -> Observable.just(null)
            .doOnNext(notification -> homeView.setLoading(true))
            .observeOn(mvlSchedulers.network())

            .switchMap(aVoid -> homeModel.getSavedRedditListing()
                    .concatWith(homeModel.postsForAll().map(homeModel::saveRedditListing)))

            .map(redditData -> redditData.data.children)
            .flatMap(redditItems -> Observable.from(redditItems)
                    .map(child -> child.data)
                    .filter(redditItem -> !redditItem.over18)
                    .toList())

            .observeOn(mvlSchedulers.mainThread())
            .doOnNext(notification -> homeView.setLoading(false));

    private static final Func3<HomeView, HomeModel, MVLSchedulers, Subscription> loadPostsFunc
            = (homeView, homeModel, mvlSchedulers) -> Observable.just(null)
            .mergeWith(homeView.refreshMenuClick())
            .mergeWith(homeView.errorRetryClick()) 
            .flatMap(aVoid -> loadRedditItemsFunc.call(homeView, homeModel, mvlSchedulers))
            .doOnError(Throwable::printStackTrace)
            .subscribe(homeView::setRedditItems, throwable -> homeView.showError());

    private static final Func2<HomeView, HomeModel, Subscription> listItemItemClicksFunc = (homeView, homeModel)
            -> homeView.listItemClicks().subscribe(homeModel::startDetailActivity);

}
