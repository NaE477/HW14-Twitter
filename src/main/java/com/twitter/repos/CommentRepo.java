package com.twitter.repos;

import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;

import java.util.List;

public interface CommentRepo extends BaseRepository<Comment> {
    List<Comment> readAllByTwit(Twit twit);
    List<Comment> readAllByUser(User user);
}
