import com.example.connect4.GameBoard;
import com.example.connect4.Piece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BoardTest {
    @Test
    public void testBoardAdd() {
        GameBoard board = new GameBoard(1, 3);
        Assertions.assertTrue(board.isColumnEmpty(0));
        board.addChip(new Piece(Piece.PieceType.RED), 0);
        Assertions.assertTrue(board.isColumnEmpty(0));
        board.addChip(new Piece(Piece.PieceType.RED), 0);
        Assertions.assertTrue(board.isColumnEmpty(0));
        board.addChip(new Piece(Piece.PieceType.YELLOW), 0);
        Assertions.assertFalse(board.isColumnEmpty(0));
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
        Assertions.assertTrue(board.isColumnEmpty(0));
        board.addChip(new Piece(Piece.PieceType.RED), 0);
        Assertions.assertTrue(board.isColumnEmpty(0));
        board.addChip(new Piece(Piece.PieceType.RED), 0);
        Assertions.assertTrue(board.isColumnEmpty(0));
        board.addChip(new Piece(Piece.PieceType.YELLOW), 0);
        Assertions.assertFalse(board.isColumnEmpty(0));
        board.removeChip(0);
        Assertions.assertTrue(board.isColumnEmpty(0));
        List<List<Piece>> listBoard = board.getBoard();
        Assertions.assertEquals(listBoard.get(0).get(0), new Piece(Piece.PieceType.RED));
        Assertions.assertEquals(listBoard.get(0).get(1), new Piece(Piece.PieceType.RED));
        Assertions.assertEquals(2, listBoard.get(0).size());
    }
}
