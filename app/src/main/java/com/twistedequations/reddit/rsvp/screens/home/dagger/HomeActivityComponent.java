package com.twistedequations.reddit.rsvp.screens.home.dagger;

import com.twistedequations.reddit.rsvp.app.dagger.RedditRsvpComponent;
import com.twistedequations.reddit.rsvp.screens.home.HomeActivity;

import dagger.Component;

@HomeScope
@Component(modules = HomeModule.class, dependencies = RedditRsvpComponent.class)
public interface HomeActivityComponent {

    void inject(HomeActivity homeActivity);
}
