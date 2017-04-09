package com.hotjoe.services.exception.mapper;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotjoe.services.exception.ServiceException;
import com.hotjoe.services.exception.mapper.model.ServiceErrorMessage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Maps a ServiceException to a response.  Basically this means copying data from the
 * ServiceException into a ServiceErrorMessage and serializing it with JSON.
 * <p>
 * Note that currently this method will HTML-ize the stack trace if it exists so that it can
 * be displayed in some sort of UI.
 *
 */
@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {
    private static final Logger logger = LoggerFactory.getLogger(ServiceExceptionMapper.class);

	@Override
	@Produces(MediaType.APPLICATION_JSON)
	public Response toResponse(ServiceException exception) {

		ServiceErrorMessage serviceErrorMessage = new ServiceErrorMessage();

		if (exception.getCause() != null) {
            String stackTrace = ExceptionUtils.getStackTrace(exception.getCause());

			serviceErrorMessage.setStackTrace(stackTrace.replaceAll("\n", "<br />")
					.replaceAll("\t", "&nbsp;&nbsp;"));
		}

		if (exception.getMessage() != null)
			serviceErrorMessage.setMessage(exception.getMessage());

		try {
			if( serviceErrorMessage.isEmpty() )
                return Response.status(exception.getStatus()).build();
            else {
                ObjectMapper mapper = new ObjectMapper();

                return Response.status(exception.getStatus()).entity(mapper.writeValueAsString(serviceErrorMessage)).build();
            }
        }
        catch( JsonProcessingException jpe ) {
		    logger.error("error processing ServiceException - reason ", jpe );
            return Response.status(exception.getStatus()).entity("error processing exception").build();
        }
	}
}
