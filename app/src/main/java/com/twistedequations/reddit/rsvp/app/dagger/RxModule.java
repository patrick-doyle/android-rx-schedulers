package com.twistedequations.reddit.rsvp.app.dagger;

import com.twistedequations.mvl.rx.DefaultAndroidSchedulers;
import com.twistedequations.mvl.rx.AndroidSchedulers;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {

    @AppScope
    @Provides
    public AndroidSchedulers rxSchedulers() {
        return new DefaultAndroidSchedulers();
    }
}
