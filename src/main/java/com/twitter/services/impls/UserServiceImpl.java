package com.twitter.services.impls;

import com.twitter.models.user.User;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.services.interfaces.UserService;

import java.util.List;

public class UserServiceImpl extends BaseServiceImpl<User,UsersRepoImpl> implements UserService {
    private final UsersRepoImpl usersRepo = new UsersRepoImpl();

    public User findByUsername(String username){
        return usersRepo.readByUsername(username);
    }

    public User findByEmail(String email){
        return usersRepo.readByEmail(email);
    }

    public Boolean login(String username, String password){
        return findByUsername(username) != null && findByUsername(username).getPassword().equals(password);
    }

    public List<User> searchUsername(String username){
        return usersRepo.searchUsername(username);
    }

    public void truncate() {usersRepo.truncate();}
}
