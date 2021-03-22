package com.example.appengine.java8.Entity;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class VoteTimeEntity extends ParentsEntity{
    private String voteTimeKind = "VoteTime";

    private Key voteTimeKey = KeyFactory.createKey(getParentsKind(), getParentsKey());
    private String voteTimeFirstProperty = "startdate";
    private String voteTimeSecondProperty = "enddate";

    public String getVoteTimeKind() {
        return voteTimeKind;
    }

    public Key getVoteTimeKey() {
        return voteTimeKey;
    }

    public String getVoteTimeFirstProperty() {
        return voteTimeFirstProperty;
    }

    public String getVoteTimeSecondProperty() {
        return voteTimeSecondProperty;
    }
}
