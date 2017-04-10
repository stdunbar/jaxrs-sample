package com.hotjoe.services.exception.mapper;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.hotjoe.services.exception.ServiceException;
import com.hotjoe.services.exception.mapper.model.ServiceErrorMessage;
import org.apache.commons.lang3.exception.ExceptionUtils;

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
    private static final Gson gson = new Gson();

	@Override
	@Produces(MediaType.APPLICATION_JSON)
	public Response toResponse(ServiceException exception) {

		ServiceErrorMessage serviceErrorMessage = new ServiceErrorMessage();

		//
        // This is sometimes useful for ui's that can display a "developer" mode
        // to show the exception to the front end.  Ultimately it "html-itizes"
        // the stack trace to be able to be shown in some ui component.  If you
        // would prefer not to have this then, when throwing the exception, don't
        // include another throwable.
        //
		if (exception.getCause() != null) {
            String stackTrace = ExceptionUtils.getStackTrace(exception.getCause());

			serviceErrorMessage.setStackTrace(stackTrace.replaceAll("\n", "<br />")
					.replaceAll("\t", "&nbsp;&nbsp;"));
		}

		if (exception.getMessage() != null)
			serviceErrorMessage.setMessage(exception.getMessage());

        if( serviceErrorMessage.isEmpty() )
            return Response.status(exception.getStatus()).build();
        else
            return Response.status(exception.getStatus()).entity(gson.toJson(serviceErrorMessage)).build();
	}
}
