package com.twitter.controllers.controllers;

import com.twitter.models.twits.BaseTwit;
import com.twitter.models.twits.Like;
import com.twitter.models.user.User;
import com.twitter.repos.impls.LikeRepoImpl;
import com.twitter.services.impls.LikeServiceImpl;
import com.twitter.services.interfaces.LikeService;
import org.hibernate.SessionFactory;

import java.util.stream.Collectors;

public class LikingController<T extends BaseTwit> {
    private final LikeService likeService;
    private final T twit;
    private final User user;

    public LikingController(SessionFactory sessionFactory, T twit, User user) {
        this.likeService = new LikeServiceImpl(new LikeRepoImpl(sessionFactory));
        this.twit = twit;
        this.user = user;
    }

    public void like() {
        if (!liked()) {
            Like newLike = new Like(twit, user);
            likeService.insert(newLike);
        } else System.out.println("Already liked this twit.");
    }

    public void dislike() {
        if (liked()) {
            Like toRemove = likeService.findByTwitAndUser(twit, user);
            if (toRemove != null) likeService.delete(toRemove);
        } else System.out.println("Haven't Liked this twit.");
    }

    private Boolean liked() {
        return twit
                .getLikes()
                .stream()
                .map(Like::getLiker)
                .filter(liker -> liker.equals(user))
                .collect(Collectors.toList())
                .contains(user);
    }
}
