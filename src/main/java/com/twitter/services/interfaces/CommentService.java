package com.twitter.services.interfaces;

import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;

import java.util.List;

public interface CommentService extends BaseService<Comment> {
    List<Comment> findAllByTwit(Twit twit);
    List<Comment> findAllByUser(User user);
    void delete(User user);
}
