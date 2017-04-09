package com.hotjoe.services.exception;

import javax.ws.rs.core.Response;

public class InternalServiceException extends ServiceException {
	private static final long serialVersionUID = 1736707486401545199L;

	public InternalServiceException() {
		super();
	}

	public InternalServiceException(String message) {
		super(message);
	}

	public InternalServiceException(Throwable cause) {
		super(cause);
	}


	public InternalServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public Response.Status getStatus() {
		return Response.Status.INTERNAL_SERVER_ERROR;
	}
}
