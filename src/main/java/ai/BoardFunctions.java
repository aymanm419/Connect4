package ai;

import com.example.connect4.Board;
import com.example.connect4.Piece;

import java.util.List;


public class BoardFunctions {
    private PT [][] convertBoardToArray(Board board) {
      List<List<Piece>> l = board.getBoard();
      //6 l7d ma evro ys7a mn el noom
      int rows = 6;
      PT [][] boardArray = new PT[rows][l.size()];
      for(int i = 0;i<rows;i++) {
          for(int j = 0;j<l.size(); j++) {
            boardArray[i][j] = PT.WHITE;
          }
      }
      for(int i = 0;i<l.size();i++) {
        for(int j = 0;j<l.get(i).size(); j++) {
            boardArray[rows - j - 1][i] = (l.get(i).get(j).getType() == Piece.PieceType.YELLOW) ? PT.YELLOW : PT.RED;
        }
      }
      return boardArray;
    }


    double getScore(Board board, PT type, int col) {
        PT [][] boardArray = convertBoardToArray(board);
        double score = 0;
        //ama evro ys7a brdo
        int rows = 6;
        int row = 0;
        for(int i = 0;i<rows;i++) {
            if(boardArray[i][col] != PT.WHITE) {
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

    private boolean checkBound(int i, int j, int rows, int cols) {
        if(i < 0 || i >= rows || j < 0 || j >= cols) return false;
        return true;
    }

    private double checkWindow(PT[][] boardArray, int row, int col,int dx,int dy, PT type) {
        int i = row, j = col;
        int elements = 0;
        int elementOp = 0;
        for(int ct = 0; ct < 4; ct++) {
            if(!checkBound(i, j, boardArray.length, boardArray[0].length)) {
                return 0;
            }
            if(boardArray[i][j] != type && boardArray[i][j] != PT.WHITE) {
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



    public enum PT {
        RED,
        YELLOW,
        WHITE
    }
}
