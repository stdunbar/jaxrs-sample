package com.hotjoe.services.logging;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * This class is here for completeness - it is not really used for this sample.  While
 * it is called if a method is annotated with Logged, it doesn't do anything.  The filter
 * method gets called <b>before</b> the contents of the response are written.  The
 * ResponseInterceptor really logs what is going on.
 * <p>
 * Having said that, if you need to do something to the response before it is committed,
 * this is the place.
 *
 * @see ResponseInterceptor
 */
@Provider
public class ResponseLoggingFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        // Use the ContainerRequestContext to extract information from the HTTP request
        // Use the ContainerResponseContext to extract information from the HTTP response
    }
}