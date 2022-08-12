package puzzles.hoppers.model;

/**
 * @author Alex Jiang acj3276@rit.edu
 * date from the model to the controller/viewer
 */
public class HoppersClientData {

    /**
     * String passed from the model
     */
    private final String input;

    /**
     * Data from the model
     * @param input (String)
     */
    public HoppersClientData(String input){
        this.input = input;
    }

    /**
     * getter for the input
     * @return (String)
     */
    public String getString(){
        return this.input;
    }
}
