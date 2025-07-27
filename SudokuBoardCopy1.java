// Mikael Tjiterosampurno
// CS 143
// HW Title and Core Topics: HW #2: Sudoku #2 (isValid, isSolved) sets, maps, efficiency, and boolean zen
//
// This program will create a 9x9 sudoku board and is able to print the board to the screen. Furthermore, it has validation checks
// that evaluates if the board is valid or solved.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SudokuBoardCopy1 {

    // 2D array to store sudoku board
    private int[][] board;

    // Constructor
    // pre: fileName refers to a valid .sdk file
    // post: Initialises the 9x9 board with values from the file
    public SudokuBoardCopy1(File fileName) throws FileNotFoundException {

        // Makes a 9x9 sudoku board
        board = new int[9][9];

        Scanner input = new Scanner(fileName);
        int row = 0;

        // fills the sudoku board using the given file
        while (input.hasNextLine() && row < 9) {
            String line = input.nextLine();

            // Loops through each character (column) of the line
            for (int column = 0; column < 9; column++) {
                char num = line.charAt(column);

                // if num is '.' a 0 will be placed at its row/column position. Otherwise it will be the number given
                if (num == '.') {
                    board[row][column] = 0;
                } else {
                    board[row][column] = num - '0';
                }
            }
            row++;
        }
    }

    // Checks if the sudoku board is valid using private helpers
    // Precondition: board is valid
    // Postcondition: if all checks are true, it will return true. Otherwise, false
    public boolean isValid() {
        return rowCheck() && columnCheck() && miniSquareCheck() && isIntegerRangeValid();
    }

    // Checks if the sudoku board is valid and solved
    // Precondition: Board is valid
    // Postcondition: Returns true if isSolved comes true and all symbols appear 9 times. Otherwise, false
    public boolean isSolved() {
        if (isValid()) {
            Map<Integer, Integer> countMap = new HashMap<>();

            // Goes through every row and column
            for (int row = 0; row < 9; row++) {
                for (int column = 0; column < 9; column++) {
                    int value = board[row][column];

                    if (value < 1 || value > 9) {
                        return false; // invalid value
                    }

                    // if the countMap contains the key, it will add 1 to the value. Otherwise, it will be added with value 1
                    if (countMap.containsKey(value)) {
                        countMap.put(value, countMap.get(value) + 1);
                    } else {
                        countMap.put(value, 1);
                    }
                }
            }

            // Checks if every each value from 1 to 9 appears 9 times
            for (int num = 1; num <= 9; num++) {
                if (!countMap.containsKey(num) || countMap.get(num) != 9) {
                    return false;
                }

            }


            return true;
        }
        return false;
    }

    // private helper methods

    // Method to check if the rows are invalid
    private boolean rowCheck() {
        // Goes through every row
        for (int row = 0; row < 9; row++) {
            Set<Integer> checked = new HashSet<>();

            // Goes through every value at every row
            for (int column = 0; column < 9; column++) {
                int value = board[row][column];

                // if the value is not empty, checks if the value has a duplicate. If it does it will print at which row
                // and return false. Otherwise, it will add the value inside the HashSet
                if (value != 0) {
                    if (checked.contains(value)) {
                        System.out.println("Error found at row: " + row);
                        return false; // duplicate found
                    }
                    checked.add(value);
                }
            }
        }

        return true;
    }

    // Method to check if the columns are invalid
    // Precondition: Board is filled correctly, integers
    // Postcondition: returns true if there aren't any duplicates. Otherwise, returns false
    private boolean columnCheck() {

        // Goes through every column
        // Precondition: Board is filled correctly, integers
        // Postcondition: returns true if there aren't any duplicates. Otherwise, returns false
        for (int column = 0; column < 9; column++) {
            Set<Integer> checked = new HashSet<>();

            // goes through every value of that column
            for (int row = 0; row < 9; row++) {
                int value = board[row][column];

                // if the value is not empty, checks if the value has a duplicate. If it does it will print at which column
                // and return false. Otherwise, it will add the value inside the HashSet
                if (value != 0) {
                    if (checked.contains(value)) {
                        System.out.println("Error found at column: " + column);
                        return false; // duplicate found
                    }
                    checked.add(value);
                }
            }
        }
        return true;
    }
    // Precondition: Board is filled correctly, integers
    // Postcondition: returns true if there aren't any duplicates. Otherwise, returns false
    private boolean miniSquareCheck() {
        for (int miniSquare = 0; miniSquare < 9; miniSquare++) {
            Set<Integer> checked = new HashSet<>();

            // Checks every 3x3 mini square
            for (int row = 0; row < 3; row++) {
                for (int column = 0; column < 3; column++) {

                    // Rows start at 0, 3, 6 | Columns start at 0, 3, 6 | Starts from the top left mini square
                    int value = board[((miniSquare / 3) * 3) + row][((miniSquare % 3) * 3) + column];

                    // if the value is not empty, checks if the value has a duplicate. If it does it will print at which mini square
                    // and return false. Otherwise, it will add the value inside the HashSet
                    if (value != 0) {
                        if (checked.contains(value)) {
                            System.out.println("Error inside square: " + miniSquare);
                            return false;
                        }

                        checked.add(value);
                    }
                }
            }
        }
        return true;
    }

    // Checks if the sudoku board is only integers
    // Precondition: Board has been populated
    // Postcondition: Returns true if the board 2 dimensional array are all integers. Otherwise, false
    private boolean isIntegerRangeValid() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                int value = board[row][column];

                if (value < 0 || value > 9) {
                    return false;
                }
            }
        }

        return true;
    }



    // toString method
    // pre: board has to be initialised 9x9 board with integers
    // post: Returns a formatted string which is the sudoku board
    @Override
    public String toString() {
        String result = "";

        for (int row = 0; row < 9; row++) {

            if (row % 3 == 0 && row != 0) {
                result += "-----------------------\n";  // horizontal separator
            }

            for (int column = 0; column < 9; column++) {
                if (column % 3 == 0 && column != 0) {
                    result += " | ";
                }

                int value = board[row][column];
                result += value + " ";
            }

            result += "\n";

        }


        return result;
    }
}
