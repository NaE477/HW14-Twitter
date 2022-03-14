package com.twitter.services.impls;

import com.twitter.models.twits.Reply;
import com.twitter.repos.interfaces.ReplyRepo;
import com.twitter.services.interfaces.ReplyService;

public class ReplyServiceImpl extends BaseServiceImpl<Reply, ReplyRepo>
        implements ReplyService {

    public ReplyServiceImpl(ReplyRepo repository) {
        super(repository);
    }
}
