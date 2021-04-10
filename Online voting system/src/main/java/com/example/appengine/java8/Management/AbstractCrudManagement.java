package com.example.appengine.java8.Management;

import com.example.appengine.java8.DTO.Candidates;
import com.example.appengine.java8.Entity.CandidatesEntity;
import com.example.appengine.java8.Service.CrudManagement;
import com.google.appengine.api.datastore.*;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractCrudManagement<DTO> implements CrudManagement<DTO> {
    private DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
    private Transaction transaction;

    @Override
    public DTO create(DTO dto) throws EntityNotFoundException {
        Entity entity = convertDtoToEntity(dto);
        transaction = datastoreService.beginTransaction();
        datastoreService.put(transaction, entity);
        transaction.commit();
        return dto;
    }

    @Override
    public DTO update(DTO dto) throws EntityNotFoundException {
        Entity entity = convertDtoToEntity(dto);
        transaction = datastoreService.beginTransaction();
        datastoreService.put(transaction, entity);
        transaction.commit();
        return dto;
    }

    @Override
    public void delete(DTO dto) throws EntityNotFoundException {
        Entity entity = convertDtoToEntity(dto);
        transaction = datastoreService.beginTransaction();
        datastoreService.delete(transaction, entity.getKey());
        transaction.commit();
        return;
    }

    @Override
    public List<DTO> get(Query query) {
        transaction = datastoreService.beginTransaction();
        PreparedQuery preparedQuery = datastoreService.prepare(query);
        List<Entity> entityList = preparedQuery.asList(FetchOptions.Builder.withDefaults());
        transaction.commit();
        return entityList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public DTO getById(Key key) throws EntityNotFoundException {
        Entity entity = datastoreService.get(key);
        return convertEntityToDto(entity);
    }

    protected abstract Entity convertDtoToEntity(DTO dto) throws EntityNotFoundException;
    protected abstract DTO convertEntityToDto(Entity entity);
}
