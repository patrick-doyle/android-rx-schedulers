package com.twistedequations.reddit.rsvp.app.dagger;

import com.twistedequations.rx.AndroidRxSchedulers;
import com.twistedequations.rx.DefaultAndroidRxSchedulers;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {

    @AppScope
    @Provides
    public AndroidRxSchedulers rxSchedulers() {
        return new DefaultAndroidRxSchedulers();
    }
}
