package com.blackpanther.webservice.blackpanthersoap.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.blackpanther.webservice.blackpanthersoap.entity.Actor;

public interface ActorRepository extends CrudRepository<Actor, Long>{
	Actor findByActorId(long actorId);
    List<Actor> findByNameAndDescription(String name, String description);
}
