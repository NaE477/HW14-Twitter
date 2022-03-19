package com.twitter.models.twits;

import com.twitter.models.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "twits")
public class Twit extends BaseTwit {
    @OneToMany(mappedBy = "ownerTwit")
    private List<Comment> comments;

    public Twit(String content,User user) {
        super(content,user);
    }

    public Twit(Integer id, String content, User user, Date date, Set<Like> likes) {
        super(id,content,user,date,likes);
    }

    @Override
    public String toString() {
        return "ID: " + super.getId() +
                " \n,Content: " + super.getContent() +
                " \n,Twit Time: " + super.getTwitTime() +
                " \n,User: " + super.getUser().getUsername() +
                " \n,Likes: " + super.getLikes().size()
                ;
    }
}
