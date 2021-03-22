package com.example.appengine.java8.Service;

import com.example.appengine.java8.DTO.Voter;
import com.example.appengine.java8.modifiedexceptions.CandidateException;
import com.example.appengine.java8.modifiedexceptions.VotException;

import java.util.List;

public interface VotService<DTO> extends CrudManagement<DTO> {
    List<Voter> getUnnotifiedVoters() throws VotException;

    List<Voter> getVoterListForReminderEmail() throws VotException;

    int getPendingVoterCount();

    int getCastedVoterCount();

    Voter getVoterByEmail(String email) throws VotException;
}
