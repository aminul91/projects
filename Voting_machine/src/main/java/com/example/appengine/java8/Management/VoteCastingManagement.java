package com.example.appengine.java8.Management;

import com.example.appengine.java8.DTO.Candidates;
import com.example.appengine.java8.DTO.Voter;
import com.example.appengine.java8.Entity.CandidatesEntity;
import com.example.appengine.java8.Service.CandidateManagementService;
import com.example.appengine.java8.Service.VotService;
import com.example.appengine.java8.Service.VoteCastingService;
import com.example.appengine.java8.modifiedexceptions.VotException;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

import java.util.logging.Logger;

public class VoteCastingManagement implements VoteCastingService {
    private Logger logger = Logger.getLogger(VoteCastingManagement.class.getName());

    @Override
    public Boolean castVote(Candidates candidate, Voter voter) {
        CandidateManagementService<Candidates> candidateManagementService = new CandidatesManagement();
        VotService<Voter> voterVoteService = new VoteManagement();
        CandidatesEntity candidatesEntity = new CandidatesEntity();
        try {
            if(!voter.getVoted()) {
                if (candidate != null) {
                    candidate = candidateManagementService.getById(KeyFactory.createKey(candidatesEntity.getCandidateKey(), candidatesEntity.getCandidateKind(), candidate.getId()));
                    long earnedVote = candidate.getEarnedVote();
                    earnedVote++;
                    candidate.setEarnedVote(earnedVote);
                    candidate = candidateManagementService.update(candidate);
                    voter.setVoted(true);
                    voter = voterVoteService.update(voter);
                }
            }
            else {
                throw new VotException("If you have already casted your vote than please come back after the voting period is over to see " +
                        "the results.If not make sure you are a registered voter.");
            }
            return true;
        } catch (VotException | EntityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
