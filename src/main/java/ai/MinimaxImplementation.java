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
        maximize(board, 0, Double.MAX_VALUE, root);
        board.addChip(new Piece(Piece.PieceType.YELLOW), bestMove);
        return bestMove;
    }

    private double maximize(Board board, int level, double minimumValue, MinimaxTreeNode node) {
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
            nextMovesReward =  minimize(board, level+1, maxTotalReward, child);
            currentBoardReward = bf.getScore(board, BoardFunctions.PT.YELLOW, i);
            totalReward =  levelPenalty * nextMovesReward + currentBoardReward;
            if(totalReward > maxTotalReward && level == 0) {
                bestMove = i;
            }
            maxTotalReward = Math.max(maxTotalReward, totalReward);
            node.setReward(maxTotalReward);
            if(alphaBetaPruning && maxTotalReward >= minimumValue) {
                return maxTotalReward;
            }
            board.removeChip(i);
        }
        return maxTotalReward;
    }

    private double minimize(Board board, int level, double maximumValue, MinimaxTreeNode node) {
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
            nextMovesReward = maximize(board, level+1, maxTotalReward, child);
            currentBoardReward = bf.getScore(board, BoardFunctions.PT.RED, i);
            totalReward = levelPenalty * nextMovesReward - currentBoardReward;
            maxTotalReward = Math.min(totalReward, maxTotalReward);
            node.setReward(maxTotalReward);
            if(alphaBetaPruning && maxTotalReward <= maximumValue) {
                return maxTotalReward;
            }
            board.removeChip(i);
        }
        return maxTotalReward;
    }
}
