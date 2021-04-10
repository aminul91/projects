package com.example.appengine.java8.Entity;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class CandidatesEntity extends ParentsEntity {
    private String candidateKind = "Candidates";
    private Key candidateKey = KeyFactory.createKey(getParentsKind(), getParentsKey());
    private String candidateFirstNameProperty = "FirstName";
    private String candidateSurNameProperty = "SurName";
    private String candidateFacultyProperty = "Faculty";
    private String candidateEarnedVoteProperty = "EarnedVote";

    public String getCandidateKind() {
        return candidateKind;
    }

    public Key getCandidateKey() {
        return candidateKey;
    }

    public String getCandidateFirstNameProperty() {
        return candidateFirstNameProperty;
    }

    public String getCandidateSurNameProperty() {
        return candidateSurNameProperty;
    }

    public String getCandidateFacultyProperty() {
        return candidateFacultyProperty;
    }

    public String getCandidateEarnedVoteProperty() {
        return candidateEarnedVoteProperty;
    }
}
