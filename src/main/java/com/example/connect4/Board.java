package com.example.connect4;

import java.util.List;

public interface Board {
    List<List<Piece>> getBoard();

    boolean isColumnHasSpace(int col);

    void addChip(Piece piece, int col);

    void removeChip(int col);

    Board cloneBoard();

    double getBoardScore();

    void printBoard();
}
