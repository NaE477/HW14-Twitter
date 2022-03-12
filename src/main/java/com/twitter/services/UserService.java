package com.twitter.services;

import com.twitter.models.user.User;
import com.twitter.repos.UsersRepoImpl;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserService extends BaseServiceImpl {
    private final UsersRepoImpl usersRepo;
    public UserService(SessionFactory sessionFactory) {
        super(sessionFactory);
        usersRepo = new UsersRepoImpl(sessionFactory);
    }

    public User singUp(User user){
        return usersRepo.ins(user);
    }

    public User findByUsername(String username){
        return usersRepo.readByUsername(username);
    }

    public User findByEmail(String email){
        return usersRepo.readByEmail(email);
    }

    public Boolean login(String username,String password){
        return findByUsername(username) != null && findByUsername(username).getPassword().equals(password);
    }

    public User update(User user){
        return usersRepo.update(user);
    }

    public List<User> searchUsername(String username){
        return usersRepo.searchUsername(username);
    }

    public void deleteUser(User user){
        usersRepo.delete(user);
    }

    public void truncate() {usersRepo.truncate();}
}
