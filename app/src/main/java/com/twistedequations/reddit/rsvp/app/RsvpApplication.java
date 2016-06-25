package com.twistedequations.reddit.rsvp.app;

import android.app.Activity;
import android.app.Application;
import android.app.Service;

import com.twistedequations.reddit.rsvp.app.dagger.AppModule;
import com.twistedequations.reddit.rsvp.app.dagger.DaggerRedditRsvpComponent;
import com.twistedequations.reddit.rsvp.app.dagger.RedditRsvpComponent;

public class RsvpApplication extends Application {

  public static RsvpApplication get(Activity activity) {
    return (RsvpApplication) activity.getApplication();
  }

  public static RsvpApplication get(Service service) {
    return (RsvpApplication) service.getApplication();
  }

  private RedditRsvpComponent redditRsvpComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    redditRsvpComponent = DaggerRedditRsvpComponent.builder()
            .appModule(new AppModule(this))
            .build();

  }

  public RedditRsvpComponent component() {
    return redditRsvpComponent;
  }
}
