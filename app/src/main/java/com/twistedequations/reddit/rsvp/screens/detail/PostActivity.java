package com.twistedequations.reddit.rsvp.screens.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.twistedequations.reddit.rsvp.app.RsvpApplication;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditItem;
import com.twistedequations.reddit.rsvp.screens.detail.dagger.DaggerPostActivityComponent;
import com.twistedequations.reddit.rsvp.screens.detail.dagger.PostModule;
import com.twistedequations.reddit.rsvp.screens.detail.mvl.PostActivityView;
import com.twistedequations.reddit.rsvp.screens.detail.mvl.PostLifecycle;

import javax.inject.Inject;

public class PostActivity extends AppCompatActivity {

    public static final String REDDIT_ITEM_KEY = "redditItem";

    public static void start(Context context, RedditItem redditItem) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra(REDDIT_ITEM_KEY, redditItem);
        context.startActivity(intent);
    }

    @Inject
    PostActivityView postActivityView;

    @Inject
    PostLifecycle postLifecycle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerPostActivityComponent.builder().redditRsvpComponent(RsvpApplication.get(this).component())
                .postModule(new PostModule(this))
                .build().inject(this);

        //registerLifecycle(postLifecycle);
        setContentView(postActivityView.getView());
    }
}
