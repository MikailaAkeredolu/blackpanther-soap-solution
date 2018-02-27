package com.blackpanther.webservice.blackpanthersoap.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="blackpanthers")
public class Actor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long actorId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	
	//Getters and Setters

	public long getActorId() {
		return actorId;
	}

	public void setActorId(long actorId) {
		this.actorId = actorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	//toString

	@Override
	public String toString() {
		return String.format("Actor [actorId=%s, name=%s, description=%s]", actorId, name, description);
	}
	
	
	
}
