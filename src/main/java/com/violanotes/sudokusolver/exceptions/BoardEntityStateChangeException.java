package com.violanotes.sudokusolver.exceptions;

import com.violanotes.sudokusolver.board.basic.BoardState;

/**
 * Created by pc on 7/20/2017.
 */

public class BoardEntityStateChangeException extends BoardEntityException {
    public BoardEntityStateChangeException(final BoardState boardState) {
        super("Invalid board state: " + boardState);
    }

    public BoardEntityStateChangeException(String message, BoardState boardState) {
        super("Invalid board state: cause: " + message + "  board state: " + boardState);
    }

    public BoardEntityStateChangeException() {
        super();
    }

    public BoardEntityStateChangeException(String message) {
        super(message);
    }

    public BoardEntityStateChangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardEntityStateChangeException(Throwable cause) {
        super(cause);
    }
}
