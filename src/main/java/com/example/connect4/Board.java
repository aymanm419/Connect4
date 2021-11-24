package com.example.connect4;

import java.util.List;

public interface Board {
    List<List<Piece>> getBoard();
    boolean isPositionEmpty(int row,int col);
}
