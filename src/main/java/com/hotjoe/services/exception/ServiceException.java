package com.hotjoe.services.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Base class for all ServiceExceptions.  This class is abstract so that
 * derived classes must override
 */
public abstract class ServiceException extends WebApplicationException {
    private static final long serialVersionUID = -1957137270200438785L;


    /* package */ ServiceException() {
        //
        // this is initialized to a null String because a WebApplicationException
        // defaults to a 500 error.  if we don't set this then getMessage()
        // will always be "Internal Error" so null out the getMessage() result.
        //
        this((String)null);
    }

    /* package */ ServiceException(String message) {
        super(message);
    }

    /* package */ ServiceException(Throwable cause) {
        super(cause);
    }

    /* package */ ServiceException(String message, Throwable cause) {
        super(message, cause);
    }


    public abstract Response.Status getStatus();
}
