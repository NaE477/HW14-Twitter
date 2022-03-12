package com.concurrencyMaktab.services;

import com.concurrencyMaktab.models.Twit;
import com.concurrencyMaktab.models.User;
import com.concurrencyMaktab.repos.TwitRepo;

import java.sql.Connection;
import java.util.List;

public class TwitService extends Service<Twit>{
    public TwitService(Connection connection) {
        super(connection);
    }
    private final TwitRepo twitRepo = new TwitRepo(getConnection());

    public Integer twit(Twit twit){
        return twitRepo.ins(twit);
    }
    public Integer editTwit(Twit twit){
        return twitRepo.update(twit);
    }
    public Integer deleteTwit(Integer twitId){
        return twitRepo.delete(twitId);
    }
    public List<Twit> findTwitsByUser(User user){
        return twitRepo.read(user);
    }
    public Twit find(Integer id){
        return twitRepo.read(id);
    }
    public List<Twit> findAll(){
        return twitRepo.readAll();
    }
    public void truncate() {twitRepo.truncate();}
}
