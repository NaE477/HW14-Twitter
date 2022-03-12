package com.twitter.models.twits;

import com.twitter.models.user.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment extends BaseTwit {
    @ManyToOne
    private Twit ownerTwit;

    public Comment(String content, User user,Twit ownerTwit) {
        super(content,user);
        this.ownerTwit = ownerTwit;
    }

    @Override
    public String toString() {
        return  "ID: " + super.getId() +
                " ,Content: " + super.getContent() +
                " \n,Time: " + super.getTwitTime() +
                " \n,User: " + super.getUser().getUsername()
                ;
    }
}