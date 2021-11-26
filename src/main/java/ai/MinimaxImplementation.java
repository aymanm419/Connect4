package ai;
import com.example.connect4.Board;
import com.example.connect4.Piece;

public class MinimaxImplementation implements MinimaxInterface{
    private int cols;
    private int maxLevels;
    private boolean alphaBetaPruning;
    private double levelPenalty;
    private BoardFunctions bf;
    private int bestMove = 0;
    MinimaxTreeNode root;
    public MinimaxImplementation(int cols, int maxLevels, boolean alphaBetaPruning, double levelPenalty) {
        this.cols = cols;
        this.maxLevels = maxLevels;
        this.alphaBetaPruning = alphaBetaPruning;
        this.levelPenalty = levelPenalty;
        bf = new BoardFunctions();
    }

    public int playNextMove(Board board) {
        root = new MinimaxTreeNode();
        root.setBoard(board);
        root.setPt(Piece.PieceType.YELLOW);
        maximize(board, 0, Double.MAX_VALUE, root, 0);
        board.addChip(new Piece(Piece.PieceType.YELLOW), bestMove);
        return bestMove;
    }

    private double maximize(Board board, int level, double minimumValue, MinimaxTreeNode node, double lastBoardReward) {
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
            MinimaxTreeNode child = node.createNode(i, Piece.PieceType.RED);
            currentBoardReward = bf.getScore(board, BoardFunctions.PT.YELLOW, i);
            nextMovesReward =  minimize(board, level+1, maxTotalReward, child, currentBoardReward);
            totalReward =  levelPenalty * nextMovesReward + currentBoardReward;
            if(totalReward > maxTotalReward && level == 0) {
                bestMove = i;
            }
            maxTotalReward = Math.max(maxTotalReward, totalReward);
            node.setReward(maxTotalReward);
            board.removeChip(i);
            if(alphaBetaPruning && maxTotalReward + lastBoardReward >= minimumValue) {
                return maxTotalReward;
            }
        }
        return maxTotalReward;
    }

    private double minimize(Board board, int level, double maximumValue, MinimaxTreeNode node, double lastBoardReward) {
        if(level == maxLevels) {
            return 0;
        }
        double maxTotalReward = Double.MAX_VALUE;
        for(int i = 0; i<cols; i++) {
            double totalReward, currentBoardReward, nextMovesReward;
            if(!board.isColumnHasSpace(i)) {
                continue;
            }
            board.addChip(new Piece(Piece.PieceType.RED), i);
            MinimaxTreeNode child = node.createNode(i, Piece.PieceType.YELLOW);
            currentBoardReward = bf.getScore(board, BoardFunctions.PT.RED, i);
            nextMovesReward = maximize(board, level+1, maxTotalReward, child, -1 * currentBoardReward);
            totalReward = levelPenalty * nextMovesReward - currentBoardReward;
            maxTotalReward = Math.min(totalReward, maxTotalReward);
            node.setReward(maxTotalReward);
            board.removeChip(i);
            if(alphaBetaPruning && maxTotalReward + lastBoardReward <= maximumValue) {
                return maxTotalReward;
            }
        }
        return maxTotalReward;
    }
}
