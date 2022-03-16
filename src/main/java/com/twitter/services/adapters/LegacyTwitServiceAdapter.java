package com.twitter.services.adapters;

import com.twitter.models.twits.Twit;
import com.twitter.services.interfaces.BaseService;
import com.twitter.services.legacy.TwitService;


import java.util.List;

public class LegacyTwitServiceAdapter implements BaseService<Twit> {
    private final TwitService adaptee;

    protected LegacyTwitServiceAdapter(TwitService twitService) {
        this.adaptee = twitService;
    }

    @Override
    public Twit insert(Twit twit) {
        Integer twitId = adaptee.twit(twit);
        return adaptee.find(twitId);
    }

    @Override
    public Twit findById(Integer id) {
        return adaptee.find(id);
    }

    @Override
    public List<Twit> findAll() {
        return adaptee.findAll();
    }

    @Override
    public Twit update(Twit twit) {
        Integer updatedTwit = adaptee.editTwit(twit);
        return adaptee.find(updatedTwit);
    }

    @Override
    public void delete(Twit twit) {
        adaptee.deleteTwit(twit.getId());
    }

    @Override
    public void truncate() {
        adaptee.truncate();
    }
}
