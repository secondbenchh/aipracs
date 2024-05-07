import java.util.*;

public class TicTacToeMagicSquare {

    static char[][] board = {
            {'1', '2', '3'},
            {'4', '5', '6'},
            {'7', '8', '9'}
    };

    static final int[][] magicSquare = {
            {2, 7, 6},
            {9, 5, 1},
            {4, 3, 8}
    };

    static char currentPlayer = 'X';

    // Print the Tic Tac Toe board
    static void printBoard() {
        for (char[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }

    // Check if the current player has won
    static boolean checkWin() {
        int[] sums = new int[8];

        // Calculate sums of rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            sums[i] = board[i][0] + board[i][1] + board[i][2]; // Rows
            sums[i + 3] = board[0][i] + board[1][i] + board[2][i]; // Columns
        }
        sums[6] = board[0][0] + board[1][1] + board[2][2]; // Main diagonal
        sums[7] = board[0][2] + board[1][1] + board[2][0]; // Secondary diagonal

        // Check if any sum equals the magic constant
        for (int sum : sums) {
            if (sum == 15) {
                return true;
            }
        }

        return false;
    }

    // Check if the game is a draw
    static boolean checkDraw() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell != 'X' && cell != 'O') {
                    return false;
                }
            }
        }
        return true;
    }

    // Make a move on the board
    static void makeMove(int position) {
        int row = (position - 1) / 3;
        int col = (position - 1) % 3;

        if (board[row][col] != 'X' && board[row][col] != 'O') {
            board[row][col] = currentPlayer;
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        } else {
            System.out.println("Invalid move! Please choose an empty cell.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printBoard();

            System.out.println("Player " + currentPlayer + "'s turn. Enter position (1-9): ");
            int position = scanner.nextInt();
            makeMove(position);

            if (checkWin()) {
                printBoard();
                System.out.println("Player " + currentPlayer + " wins!");
                break;
            }

            if (checkDraw()) {
                printBoard();
                System.out.println("It's a draw!");
                break;
            }
        }

        scanner.close();
    }
}
