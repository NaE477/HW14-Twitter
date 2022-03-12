package com.concurrencyMaktab.repos;

import com.concurrencyMaktab.models.Comment;
import com.concurrencyMaktab.models.Twit;
import com.concurrencyMaktab.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentRepo extends BaseRepo<Comment> implements Repo<Comment> {
    public CommentRepo(Connection connection) {
        super(connection);
    }

    @Override
    protected Comment mapTo(ResultSet rs) {
        try {
            if (rs.next()) {
                return new Comment(
                        rs.getInt("comment_id")
                        , rs.getString("comment_content")
                        , new Twit(
                        rs.getInt("twit_id"),
                        rs.getString("twit_content"),
                        new User(
                                rs.getInt("user_id")
                                , rs.getString("firstname")
                                , rs.getString("lastname")
                                , rs.getString("username")
                                , rs.getString("password")
                                , rs.getString("email")
                        ),
                        rs.getTimestamp("twit_date")
                ),
                        new User(
                                rs.getInt("user_id")
                                , rs.getString("firstname")
                                , rs.getString("lastname")
                                , rs.getString("username")
                                , rs.getString("password")
                                , rs.getString("email")
                        ), rs.getTimestamp("comment_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected List<Comment> mapToList(ResultSet rs) {
        List<Comment> comments = new ArrayList<>();
        try {
            while (rs.next()) {
                comments.add(
                        new Comment(
                                rs.getInt("comment_id")
                                , rs.getString("comment_content")
                                , new Twit(
                                rs.getInt("twit_id"),
                                rs.getString("twit_content"),
                                new User(
                                        rs.getInt("user_id")
                                        , rs.getString("firstname")
                                        , rs.getString("lastname")
                                        , rs.getString("username")
                                        , rs.getString("password")
                                        , rs.getString("email")
                                ),rs.getTimestamp("twit_date")
                        ),
                                new User(
                                        rs.getInt("user_id")
                                        , rs.getString("firstname")
                                        , rs.getString("lastname")
                                        , rs.getString("username")
                                        , rs.getString("password")
                                        , rs.getString("email")
                                ), rs.getTimestamp("comment_date")
                        )
                );
            }
            return comments;
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    @Override
    public Integer ins(Comment comment) {
        String insStmt = "INSERT INTO comments (comment_content, twit_id, user_id,comment_time) " +
                "VALUES (?,?,?,NOW()) RETURNING comment_id;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(insStmt);
            ps.setString(1,comment.getContent());
            ps.setInt(2,comment.getOwnerTwit().getId());
            ps.setInt(3,comment.getUser().getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("comment_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    @Override
    public Comment read(Integer id) {
        String slcStmt = "SELECT * FROM comments" +
                " INNER JOIN twits t on t.twit_id = comments.twit_id " +
                "INNER JOIN users u on u.user_id = comments.user_id" +
                "  WHERE comment_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcStmt);
            ps.setInt(1,id);
            return mapTo(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    @Override
    public List<Comment> readAll() {
        String slcStmt = "SELECT * FROM comments " +
                " INNER JOIN twits t on t.twit_id = comments.twit_id " +
                " INNER JOIN users u on u.user_id = comments.user_id";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcStmt);
            return mapToList(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    public List<Comment> readAllByTwit(Twit twit){
        String slcStmt = "SELECT * FROM comments " +
                " INNER JOIN twits t on t.twit_id = comments.twit_id" +
                " INNER JOIN users u on u.user_id = comments.user_id" +
                " WHERE t.twit_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcStmt);
            ps.setInt(1,twit.getId());
            return mapToList(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }return null;
    }

    public List<Comment> readAllByUser(User user){
        String slcStmt = "SELECT * FROM comments " +
                " INNER JOIN twits t on t.twit_id = comments.twit_id" +
                " INNER JOIN users u on u.user_id = comments.user_id" +
                " WHERE u.user_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcStmt);
            ps.setInt(1,user.getId());
            return mapToList(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }return null;
    }

    @Override
    public Integer update(Comment comment) {
        String upStmt = "UPDATE comments SET comment_content = ? Where comment_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(upStmt);
            ps.setString(1,comment.getContent());
            ps.setInt(2,comment.getId());
            ps.executeUpdate();
            return comment.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    @Override
    public Integer delete(Integer id) {
        String delStmt = "DELETE FROM comments WHERE comment_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(delStmt);
            ps.setInt(1,id);
            ps.executeUpdate();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    public void truncate() {
        String truncateStmt = "TRUNCATE comments cascade ;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(truncateStmt);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
