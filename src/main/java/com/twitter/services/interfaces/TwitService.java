package com.twitter.services.interfaces;

import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;

import java.util.List;

public interface TwitService extends BaseService<Twit> {
    List<Twit> findTwitsByUser(User user);
    void delete(User user);
}
