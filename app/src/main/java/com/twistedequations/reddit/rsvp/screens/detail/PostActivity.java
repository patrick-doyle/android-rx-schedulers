package com.twistedequations.reddit.rsvp.screens.detail;

import android.content.Context;
import android.content.Intent;

import com.twistedequations.mvl.MVLCompatActivity;
import com.twistedequations.reddit.rsvp.app.RsvpApplication;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditItem;
import com.twistedequations.reddit.rsvp.screens.detail.dagger.DaggerPostActivityComponent;
import com.twistedequations.reddit.rsvp.screens.detail.dagger.PostModule;
import com.twistedequations.reddit.rsvp.screens.detail.mvp.PostActivityView;
import com.twistedequations.reddit.rsvp.screens.detail.mvp.PostLifecycle;

import javax.inject.Inject;

public class PostActivity extends MVLCompatActivity {

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
    public void main() {
        super.main();

        DaggerPostActivityComponent.builder().redditRsvpComponent(RsvpApplication.get(this).component())
                .postModule(new PostModule(this))
                .build().inject(this);

        registerLifecycle(postLifecycle);
        setContentView(postActivityView.getView());
    }
}
