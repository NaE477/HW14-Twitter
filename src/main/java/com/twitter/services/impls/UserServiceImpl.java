package com.twitter.services.impls;

import com.twitter.models.user.User;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.repos.interfaces.UsersRepo;
import com.twitter.services.interfaces.UserService;

import java.util.List;

public class UserServiceImpl extends BaseServiceImpl<User,UsersRepo> implements UserService {

    public UserServiceImpl(UsersRepo repository) {
        super(repository);
    }

    public User findByUsername(String username){
        return repository.readByUsername(username);
    }

    public User findByEmail(String email){
        return repository.readByEmail(email);
    }

    public List<User> searchUsername(String username){
        return repository.searchUsername(username);
    }

    public void truncate() {
        repository.truncate();}
}
