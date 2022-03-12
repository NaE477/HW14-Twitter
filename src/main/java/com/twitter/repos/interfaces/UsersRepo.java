package com.twitter.repos.interfaces;

import com.twitter.models.user.User;

import java.util.List;

public interface UsersRepo extends BaseRepository<User> {
    User readByUsername(String username);
    User readByEmail(String email);
    List<User> searchUsername(String username);
}
