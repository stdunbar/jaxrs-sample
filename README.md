# JAX-RS Sample application for Wildfly
A very simple JAX-RS sample application that implements a few services.  The
services are meant purely as a demonstration and are not "real" services.


Deployment
----

To build, simply run

```mvn clean package```

This will create a war file that can be deployed into an JEE app server.  I've only
tested with Wildfly at this point.

The war file can be deployed to Wildfly and possibly other JEE application servers.
For Wildlfy, there are many options to deploy listed on
[the application deployment page](https://docs.jboss.org/author/display/WFLY10/Application+deployment)
that can help deploy.

Sample Service Info
----
There are a total of three service endpoints:

```GET /v1/heartbeat``` - returns a "text/plain" HTTP body of "OK" if the service is up
  and running. A heartbeat service like this is commonly used in load balancing
  environments so that a load balancer can validate that an application is healthy.

```POST /v1/product``` - puts a "product" in the catalog.  The product is a simple JSON
  formatted object:
  
  ```json
{"description": "The Product Description"}
```

This creates a product in the catalog.  The response will look like:

```json
{
  "productId": 96361,
  "description": "The Product Description",
  "createDate": "2017-04-10T02:51:12.772Z"
}
```

Note that the productId is simply a random integer between 10000 and 1000000.  The
createDate is when the product was added to the catalog.  Note that you can also
send your own product id in the POST:

```json
{
  "productId": 123456,
  "description": "The Product Description"
}
```

in which case the id that is given will be used.

If the product id is already in use a 409 (Conflict) HTTP error will be returned
with the body

```json
{
  "message": "product id 123456 already exists"
}
```

```GET /v1/product/{productId}``` gets an existing product with the given product id.
The response body looks the same as the call to create a new product.  A call with a
product id that doesn't exist will return an HTTP 404 error with the body:

```json
{
  "message": "productId not found - 123456"
}
```

Logging
----
The two product services are annotated with @Logged which means that the input
and output of them will be logged to the loggers.  This is a nice way to get
the input and output of the web service calls without using any proprietary libraries.


Copyright (c) 2017
by Xigole Systems
Licensed under the MIT License - see the file LICENSE for details. 
