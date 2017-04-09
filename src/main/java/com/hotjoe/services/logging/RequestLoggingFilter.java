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
 * Because this has the Logged annotation, it is used for logging, in this case, requests.
 *
 */
@Logged
@Provider
public class RequestLoggingFilter implements ContainerRequestFilter {
    private static Logger LOG = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        LOG.info("got a " + requestContext.getMethod() + " request to " + requestContext.getUriInfo().getPath());

        StringWriter writer = new StringWriter();
        IOUtils.copy(requestContext.getEntityStream(), writer, "UTF-8");

        requestContext.setEntityStream(new ByteArrayInputStream(writer.toString().getBytes()));

        if(!requestContext.getMethod().toLowerCase().equals("get"))
            LOG.info("input string is \"" + writer.toString() + "\"" );
    }
}
