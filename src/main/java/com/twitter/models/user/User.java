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
import java.util.Set;

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
    private Set<Twit> twits;

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "liker")
    private Set<Like> likes;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "USER_RELATIONS",
            joinColumns = @JoinColumn(name = "FOLLOWED_ID"),
            inverseJoinColumns = @JoinColumn(name = "FOLLOWER_ID"))
    private Set<User> followers;

    @ManyToMany(mappedBy = "followers",fetch = FetchType.EAGER)
    private Set<User> following;

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
                "\n, Firstname: " + firstname +
                "\n, Lastname: " + lastname +
                "\n, Username: " + username +
                "\n, Password: " + password +
                "\n, Email: " + email +
                "\n, Followers: " + followers.size();
    }
}
