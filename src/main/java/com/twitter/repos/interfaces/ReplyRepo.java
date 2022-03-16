package com.twitter.repos.interfaces;

import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Reply;
import com.twitter.models.user.User;

import java.util.List;

public interface ReplyRepo extends BaseRepository<Reply> {
    void delete(User user);
    List<Reply> readAllByUser(User user);
    List<Reply> readAllByComment(Comment comment);
}
