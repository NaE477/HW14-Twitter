package com.twitter.services.impls;

import com.twitter.models.user.User;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.services.interfaces.UserService;

import java.util.List;

public class UserServiceImpl extends BaseServiceImpl<User,UsersRepoImpl> implements UserService {

    public UserServiceImpl(UsersRepoImpl repo) {
        super(repo);
    }

    public User findByUsername(String username){
        return repo.readByUsername(username);
    }

    public User findByEmail(String email){
        return repo.readByEmail(email);
    }

    public List<User> searchUsername(String username){
        return repo.searchUsername(username);
    }

    public void truncate() {repo.truncate();}
}
