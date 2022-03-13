package com.twitter.services.impls;

import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.impls.TwitRepoImpl;
import com.twitter.services.interfaces.TwitService;

import java.util.List;

public class TwitServiceImpl extends BaseServiceImpl<Twit, TwitRepoImpl> implements TwitService {

    public TwitServiceImpl(TwitRepoImpl twitRepo) {
        super(twitRepo);
    }

    public List<Twit> findTwitsByUser(User user) {
        return repo.readByUser(user);
    }

    public void truncate() {
        repo.truncate();
    }

}
