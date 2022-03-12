package com.concurrencyMaktab.services;

import com.concurrencyMaktab.models.Comment;
import com.concurrencyMaktab.models.Twit;
import com.concurrencyMaktab.models.User;
import com.concurrencyMaktab.repos.CommentRepo;

import java.sql.Connection;
import java.util.List;

public class CommentService extends Service<Comment> {
    public CommentService(Connection connection) {
        super(connection);
    }
    private final CommentRepo commentRepo = new CommentRepo(getConnection());

    public Integer newComment(Comment comment){
        return commentRepo.ins(comment);
    }
    public Integer deleteComment(Integer commentId) {
        return commentRepo.delete(commentId);
    }
    public Integer editComment(Comment comment) {
        return commentRepo.update(comment);
    }
    public List<Comment> findAllByTwit(Twit twit){
        return commentRepo.readAllByTwit(twit);
    }
    public List<Comment> findAllByUser(User user){
        return commentRepo.readAllByUser(user);
    }
}
