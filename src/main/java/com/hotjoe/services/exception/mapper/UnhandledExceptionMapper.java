package com.hotjoe.services.exception.mapper;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonParseException;
import org.jboss.resteasy.spi.UnhandledException;

/**
 * Maps a ReaderException to a response.  A ReaderException is most often thrown
 * when we get a bad JSON packet.
 *
 */
@Provider
public class UnhandledExceptionMapper implements ExceptionMapper<UnhandledException> {

	@Override
	@Produces(MediaType.TEXT_PLAIN)
	public Response toResponse(UnhandledException exception) {

	    if( exception.getCause() instanceof JsonParseException ) {
	        return Response.status(Response.Status.BAD_REQUEST).entity("error parsing json body - " + exception.getMessage()).build();
        }

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
}