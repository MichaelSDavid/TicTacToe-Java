// Import(s)
import java.util.Scanner;
import java.util.Random;

public class TicTacToe {
    // Helper method for printing the game board
    public static void printBoard(char[][] array) {
        //noinspection ConstantConditions
        for (int a = 0; a < 3; a++) { // Outer loop (y-dim, row)
            if (a == 2) {
                // Special printing case for last row
                System.out.print(" " + array[2][0]
                + " | " + array[2][1] + " | " + array[2][2]);
                break;
            }
            for (int b = 0; b < 3; b++) { // Inner loop (x-dim, col)
                if (b == 2) {
                    // Special printing case for dashes between each row
                    System.out.print(" " + array[a][b]);
                    System.out.print("\n——————————-\n");
                } else {
                    System.out.print(" " + array[a][b] + " |");
                }
            }
        }
        System.out.println();
    }

    // Helper method for checking if an integer is present as an element in an array
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean intInArray(int[] array, int n) {
        for (int i : array) {
            if (i == n) {
                return true;
            }
        }
        return false;
    }

    // Helper method for converting a 1D index (player's move) to 2D indices (square on board)
    public static int[] moveToIndex(int n) {
        switch (n) {
            case 1:
                return new int[]{2, 0};
            case 2:
                return new int[]{2, 1};
            case 3:
                return new int[]{2, 2};
            case 4:
                return new int[]{1, 0};
            case 5:
                return new int[]{1, 1};
            case 6:
                return new int[]{1, 2};
            case 7:
                return new int[]{0, 0};
            case 8:
                return new int[]{0, 1};
            case 9:
                return new int[]{0, 2};
            default:
                return new int[]{-1};
        }
    }

    // Helper method that checks all possible winning combinations/positions
    public static boolean winCheck(char[][] array, char letter) {
        return ((array[0][0] == letter && array[0][1] == letter && array[0][2] == letter) // Checks top row
        || (array[1][0] == letter && array[1][1] == letter && array[1][2] == letter) // Checks middle row
        || (array[2][0] == letter && array[2][1] == letter && array[2][2] == letter) // Checks bottom row
        || (array[0][0] == letter && array[1][0] == letter && array[2][0] == letter) // Checks left column
        || (array[0][1] == letter && array[1][1] == letter && array[2][1] == letter) // Checks middle column
        || (array[0][2] == letter && array[1][2] == letter && array[2][2] == letter) // Checks right column
        || (array[0][0] == letter && array[1][1] == letter && array[2][2] == letter) // Checks L-R diagonal (from top)
        || (array[0][2] == letter && array[1][1] == letter && array[2][0] == letter)); // Checks R-L diagonal (from top)
    }

    // Helper method that checks if the board is entirely filled
    public static boolean fullBoard(char[][] array) {
        for (int a = 0; a < 3; a++) {
            for (int b = 0; b < 3; b++) {
                if (array[a][b] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    @SuppressWarnings({"DuplicatedCode", "unused"})
    public static void main(String[] args) {
        // Var(s) and const(s) init
        char[][] board;
        int[] moveset = {1, 2,3 ,4 ,5,6,7,8,9};
        int[] xyIndex = new int[2];
        Scanner scan = new Scanner(System.in);
        char p1Le, p2Le;
        String firstPlayer, turn, playAgain;
        boolean gameIsPlay;

        // Welcome message
        System.out.println("Welcome to Tic Tac Toe!\n"
                + "Positions are from 1-9, left to right on each row.\n"
                + "(1 is bottom left, 9 is top right)\n");

        // Main loop
        do {
            // Inner loop var(s) init
            // 3x3 matrix
            board = new char[][]{{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
            Random rand = new Random();
            gameIsPlay = true;

            // Chooser first player
            if (rand.nextInt(2) == 0) {
                firstPlayer = "p1";
            } else {
                firstPlayer = "p2";
            }
            turn = firstPlayer;

            // Choose markers
            char letter = ' ';
            if (firstPlayer.equals("p1")) {
                // If player 1 is first, they can choose their marker
                System.out.println("Player 1 will go first.");
                // While loop keeps running until 'X' or 'O' has been inputted
                while (!(letter == 'X' || letter == 'O')) {
                    System.out.println("Do you want to be X or O?");
                    letter = scan.next().toUpperCase().charAt(0);
                }

                // Conditional checks and marker cases
                if (letter == 'X') {
                    p1Le = 'X';
                    p2Le = 'O';
                } else {
                    p1Le = 'O';
                    p2Le = 'X';
                }
            } else {
                // If player 2 is first, they can choose their marker
                System.out.println("Player 2 will go first.");
                // While loop keeps running until 'X' or 'O' has been inputted
                while (!(letter == 'X' || letter == 'O')) {
                    System.out.println("Do you want to be X or O?");
                    letter = scan.next().toUpperCase().charAt(0);
                }

                // Conditional checks and marker cases
                if (letter == 'X') {
                    p2Le = 'X';
                    p1Le = 'O';
                } else {
                    p2Le = 'O';
                    p1Le = 'X';
                }
            }

            // Game loop
            while (gameIsPlay) {
                if (turn.equals("p1")) {
                    printBoard(board);

                    // Input next move
                    int move = 0;
                    while (!intInArray(moveset, move) || board[xyIndex[0]][xyIndex[1]] != ' ') {
                        System.out.println("Player 1, what is your next move? (1-9)");
                        move = scan.nextInt();
                        xyIndex = moveToIndex(move);
                    }
                    board[xyIndex[0]][xyIndex[1]] = p1Le;

                    // Check if player 1 has won or the board has been filled (with no clear win)
                    if (winCheck(board, p1Le)) {
                        printBoard(board);
                        System.out.println("Hooray! Player 1 has won the game!");
                        gameIsPlay = false;
                    } else {
                        if (fullBoard(board)) {
                            printBoard(board);
                            System.out.println("The game is a tie!");
                            break;
                        } else {
                            turn = "p2";
                        }
                    }
                } else {
                    printBoard(board);

                    // Input next move
                    int move = 0;
                    while (!intInArray(moveset, move) || board[xyIndex[0]][xyIndex[1]] != ' ') {
                        System.out.println("Player 2, what is your next move? (1-9)");
                        move = scan.nextInt();
                        xyIndex = moveToIndex(move);
                    }
                    board[xyIndex[0]][xyIndex[1]] = p2Le;

                    // Check if player 2 has won or the board has been filled (with no clear win)
                    if (winCheck(board, p2Le)) {
                        printBoard(board);
                        System.out.println("Hooray! Player 2 has won the game!");
                        gameIsPlay = false;
                    } else {
                        if (fullBoard(board)) {
                            printBoard(board);
                            System.out.println("The game is a tie!");
                            break;
                        } else {
                            turn = "p1";
                        }
                    }
                }
            }
            // Request to play again (or end the program)
            System.out.println("Do you want to play again? (yes or no)");
            playAgain = scan.next();
        } while (playAgain.equalsIgnoreCase("yes"));
    }
}
