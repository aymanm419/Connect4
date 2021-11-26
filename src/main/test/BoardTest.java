import com.example.connect4.Board;
import com.example.connect4.GameBoard;
import com.example.connect4.Piece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

public class BoardTest {
    @Test
    public void testBoardAdd() {
        GameBoard board = new GameBoard(1, 3);
        Assertions.assertTrue(board.isColumnHasSpace(0));
        board.addChip(new Piece(Piece.PieceType.RED), 0);
        Assertions.assertTrue(board.isColumnHasSpace(0));
        board.addChip(new Piece(Piece.PieceType.RED), 0);
        Assertions.assertTrue(board.isColumnHasSpace(0));
        board.addChip(new Piece(Piece.PieceType.YELLOW), 0);
        Assertions.assertFalse(board.isColumnHasSpace(0));
        board.addChip(new Piece(Piece.PieceType.YELLOW), 0);
        List<List<Piece>> listBoard = board.getBoard();
        Assertions.assertEquals(listBoard.get(0).get(0), new Piece(Piece.PieceType.RED));
        Assertions.assertEquals(listBoard.get(0).get(1), new Piece(Piece.PieceType.RED));
        Assertions.assertEquals(listBoard.get(0).get(2), new Piece(Piece.PieceType.YELLOW));
        Assertions.assertEquals(3, listBoard.get(0).size());
    }

    @Test
    public void testBoardRemove() {
        GameBoard board = new GameBoard(1, 3);
        Assertions.assertTrue(board.isColumnHasSpace(0));
        board.addChip(new Piece(Piece.PieceType.RED), 0);
        Assertions.assertTrue(board.isColumnHasSpace(0));
        board.addChip(new Piece(Piece.PieceType.RED), 0);
        Assertions.assertTrue(board.isColumnHasSpace(0));
        board.addChip(new Piece(Piece.PieceType.YELLOW), 0);
        Assertions.assertFalse(board.isColumnHasSpace(0));

        Assertions.assertFalse(board.isColumnHasSpace(0));
        board.removeChip(0);
        Assertions.assertTrue(board.isColumnHasSpace(0));
        List<List<Piece>> listBoard = board.getBoard();
        Assertions.assertEquals(listBoard.get(0).get(0), new Piece(Piece.PieceType.RED));
        Assertions.assertEquals(listBoard.get(0).get(1), new Piece(Piece.PieceType.RED));
        Assertions.assertEquals(2, listBoard.get(0).size());
        Assertions.assertTrue(board.isColumnHasSpace(0));

        board.removeChip(0);
        Assertions.assertEquals(listBoard.get(0).get(0), new Piece(Piece.PieceType.RED));
        Assertions.assertEquals(1, listBoard.get(0).size());
        Assertions.assertTrue(board.isColumnHasSpace(0));

        board.removeChip(0);
        Assertions.assertEquals(0, listBoard.get(0).size());
        Assertions.assertTrue(board.isColumnHasSpace(0));

        board.removeChip(0);
        Assertions.assertEquals(0, listBoard.get(0).size());

        board.addChip(new Piece(Piece.PieceType.RED), 0);
        board.removeChip(0);
        Assertions.assertEquals(0, listBoard.get(0).size());
    }

    @Test
    public void testBoard() {
        GameBoard board = new GameBoard(2, 1);
        board.addChip(new Piece(Piece.PieceType.RED), 0);
        board.addChip(new Piece(Piece.PieceType.YELLOW), 1);
        List<List<Piece>> listBoard = board.getBoard();
        Assertions.assertEquals(listBoard.get(0).get(0), new Piece(Piece.PieceType.RED));
        Assertions.assertEquals(listBoard.get(1).get(0), new Piece(Piece.PieceType.YELLOW));
    }

    @Test
    public void testBoardClone() {
        GameBoard board = new GameBoard(2, 2);
        board.addChip(new Piece(Piece.PieceType.RED), 0);
        board.addChip(new Piece(Piece.PieceType.YELLOW), 1);
        Board cloneBoard = board.cloneBoard();
        List<List<Piece>> listBoard = cloneBoard.getBoard();
        Assertions.assertEquals(listBoard.get(0).get(0), new Piece(Piece.PieceType.RED));
        Assertions.assertEquals(listBoard.get(1).get(0), new Piece(Piece.PieceType.YELLOW));
        cloneBoard.addChip(new Piece(Piece.PieceType.YELLOW), 0);
        Assertions.assertEquals(1, board.getBoard().get(0).size());
    }

