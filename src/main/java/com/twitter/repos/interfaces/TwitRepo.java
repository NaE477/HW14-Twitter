package com.twitter.repos.interfaces;

import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;

import java.util.List;

public interface TwitRepo extends BaseRepository<Twit> {
    List<Twit> readByUser(User user);
    void delete(User user);
}
