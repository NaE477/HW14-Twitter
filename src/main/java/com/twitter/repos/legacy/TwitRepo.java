package com.twitter.repos.legacy;

import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TwitRepo extends BaseRepo<Twit> implements Repo<Twit> {
    public TwitRepo(Connection connection) {
        super(connection);
    }

    @Override
    protected Twit mapTo(ResultSet rs) {
        try {
            if (rs.next()) {
                return new Twit(
                        rs.getInt(1),
                        rs.getString("content"),
                        new User(
                                rs.getInt("user_id")
                                , rs.getString("firstname")
                                , rs.getString("lastname")
                                , rs.getString("username")
                                , rs.getString("password")
                                , rs.getString("email")
                                , new HashSet<>()
                                , new HashSet<>()
                        )
                        , rs.getDate("twit_time")
                        , new HashSet<>()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected List<Twit> mapToList(ResultSet rs) {
        List<Twit> twits = new ArrayList<>();
        try {
            while (rs.next()) {
                twits.add(new Twit(
                        rs.getInt(1),
                        rs.getString("content"),
                        new User(
                                rs.getInt("user_id")
                                , rs.getString("firstname")
                                , rs.getString("lastname")
                                , rs.getString("username")
                                , rs.getString("password")
                                , rs.getString("email")
                                , null
                                , null
                        )
                        , rs.getDate("twit_time")
                        , null
                ));
            }
            return twits;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer ins(Twit twit) {
        String insStmt = "INSERT INTO twits (id,content, user_id,twit_time,delete_stat) " +
                "VALUES (100,?,?,NOW(),false) RETURNING id;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(insStmt);
            ps.setString(1, twit.getContent());
            ps.setInt(2, twit.getUser().getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Twit read(Integer id) {
        String slcStmt = "SELECT * FROM twits" +
                " INNER JOIN users u on u.id = twits.user_id" +
                " WHERE twits.id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcStmt);
            ps.setInt(1, id);
            return mapTo(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Twit> read(User user){
        String slcStmt = "SELECT * FROM twits " +
                "INNER JOIN users u on u.id = twits.user_id WHERE twits.user_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcStmt);
            ps.setInt(1,user.getId());
            return mapToList(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    @Override
    public List<Twit> readAll() {
        String slcStmt = "SELECT * FROM twits INNER JOIN users u on u.id = twits.user_id;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcStmt);
            return mapToList(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(Twit twit) {
        String upStmt = "UPDATE twits SET content = ? WHERE id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(upStmt);
            ps.setString(1,twit.getContent());
            ps.setInt(2,twit.getId());
            ps.executeUpdate();
            return twit.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    @Override
    public Integer delete(Integer id) {
        String delStmt = "DELETE FROM twits WHERE id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(delStmt);
            ps.setInt(1,id);
            ps.executeUpdate();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void truncate() {
        String truncateStmt = "truncate twitter_test.public.twits cascade ;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(truncateStmt);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
