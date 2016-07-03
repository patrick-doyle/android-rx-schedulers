package com.twistedequations.reddit.rsvp.screens.home.mvp;

import com.twistedequations.mvl.view.MVLView;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditItem;

import java.util.List;

import rx.Observable;

public interface HomeView extends MVLView {

    Observable<Void> refreshMenuClick();

    Observable<Void> errorRetryClick();

    Observable<RedditItem> listItemClicks();

    void setRedditItems(List<RedditItem> items);

    void setLoading(boolean loading);

    void showError();

}