    @Test
    public void testBoardScoreHorizontal() {
        GameBoard board = new GameBoard(4, 1);
        board.addChip(new Piece(Piece.PieceType.RED), 0);
        board.addChip(new Piece(Piece.PieceType.RED), 1);
        board.addChip(new Piece(Piece.PieceType.RED), 2);
        board.addChip(new Piece(Piece.PieceType.RED), 3);
        Assertions.assertEquals(1, board.getBoardScore());
    }

    @Test
    public void testBoardScoreVertical() {
        GameBoard board = new GameBoard(1, 4);
        board.addChip(new Piece(Piece.PieceType.RED), 0);
        board.addChip(new Piece(Piece.PieceType.RED), 0);
        board.addChip(new Piece(Piece.PieceType.RED), 0);
        board.addChip(new Piece(Piece.PieceType.RED), 0);
        Assertions.assertEquals(1, board.getBoardScore());
    }

    @Test
    public void testBoardScoreDiagonal() {
        GameBoard board = new GameBoard(4, 4);
        board.addChip(new Piece(Piece.PieceType.RED), 0);

        board.addChip(new Piece(Piece.PieceType.YELLOW), 1);
        board.addChip(new Piece(Piece.PieceType.RED), 1);

        board.addChip(new Piece(Piece.PieceType.YELLOW), 2);
        board.addChip(new Piece(Piece.PieceType.YELLOW), 2);
        board.addChip(new Piece(Piece.PieceType.RED), 2);

        board.addChip(new Piece(Piece.PieceType.YELLOW), 3);
        board.addChip(new Piece(Piece.PieceType.YELLOW), 3);
        board.addChip(new Piece(Piece.PieceType.YELLOW), 3);
        board.addChip(new Piece(Piece.PieceType.RED), 3);
        Assertions.assertEquals(1, board.getBoardScore());
    }

    /*
     O O O O
     X O O X
     X O O X
     X O O X
     X X X X
     */
    @Test
    public void testBoardScoreComplex() {
        GameBoard board = new GameBoard(4, 5);

        IntStream.range(0, 4).forEach(i -> board.addChip(new Piece(Piece.PieceType.RED), 0));
        board.addChip(new Piece(Piece.PieceType.YELLOW), 0);

        board.addChip(new Piece(Piece.PieceType.RED), 1);
        IntStream.range(1, 5).forEach(i -> board.addChip(new Piece(Piece.PieceType.YELLOW), 1));

        board.addChip(new Piece(Piece.PieceType.RED), 2);
        IntStream.range(1, 5).forEach(i -> board.addChip(new Piece(Piece.PieceType.YELLOW), 2));

        IntStream.range(0, 4).forEach(i -> board.addChip(new Piece(Piece.PieceType.RED), 3));
        board.addChip(new Piece(Piece.PieceType.YELLOW), 3);

        Assertions.assertEquals(0, board.getBoardScore());
    }

    /*
     X O O O
     X O O X
     X O O X
     X O O X
     X X X X
     */
    @Test
    public void testBoardScoreComplexPositive() {
        GameBoard board = new GameBoard(4, 5);

        IntStream.range(0, 5).forEach(i -> board.addChip(new Piece(Piece.PieceType.RED), 0));

        board.addChip(new Piece(Piece.PieceType.RED), 1);
        IntStream.range(1, 5).forEach(i -> board.addChip(new Piece(Piece.PieceType.YELLOW), 1));

        board.addChip(new Piece(Piece.PieceType.RED), 2);
        IntStream.range(1, 5).forEach(i -> board.addChip(new Piece(Piece.PieceType.YELLOW), 2));

        IntStream.range(0, 4).forEach(i -> board.addChip(new Piece(Piece.PieceType.RED), 3));
        board.addChip(new Piece(Piece.PieceType.YELLOW), 3);

        Assertions.assertEquals(2, board.getBoardScore());
    }

    /*
     O X X X
     O X X O
     O X X O
     O X X O
     O O O O
     */
    @Test
    public void testBoardScoreComplexNegative() {
        GameBoard board = new GameBoard(4, 5);

        IntStream.range(0, 5).forEach(i -> board.addChip(new Piece(Piece.PieceType.YELLOW), 0));

        board.addChip(new Piece(Piece.PieceType.YELLOW), 1);
        IntStream.range(1, 5).forEach(i -> board.addChip(new Piece(Piece.PieceType.RED), 1));

        board.addChip(new Piece(Piece.PieceType.YELLOW), 2);
        IntStream.range(1, 5).forEach(i -> board.addChip(new Piece(Piece.PieceType.RED), 2));

        IntStream.range(0, 4).forEach(i -> board.addChip(new Piece(Piece.PieceType.YELLOW), 3));
        board.addChip(new Piece(Piece.PieceType.RED), 3);

        Assertions.assertEquals(-2, board.getBoardScore());
    }
}
