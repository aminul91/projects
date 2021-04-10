package com.example.appengine.java8.Entity;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class VoteEntity extends ParentsEntity{
    private String voterKind = "Voter";
    private String VOTER_NAME_PROPERTY = "name";
    private String VOTER_EMAIL_PROPERTY = "email";
    private String VOTER_TOKEN_PROPERTY = "token";
    private String VOTER_EMAILSENT_PROPERTY = "emailsent";
    private String VOTER_REMINDER_PROPERTY = "reminder";
    private String VOTER_ISVOTED_PROPERTY = "isvoted";

    private Key voterKey = KeyFactory.createKey(getParentsKind(), getParentsKey());


    public String getVoterKind() {
        return voterKind;
    }

    public Key getVoterKey() {
        return voterKey;
    }

    public String getVOTER_NAME_PROPERTY() {
        return VOTER_NAME_PROPERTY;
    }

    public String getVOTER_EMAIL_PROPERTY() {
        return VOTER_EMAIL_PROPERTY;
    }

    public String getVOTER_TOKEN_PROPERTY() {
        return VOTER_TOKEN_PROPERTY;
    }

    public String getVOTER_EMAILSENT_PROPERTY() {
        return VOTER_EMAILSENT_PROPERTY;
    }

    public String getVOTER_REMINDER_PROPERTY() {
        return VOTER_REMINDER_PROPERTY;
    }

    public String getVOTER_ISVOTED_PROPERTY() {
        return VOTER_ISVOTED_PROPERTY;
    }





}
