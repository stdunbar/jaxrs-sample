package com.hotjoe.services.logging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * In JAX-RS, this method is needed to log responses, <b>after</b> they have been committed.  It
 * isn't possible to use the ContainerResponseFilter to do what we want because that filter is called
 * <b>before</b> the response is actually written.  That's great for things like additional headers, but
 * it won't do us any good for logging.
 *
 * This class is marked with @Logged so that it pays attention to the annotation.
 * 
 */
@Provider
@Logged
public class ResponseInterceptor implements WriterInterceptor {
    private static Logger logger = LoggerFactory.getLogger(ResponseInterceptor.class);
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        OutputStream originalStream = context.getOutputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        context.setOutputStream(baos);
        try {
            context.proceed();
        }
        finally {
            MediaType mediaType = MediaType.WILDCARD_TYPE;

            List<Object> contentTypes = context.getHeaders().get("Content-Type");
            if( contentTypes != null ) {
                if( contentTypes.get(0) instanceof MediaType)
                    mediaType = (MediaType) contentTypes.get(0);
                else if( contentTypes.get(0) instanceof String )
                    mediaType = MediaType.valueOf((String)(contentTypes.get(0)));
            }

            if( mediaType.getType().startsWith("text") || mediaType.equals(MediaType.APPLICATION_JSON_TYPE)) {
                logger.info("response body: " + baos.toString(StandardCharsets.UTF_8));
            }
            else {
                logger.info("response body is of type " + mediaType.toString() + " and it starts with these hex chars: " + bytesToHex(baos.toByteArray(), 25));
            }
            baos.writeTo(originalStream);
            baos.close();
            context.setOutputStream(originalStream);
        }
    }

    private static String bytesToHex(byte[] bytes, int length) {
        int lengthToUse = Math.min(bytes.length, length);

        char[] hexChars = new char[lengthToUse * 2];
        for ( int j = 0; j < lengthToUse; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
