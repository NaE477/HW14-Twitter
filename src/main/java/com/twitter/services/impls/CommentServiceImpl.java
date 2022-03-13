package com.twitter.services.impls;

import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.impls.CommentRepoImpl;
import com.twitter.services.interfaces.CommentService;

import java.util.List;

public class CommentServiceImpl extends BaseServiceImpl<Comment, CommentRepoImpl> implements CommentService {
    public CommentServiceImpl(CommentRepoImpl repo) {
        super(repo);
    }

    public List<Comment> findAllByTwit(Twit twit) {
        return repo.readAllByTwit(twit);
    }

    public List<Comment> findAllByUser(User user) {
        return repo.readAllByUser(user);
    }

    public void truncate() {
        repo.truncate();
    }

}
