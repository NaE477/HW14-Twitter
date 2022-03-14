package com.twitter.services.impls;

import com.twitter.models.twits.BaseTwit;
import com.twitter.models.twits.Like;
import com.twitter.models.user.User;
import com.twitter.repos.interfaces.LikeRepo;
import com.twitter.services.interfaces.LikeService;

public class LikeServiceImpl extends BaseServiceImpl<Like, LikeRepo>
        implements LikeService {
    public LikeServiceImpl(LikeRepo repository) {
        super(repository);
    }

    @Override
    public <T extends BaseTwit> Like findByTwitAndUser(T twit, User user) {
        return repository.readByTwitAndUser(twit,user);
    }
}
