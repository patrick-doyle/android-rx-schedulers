package com.twistedequations.reddit.rsvp.screens.detail.dagger;

import com.twistedequations.reddit.rsvp.app.dagger.RedditRsvpComponent;
import com.twistedequations.reddit.rsvp.screens.detail.PostActivity;

import dagger.Component;

@PostScope
@Component(modules = PostModule.class, dependencies = RedditRsvpComponent.class)
public interface PostActivityComponent {

    void inject(PostActivity postActivity);
}
