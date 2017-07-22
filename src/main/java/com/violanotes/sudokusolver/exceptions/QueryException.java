package com.violanotes.sudokusolver.exceptions;

/**
 * Created by pc on 7/20/2017.
 */

public class QueryException extends BoardEntityException {
    public QueryException(final Class clazz1, final Class clazz2) {
        super("error querying for class '" + clazz1.getSimpleName() + "' in '" + clazz2.getSimpleName() +
                "'. Condition may not be defined for class '" + clazz1.getSimpleName() + "'.");
    }

    public QueryException() {
        super();
    }

    public QueryException(String message) {
        super(message);
    }

    public QueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryException(Throwable cause) {
        super(cause);
    }
}
