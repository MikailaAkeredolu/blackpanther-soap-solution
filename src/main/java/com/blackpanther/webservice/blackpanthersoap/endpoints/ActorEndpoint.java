package com.blackpanther.webservice.blackpanthersoap.endpoints;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.blackpanther.panther.ActorInfo;
import com.blackpanther.panther.AddActorRequest;
import com.blackpanther.panther.AddActorResponse;
import com.blackpanther.panther.DeleteActorRequest;
import com.blackpanther.panther.DeleteActorResponse;
import com.blackpanther.panther.GetActorByIdRequest;
import com.blackpanther.panther.GetActorByIdResponse;
import com.blackpanther.panther.GetAllActorsResponse;
import com.blackpanther.panther.ServiceStatus;
import com.blackpanther.panther.UpdateActorRequest;
import com.blackpanther.panther.UpdateActorResponse;
import com.blackpanther.webservice.blackpanthersoap.entity.Actor;
import com.blackpanther.webservice.blackpanthersoap.service.IActorService;

//http://<host>:<port>/ws/countries.wsdl =  http://localhost:8080/ws/panthers.wsdl
@Endpoint
public class ActorEndpoint {
	private static final String NAMESPACE_URI = "http://www.blackpanther.com/panther";
	
	@Autowired
	private IActorService actorService;
	
	
	@PayloadRoot(namespace=NAMESPACE_URI, localPart="getActorByIdRequest")
	@ResponsePayload
	public GetActorByIdResponse getActor(@RequestPayload GetActorByIdRequest request) {
		GetActorByIdResponse response = new GetActorByIdResponse();
		ActorInfo actorInfo = new ActorInfo();
		BeanUtils.copyProperties(actorService.getActorById(request.getActorId()), actorInfo);
		response.setActorInfo(actorInfo);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllActorsRequest")
	@ResponsePayload
	public GetAllActorsResponse getAllActors() {
		GetAllActorsResponse response = new GetAllActorsResponse();
		List<ActorInfo> actorInfoList = new ArrayList<>();
		List<Actor> actorList = actorService.getAllActors();
		for (int i = 0; i < actorList.size(); i++) {
		     ActorInfo actor = new ActorInfo();
		     BeanUtils.copyProperties(actorList.get(i), actor);
		     actorInfoList.add(actor);    
		}
		response.getActorInfo().addAll(actorInfoList);
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addActorRequest")
	@ResponsePayload
	public AddActorResponse addActor(@RequestPayload AddActorRequest request) {
		AddActorResponse response = new AddActorResponse();		
    	        ServiceStatus serviceStatus = new ServiceStatus();		
		Actor actor = new Actor();
		actor.setName(request.getName());
		actor.setDescription(request.getDescription());		
                boolean flag = actorService.addActor(actor);
                if (flag == false) {
        	   serviceStatus.setStatusCode("CONFLICT");
        	   serviceStatus.setMessage("Actor Already Available");
        	   response.setServiceStatus(serviceStatus);
                } else {
		   ActorInfo actorInfo = new ActorInfo();
	           BeanUtils.copyProperties(actor, actorInfo);
		   response.setActorInfo(actorInfo);
        	   serviceStatus.setStatusCode("SUCCESS");
        	   serviceStatus.setMessage("Actor Added Successfully");
        	   response.setServiceStatus(serviceStatus);
                }
                return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateActorRequest")
	@ResponsePayload
	public UpdateActorResponse updateArticle(@RequestPayload UpdateActorRequest request) {
		Actor actor = new Actor();
		BeanUtils.copyProperties(request.getActorInfo(), actor);
		actorService.updateActor(actor);
    	        ServiceStatus serviceStatus = new ServiceStatus();
    	        serviceStatus.setStatusCode("SUCCESS");
    	        serviceStatus.setMessage("Actor Updated Successfully");
    	        UpdateActorResponse response = new UpdateActorResponse();
    	        response.setServiceStatus(serviceStatus);
    	        return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteActorRequest")
	@ResponsePayload
	public DeleteActorResponse deleteActor(@RequestPayload DeleteActorRequest request) {
		Actor actor = actorService.getActorById(request.getActorId());
    	        ServiceStatus serviceStatus = new ServiceStatus();
		if (actor == null ) {
	    	    serviceStatus.setStatusCode("FAIL");
	    	    serviceStatus.setMessage("Actor Not Available");
		} else {
		    actorService.deleteActor(actor);
	    	    serviceStatus.setStatusCode("SUCCESS");
	    	    serviceStatus.setMessage("Actor Deleted Successfully");
		}
    	        DeleteActorResponse response = new DeleteActorResponse();
    	        response.setServiceStatus(serviceStatus);
		return response;
	}

}
