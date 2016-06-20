package com.twistedequations.reddit.rsvp.app;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.util.Log;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.twistedequations.reddit.rsvp.network.reddit.RedditService;
import com.twistedequations.reddit.rsvp.rx.DefaultSchedulers;
import com.twistedequations.reddit.rsvp.rx.RxSchedulers;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RsvpApplication extends Application {

  public static RsvpApplication get(Activity activity) {
    return (RsvpApplication) activity.getApplication();
  }

  public static RsvpApplication get(Service service) {
    return (RsvpApplication) service.getApplication();
  }

  private RedditService redditService;
  private RxSchedulers rxSchedulers = new DefaultSchedulers();
  private Gson gson;
  private Picasso picasso;

  @Override
  public void onCreate() {
    super.onCreate();

    GsonBuilder builder = new GsonBuilder();
    Converters.registerAll(builder);
    gson = builder.create();

    final HttpLoggingInterceptor loggingInterceptor =
        new HttpLoggingInterceptor(message -> Log.i("HttpLoggingInterceptor", message))
        .setLevel(HttpLoggingInterceptor.Level.BASIC);

    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .addNetworkInterceptor(loggingInterceptor)
        .build();

    final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://www.reddit.com/")
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(rxSchedulers.network()))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build();

    redditService = retrofit.create(RedditService.class);

    picasso = new Picasso.Builder(this)
        .downloader(new OkHttp3Downloader(okHttpClient))
        .build();
  }

  public RedditService getRedditService() {
    return redditService;
  }

  public RxSchedulers getRxSchedulers() {
    return rxSchedulers;
  }

  public Picasso getPicasso() {
    return picasso;
  }
}
