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
        if (col >= board.size() || col < 0 || board.get(col).size() >= rows)
            return;
        this.board.get(col).add(piece);
    }

    @Override
    public void removeChip(int col) {
        if (col >= board.size() || col < 0 || board.get(col).size() <= 0)
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

    @Override
    public void printBoard() {
        System.out.println("=============== ");
        for (int j = rows - 1; j >= 0; j--) {
            System.out.print("|");
            for (List<Piece> pieces : board) {
                if (j < pieces.size())
                    System.out.print(pieces.get(j).getType().symbol);
                else
                    System.out.print("E");
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println("=============== ");
    }


    public double getMoveScore(Piece.PieceType type, int col) {
        Board board = this;
        Piece.PieceType [][] boardArray = convertBoardToArray(board);
        double score = 0;
        int row = 0;
        for(int i = 0;i<rows;i++) {
            if(boardArray[i][col] != Piece.PieceType.WHITE) {
                row = i;
                break;
            }
        }
        for(int i = -1;i<=1;i++) {
            for(int j = -1;j<=1;j++) {
                if(i == 0 && j == 0) continue;
                score += checkWindow(boardArray, row, col, i, j, type);
                score += checkWindow(boardArray, row - (i), col - (j), i, j, type);
                score += checkWindow(boardArray, row - (i * 2), col - (j * 2), i, j, type);
                score += checkWindow(boardArray, row - (i * 3), col - (j * 3), i, j, type);
            }
        }
        return score;
    }

    private Piece.PieceType[][] convertBoardToArray(Board board) {
        List<List<Piece>> l = board.getBoard();
        Piece.PieceType [][] boardArray = new Piece.PieceType[rows][l.size()];
        for(int i = 0;i<rows;i++) {
            for(int j = 0;j<l.size(); j++) {
                boardArray[i][j] = Piece.PieceType.WHITE;
            }
        }
        for(int i = 0;i<l.size();i++) {
            for(int j = 0;j<l.get(i).size(); j++) {
                boardArray[rows - j - 1][i] = (l.get(i).get(j).getType() == Piece.PieceType.YELLOW) ? Piece.PieceType.YELLOW : Piece.PieceType.RED;
            }
        }
        return boardArray;
    }

    private boolean checkBound(int i, int j, int rows, int cols) {
        if(i < 0 || i >= rows || j < 0 || j >= cols) return false;
        return true;
    }

    private double checkWindow(Piece.PieceType[][] boardArray, int row, int col,int dx,int dy, Piece.PieceType type) {
        int i = row, j = col;
        int elements = 0;
        int elementOp = 0;
        for(int ct = 0; ct < 4; ct++) {
            if(!checkBound(i, j, boardArray.length, boardArray[0].length)) {
                return 0;
            }
            if(boardArray[i][j] != type && boardArray[i][j] != Piece.PieceType.WHITE) {
                elementOp++;
            }
            if(boardArray[i][j] == type) {
                elements++;
            }
            i += dx;
            j += dy;
        }
        if(elementOp > 0) {
            if(elements > 1) return 0;
            return elementOp * 10;
        }
        return elements == 4? 40 : elements * 3;
    }
}
