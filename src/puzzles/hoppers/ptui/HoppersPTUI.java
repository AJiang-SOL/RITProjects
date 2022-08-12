package puzzles.hoppers.ptui;

import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersClientData;
import puzzles.hoppers.model.HoppersModel;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Alex Jiang acj3276@rit.edu
 * Plain Text User interface for the Hopper game
 */
public class HoppersPTUI implements Observer<HoppersModel, HoppersClientData> {

    /**
     * the current model of the game
     */
    private HoppersModel model;

    /**
     * The Constructor
     * @param fileName (String) the file's name
     * @throws IOException reading file
     */
    public HoppersPTUI(String fileName) throws IOException {
        this.model = new HoppersModel(fileName);
        initializeView();
    }

    /**
     * Runs the program
     * @throws IOException reading file
     */
    private void run() throws IOException {
        Scanner in = new Scanner(System.in);
        for (; ; ) {
            System.out.print("game command: ");
            String line = in.nextLine();
            String[] words = line.split("\\s+");
            if (words.length > 0) {
                //hint
                if (words[0].startsWith("h")) {
                    this.model.hint();
                }
                // load
                else if (words[0].startsWith("l")) {
                        this.model.loadFile(words[1]);
                }
                // select
                else if (words[0].startsWith("s")) {
                    this.model.selectCell(Integer.parseInt(words[1]),Integer.parseInt(words[2]));
                }
                //quit
                else if (words[0].startsWith("q")) {
                    break;
                }
                //reset
                else if (words[0].startsWith("r")){
                    this.model.restGame();

                }
                //display the commands
                else {
                    displayHelp();
                }
            }
        }
    }

    /**
     * initialize the view
     */
    public void initializeView() {
        this.model.addObserver( this );
        displayBoard(this.model.getRowNum(),this.model.getColNum());
        displayHelp();
    }

    /**
     * Prints out the commands for the game
     */
    private void displayHelp(){
        System.out.println( "h(int)              -- hint next move" );
        System.out.println( "l(oad) filename     -- load new puzzle file" );
        System.out.println( "s(elect) r c        -- select cell at r, c" );
        System.out.println( "q(uit)              -- quit the game" );
        System.out.println( "r(eset)             -- reset the current game" );
        System.out.println( " ");
    }

    /**
     * displays the current board
     * @param colNum (int)  the number of columns
     * @param rowNum (int) the number of rows
     */
    private void displayBoard(int rowNum,int colNum){
        //prints first line
        String firstLine = "  ";
        for(int i = 0; i<colNum;i++){
            firstLine +=" " +i;
        }
        System.out.println(firstLine);
        //prints second line
        String secondLine = "  ";
        for (int i = 0; i<colNum*2;i++){
            secondLine+= "-";
        }
        System.out.println(secondLine);
        //prints the board
        String board = model.getCurrentConfig().toString();
        String[] temp = board.split("\n");
        for (int row=0;row<rowNum;row++){
            String line = row + "| " + temp[row];
            System.out.println(line);
        }
        System.out.println(" ");
    }

    /**
     * updates the game
     * @param model (HoppersModel) the model the game is being run on
     * @param data optional data the server.model can send to the observer
     *
     */
    @Override
    public void update(HoppersModel model, HoppersClientData data) {
        System.out.println(data.getString());
        displayBoard(model.getRowNum(), model.getColNum());
    }

    /**
     * main
     * @param args (String[]) filename
     * @throws IOException reading a file
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        }
        else{
            System.out.println("Loaded: "+ args[0].split("/")[2]);
            HoppersPTUI ptui = new HoppersPTUI(args[0]);
            ptui.run();
        }
    }
}
