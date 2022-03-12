package com.concurrencyMaktab.models;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    Integer id;
    String content;
    Twit ownerTwit;
    User user;
    Timestamp commentTime;

    @Override
    public String toString() {
        return  "ID: " + id +
                " ,Content: " + content +
                " \n,Time: " + commentTime +
                " \n,User: " + user.getUsername()
                ;
    }
}
