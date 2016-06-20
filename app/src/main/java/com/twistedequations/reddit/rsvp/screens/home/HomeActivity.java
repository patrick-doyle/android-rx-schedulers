package com.twistedequations.reddit.rsvp.screens.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jakewharton.rxbinding.view.RxMenuItem;
import com.twistedequations.reddit.rsvp.R;
import com.twistedequations.reddit.rsvp.app.RsvpApplication;
import com.twistedequations.reddit.rsvp.network.reddit.RedditService;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditItem;
import com.twistedequations.reddit.rsvp.rx.RxSchedulers;
import com.twistedequations.reddit.rsvp.screens.detail.DetailActivity;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class HomeActivity extends AppCompatActivity {

  private final CompositeSubscription compositeSubscription = new CompositeSubscription();
  private PostListAdapter postListAdapter;
  private ProgressDialog progressDialog;
  private RxSchedulers rxSchedulers;
  private MenuItem refreshMenuItem;
  private RedditService redditService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    RsvpApplication rsvpApplication = RsvpApplication.get(this);
    rxSchedulers = rsvpApplication.getRxSchedulers();
    redditService = rsvpApplication.getRedditService();
    postListAdapter = new PostListAdapter(this, rsvpApplication.getPicasso());

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.post_listview);
    recyclerView.setAdapter(postListAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Loading");

    compositeSubscription.add(loadPosts());
    compositeSubscription.add(observableItemClicks());
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    compositeSubscription.clear();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_home, menu);
    refreshMenuItem = menu.findItem(R.id.menu_refresh);
    compositeSubscription.add(menuClicks());
    return true;
  }

  private Subscription loadPosts() {
    return Observable.just(null)
        .flatMap(aVoid -> loadRedditItems())
        .subscribe(redditItems -> postListAdapter.setRedditItems(redditItems), throwable -> showErrorAlert());
  }

  private Observable<List<RedditItem>> loadRedditItems() {
    return Observable.just(null)
        .doOnEach(notification -> progressDialog.show())
        .observeOn(rxSchedulers.network())
        .switchMap((aVoid) -> redditService.postsForAll())
        .map(redditData -> redditData.data.children)
        .flatMap(Observable::from)
        .map(child -> child.data)
        .toList()
        .doOnError(Throwable::printStackTrace)
        .observeOn(rxSchedulers.mainThread())
        .doOnEach(notification -> progressDialog.hide());
  }

  private Subscription menuClicks() {
    return RxMenuItem.clicks(refreshMenuItem)
        .flatMap(aVoid -> loadRedditItems())
        .subscribe(redditItems -> postListAdapter.setRedditItems(redditItems), throwable -> showErrorAlert());
  }

  private Subscription observableItemClicks() {
    return postListAdapter.observeClicks()
        .map(position -> postListAdapter.getRedditItem(position))
        .subscribe(item -> DetailActivity.start(this, item));
  }

  private void showErrorAlert() {
    progressDialog.hide();
    AlertDialog alertDialog = new AlertDialog.Builder(this)
        .setTitle("Error Loading reddit posts")
        .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss())
        .setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss())
        .create();
    alertDialog.show();
  }
}
