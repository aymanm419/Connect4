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
    private List<MinimaxTreeNode> tree;

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
        tree = new ArrayList<>();
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

    public MinimaxTreeNode createNode(int col, Piece.PieceType pt) {
        MinimaxTreeNode child = new MinimaxTreeNode();
        child.setCol(col);
        child.setPt(pt);
        tree.add(child);
        return child;
    }

    public boolean isFinalLevel() {
        return tree.get(0).getTree().size() == 0;
    }
}
