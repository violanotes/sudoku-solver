package com.violanotes.sudokusolver.exceptions;

import com.violanotes.sudokusolver.board.basic.BoardState;

/**
 * Created by pc on 7/20/2017.
 */

public class BoardEntityValidationException extends BoardEntityException {
    public BoardEntityValidationException(final BoardState boardState) {
        super("Invalid board state: " + boardState);
    }

    public BoardEntityValidationException(String message, BoardState boardState) {
        super("Invalid board state: cause: " + message + "  board state: " + boardState);
    }

    public BoardEntityValidationException() {
        super();
    }

    public BoardEntityValidationException(String message) {
        super(message);
    }

    public BoardEntityValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardEntityValidationException(Throwable cause) {
        super(cause);
    }
}
