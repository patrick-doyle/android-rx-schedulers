package com.twistedequations.reddit.rsvp.screens.detail.dagger;

import com.squareup.picasso.Picasso;
import com.twistedequations.reddit.rsvp.network.reddit.RedditService;
import com.twistedequations.mvl.rx.RxSchedulers;
import com.twistedequations.reddit.rsvp.screens.detail.PostActivity;
import com.twistedequations.reddit.rsvp.screens.detail.mvp.PostActivityView;
import com.twistedequations.reddit.rsvp.screens.detail.mvp.PostLifecycle;
import com.twistedequations.reddit.rsvp.screens.detail.mvp.PostModel;

import dagger.Module;
import dagger.Provides;

@Module
public class PostModule {

    private final PostActivity activity;

    public PostModule(PostActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PostScope
    public PostActivityView homeView(Picasso picasso) {
        return new PostActivityView(activity, picasso);
    }

    @Provides
    @PostScope
    public PostLifecycle homeLifecycle(PostActivityView view, PostModel model,
                                       RxSchedulers rxSchedulers) {
        return new PostLifecycle(view, model, rxSchedulers);
    }

    @Provides
    @PostScope
    public PostModel homeModel(RedditService redditService) {
        return new PostModel(redditService, activity);
    }
}
