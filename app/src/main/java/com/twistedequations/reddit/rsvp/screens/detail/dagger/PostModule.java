package com.twistedequations.reddit.rsvp.screens.detail.dagger;

import com.squareup.picasso.Picasso;
import com.twistedequations.mvl.rx.AndroidSchedulers;
import com.twistedequations.reddit.rsvp.network.reddit.RedditService;
import com.twistedequations.reddit.rsvp.screens.detail.PostActivity;
import com.twistedequations.reddit.rsvp.screens.detail.mvl.PostActivityView;
import com.twistedequations.reddit.rsvp.screens.detail.mvl.PostLifecycle;
import com.twistedequations.reddit.rsvp.screens.detail.mvl.PostModel;

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
    public PostLifecycle homeLifecycle(PostActivityView view, PostModel model, AndroidSchedulers androidSchedulers) {
        return new PostLifecycle(view, model, androidSchedulers);
    }

    @Provides
    @PostScope
    public PostModel homeModel(RedditService redditService) {
        return new PostModel(redditService, activity);
    }
}
