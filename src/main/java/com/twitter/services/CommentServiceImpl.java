package com.twitter.services;

import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.CommentRepoImpl;

import java.util.List;

public class CommentServiceImpl extends BaseServiceImpl<Comment, CommentRepoImpl> implements CommentService {
    private final CommentRepoImpl commentRepo = new CommentRepoImpl();

    public List<Comment> findAllByTwit(Twit twit) {
        return commentRepo.readAllByTwit(twit);
    }

    public List<Comment> findAllByUser(User user) {
        return commentRepo.readAllByUser(user);
    }

    public void truncate() {
        commentRepo.truncate();
    }

}
