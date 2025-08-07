package com.example.TZRESTFullAPI.services;


import java.util.List;

public interface ServiceEntity<Entity,ID> {

    Entity findById(ID id);

    Entity update(Entity request);

    Entity delete(ID id);

    Entity create(Entity request);

    List<Entity> findByAll();

}

