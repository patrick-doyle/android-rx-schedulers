package com.twistedequations.reddit.rsvp.screens.home.mvl;

import com.twistedequations.reddit.rsvp.network.reddit.RedditService;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditItem;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditListing;
import com.twistedequations.reddit.rsvp.screens.detail.PostActivity;
import com.twistedequations.reddit.rsvp.screens.home.HomeActivity;
import com.twistedequations.rxstate.RxSaveState;

import rx.Observable;

public class HomeModel {

    private final RedditService redditService;
    private final HomeActivity homeActivity;

    public HomeModel(RedditService redditService, HomeActivity homeActivity) {
        this.redditService = redditService;
        this.homeActivity = homeActivity;
    }

    public Observable<RedditListing> getSavedRedditListing() {
        return RxSaveState.getSavedState(homeActivity)
                .map(bundle -> bundle.getParcelable("reddit_listing"));
    }

    public Observable<RedditListing> postsForAll() {
        return redditService.postsForAll();
    }

    public RedditListing saveRedditListing(RedditListing redditListing) {
        RxSaveState.updateSaveState(homeActivity, bundle -> bundle.putParcelable("reddit_listing", redditListing));
        return redditListing;
    }

    public void startDetailActivity(RedditItem item) {
        PostActivity.start(homeActivity, item);
    }
}
