package com.concurrencyMaktab.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Twit {
    Integer id;
    String content;
    List<Comment> comments;
    User user;
    Timestamp twitTime;

    public Twit(Integer id, String content, User user,Timestamp twitTime){
        this.id = id;
        this.content = content;
        this.user = user;
        this.twitTime = twitTime;
    }

    @Override
    public String toString() {
        return  "ID: " + id +
                " ,Content: " + content +
                " \n,Twit Time: " + twitTime +
                " \n,User: " + user.getUsername()
                ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Twit twit = (Twit) o;
        return Objects.equals(id, twit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
