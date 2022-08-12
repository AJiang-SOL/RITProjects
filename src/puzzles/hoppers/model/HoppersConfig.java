package puzzles.hoppers.model;


import puzzles.common.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class HoppersConfig implements Configuration {

    /** symbol for green frog */
    private final static char GREEN_FROG = 'G';
    /** symbol for red frog */
    private final static char RED_FROG = 'R';
    /** symbol for empty space */
    private final static char EMPTY_SPACE = '.';

    /** the number of rows */
    private final int numRows;
    /** the number of columns */
    private final int numCols;
    /** the board */
    public char[][] board;

    /**
     * The constructor for HoppersConfig
     * @param fileName (String) file's name
     * @throws IOException reading file
     */
    public HoppersConfig(String fileName) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String[] line1 = in.readLine().split(" ");
        this.numRows = Integer.parseInt(line1[0]);
        this.numCols = Integer.parseInt(line1[1]);
        this.board = new char[numRows][numCols];

        for (int row = 0; row < this.numRows; row++) {
            String[] pond = in.readLine().split("\\s+");
            for (int col = 0; col < this.numCols; col++) {
                this.board[row][col] = pond[col].charAt(0);
            }
        }
        in.close();
    }

    /**
     * makes a copy config copy of other
     * @param other (HoppersConfig)
     */
    private HoppersConfig(HoppersConfig other) {
        this.numRows = other.numRows;
        this.numCols = other.numCols;
        this.board = new char[other.numRows][other.numCols];
        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                this.board[row][col] = other.board[row][col];
            }
        }
    }

    /**
     * HopperConfig Constructor for the model
     * -ONLY USE FOR MODEL
     * @param other (Configuration) the other configuration
     * @param numberRows (int) the max number of rows
     * @param numberCols (int) the max number of columns
     */
    public HoppersConfig(Configuration other,int numberRows, int numberCols){
        String[] Board = other.toString().split("\n");
        this.numRows= numberRows;
        this.numCols= numberCols;
        this.board= new char[numberRows][numberCols];
        for (int row = 0; row < this.numRows; row++) {
            String[] temp = Board[row].split(" ");
            for (int col = 0; col < this.numCols; col++) {
                this.board[row][col] = temp[col].charAt(0);
            }
        }
    }


    /**
     * gets all the successors of the current config
     * @return list of successors
     */
    @Override
    public Collection<Configuration> getSuccessors() {
        ArrayList<Configuration> successorList = new ArrayList<>();
        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                if (this.board[row][col] == RED_FROG || this.board[row][col] == GREEN_FROG) {
                    //top
                    if (row - 4 >= 0 && this.board[row - 2][col] == GREEN_FROG && this.board[row - 4][col] == EMPTY_SPACE) {
                        HoppersConfig config = new HoppersConfig(this);
                        config.board[row - 4][col] = config.board[row][col];
                        config.board[row][col] = EMPTY_SPACE;
                        config.board[row - 2][col] = EMPTY_SPACE;
                        successorList.add(config);
                    }
                    //bottom
                    if (row + 4 <= this.numRows-1 && this.board[row + 2][col] == GREEN_FROG && this.board[row + 4][col] == EMPTY_SPACE) {
                        HoppersConfig config = new HoppersConfig(this);
                        config.board[row + 4][col] = config.board[row][col];
                        config.board[row][col] = EMPTY_SPACE;
                        config.board[row + 2][col] = EMPTY_SPACE;
                        successorList.add(config);
                    }
                    //left
                    if (col - 4 >= 0 && this.board[row][col - 2] == GREEN_FROG && this.board[row][col - 4] == EMPTY_SPACE) {
                        HoppersConfig config = new HoppersConfig(this);
                        config.board[row][col - 4] = config.board[row][col];
                        config.board[row][col] = EMPTY_SPACE;
                        config.board[row][col - 2] = EMPTY_SPACE;
                        successorList.add(config);
                    }
                    //right
                    if (col + 4 <= this.numCols-1 && this.board[row][col + 2] == GREEN_FROG && this.board[row][col + 4] == EMPTY_SPACE) {
                        HoppersConfig config = new HoppersConfig(this);
                        config.board[row][col + 4] = config.board[row][col];
                        config.board[row][col] = EMPTY_SPACE;
                        config.board[row][col + 2] = EMPTY_SPACE;
                        successorList.add(config);
                    }
                    //top left
                    if (col-2 >= 0 && row-2>= 0 && this.board[row-1][col-1] == GREEN_FROG && this.board[row-2][col-2] == EMPTY_SPACE){
                        HoppersConfig config = new HoppersConfig(this);
                        config.board[row-2][col-2] = config.board[row][col];
                        config.board[row][col] = EMPTY_SPACE;
                        config.board[row-1][col-1] = EMPTY_SPACE;
                        successorList.add(config);
                    }
                    //top right
                    if (col+2<=this.numCols-1 && row-2>=0 && this.board[row-1][col+1] == GREEN_FROG && this.board[row-2][col+2] == EMPTY_SPACE){
                        HoppersConfig config = new HoppersConfig(this);
                        config.board[row-2][col+2] = config.board[row][col];
                        config.board[row][col] = EMPTY_SPACE;
                        config.board[row-1][col+1] = EMPTY_SPACE;
                        successorList.add(config);
                    }
                    //bottom left
                    if (col-2 >= 0 && row+2<=this.numRows-1 && this.board[row+1][col-1] == GREEN_FROG && this.board[row+2][col-2] == EMPTY_SPACE){
                        HoppersConfig config = new HoppersConfig(this);
                        config.board[row+2][col-2] = config.board[row][col];
                        config.board[row][col] = EMPTY_SPACE;
                        config.board[row+1][col-1] = EMPTY_SPACE;
                        successorList.add(config);
                    }
                    //bottom right
                    if (col+2<=this.numCols-1 && row+2<=this.numRows-1 && this.board[row+1][col+1] == GREEN_FROG && this.board[row+2][col+2] == EMPTY_SPACE){
                        HoppersConfig config = new HoppersConfig(this);
                        config.board[row+2][col+2] = config.board[row][col];
                        config.board[row][col] = EMPTY_SPACE;
                        config.board[row+1][col+1] = EMPTY_SPACE;
                        successorList.add(config);
                    }
                }
            }
        }
        return successorList;
    }

    /**
     * checks if both are equal
     * @param obj (Object)
     * @return (boolean)
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof HoppersConfig){
            return Arrays.deepEquals(this.board, ((HoppersConfig) obj).board);
        }
        return false;
    }

    /**
     * generates a hash code
     * @return (int)
     */
    @Override
    public int hashCode(){
        return (this.numCols+this.numCols+ Arrays.deepHashCode(this.board));
    }

    /**
     * checks to see if the config is the goal
     * @return (boolean) is there only one red frog left
     */
    @Override
    public boolean isGoal() {
        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                if (this.board[row][col] == GREEN_FROG) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * converts the config into a string
     * @return (string)
     */
    @Override
    public String toString() {
        String result = "";
        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                result += this.board[row][col] + " ";
            }
            if(row+1 !=this.numRows){
                result += "\n";
            }
        }
        result+="\n";
        return result;
    }
}


