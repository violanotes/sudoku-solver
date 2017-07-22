package com.violanotes.sudokusolver.exceptions;

import com.violanotes.sudokusolver.board.basic.BoardState;

/**
 * Created by pc on 7/20/2017.
 */

public class BoardStateValidationException extends BoardEntityException {
    public BoardStateValidationException(final BoardState boardState) {
        super("Invalid board state: " + boardState);
    }

    public BoardStateValidationException(String message, BoardState boardState) {
        super("Invalid board state: cause: " + message + "  board state: " + boardState);
    }

    public BoardStateValidationException() {
        super();
    }

    public BoardStateValidationException(String message) {
        super(message);
    }

    public BoardStateValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardStateValidationException(Throwable cause) {
        super(cause);
    }
}
