package com.violanotes.sudokusolver.board.entity;

/**
 * Created by pc on 7/22/2017.
 */
public interface HasAddress {
    default String address() {
        return getAddress();
    }

    String getAddress();
}
