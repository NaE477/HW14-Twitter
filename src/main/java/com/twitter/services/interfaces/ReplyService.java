package com.twitter.services.interfaces;

import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Reply;
import com.twitter.models.user.User;

import java.util.Arrays;
import java.util.List;

public interface ReplyService extends BaseService<Reply> {
    void delete(User user);

    List<Reply> findAllByUser(User user);

    List<Reply> findAllByComment(Comment comment);
}
