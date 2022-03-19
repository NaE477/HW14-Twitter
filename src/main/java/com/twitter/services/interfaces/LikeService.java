package com.twitter.services.interfaces;

import com.twitter.models.twits.BaseTwit;
import com.twitter.models.twits.Like;
import com.twitter.models.user.User;

import java.util.Set;

public interface LikeService extends BaseService<Like> {
    <T extends BaseTwit> Like findByTwitAndUser(T twit, User user);
    <T extends BaseTwit> Set<Like> findByTwit(T twit);
    void delete(User user);
}
