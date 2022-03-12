package com.concurrencyMaktab.repos;

import com.concurrencyMaktab.models.Twit;
import com.concurrencyMaktab.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
                        rs.getInt("twit_id"),
                        rs.getString("twit_content"),
                        new User(
                                rs.getInt("user_id")
                                , rs.getString("firstname")
                                , rs.getString("lastname")
                                , rs.getString("username")
                                , rs.getString("password")
                                , rs.getString("email")
                        )
                        , rs.getTimestamp("twit_date")
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
                        rs.getInt("twit_id"),
                        rs.getString("twit_content"),
                        new User(
                                rs.getInt("user_id")
                                , rs.getString("firstname")
                                , rs.getString("lastname")
                                , rs.getString("username")
                                , rs.getString("password")
                                , rs.getString("email")

                        )
                        , rs.getTimestamp("twit_date")
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
        String insStmt = "INSERT INTO twits (twit_content, user_id,twit_time) " +
                "VALUES (?,?,NOW()) RETURNING twit_id;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(insStmt);
            ps.setString(1, twit.getContent());
            ps.setInt(2, twit.getUser().getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("twit_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Twit read(Integer id) {
        String slcStmt = "SELECT * FROM twits" +
                " INNER JOIN users u on u.user_id = twits.user_id" +
                " WHERE twit_id = ?;";
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
                "INNER JOIN users u on u.user_id = twits.user_id WHERE twits.user_id = ?;";
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
        String slcStmt = "SELECT * FROM twits INNER JOIN users u on u.user_id = twits.user_id;";
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
        String upStmt = "UPDATE twits SET twit_content = ? WHERE twit_id = ?;";
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
        String delStmt = "DELETE FROM twits WHERE twit_id = ?;";
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
        String truncateStmt = "TRUNCATE twits cascade ;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(truncateStmt);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
