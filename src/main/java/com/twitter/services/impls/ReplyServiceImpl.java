package com.twitter.services.impls;

import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Reply;
import com.twitter.models.user.User;
import com.twitter.repos.interfaces.ReplyRepo;
import com.twitter.services.interfaces.ReplyService;

import java.util.List;

public class ReplyServiceImpl extends BaseServiceImpl<Reply, ReplyRepo>
        implements ReplyService {

    public ReplyServiceImpl(ReplyRepo repository) {
        super(repository);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public List<Reply> findAllByUser(User user) {
        return repository.readAllByUser(user);
    }

    @Override
    public List<Reply> findAllByComment(Comment comment) {
        return repository.readAllByComment(comment);
    }
}
