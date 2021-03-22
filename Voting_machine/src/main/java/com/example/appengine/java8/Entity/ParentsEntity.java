package com.example.appengine.java8.Entity;

public abstract class ParentsEntity {
    private String parentsKind = "Election";
    private String parentsKey = "election";

    public String getParentsKind() {
        return parentsKind;
    }

    public String getParentsKey() {
        return parentsKey;
    }
}
