package com.twitter.repos.adapters;

import com.twitter.models.twits.Twit;
import com.twitter.repos.interfaces.BaseRepository;
import com.twitter.repos.legacy.TwitRepo;

import java.util.List;

public class LegacyTwitRepoAdapter implements BaseRepository<Twit> {
    private final TwitRepo adaptee;

    public LegacyTwitRepoAdapter(TwitRepo twitRepo) {
        adaptee = twitRepo;
    }

    @Override
    public Twit ins(Twit twit) {
        Integer twitId = adaptee.ins(twit);
        return adaptee.read(twitId);
    }

    @Override
    public Twit readById(Integer id) {
        return adaptee.read(id);
    }

    @Override
    public List<Twit> readAll() {
        return adaptee.readAll();
    }

    @Override
    public Twit update(Twit twit) {
        Integer updateTwit = adaptee.update(twit);
        return adaptee.read(updateTwit);
    }

    @Override
    public void delete(Twit twit) {
        adaptee.delete(twit.getId());
    }

    @Override
    public void truncate() {
        adaptee.truncate();
    }
}
