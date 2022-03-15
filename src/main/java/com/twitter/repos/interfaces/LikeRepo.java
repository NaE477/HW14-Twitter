package com.twitter.repos.interfaces;

import com.twitter.models.twits.BaseTwit;
import com.twitter.models.twits.Like;
import com.twitter.models.user.User;

public interface LikeRepo extends BaseRepository<Like>{
    <T extends BaseTwit> Like readByTwitAndUser(T twit, User user);
    void delete(User user);
}
