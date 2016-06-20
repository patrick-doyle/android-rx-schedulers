package com.twistedequations.reddit.rsvp.screens.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twistedequations.reddit.rsvp.R;
import com.twistedequations.reddit.rsvp.app.RsvpApplication;
import com.twistedequations.reddit.rsvp.network.reddit.RedditService;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditItem;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditListing;
import com.twistedequations.reddit.rsvp.rx.RxSchedulers;
import com.twistedequations.reddit.rsvp.screens.home.ListItemViewPost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public class DetailActivity extends AppCompatActivity {

  private static final String REDDIT_ITEM_KEY = "redditItem";

  private final CompositeSubscription compositeSubscription = new CompositeSubscription();

  private Picasso picasso;
  private RedditService redditService;
  private RxSchedulers rxSchedulers;

  private PostView postView;
  private ListView listView;
  private CommentListAdapter commentListAdapter;

  public static void start(Context context, RedditItem redditItem) {
    Intent intent = new Intent(context, DetailActivity.class);
    intent.putExtra(REDDIT_ITEM_KEY, redditItem);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_detail);
    picasso = RsvpApplication.get(this).getPicasso();
    redditService = RsvpApplication.get(this).getRedditService();
    rxSchedulers = RsvpApplication.get(this).getRxSchedulers();

    postView = new PostView(this);

    listView = (ListView) findViewById(R.id.comments_list_view);
    commentListAdapter = new CommentListAdapter(this);
    listView.addHeaderView(postView, null, false);
    listView.setAdapter(commentListAdapter);

    RedditItem redditItem = getIntent().getParcelableExtra(REDDIT_ITEM_KEY);
    setRedditItem(redditItem);

    compositeSubscription.add(subscribeToComments(redditItem));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    compositeSubscription.clear();
  }

  private Subscription subscribeToComments(RedditItem redditItem) {

    final CompositeSubscription compositeSubscription = new CompositeSubscription();
    final PublishSubject<List<RedditListing>> listPublishSubject = PublishSubject.create();

    final Subscription postSub = listPublishSubject.map((redditListings -> redditListings.get(0)))
        .map(redditListing -> redditListing.data.children.get(0).data)
        .onErrorResumeNext(throwable -> Observable.empty())
        .observeOn(rxSchedulers.mainThread())
        .subscribe(this::setRedditItem);

    final Subscription commentSub = listPublishSubject.map((redditListings -> redditListings.get(1)))
        .map(redditListing -> redditListing.data.children)
        .onErrorResumeNext(throwable -> Observable.just(Collections.emptyList()))
        .flatMap(children -> Observable.from(children)
            .map(child -> child.data)
            .toList())
        .observeOn(rxSchedulers.mainThread())
        .subscribe(this::setRedditComments);

    final Subscription subscription = redditService.commentsForPost(redditItem.subreddit, redditItem.id)
        .subscribeOn(rxSchedulers.network())
        .subscribe(listPublishSubject);

    compositeSubscription.add(subscription);
    compositeSubscription.add(postSub);
    compositeSubscription.add(commentSub);
    return compositeSubscription;
  }

  private void setRedditComments(List<RedditItem> comments) {
    commentListAdapter.setRedditItems(comments);
  }

  private void setRedditItem(RedditItem redditItem) {
    postView.setRedditItem(redditItem, picasso);
  }
}
