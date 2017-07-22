package com.violanotes.sudokusolver.board.entity;

/**
 * Created by pc on 7/22/2017.
 */
public interface Identifiable {
    default String id() {
        return "[" + getId() + "]";
    }

    String getId();
}
