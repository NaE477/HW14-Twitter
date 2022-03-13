package com.twitter.services.interfaces;

import com.twitter.models.user.User;

import java.util.List;

public interface UserService extends BaseService<User>{
    User findByUsername(String username);

    User findByEmail(String email);

    List<User> searchUsername(String username);
}
