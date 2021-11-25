package com.example.connect4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameBoard implements Board {
    private final List<List<Piece>> board;
    private final int rows;

    public GameBoard(int columns, int rows) {
        this.rows = rows;
        this.board = Collections.nCopies(columns, new ArrayList<>());
    }

    @Override
    public List<List<Piece>> getBoard() {
        return this.board;
    }

    public boolean isColumnHasSpace(int col) {
        if (col >= board.size() || col < 0)
            return true;
        return this.board.get(col).size() < rows;
    }

    @Override
    public void addChip(Piece piece, int col) {
        if (col >= board.size() || !isColumnHasSpace(col))
            return;
        this.board.get(col).add(piece);
    }

    @Override
    public void removeChip(int col) {
        if (col >= board.size() || isColumnHasSpace(col))
            return;
        this.board.get(col).remove(this.board.get(col).size() - 1);
    }
}
