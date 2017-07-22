package com.violanotes.sudokusolver.exceptions;

/**
 * Created by pc on 7/20/2017.
 */
public class BoardEntityException extends Exception {
    public BoardEntityException() {
        super();
    }

    public BoardEntityException(String message) {
        super(message);
    }

    public BoardEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardEntityException(Throwable cause) {
        super(cause);
    }
}
