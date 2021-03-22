package com.example.appengine.java8.Management;

import com.example.appengine.java8.DTO.VoteTime;
import com.example.appengine.java8.Entity.VoteTimeEntity;
import com.example.appengine.java8.Service.VoteTimeManagementService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

import java.util.Date;

public class VoteTimeManagement extends AbstractCrudManagement<VoteTime> implements VoteTimeManagementService<VoteTime> {

    private VoteTimeEntity voteTimeEntity = new VoteTimeEntity();

    @Override
    protected Entity convertDtoToEntity(VoteTime voteTime)  {
        try {
            Entity voteEntity= voteTime.getId()== null ?  new Entity(voteTimeEntity.getVoteTimeKind(),voteTimeEntity.getVoteTimeKey()):
                    DatastoreServiceFactory.getDatastoreService().get(KeyFactory.createKey(voteTimeEntity.getVoteTimeKey(), voteTimeEntity.getVoteTimeKind(), voteTime.getId()));
            voteEntity.setProperty(voteTimeEntity.getVoteTimeFirstProperty(), voteTime.getStartdate());
            voteEntity.setProperty(voteTimeEntity.getVoteTimeSecondProperty(), voteTime.getEnddate());
            return voteEntity;
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected VoteTime convertEntityToDto(Entity entity) {
        VoteTime voteTime = new VoteTime();
        voteTime.setKey(entity.getKey());
        voteTime.setStartdate((Date) entity.getProperty(voteTimeEntity.getVoteTimeFirstProperty()));
        voteTime.setEnddate((Date) entity.getProperty(voteTimeEntity.getVoteTimeSecondProperty()));
        return voteTime;
    }
}
