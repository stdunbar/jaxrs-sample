package com.hotjoe.services.exception;

import javax.ws.rs.core.Response;

public class ConflictServiceException extends ServiceException {
	private static final long serialVersionUID = 1736707486401545199L;

	public ConflictServiceException() {
		super();
	}

	public ConflictServiceException(String message) {
		super(message);
	}

	public ConflictServiceException(Throwable cause) {
		super(cause);
	}


	public ConflictServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public Response.Status getStatus() {
		return Response.Status.CONFLICT;
	}
}
