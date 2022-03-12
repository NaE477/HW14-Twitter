package com.twitter.services;

import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.CommentRepoImpl;
import org.hibernate.SessionFactory;

import java.util.List;

public class CommentService extends BaseServiceImpl<Comment> {
    private final CommentRepoImpl commentRepo;
    public CommentService(SessionFactory sessionFactory) {
        super(sessionFactory);
        commentRepo = new CommentRepoImpl(sessionFactory);
    }

    public Comment newComment(Comment comment){
        return commentRepo.ins(comment);
    }
    public void deleteComment(Comment comment) {
        commentRepo.delete(comment);
    }
    public Comment editComment(Comment comment) {
        return commentRepo.update(comment);
    }
    public List<Comment> findAllByTwit(Twit twit){
        return commentRepo.readAllByTwit(twit);
    }
    public List<Comment> findAllByUser(User user){
        return commentRepo.readAllByUser(user);
    }
}
