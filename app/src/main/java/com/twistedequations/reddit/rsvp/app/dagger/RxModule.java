package com.twistedequations.reddit.rsvp.app.dagger;

import com.twistedequations.mvl.rx.AndroidRxSchedulers;
import com.twistedequations.mvl.rx.RxSchedulers;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {

    @AppScope
    @Provides
    public RxSchedulers rxSchedulers() {
        return new AndroidRxSchedulers();
    }
}
