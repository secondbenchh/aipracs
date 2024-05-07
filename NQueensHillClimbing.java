import java.util.Arrays;
import java.util.Random;

public class NQueensHillClimbing {
    static class State {
        int[] board; // Represent the placement of queens on the board
        int conflicts; // Number of conflicting queens

        public State(int[] board) {
            this.board = board;
            this.conflicts = calculateConflicts();
        }

        // Calculate the number of conflicts (i.e., attacking pairs of queens)
        private int calculateConflicts() {
            int conflicts = 0;
            for (int i = 0; i < board.length; i++) {
                for (int j = i + 1; j < board.length; j++) {
                    if (board[i] == board[j] || Math.abs(board[i] - board[j]) == j - i) {
                        conflicts++;
                    }
                }
            }
            return conflicts;
        }

        // Move queen to minimize conflicts
        public void moveQueen(int col, int newRow) {
            int oldRow = board[col];
            board[col] = newRow;
            conflicts = calculateConflicts();
        }

        // Randomly move a queen to a new row in its column
        public void randomMove() {
            int col = new Random().nextInt(board.length);
            int newRow = new Random().nextInt(board.length);
            moveQueen(col, newRow);
        }

        // Clone the current state
        public State clone() {
            return new State(Arrays.copyOf(board, board.length));
        }

        // Check if the state is a goal state (i.e., no conflicts)
        public boolean isGoalState() {
            return conflicts == 0;
        }
    }

    // Hill Climbing algorithm
    public static State hillClimbing(int n) {
        // Initialize a random board
        int[] board = new int[n];
        for (int i = 0; i < n; i++) {
            board[i] = new Random().nextInt(n);
        }
        State currentState = new State(board);

        while (true) {
            State nextBestState = null;
            int minConflicts = currentState.conflicts;

            // Generate all possible neighbor states by moving each queen
            for (int col = 0; col < n; col++) {
                for (int newRow = 0; newRow < n; newRow++) {
                    State neighborState = currentState.clone();
                    neighborState.moveQueen(col, newRow);
                    if (neighborState.conflicts < minConflicts) {
                        nextBestState = neighborState;
                        minConflicts = neighborState.conflicts;
                    }
                }
            }

            // If no better neighbor found, return current state
            if (minConflicts >= currentState.conflicts) {
                return currentState;
            }

            // Move to the next best state
            currentState = nextBestState;
        }
    }

    // Print the board
    public static void printBoard(int[] board) {
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[j] == i) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int n = 8; // Board size
        State solution = hillClimbing(n);
        if (solution.isGoalState()) {
            System.out.println("Solution found:");
            printBoard(solution.board);
        } else {
            System.out.println("No solution found.");
        }
    }
}
