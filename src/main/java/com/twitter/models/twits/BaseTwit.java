package com.twitter.models.twits;

import com.twitter.models.Identity;
import com.twitter.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseTwit extends Identity {

    @Column(nullable = false
            , length = 280)
    private String content;

    @ManyToOne
    private User user;

    @Column(name = "Twit_Time")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date twitTime;

    @Column(name = "delete_stat")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "twit",fetch = FetchType.EAGER)
    private Set<Like> likes;

    public BaseTwit(String content, User user) {
        this.content = content;
        this.user = user;
        this.twitTime = new Date(System.currentTimeMillis());
    }

    public BaseTwit(Integer id,String content,User user,Date date,Set<Like> likes) {
        super(id);
        this.content = content;
        this.user = user;
        this.twitTime = date;
        this.likes = likes;
    }
}
