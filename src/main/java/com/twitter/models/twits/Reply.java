package com.twitter.models.twits;

import com.twitter.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reply extends BaseTwit {
    @ManyToOne
    private Comment comment;

    public Reply(String content, User user, Comment comment) {
        super(content, user);
        this.comment = comment;
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
