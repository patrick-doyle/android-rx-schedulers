package com.twistedequations.reddit.rsvp.app.dagger;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.twistedequations.rx.AndroidRxSchedulers;
import com.twistedequations.reddit.rsvp.network.reddit.RedditService;

import dagger.Component;

@AppScope
@Component(modules = {AppModule.class, NetworkModule.class, RestServiceModule.class, GsonModule.class, RxModule.class})
public interface RedditRsvpComponent {

    Context context();

    Picasso picasso();

    AndroidRxSchedulers rxSchedulers();

    RedditService redditService();
}
