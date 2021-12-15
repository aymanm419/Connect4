package com.example.connect4;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public interface Board {
    List<List<Piece>> getBoard();

    boolean isColumnHasSpace(int col);

    void addChip(Piece piece, int col);

    void removeChip(int col);

    Board cloneBoard();

    double getBoardScore();

    double getMoveScore(Piece.PieceType type, int col);

    void printBoard(FileWriter fw) throws IOException;
}
