package com.twitter.models.twits;

import com.twitter.repos.impls.CommentRepoImpl;
import com.twitter.repos.impls.TwitRepoImpl;
import com.twitter.services.impls.CommentServiceImpl;
import com.twitter.services.impls.TwitServiceImpl;
import com.twitter.services.interfaces.CommentService;
import com.twitter.services.interfaces.TwitService;

public class TwitProxy {
    private Twit twit;
    private Boolean commentsNecessity;

    private TwitProxy(){}

    public TwitProxy(Integer twitID,Boolean commentsNecessity) {
        this.commentsNecessity = commentsNecessity;
        TwitService twitService = new TwitServiceImpl(new TwitRepoImpl());
        twit = twitService.findById(twitID);
        if (twit != null) {
            if (this.commentsNecessity) {
                CommentService commentService = new CommentServiceImpl(new CommentRepoImpl());
                twit.setComments(commentService.findAllByTwit(twit));
            }
        }
    }

    public void initializeComments() {
        if (!commentsNecessity) {
            CommentService commentService = new CommentServiceImpl(new CommentRepoImpl());
            twit.setComments(commentService.findAllByTwit(twit));
            this.commentsNecessity = true;
        }
    }

    public Twit getTwit() {
        return twit;
    }
}
