package com.example.appengine.java8.Service;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

import java.util.List;

public interface CrudManagement<DTO> {
    DTO create(DTO dto) throws EntityNotFoundException;
    DTO update(DTO dto) throws EntityNotFoundException;
    void delete(DTO dto) throws EntityNotFoundException;
    List<DTO> get(Query query);
    DTO getById(Key key) throws EntityNotFoundException;
}
