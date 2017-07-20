package com.violanotes.sudokusolver.exceptions;

/**
 * Created by pc on 7/20/2017.
 */
class BoardEntityException extends Exception {
    BoardEntityException(Throwable cause) {
        super(cause)
    }

    BoardEntityException(String message) {
        super(message)
    }

    BoardEntityException(String message, Throwable cause) {
        super(message, cause)
    }
}
