package com.twitter.services;

import com.twitter.models.user.User;

import java.util.List;

public interface UserService extends BaseService<User>{
    User findByUsername(String username);

    User findByEmail(String email);

    Boolean login(String username, String password);

    List<User> searchUsername(String username);
}
