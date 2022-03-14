package com.twitter.services.interfaces;

import com.twitter.models.twits.BaseTwit;
import com.twitter.models.twits.Like;
import com.twitter.models.user.User;

public interface LikeService extends BaseService<Like> {
    <T extends BaseTwit> Like findByTwitAndUser(T twit, User user);
}
