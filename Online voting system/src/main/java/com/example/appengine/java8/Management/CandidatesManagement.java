package com.example.appengine.java8.Management;

import com.example.appengine.java8.DTO.Candidates;
import com.example.appengine.java8.Entity.CandidatesEntity;
import com.example.appengine.java8.Service.CandidateManagementService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

import java.util.logging.Logger;

public class CandidatesManagement extends AbstractCrudManagement<Candidates> implements CandidateManagementService<Candidates> {
    private static Logger logger = Logger.getLogger(CandidatesManagement.class.getName());

    private CandidatesEntity candidatesEntity = new CandidatesEntity();

    @Override
    public Entity convertDtoToEntity(Candidates candidates) {
        try {
            Entity candidateEntity = candidates.getId() == null ? new Entity(candidatesEntity.getCandidateKind(), candidatesEntity.getCandidateKey()) :
                    DatastoreServiceFactory.getDatastoreService().get(KeyFactory.createKey(candidatesEntity.getCandidateKey(), candidatesEntity.getCandidateKind(), candidates.getId()));
            if(candidates.getFirstName() != null && !candidates.getFirstName().isEmpty()) {
                candidateEntity.setProperty(candidatesEntity.getCandidateFirstNameProperty(), candidates.getFirstName());
            }
            if(candidates.getSurName() != null && !candidates.getSurName().isEmpty()) {
                candidateEntity.setProperty(candidatesEntity.getCandidateSurNameProperty(), candidates.getSurName());
            }
            if(candidates.getFaculty() != null && !candidates.getFaculty().isEmpty()) {
                candidateEntity.setProperty(candidatesEntity.getCandidateFacultyProperty(), candidates.getFaculty());
            }
            if(candidates.getEarnedVote() != null) {
                candidateEntity.setProperty(candidatesEntity.getCandidateEarnedVoteProperty(), candidates.getEarnedVote());
            }
            return candidateEntity;
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Candidates convertEntityToDto(Entity entity) {
        Candidates candidates = new Candidates();
        candidates.setKey(entity.getKey());
        candidates.setId(entity.getKey().getId());
        candidates.setFirstName((String) entity.getProperty(candidatesEntity.getCandidateFirstNameProperty()));
        candidates.setSurName((String) entity.getProperty(candidatesEntity.getCandidateSurNameProperty()));
        candidates.setFaculty((String) entity.getProperty(candidatesEntity.getCandidateFacultyProperty()));
        candidates.setEarnedVote((Long) entity.getProperty(candidatesEntity.getCandidateEarnedVoteProperty()));
        return candidates;
    }
}
