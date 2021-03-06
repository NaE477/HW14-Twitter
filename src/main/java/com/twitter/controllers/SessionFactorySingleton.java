package com.twitter.controllers;

import com.twitter.models.twits.*;
import com.twitter.models.user.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactorySingleton {
    private SessionFactorySingleton() {
    }

    private static class Holder {
        private final static SessionFactory INSTANCE;

        static {
            var registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();

            //registry is useful for creating session factory
            INSTANCE = new MetadataSources(registry)

                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(BaseTwit.class)
                    .addAnnotatedClass(Twit.class)
                    .addAnnotatedClass(Comment.class)
                    .addAnnotatedClass(Like.class)
                    .addAnnotatedClass(Reply.class)

                    .buildMetadata()
                    .buildSessionFactory();
        }
    }

    public static SessionFactory getInstance() {
        return Holder.INSTANCE;
    }
}
