package com.twitter.services;

import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.TwitRepoImpl;
import org.hibernate.SessionFactory;

import java.util.List;

public class TwitService extends BaseServiceImpl {
    private final TwitRepoImpl twitRepo;
    public TwitService(SessionFactory sessionFactory) {
        super(sessionFactory);
        twitRepo = new TwitRepoImpl(sessionFactory);
    }

    public Twit twit(Twit twit){
        return twitRepo.ins(twit);
    }
    public Twit editTwit(Twit twit){
        return twitRepo.update(twit);
    }
    public void deleteTwit(Twit twit){
        twitRepo.delete(twit);
    }
    public List<Twit> findTwitsByUser(User user){
        return twitRepo.readByUser(user);
    }
    public Twit find(Integer id){
        return twitRepo.readById(id);
    }
    public List<Twit> findAll(){
        return twitRepo.readAll();
    }
    public void truncate() {twitRepo.truncate();}
}
