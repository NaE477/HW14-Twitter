package com.twitter.services.impls;

import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.impls.TwitRepoImpl;
import com.twitter.services.interfaces.TwitService;

import java.util.List;

public class TwitServiceImpl extends BaseServiceImpl<Twit, TwitRepoImpl> implements TwitService {
    private final TwitRepoImpl twitRepo = new TwitRepoImpl();

    public List<Twit> findTwitsByUser(User user) {
        return twitRepo.readByUser(user);
    }

    public void truncate() {
        twitRepo.truncate();
    }

}
