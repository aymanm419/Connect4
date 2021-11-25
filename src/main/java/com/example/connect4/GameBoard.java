package com.example.connect4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GameBoard implements Board {
    private final List<List<Piece>> board;
    private final int rows;
    private final static int[][] MOVES = {{-1, 1}, {0, 1}, {1, 1}, {1, 0}};

    /*
         \ ^ /
         D X >
         D D D
     */
    public GameBoard(int columns, int rows) {
        this.rows = rows;
        this.board = new ArrayList<>();
        for (int i = 0; i < columns; i++)
            this.board.add(new ArrayList<>());
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

    @Override
    public Board cloneBoard() {
        Board clonedBoard = new GameBoard(this.board.size(), rows);
        for (int i = 0; i < this.board.size(); i++) {
            for (Piece piece : this.board.get(i))
                clonedBoard.addChip(new Piece(piece.getType()), i);
        }
        return clonedBoard;
    }

    private int consecutiveFourCount() {
        int ans = 0;
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).size(); j++) {
                final Piece checkingPiece = board.get(i).get(j);
                int finalI = i;
                int finalJ = j;
                for (int[] move : MOVES) {
                    boolean allMatch = IntStream.range(0, 4).allMatch(
                            val -> {
                                int colPos = finalI + val * move[0];
                                int rowPos = finalJ + val * move[1];
                                if (colPos < 0 || colPos >= board.size() || rowPos < 0 || rowPos >= board.get(colPos).size())
                                    return false;
                                return checkingPiece.equals(board.get(colPos).get(rowPos));
                            });
                    if (!allMatch)
                        continue;
                    if (checkingPiece.getType().equals(Piece.PieceType.RED))
                        ans++;
                    else
                        ans--;
                }
            }
        }
        return ans;
    }

    @Override
    public double getBoardScore() {
        return consecutiveFourCount();
    }
}
