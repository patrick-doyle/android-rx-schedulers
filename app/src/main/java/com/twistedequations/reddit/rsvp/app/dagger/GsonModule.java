package com.twistedequations.reddit.rsvp.app.dagger;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {

    @AppScope
    @Provides
    public Gson gson() {
        GsonBuilder builder = new GsonBuilder();
        Converters.registerAll(builder);
        return builder.create();
    }
}
