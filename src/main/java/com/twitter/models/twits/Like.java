package com.twitter.models.twits;

import com.twitter.models.Identity;
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
public class Like extends Identity {
    @ManyToOne
    private BaseTwit twit;

    @ManyToOne
    private User liker;
}
