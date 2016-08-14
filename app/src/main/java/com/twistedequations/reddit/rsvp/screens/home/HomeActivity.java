package com.twistedequations.reddit.rsvp.screens.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.twistedequations.reddit.rsvp.app.RsvpApplication;
import com.twistedequations.reddit.rsvp.screens.home.dagger.DaggerHomeActivityComponent;
import com.twistedequations.reddit.rsvp.screens.home.dagger.HomeModule;
import com.twistedequations.reddit.rsvp.screens.home.mvl.HomePresenter;
import com.twistedequations.reddit.rsvp.screens.home.mvl.HomeView;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {

  @Inject
  HomeView homeView;

  @Inject
  HomePresenter presenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    DaggerHomeActivityComponent.builder()
            .redditRsvpComponent(RsvpApplication.get(this).component())
            .homeModule(new HomeModule(this))
            .build().inject(this);

    setContentView(homeView.getView());
    presenter.onCreate();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }
}
