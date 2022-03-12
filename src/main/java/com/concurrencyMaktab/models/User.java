package com.concurrencyMaktab.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    Integer id;
    String firstname,lastname,username,password,email;
    List<Twit> twits;

    public User(Integer id, String firstname,String lastname,String username,String password,String email){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Firstname: '" + firstname + '\'' +
                ", Lastname: '" + lastname + '\'' +
                ", Username: '" + username + '\'' +
                ", Password: '" + password + '\'' +
                ", Email: '" + email + '\'' +
                '}';
    }
}
