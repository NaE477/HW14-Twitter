package com.twitter.services.impls;

import com.twitter.models.twits.BaseTwit;
import com.twitter.models.twits.Like;
import com.twitter.models.user.User;
import com.twitter.repos.interfaces.LikeRepo;
import com.twitter.services.interfaces.LikeService;

import java.util.Set;

public class LikeServiceImpl extends BaseServiceImpl<Like, LikeRepo>
        implements LikeService {
    public LikeServiceImpl(LikeRepo repository) {
        super(repository);
    }

    @Override
    public <T extends BaseTwit> Like findByTwitAndUser(T twit, User user) {
        return repository.readByTwitAndUser(twit,user);
    }

    @Override
    public <T extends BaseTwit> Set<Like> findByTwit(T twit) {
        return repository.readByTwit(twit);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

}
