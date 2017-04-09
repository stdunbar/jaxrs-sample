package com.hotjoe.services.services;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.hotjoe.model.Product;
import com.hotjoe.services.exception.BadRequestServiceException;
import com.hotjoe.services.exception.ConflictServiceException;
import com.hotjoe.services.exception.NotFoundServiceException;
import com.hotjoe.services.logging.Logged;


/**
 * A sample service to demonstrate some simple JAX-RS calls.
 */

@Path("/v1/product")
public class SampleService {
    private static final Logger logger = Logger.getLogger(SampleService.class.getName());

    //
    // a demo backing store - it could be a db or whatever
    //
    private static final Map<String, Product> products = new HashMap<>();

    @Logged  // this request is logged
    @GET
    @Path("/{identifier}")
    @Produces("application/json")
    public Response getSomething(@PathParam("identifier") String identifier) throws WebApplicationException {

        if( products.containsKey(identifier))
            return Response.ok(products.get(identifier)).build();

        //
        // A full exception - includes a messages and a full stack trace
        //
        // throw new NotFoundServiceException("identifier '" + identifier + "' not found", new Throwable());

        //
        // A message only exception
        //
        // throw new NotFoundServiceException("identifier '" + identifier + "' not found");

        //
        //
        NotFoundServiceException notFoundServiceException = new NotFoundServiceException();

        throw new NotFoundServiceException();
    }

    @Logged  // this request is logged
    @POST
    @Path("/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createSomething(Product product) throws WebApplicationException {

        logger.log(Level.FINE, "got a create product request");

        if(product == null)
            throw new BadRequestServiceException("record body is missing");

        if( products.containsKey(product.getIdentifier()))
            throw new ConflictServiceException("identifier " + product.getIdentifier() + " already exists");

        if( (product.getIdentifier() != null) && product.getIdentifier().contains("-"))
            throw new BadRequestServiceException("record identifier " + product.getIdentifier() +
                    " is invalid. it cannot contain the minus sign (dash) character");

        Random random = new Random();

        product.setProductId( Math.abs(random.nextInt()) );

        logger.log(Level.INFO, "Created record: " + product.getIdentifier());

        products.put(product.getIdentifier(), product);

        //
        // return what makes sense - just 200 or more?
        //
        // return Response.ok(product).build();

        return Response.status(Response.Status.CREATED).entity(product).build();
    }
}
