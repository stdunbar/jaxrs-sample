package com.hotjoe.services.exception;

import javax.ws.rs.core.Response;

public class BadRequestServiceException extends ServiceException {
	private static final long serialVersionUID = -2621117784193984815L;

	public BadRequestServiceException() {
		super();
	}

	public BadRequestServiceException(String message) {
		super(message);
	}

	public BadRequestServiceException(Throwable cause) {
		super(cause);
	}

	public BadRequestServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public Response.Status getStatus() {
		return Response.Status.BAD_REQUEST;
	}

}
