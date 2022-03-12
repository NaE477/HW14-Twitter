package com.concurrencyMaktab.repos;

import com.concurrencyMaktab.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersRepo extends BaseRepo<User> implements Repo<User> {

    public UsersRepo(Connection connection) {
        super(connection);
    }

    @Override
    protected User mapTo(ResultSet rs) {
        try {
            if(rs.next()) {
                return new User(
                        rs.getInt("user_id")
                        , rs.getString("firstname")
                        , rs.getString("lastname")
                        , rs.getString("username")
                        , rs.getString("password")
                        , rs.getString("email")
                );
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected List<User> mapToList(ResultSet rs) {
        List<User> users = new ArrayList<>();
        try {
            while (rs.next()) {
                users.add(new User(
                                rs.getInt("user_id")
                                , rs.getString("firstname")
                                , rs.getString("lastname")
                                , rs.getString("username")
                                , rs.getString("password")
                                , rs.getString("email")
                        )
                );
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer ins(User user) {
        String insStmt = "INSERT INTO users (firstname, lastname, username, password, email) " +
                "VALUES (?,?,?,?,?) RETURNING user_id;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(insStmt);
            ps.setString(1,user.getFirstname());
            ps.setString(2,user.getLastname());
            ps.setString(3,user.getUsername());
            ps.setString(4,user.getPassword());
            ps.setString(5,user.getEmail());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public User read(Integer id) {
        String slcStmt = "SELECT * FROM users WHERE user_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcStmt);
            ps.setInt(1,id) ;
            return mapTo(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public User read(String username){
        String slcStmt = "SELECT * FROM users WHERE username = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcStmt);
            ps.setString(1,username);
            return mapTo(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public User readByEmail(String email){
        String slcStmt = "SELECT * FROM users WHERE email = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcStmt);
            ps.setString(1,email);
            return mapTo(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> search(String username){
        String slcStmt = "SELECT * FROM users WHERE username LIKE ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcStmt);
            ps.setString(1,username + "%");
            return mapToList(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> readAll() {
        String slcStmt = "SELECT * FROM users;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcStmt);
            return mapToList(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(User user) {
        String upStmt = "UPDATE users SET firstname = ?, lastname = ?, username = ?, password = ?, email = ? WHERE user_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(upStmt);
            ps.setString(1,user.getFirstname());
            ps.setString(2,user.getLastname());
            ps.setString(3,user.getUsername());
            ps.setString(4,user.getPassword());
            ps.setString(5,user.getEmail());
            ps.setInt(6,user.getId());
            ps.executeUpdate();
            return user.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        String delStmt = "DELETE FROM users WHERE user_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(delStmt);
            ps.setInt(1,id);
            ps.executeUpdate();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }return null;
    }

    public void truncate() {
        String truncateStmt = "TRUNCATE users cascade ;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(truncateStmt);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
