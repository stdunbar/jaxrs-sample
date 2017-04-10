package com.hotjoe.services.exception.bodywriter;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import com.google.gson.Gson;
import com.hotjoe.services.exception.ServiceException;

// import com.fasterxml.jackson.databind.ObjectMapper;

@Produces( MediaType.APPLICATION_JSON )
public class ServiceMessageBodyWriter implements MessageBodyWriter<ServiceException> {
 
    @Override
    public boolean isWriteable(Class<?> type, Type genericType,
                               Annotation[] annotations, MediaType mediaType) {
        return type == ServiceException.class;
    }
 
    @Override
    public long getSize(ServiceException myBean, Class<?> type, Type genericType,
                        Annotation[] annotations, MediaType mediaType) {
        // deprecated by JAX-RS 2.0 and ignored by JAX-RS runtime
        return -1;
    }

    @Override
    public void writeTo(ServiceException exception,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream)
                        throws IOException, WebApplicationException {

        String jsonString = new Gson().toJson(exception);
        
        entityStream.write( jsonString.getBytes( "UTF-8" ) );
    }
}