package ai;
import com.example.connect4.Board;
import com.example.connect4.Piece;

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
        maximize(board, 0, Double.MAX_VALUE);
        board.addChip(new Piece(Piece.PieceType.YELLOW), bestMove);
        return bestMove;
    }

    private double maximize(Board board, int level, double minimumValue) {
        if(level == maxLevels) {
            //add heuristic here
            return 0;
        }
        double totalReward = Double.MIN_VALUE;
        for(int i = 0; i<cols; i++) {
            if(!board.isColumnHasSpace(i)) {
                continue;
            }
            board.addChip(new Piece(Piece.PieceType.YELLOW), i);
            double currentValue =  minimize(board, level+1, totalReward);
            if(totalReward < currentValue && level == 0) {
                bestMove = i;
                totalReward = currentValue;
            }
            if(alphaBetaPruning && totalReward >= minimumValue) {
                return Double.MAX_VALUE;
            }
            board.removeChip(i);
        }
        //calculate current board reward and add it to total reward
        return totalReward;
    }

    private double minimize(Board board, int level, double maximumValue) {
        if(level == maxLevels) {
            //add heuristic here
            return 0;
        }
        double totalReward = Double.MAX_VALUE;
        for(int i = 0; i<cols; i++) {
            if(!board.isColumnHasSpace(i)) {
                continue;
            }
            board.addChip(new Piece(Piece.PieceType.YELLOW), i);
            double currentValue = maximize(board, level+1, totalReward);
            totalReward = Math.min(totalReward, currentValue);
            if(alphaBetaPruning && totalReward <= maximumValue) {
                return Double.MIN_VALUE;
            }
            board.removeChip(i);
        }
        //calculate current board reward and add it to total reward
        return totalReward;
    }
}
