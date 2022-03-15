package com.twitter.services.impls;

import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.impls.CommentRepoImpl;
import com.twitter.repos.interfaces.CommentRepo;
import com.twitter.services.interfaces.CommentService;
import org.hibernate.SessionFactory;

import java.util.List;

public class CommentServiceImpl extends BaseServiceImpl<Comment, CommentRepo>
        implements CommentService {

    public CommentServiceImpl(CommentRepo repository) {
        super(repository);
    }

    public List<Comment> findAllByTwit(Twit twit) {
        return repository.readAllByTwit(twit);
    }

    public List<Comment> findAllByUser(User user) {
        return repository.readAllByUser(user);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    public void truncate() {
        repository.truncate();
    }
}
