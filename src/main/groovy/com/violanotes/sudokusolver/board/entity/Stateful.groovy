package com.violanotes.sudokusolver.board.entity

/**
 * Created by pc on 7/20/2017.
 */
interface Stateful {
     void changeState(State newState)
     void setInitialState()
}