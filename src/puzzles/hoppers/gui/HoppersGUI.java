package puzzles.hoppers.gui;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersClientData;
import puzzles.hoppers.model.HoppersModel;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * @author Alex Jiang acj3276@rit.edu
 * The HoppersGUI
 */
public class HoppersGUI extends Application implements Observer<HoppersModel, HoppersClientData> {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    /** Image of the red frog */
    private final Image redFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"red_frog.png"));
    /** Image of the Green frog */
    private final Image greenFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"green_frog.png"));
    /** Image of the lily pad */
    private final Image lilyPad = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"lily_pad.png"));
    /** Image of the water */
    private final Image water = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"water.png"));

    /** The body of the stage */
    private BorderPane borderPane;
    /** The top text from the model  */
    private Text response;
    /** the hopper model used to play the game*/
    private HoppersModel model;
    /** the state */
    private Stage stage;


    /**
     * initialize the models and the observer
     * @throws IOException reading filename
     */
    public void init() throws IOException {
        String filename = getParameters().getRaw().get(0);
        this.model = new HoppersModel(filename);
        this.model.addObserver(this);
    }

    /**
     * state the GUI
     * @param stage (Stage) the current state
     * @throws Exception reading file
     */
    @Override
    public void start(Stage stage) throws Exception {

        this.borderPane = new BorderPane();
        this.response = new Text("Loaded: "+ this.model.getCurrentFile().split("/")[2]);
        HBox hBox1 = new HBox(this.response);
        hBox1.setAlignment(Pos.CENTER);
        this.borderPane.setTop(hBox1);

        GridPane gridPane = makeGridPane(this.model);
        gridPane.setGridLinesVisible(false);
        this.borderPane.setCenter(gridPane);

        HBox hBox2 = makeLowerButtons(stage);
        this.borderPane.setBottom(hBox2);

        Scene scene = new Scene(this.borderPane);
        stage.setScene(scene);
        stage.setTitle("Hoppers GUI");
        this.stage = stage;
        stage.show();
    }

    /**
     * makes a grid pand full of buttons from the model
     * @param model (HoppersModel) the current hopper model
     * @return (GridPane)
     */
    private GridPane makeGridPane(HoppersModel model){
        GridPane gridPane = new GridPane();
        String[] PondGrid = model.getCurrentConfig().toString().split("\n");
        for(int row = 0;row<model.getRowNum();row++){
            String[] temp = PondGrid[row].split(" ");
            for(int col = 0;col<model.getColNum();col++){
                Button button = new Button();
                button.setMinSize(75, 75);
                button.setMaxSize(75, 75);
                String element = temp[col];
                if (element.equals(".")){
                    button.setGraphic(new ImageView(lilyPad));
                }
                else if(element.equals("*")){
                    button.setGraphic(new ImageView(water));
                }
                else if (element.equals("G")){
                    button.setGraphic(new ImageView(greenFrog));
                }
                else if (element.equals("R")){
                    button.setGraphic(new ImageView(redFrog));
                }
                gridPane.add(button,col,row);
                int Column = col;
                int Row = row;
                button.setOnAction((event)->{
                    model.selectCell(Row,Column);
                });
            }
        }
        return gridPane;
    }

    /**
     * makes the lower buttons
     * @param stage (State) the current state
     * @return (HBox)
     */
    private HBox makeLowerButtons(Stage stage){
        HBox hBox = new HBox();
        Button Load = new Button("Load");
        Button Reset = new Button("Reset");
        Button Hint = new Button("Hint");
        hBox.getChildren().addAll(Load,Reset,Hint);

        //load
        Load.setOnAction((actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            // current path
            // Paths.get('.')
            String path = System.getProperty("user.dir");
            path+="/data/hoppers";
            File initialD = new File(path);
            fileChooser.setTitle("Select file");
            fileChooser.setInitialDirectory(initialD);
            File file = fileChooser.showOpenDialog(stage);
            try {
                this.model.loadFile(file.getAbsolutePath());
            } catch (IOException ignored) {
            }
        }));

        //reset
        Reset.setOnAction(actionEvent -> {
            try {
                this.model.restGame();
            }
            catch (IOException ignored) {}
        });

        //hint
        Hint.setOnAction((actionEvent -> {
            this.model.hint();
        }));

        hBox.setAlignment(Pos.CENTER);
        return  hBox;
    }

    /**
     * updates the GUI
     * @param hoppersModel (HoppersModel) the current hopper model
     * @param hoppersClientData (HoppersClientData) date from the model
     */
    @Override
    public void update(HoppersModel hoppersModel, HoppersClientData hoppersClientData) {
        updateTopTextBox(hoppersClientData);
        updateGrid();
        updateSize();
    }

    /**
     * updates the top textbox
     * @param data (HoppersClientData)
     */
    private void updateTopTextBox(HoppersClientData data){
        this.response.setText(data.getString());
    }

    /**
     * update the Grid of buttons
     */
    private void updateGrid(){
        this.borderPane.setCenter(makeGridPane(this.model));
    }

    /**
     * updates the size of the stage
     */
    private void updateSize(){
        this.stage.sizeToScene();
    }

    /**
     * main
     * @param args filename
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            Application.launch(args);
        }
    }
}
