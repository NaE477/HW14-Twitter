package com.twitter.models.user;

import com.twitter.models.Identity;
import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Like;
import com.twitter.models.twits.Twit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends Identity {
    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false
            , unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false
            , unique = true)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Twit> twits;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "liker")
    private List<Like> likes;

    public User(Integer id, String firstname, String lastname, String username, String password, String email) {
        super(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return "ID: " + super.getId() +
                ", Firstname: '" + firstname +
                ", Lastname: '" + lastname +
                ", Username: '" + username +
                ", Password: '" + password +
                ", Email: '" + email;
    }
}
