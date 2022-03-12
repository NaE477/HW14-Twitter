package com.twitter.services;

import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.TwitRepoImpl;

import java.util.List;

public class TwitServiceImpl extends BaseServiceImpl<Twit, TwitRepoImpl> implements TwitService {
    TwitRepoImpl twitRepo = new TwitRepoImpl();

    public List<Twit> findTwitsByUser(User user) {
        return twitRepo.readByUser(user);
    }

    public void truncate() {
        twitRepo.truncate();
    }

}
