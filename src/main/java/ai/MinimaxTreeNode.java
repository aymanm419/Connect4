package ai;

import com.example.connect4.Board;
import com.example.connect4.Piece;

import java.util.ArrayList;
import java.util.List;

public class MinimaxTreeNode {
    private double reward;
    private int col;
    private Piece.PieceType pt;
    private Board board;
    private List<MinimaxTreeNode> tree = new ArrayList<>();

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }


    public List<MinimaxTreeNode> getTree() {
        return tree;
    }

    public void setTree(List<MinimaxTreeNode> tree) {
        this.tree = tree;
    }

    public MinimaxTreeNode() {

    }

    public MinimaxTreeNode(int col, Piece.PieceType pt) {
        this.col = col;
        this.pt = pt;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Piece.PieceType getPt() {
        return pt;
    }

    public void setPt(Piece.PieceType pt) {
        this.pt = pt;
    }

    public boolean isFinalLevel() {
        return tree.get(0).getTree().size() == 0;
    }

    void printTreeNode(String path, Board board) {
        if(this.pt != null) {
            System.out.println("------------------------------------------------------------------------------------------");
            path += String.valueOf(col) + ' ';
            System.out.println("Current path is " + path);
            System.out.println("Piece of Color " + this.pt + " is inserted at Column " + this.col + " With Reward " + this.reward);
            //print board
            System.out.println();
            board.addChip(new Piece(pt), col);
        }
        for (MinimaxTreeNode minimaxTreeNode : tree) {
            minimaxTreeNode.printTreeNode(path, board);
        }
        if(this.pt != null) {
            board.removeChip(col);
        }
    }
}
