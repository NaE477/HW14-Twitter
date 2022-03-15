package com.twitter.services.impls;

import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.impls.TwitRepoImpl;
import com.twitter.repos.interfaces.TwitRepo;
import com.twitter.services.interfaces.TwitService;
import org.hibernate.SessionFactory;

import java.util.List;

public class TwitServiceImpl extends BaseServiceImpl<Twit, TwitRepo> implements TwitService {


    public TwitServiceImpl(TwitRepo repository) {
        super(repository);
    }

    public List<Twit> findTwitsByUser(User user) {
        return repository.readByUser(user);
    }

    public void truncate() {
        repository.truncate();
    }

    public void delete(User user) {
        repository.delete(user);
    }
}
