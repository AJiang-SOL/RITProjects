package puzzles.hoppers.model;

import puzzles.common.Configuration;
import puzzles.common.Observer;
import puzzles.common.solver.Solver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Alex Jiang acj3276@rit.edu
 * the Hopper Model that runs the game
 */
public class HoppersModel {
    /** the collection of observers of this model */
    private final List<Observer<HoppersModel, HoppersClientData>> observers = new LinkedList<>();

    /** the current configuration */
    private HoppersConfig currentConfig;
    /** the number of rows */
    private int rowNum;
    /** the number of rows */
    private int colNum;
    /** the current file being used */
    private String currentFile;
    /** the previous Row selected */
    private int previousRow;
    /** the previous column selected */
    private int previousCol;
    /** the loop cycle */
    private int LoopSelection = 0;


    /**
     * Constructor makes the model
     * @param filename (String) the file name
     * @throws IOException reading a file
     */
    public HoppersModel(String filename) throws IOException {
        this.currentConfig = new HoppersConfig(filename);
        this.currentFile = filename;
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String[] line1 = in.readLine().split(" ");
        this.rowNum = Integer.parseInt(line1[0]);
        this.colNum = Integer.parseInt(line1[1]);
        in.close();
    }

    /**
     * attempts to solve the current config and does the next step
     */
    public void hint(){
        Solver solver = new Solver(this.currentConfig);
        List<Configuration> path = solver.solve();
        if (path.isEmpty()){
            alertObservers(new HoppersClientData("No solution"));
        }
        else if (path.size()==1) {
            alertObservers(new HoppersClientData("Already solved!"));
        }
        else{
            this.currentConfig = new HoppersConfig(path.get(1),this.rowNum,this.colNum);
            alertObservers(new HoppersClientData("Next step!"));
        }
        this.LoopSelection =0;
    }

    /**
     * Loads another file
     * @param fileName (String) the file's name
     * @throws IOException reading a file
     */
    public void loadFile(String fileName) throws IOException {
        try{
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String[] line1 = in.readLine().split(" ");
            this.rowNum = Integer.parseInt(line1[0]);
            this.colNum = Integer.parseInt(line1[1]);
            this.currentConfig = new HoppersConfig(fileName);
            this.currentFile= fileName;
            in.close();
            this.LoopSelection=0;
            String[] name = fileName.split("\\\\"); // GUI only
            String[] name2 = fileName.split("/");   // PTUI only
            // for the GUI
            if (name.length>1){
                alertObservers(new HoppersClientData("Loaded: "+ name[name.length-1]));
            }
            // for the PTUI
            else{
                alertObservers(new HoppersClientData("Loaded: "+ name2[name2.length-1]));
            }

        }
        //file not found
        catch (IOException e){
            alertObservers(new HoppersClientData("Failed to load: "+ fileName));
        }

    }

    /**
     * selecting the cells
     * @param row (int) the row being selected
     * @param col (int) the column being selected
     */
    public void selectCell(int row, int col){
        //did not selected frong
        if (this.currentConfig.board[row][col]=='*' ){
            alertObservers(new HoppersClientData("Invalid selection ("+row+", "+col+")"));
        }
        else{
            //first selection
            if (this.LoopSelection == 0 && this.currentConfig.board[row][col]!='.'){
                this.previousRow = row;
                this.previousCol = col;
                this.LoopSelection++;
                alertObservers(new HoppersClientData("Selected("+row+", "+col+")"));
            }
            //second selection
            else if (this.LoopSelection ==1 && this.currentConfig.board[row][col]=='.') {
                Collection<Configuration> successorsList = this.currentConfig.getSuccessors();
                HoppersConfig temp = new HoppersConfig(this.currentConfig,this.rowNum,this.colNum);
                temp.board[row][col] = temp.board[this.previousRow][this.previousCol];
                temp.board[this.previousRow][this.previousCol] = '.';
                temp.board[(row+this.previousRow)/2][(col+this.previousCol)/2] = '.';
                for(Configuration config: successorsList){
                    if (config.equals(temp)){
                        this.currentConfig = new HoppersConfig(config,this.rowNum,this.colNum);
                        this.LoopSelection=0;
                        alertObservers(new HoppersClientData("Jumped from ("+this.previousRow+", "+ this.previousCol+") to ("+row+", "+ col+")" ));
                        break;
                    }
                    else{
                        //not a valid successors so not a valid move
                        this.LoopSelection=0;
                        alertObservers(new HoppersClientData("Can't jump from ("+this.previousRow+", "+ this.previousCol+") to ("+row+", "+ col+")"));
                    }
                }
            }
            else{
                this.LoopSelection=0;
                alertObservers(new HoppersClientData("Invalid selection ("+row+", "+col+")"));

            }

        }
    }

    /**
     * rests the game
     * @throws IOException reading file
     */
    public void restGame() throws IOException {
        loadFile(this.currentFile);
        this.currentConfig = new HoppersConfig(this.currentFile);
        this.LoopSelection =0;
        alertObservers(new HoppersClientData("Puzzle reset!"));
    }


    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<HoppersModel, HoppersClientData> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(HoppersClientData data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }

    /**
     * getter for RowNum
     * @return (int) the number of rows
     */
    public int getRowNum() {
        return rowNum;
    }

    /**
     * getter for colNum
     * @return (int) the number of columns
     */
    public int getColNum() {
        return colNum;
    }

    /**
     * getter for the currentFile
     * @return (string) the current file name
     */
    public String getCurrentFile(){
        return currentFile;
    }


    /**
     * getter for the currentConfig
     * @return (HoppersConfig) the current config
     */
    public HoppersConfig getCurrentConfig() {
        return currentConfig;
    }
}
