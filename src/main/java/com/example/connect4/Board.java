package com.example.connect4;

import java.util.List;
import java.util.Stack;

public interface Board {
    List<List<Piece>> getBoard();
    boolean isColumnHasSpace(int col);
    void addChip(Piece piece,int col);
    void removeChip(int col);
}
