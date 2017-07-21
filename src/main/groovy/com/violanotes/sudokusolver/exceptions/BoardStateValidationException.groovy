package com.violanotes.sudokusolver.exceptions

import com.violanotes.sudokusolver.board.basic.BoardState
import groovy.transform.InheritConstructors

/**
 * Created by pc on 7/20/2017.
 */
@InheritConstructors
class BoardStateValidationException extends BoardEntityException {
    BoardStateValidationException(BoardState boardState) {
        super("Invalid board state: ${boardState.json()}")
    }

    BoardStateValidationException(String message, BoardState boardState) {
        super("Invalid board state: cause: " + message + "  board state: " + boardState)
    }
}