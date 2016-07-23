package com.twistedequations.reddit.rsvp.screens.detail.mvl;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.squareup.picasso.Picasso;
import com.twistedequations.mvl.view.MVLView;
import com.twistedequations.reddit.rsvp.R;
import com.twistedequations.reddit.rsvp.network.reddit.models.RedditItem;
import com.twistedequations.reddit.rsvp.screens.detail.PostActivity;
import com.twistedequations.reddit.rsvp.screens.detail.mvl.view.CommentListAdapter;
import com.twistedequations.reddit.rsvp.screens.detail.mvl.view.RedditPostView;

import java.util.List;

public class PostActivityView extends FrameLayout implements MVLView {

    private final Picasso picasso;
    private RedditPostView postView;
    private ListView listView;
    private CommentListAdapter commentListAdapter;

    public PostActivityView(PostActivity context, Picasso picasso) {
        super(context);
        this.picasso = picasso;
        inflate(context, R.layout.activity_post, this);

        postView = new RedditPostView(context);

        listView = (ListView) findViewById(R.id.comments_list_view);
        commentListAdapter = new CommentListAdapter(context);
        listView.addHeaderView(postView, null, false);
        listView.setAdapter(commentListAdapter);

    }

    public void setRedditItem(RedditItem redditItem) {
        postView.setRedditItem(redditItem, picasso);
    }


    public void setCommentList(List<RedditItem> comments) {
        commentListAdapter.setRedditItems(comments);
    }

    @Override
    public View getView() {
        return this;
    }
}
