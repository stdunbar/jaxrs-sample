package com.hotjoe.services;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Provides a simple heartbeat.
 *
 */
@Path("/v1/heartbeat")
public class ServiceHeartBeat {

	/**
	 * Get the heartbeat.  Basically if you can hit this "service"
	 * then the machine and process are up.
	 * 
	 * @return a HTTP 200 with a simple "OK" text response packet.
	 * 
	 */
    @Produces({ MediaType.TEXT_PLAIN })
    @GET
	public Response getHeartBeat() {
		return Response.ok("OK").build();
	}
}