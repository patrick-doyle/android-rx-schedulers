package com.twistedequations.reddit.rsvp.screens.home.mvl;

import android.view.View;

import com.twistedequations.reddit.rsvp.network.reddit.models.RedditItem;

import java.util.List;

import rx.Observable;

public interface HomeView {

    Observable<Void> refreshMenuClick();

    Observable<Void> errorRetryClick();

    Observable<RedditItem> listItemClicks();

    void setRedditItems(List<RedditItem> items);

    void setLoading(boolean loading);

    void showError();

    View getView();

}




