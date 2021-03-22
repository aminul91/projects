package com.example.appengine.java8.DTO;

import com.google.appengine.api.datastore.Key;

import java.io.Serializable;


public class Voter implements Serializable {
    private Long id;
    private Boolean emailSent;
    private Boolean reminder;
    Key key;
    String email;
    String name;
    String token;
    Boolean isVoted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getEmailSent() {
        return emailSent;
    }

    public void setEmailSent(boolean emailSent) {
        this.emailSent = emailSent;
    }

    public Boolean getReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public Boolean getVoted() {
        return isVoted;
    }

    public void setVoted(Boolean voted) {
        isVoted = voted;
    }

    @Override
    public String toString() {
        return "Voter{" +
                "emailSent=" + emailSent +
                ", reminder=" + reminder +
                ", id=" + id +
                ", key=" + key +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", isVoted=" + isVoted +
                '}';
    }
}
