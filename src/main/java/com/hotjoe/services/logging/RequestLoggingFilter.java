package com.hotjoe.services.logging;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs requests to the services if they are marked with the Logged annotation.
 * This class is marked with @Logged so that it pays attention to the annotation.
 *
 */
@Provider
@Logged
public class RequestLoggingFilter implements ContainerRequestFilter {
    private static Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        logger.info("got a " + requestContext.getMethod() + " request to " + requestContext.getUriInfo().getPath());

        StringWriter writer = new StringWriter();
        IOUtils.copy(requestContext.getEntityStream(), writer, "UTF-8");

        requestContext.setEntityStream(new ByteArrayInputStream(writer.toString().getBytes()));

        if(!requestContext.getMethod().toLowerCase().equals("get"))
            logger.info("input string is \"" + writer.toString() + "\"" );
    }
}
