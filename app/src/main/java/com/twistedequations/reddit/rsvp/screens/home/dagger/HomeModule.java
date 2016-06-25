package com.twistedequations.reddit.rsvp.screens.home.dagger;

import com.squareup.picasso.Picasso;
import com.twistedequations.mvl.rx.RxSchedulers;
import com.twistedequations.reddit.rsvp.network.reddit.RedditService;
import com.twistedequations.reddit.rsvp.screens.home.HomeActivity;
import com.twistedequations.reddit.rsvp.screens.home.mvp.DefaultHomeView;
import com.twistedequations.reddit.rsvp.screens.home.mvp.HomeLifecycle;
import com.twistedequations.reddit.rsvp.screens.home.mvp.HomeModel;
import com.twistedequations.reddit.rsvp.screens.home.mvp.HomeView;

import dagger.Module;
import dagger.Provides;

    @Module
    public class HomeModule {

        private final HomeActivity homeActivity;

        public HomeModule(HomeActivity homeActivity) {
            this.homeActivity = homeActivity;
        }

        @Provides
        @HomeScope
        public HomeView homeView(Picasso picasso) {
            return new DefaultHomeView(homeActivity, picasso);
        }

        @Provides
        @HomeScope
        public HomeLifecycle homeLifecycle(HomeView homeView, HomeModel homeModel,
                                           RxSchedulers rxSchedulers) {
            return new HomeLifecycle(homeView, homeModel, rxSchedulers);
        }

        @Provides
        @HomeScope
        public HomeModel homeModel(RedditService redditService) {
            return new HomeModel(redditService, homeActivity);
        }
    }
