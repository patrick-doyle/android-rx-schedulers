package com.twistedequations.reddit.rsvp.screens.home.dagger;

import com.squareup.picasso.Picasso;
import com.twistedequations.mvl.rx.AndroidSchedulers;
import com.twistedequations.reddit.rsvp.network.reddit.RedditService;
import com.twistedequations.reddit.rsvp.screens.home.HomeActivity;
import com.twistedequations.reddit.rsvp.screens.home.mvl.DefaultHomeView;
import com.twistedequations.reddit.rsvp.screens.home.mvl.HomeLifecycle;
import com.twistedequations.reddit.rsvp.screens.home.mvl.HomeModel;
import com.twistedequations.reddit.rsvp.screens.home.mvl.HomeView;

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
        public HomeLifecycle homeLifecycle(HomeView homeView, HomeModel homeModel, AndroidSchedulers androidSchedulers) {
            return new HomeLifecycle(homeView, homeModel, androidSchedulers);
        }

        @Provides
        @HomeScope
        public HomeModel homeModel(RedditService redditService) {
            return new HomeModel(redditService, homeActivity);
        }
    }
