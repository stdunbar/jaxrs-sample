package com.hotjoe.services.exception;

import javax.ws.rs.core.Response;

public class NotFoundServiceException extends ServiceException {
	private static final long serialVersionUID = 1736707486401545199L;

	public NotFoundServiceException() {
		super();
	}

	public NotFoundServiceException(String message) {
		super(message);
	}

	public NotFoundServiceException(Throwable cause) {
		super(cause);
	}


	public NotFoundServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public Response.Status getStatus() {
		return Response.Status.NOT_FOUND;
	}
}
