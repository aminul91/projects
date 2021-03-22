package com.example.appengine.java8.Service;

import com.example.appengine.java8.DTO.Candidates;
import com.example.appengine.java8.DTO.Voter;

public interface VoteCastingService {
    Boolean castVote(Candidates candidate, Voter voter);
}
