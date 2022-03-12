package com.twitter.models.twits;

import com.twitter.models.Identity;
import com.twitter.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public abstract class BaseTwit extends Identity {

    @Column(nullable = false
            , length = 280)
    private String content;

    @ManyToOne
    @Column(name = "owner")
    private User user;

    @Column(name = "Twit_Time")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp twitTime;

    @Column(name = "delete_stat")
    private Boolean isDeleted;

    public BaseTwit(String content,User user) {
        this.content = content;
        this.user = user;
        this.twitTime = new Timestamp(System.currentTimeMillis());
        isDeleted = false;
    }
}
