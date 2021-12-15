package ai;
import com.example.connect4.Board;
import com.example.connect4.Piece;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MinimaxImplementation implements MinimaxInterface{
    private int cols;
    private int maxLevels;
    private boolean alphaBetaPruning;
    private double levelPenalty;
    private int bestMove = 0;

    public MinimaxImplementation(int cols, int maxLevels, boolean alphaBetaPruning, double levelPenalty) {
        this.cols = cols;
        this.maxLevels = maxLevels;
        this.alphaBetaPruning = alphaBetaPruning;
        this.levelPenalty = levelPenalty;
    }


    public int playNextMove(Board board) {
        MinimaxTreeNode root = new MinimaxTreeNode();
        root.setBoard(board);
        long startTime = System.currentTimeMillis();
        maximize(board, 0, Double.MAX_VALUE, root.getTree(), 0);
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time taken for move is " + String.valueOf(timeTaken) + " Milliseconds");
        try {
            FileWriter fw = new FileWriter("tree.txt");
            root.printTreeNode("", board, fw);
            fw.close();
        } catch (IOException e) {
            System.out.println("File Open Failed");
        }
        board.addChip(new Piece(Piece.PieceType.YELLOW), bestMove);
        return bestMove;
    }

    private double maximize(Board board, int level, double minimumValue, List<MinimaxTreeNode> tree, double lastBoardReward) {
        if(level == maxLevels) {
            return 0;
        }
        double maxTotalReward = -1 * Double.MAX_VALUE;
        for(int i = 0; i<cols; i++) {
            double totalReward;
            double nextMovesReward, currentBoardReward;
            if(!board.isColumnHasSpace(i)) {
                continue;
            }
            board.addChip(new Piece(Piece.PieceType.YELLOW), i);
            MinimaxTreeNode node = new MinimaxTreeNode(i, Piece.PieceType.YELLOW);
            tree.add(node);
            currentBoardReward = board.getMoveScore(Piece.PieceType.YELLOW, i);
            nextMovesReward =  minimize(board, level+1, maxTotalReward, node.getTree(), currentBoardReward);
            totalReward =  levelPenalty * nextMovesReward + currentBoardReward;
            if(totalReward > maxTotalReward && level == 0) {
                bestMove = i;
            }
            maxTotalReward = Math.max(maxTotalReward, totalReward);
            node.setReward(totalReward);
            board.removeChip(i);
            if(alphaBetaPruning && maxTotalReward + lastBoardReward >= minimumValue) {
                return maxTotalReward;
            }
        }
        return maxTotalReward;
    }

    private double minimize(Board board, int level, double maximumValue, List<MinimaxTreeNode> tree, double lastBoardReward) {
        if(level == maxLevels) {
            return 0;
        }
        double minTotalReward = Double.MAX_VALUE;
        for(int i = 0; i<cols; i++) {
            double totalReward, currentBoardReward, nextMovesReward;
            if(!board.isColumnHasSpace(i)) {
                continue;
            }
            board.addChip(new Piece(Piece.PieceType.RED), i);
            MinimaxTreeNode node = new MinimaxTreeNode(i, Piece.PieceType.RED);
            tree.add(node);
            currentBoardReward = board.getMoveScore(Piece.PieceType.RED, i);
            nextMovesReward = maximize(board, level+1, minTotalReward, node.getTree(), -1 * currentBoardReward);
            totalReward = levelPenalty * nextMovesReward - currentBoardReward;
            minTotalReward = Math.min(totalReward, minTotalReward);
            node.setReward(totalReward);
            board.removeChip(i);
            if(alphaBetaPruning && minTotalReward + lastBoardReward <= maximumValue) {
                return minTotalReward;
            }
        }
        return minTotalReward;
    }
}
