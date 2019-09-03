package com.hotjoe.services;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
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

import com.hotjoe.services.model.Product;
import com.hotjoe.services.exception.BadRequestServiceException;
import com.hotjoe.services.exception.ConflictServiceException;
import com.hotjoe.services.exception.NotFoundServiceException;
import com.hotjoe.services.logging.Logged;


/**
 * A sample service to demonstrate some simple JAX-RS calls.  This service is
 * meant to simulate a simple catalog - you can add items to the catalog and
 * you can query and get items from the catalog.  For this demo it just keeps
 * a simple Map of product id to Product.
 *
 */

@Path("/v1/product")
public class ProductService {
    private static final Logger logger = Logger.getLogger(ProductService.class.getName());

    //
    // a demo backing store - it could be a db or whatever
    //
    private static final Map<Integer, Product> products = new HashMap<>();

    @Logged  // this request is logged
    @GET
    @Path("/{productId}")
    @Produces("application/json")
    public Response getProduct(@PathParam("productId") Integer productId) throws WebApplicationException {

        if( products.containsKey(productId))
            return Response.ok(products.get(productId)).build();

        //
        // A full exception - includes a messages and a full stack trace
        //
        // throw new NotFoundServiceException("productId not found - " + productId, new Throwable());

        //
        // A message only exception
        //
        throw new NotFoundServiceException("productId not found - " + productId);

        //
        // Just a HTTP return code (404 in this case)
        //
        // throw new NotFoundServiceException();
    }

    @Logged  // this request is logged
    @POST
    @Path("/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createProduct(Product product) throws WebApplicationException {

        logger.log(Level.FINE, "got a create product request");

        if(product == null)
            throw new BadRequestServiceException("record body is missing");

        if( product.getProductId() == null )
            product.setProductId( ThreadLocalRandom.current().nextInt(10000, 1000000 + 1));
        else if( products.containsKey(product.getProductId()))
            throw new ConflictServiceException("product id " + product.getProductId() + " already exists");

        //
        // arbitrary business rule to show other exceptions - record description can't have dashes.
        //
        if( (product.getDescription() != null) && product.getDescription().contains("-"))
            throw new BadRequestServiceException("record description " + product.getDescription() +
                    " is invalid. it cannot contain the minus sign (dash) character");

        logger.log(Level.INFO, "Created product record with description \"" + product.getDescription() + "\"");

        products.put(product.getProductId(), product);

        return Response.status(Response.Status.CREATED).entity(product).build();
    }
}
