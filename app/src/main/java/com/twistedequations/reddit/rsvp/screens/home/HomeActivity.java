package com.twistedequations.reddit.rsvp.screens.home;

import com.twistedequations.mvl.MVLActivity;
import com.twistedequations.reddit.rsvp.app.RsvpApplication;
import com.twistedequations.reddit.rsvp.screens.home.dagger.DaggerHomeActivityComponent;
import com.twistedequations.reddit.rsvp.screens.home.dagger.HomeModule;
import com.twistedequations.reddit.rsvp.screens.home.mvp.HomeLifecycle;
import com.twistedequations.reddit.rsvp.screens.home.mvp.HomeView;

import javax.inject.Inject;

public class HomeActivity extends MVLActivity {

  @Inject
  HomeView homeView;

  @Inject
  HomeLifecycle homeLifecycle;

  @Override
  protected void onRegisterLifecycles() {

    DaggerHomeActivityComponent.builder()
            .redditRsvpComponent(RsvpApplication.get(this).component())
            .homeModule(new HomeModule(this))
            .build().inject(this);

    setContentView(homeView.getView());
    registerLifecycle(homeLifecycle);
  }
}
