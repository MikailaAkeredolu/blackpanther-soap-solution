package com.blackpanther.webservice.blackpanthersoap.service;

import java.util.List;

import com.blackpanther.webservice.blackpanthersoap.entity.Actor;

public interface IActorService {
	List<Actor>getAllActors();
	Actor getActorById(long actorId);
	boolean addActor(Actor actor);
    void updateActor(Actor actor);
    void deleteActor(Actor actor);
}
