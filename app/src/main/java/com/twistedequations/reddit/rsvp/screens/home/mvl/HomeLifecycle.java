package com.twistedequations.reddit.rsvp.screens.home.mvl;

import com.twistedequations.mvl.rx.AndroidSchedulers;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditItem;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.subscriptions.CompositeSubscription;

public class HomeLifecycle {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private final HomeView homeView;
    private final HomeModel homeModel;
    private final AndroidSchedulers androidSchedulers;

    public HomeLifecycle(HomeView homeView, HomeModel homeModel, AndroidSchedulers androidSchedulers) {
        this.homeView = homeView;
        this.homeModel = homeModel;
        this.androidSchedulers = androidSchedulers;
    }

    public void onCreate() {
        compositeSubscription.add(loadPostsFunc.call(homeView, homeModel, androidSchedulers));
        compositeSubscription.add(listItemItemClicksFunc.call(homeView, homeModel));
    }

    public void onDestroy() {
        compositeSubscription.clear();
    }

    //Loads posts from both the saved state and then the network
    private static final Func3<HomeView, HomeModel, AndroidSchedulers, Observable<List<RedditItem>>> loadRedditItemsFunc
            = (homeView, homeModel, mvlSchedulers) -> Observable.just(null)
            .doOnNext(notification -> homeView.setLoading(true))

            .switchMap(aVoid -> homeModel.getSavedRedditListing()
                    .concatWith(homeModel.postsForAll()
                            .subscribeOn(mvlSchedulers.network())
                            .observeOn(mvlSchedulers.mainThread())
                            .map(homeModel::saveRedditListing)))

            .map(redditData -> redditData.data.children)
            .flatMap(redditItems -> Observable.from(redditItems)
                    .map(child -> child.data)
                    .filter(redditItem -> !redditItem.over18)
                    .toList())

            .observeOn(mvlSchedulers.mainThread())
            .doOnNext(notification -> homeView.setLoading(false));

    private static final Func3<HomeView, HomeModel, AndroidSchedulers, Subscription> loadPostsFunc
            = (homeView, homeModel, mvlSchedulers) -> Observable.just(null)
            .mergeWith(homeView.refreshMenuClick())
            .mergeWith(homeView.errorRetryClick())
            .flatMap(aVoid -> loadRedditItemsFunc.call(homeView, homeModel, mvlSchedulers))
            .doOnError(Throwable::printStackTrace)
            .subscribe(homeView::setRedditItems, throwable -> homeView.showError());

    private static final Func2<HomeView, HomeModel, Subscription> listItemItemClicksFunc = (homeView, homeModel)
            -> homeView.listItemClicks().subscribe(homeModel::startDetailActivity);

}


