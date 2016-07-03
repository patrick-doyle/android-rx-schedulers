package com.twistedequations.reddit.rsvp.app.dagger;

import com.twistedequations.mvl.rx.AndroidMVLSchedulers;
import com.twistedequations.mvl.rx.MVLSchedulers;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {

    @AppScope
    @Provides
    public MVLSchedulers rxSchedulers() {
        return new AndroidMVLSchedulers();
    }
}
