

package com.hotjoe.services.exception.mapper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * This class exists as the return to the front end.  Messages are serialized
 * from this to an error response.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceErrorMessage {

    private String message = null;
    private String stackTrace = null;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStackTrace(String value) {
        this.stackTrace = value;
    }

    /**
     * Tests if there is anything interesting in this message.
     *
     * @return true if any of the fields are set, false otherwise
     *
     */
    @JsonIgnore
    public boolean isEmpty() {
        return (message == null) && (stackTrace == null);
    }
}
