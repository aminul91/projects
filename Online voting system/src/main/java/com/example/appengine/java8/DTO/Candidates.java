package com.example.appengine.java8.DTO;

import com.google.appengine.api.datastore.Key;

import java.io.Serializable;

public class Candidates implements Serializable {
    private Long id;
    private Key key;
    private String firstName;
    private String surName;
    private String faculty;
    private Long earnedVote = Long.valueOf(0);

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public Long getEarnedVote() {
        return earnedVote;
    }

    public void setEarnedVote(Long earnedVote) {
        this.earnedVote = earnedVote;
    }

    @Override
    public String toString() {
        return "Candidates{" +
                "id=" + id +
                ", key=" + key +
                ", firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                ", faculty='" + faculty + '\'' +
                ", earnedVote=" + earnedVote +
                '}';
    }
}
