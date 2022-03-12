package com.concurrencyMaktab.services;

import com.concurrencyMaktab.models.User;
import com.concurrencyMaktab.repos.UsersRepo;

import java.sql.Connection;
import java.util.List;

public class UserService extends Service<User> {
    public UserService(Connection connection) {
        super(connection);
    }
    private final UsersRepo usersRepo = new UsersRepo(super.getConnection());

    public Integer singUp(User user){
        return usersRepo.ins(user);
    }

    public User findByUsername(String username){
        return usersRepo.read(username);
    }

    public User findByEmail(String email){
        return usersRepo.readByEmail(email);
    }

    public Boolean login(String username,String password){
        return findByUsername(username) != null && findByUsername(username).getPassword().equals(password);
    }

    public Integer update(User user){
        return usersRepo.update(user);
    }

    public List<User> searchUsername(String username){
        return usersRepo.search(username);
    }

    public void deleteAcc(Integer id){
        usersRepo.delete(id);
    }

    public void truncate() {usersRepo.truncate();}
}
