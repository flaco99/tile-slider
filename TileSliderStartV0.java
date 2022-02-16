import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.collections.ObservableList;
import java.util.concurrent.ThreadLocalRandom;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import java.io.File;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * TODO update the header with your details.
 * Write a description of JavaFX class Tile Slider here.
 *
 * naomi weissberg
 * January 12, 2022
 * tile slider
 */
public class TileSliderStartV0 extends Application
{
    // Track: Create the Tracking Area
    private TextArea trackArea = new TextArea(""); 

    // create 2D arrays. pic is for the current alignment and rightPic is for the correct alignment.
    Image[][] pic, rightPic;
    Image blank;
    // create integer variables for the coordinates for the blank tile.
    int blankX, blankY;
    // create all the buttons
    Button startSolve, takeAway, reset, conc, concstop, autoshuffle;
    // create an integer varialbe for the number of correctly placed tiles.
    int correct;
    // create all audio clips
    AudioClip cheer, shuffle, swap, dream, startgame;
    // create integer varialbes for the number of moves made to shuffle the tiles and the number of steps made to solve the puzzle. 
    int moves, steps;
    // create a string variable for what stage of the game the player is in. 
    String turn;
    // create a gridpane to contain and show everything.
    GridPane gridPane;
    // create a boolean variable for telling whether a certain move is valid or not.
    boolean validMove = false;

    @Override
    public void start(Stage stage) throws Exception {
        // initialize the number of moves (for shuffling) and steps (for solving).
        moves=0;
        steps=0;

        // Load the audio files to be used
        String uriString = new File("Party Horn - Sound Effect.mp3").toURI().toString();
        cheer = new AudioClip(uriString);

        uriString = new File("click.wav").toURI().toString();
        swap = new AudioClip(uriString);

        uriString = new File("shuffle.wav").toURI().toString();
        shuffle = new AudioClip(uriString);
        
        uriString = new File("aaa.mp3").toURI().toString();
        dream = new AudioClip(uriString);
        
        uriString = new File("startgame.wav").toURI().toString();
        startgame = new AudioClip(uriString);

        // Create a button for "Remove a Tile"
        takeAway=new Button("Remove a Tile");
        takeAway.setStyle("-fx-font-size: 12pt;");

        // Create a button for "Start to Solve"
        startSolve=new Button("Start to Solve");
        startSolve.setStyle("-fx-font-size: 12pt;");

        // Create a button for "Reset"
        reset=new Button("Reset");
        reset.setStyle("-fx-font-size: 12pt;");
        
        // Create a button for "Auto shuffle"
        autoshuffle=new Button("Auto shuffle");
        autoshuffle.setStyle("-fx-font-size: 12pt;");
        
        // Create a button for "Concentration music"
        conc=new Button("Concentration music");
        conc.setStyle("-fx-font-size: 12pt;");
        
        // Create a button for "Stop music"
        concstop=new Button("Stop music");
        concstop.setStyle("-fx-font-size: 12pt;");

        // Set an action on the buttons
        takeAway.setOnAction(this::buttonPressed);
        startSolve.setOnAction(this::buttonPressed);
        reset.setOnAction(this::buttonPressed);
        autoshuffle.setOnAction(this::buttonPressed);
        conc.setOnAction(this::buttonPressed);
        concstop.setOnAction(this::buttonPressed);

        // Create a 2D array of images and fill the 5x5 array
        // Follow grid layout - [col][row]
        pic=new Image[5][5];
        pic[0][0]=new Image ("image_part_001.jpg");
        pic[1][0]=new Image ("image_part_002.jpg");
        pic[2][0]=new Image ("image_part_003.jpg");
        pic[3][0]=new Image ("image_part_004.jpg");
        pic[4][0]=new Image ("image_part_005.jpg");
        pic[0][1]=new Image ("image_part_006.jpg");
        pic[1][1]=new Image ("image_part_007.jpg");
        pic[2][1]=new Image ("image_part_008.jpg");
        pic[3][1]=new Image ("image_part_009.jpg");
        pic[4][1]=new Image ("image_part_010.jpg");
        pic[0][2]=new Image ("image_part_011.jpg");
        pic[1][2]=new Image ("image_part_012.jpg");
        pic[2][2]=new Image ("image_part_013.jpg");
        pic[3][2]=new Image ("image_part_014.jpg");
        pic[4][2]=new Image ("image_part_015.jpg");
        pic[0][3]=new Image ("image_part_016.jpg");
        pic[1][3]=new Image ("image_part_017.jpg");
        pic[2][3]=new Image ("image_part_018.jpg");
        pic[3][3]=new Image ("image_part_019.jpg");
        pic[4][3]=new Image ("image_part_020.jpg");
        pic[0][4]=new Image ("image_part_021.jpg");
        pic[1][4]=new Image ("image_part_022.jpg");
        pic[2][4]=new Image ("image_part_023.jpg");
        pic[3][4]=new Image ("image_part_024.jpg");
        pic[4][4]=new Image ("image_part_025.jpg");

        // Set an image to be the blank tile
        blank=new Image ("b.JPG");

        // Create a GridPane to hold the tiles
        gridPane = new GridPane();

        // Create each of the tiles reusing an image view and add to gridPane
        // Use nested for loops
        for (int i=0;i<5;i++) {
            for (int j=0;j<5;j++) {
                ImageView imageView = new ImageView(pic[i][j]);
                gridPane.add(imageView,i,j);
            }
        }

        // Initialize rightPic. Create a 2D array of images and fill the 5x5 array with the correct tiles.
        // Use nested for loops
        rightPic=new Image[5][5];
        for (int i=0;i<5;i++) {
            for (int j=0;j<5;j++) {
                rightPic[i][j]=pic[i][j];
            }
        }

        // Create a BorderPane to hold the other layouts (vbox and grid)
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        vBox.setPrefWidth(250); 
        trackArea.setEditable(false);
        trackArea.setFocusTraversable(false);
        trackArea.setMouseTransparent(true);

        // Add the buttons to vbox
        vBox.getChildren().add(takeAway);
        vBox.getChildren().add(autoshuffle);
        vBox.getChildren().add(startSolve);
        vBox.getChildren().add(reset);
        vBox.getChildren().add(conc);
        vBox.getChildren().add(concstop);
        vBox.getChildren().add(trackArea);

        // Add vbox and grid pane to the border pane
        borderPane.setRight(vBox);
        borderPane.setCenter(gridPane);

        // Create a scene and add the border pane
        Scene scene = new Scene(borderPane, 800, 550);

        // Set up a key event handler
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    handleKeyPress (event);
                }
            });

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // run this section when a key is pressed
    private void handleKeyPress (KeyEvent event) {
        switch (event.getCode()) {
            case W:  //UP    
            // Make sure move is possible, blank is to above
            if (blankY < 4) {
                // Swap the blank tile with the tile below to appear moving up
                pic[blankX][blankY]=pic[blankX][blankY+1];
                replaceTile(blankX, blankY, pic[blankX][blankY]);
                blankY = blankY + 1;
                replaceTile(blankX, blankY, blank);
                // indicate that its a valid move
                validMove = true;
            }
            break;

            case S:  //DOWN
            // Make sure move is possible, blank is to below
            if (blankY > 0) {
                // Swap the blank tile with the tile below to appear moving up
                pic[blankX][blankY]=pic[blankX][blankY-1];
                replaceTile(blankX, blankY, pic[blankX][blankY]);
                blankY = blankY - 1;
                replaceTile(blankX, blankY, blank);
                // indicate that its a valid move
                validMove = true;
            }
            break;

            case A:  //LEFT
            // Make sure move is possible, blank is to left
            if (blankX < 4) {
                // Swap the blank tile with the tile to right to appear moving left
                pic[blankX][blankY]=pic[blankX+1][blankY];
                replaceTile(blankX, blankY, pic[blankX][blankY]);
                blankX = blankX + 1;
                replaceTile(blankX, blankY, blank);
                // indicate that its a valid move
                validMove = true;
            }
            break;

            case D: //RIGHT
            // Make sure move is possible, blank is to right
            if (blankX > 0) {
                // Swap the blank tile with the tile below to appear moving up
                pic[blankX][blankY]=pic[blankX-1][blankY];
                replaceTile(blankX, blankY, pic[blankX][blankY]);
                blankX = blankX - 1;
                replaceTile(blankX, blankY, blank);
                // indicate that its a valid move
                validMove = true;
            }
            break;
        }

        if (validMove) {
            // Count the step or the move
            if (turn.equals("solve")) {
                steps++;
            }
            else if (turn.equals("mix")) {
                moves++;
            }
            // Play the swap sound
            swap.play();
            // initialize validMove
            validMove = false;
        }
        
        // run this section if the user started to mix the tiles or solve the puzzle.
        if (turn.equals("solve")) {
            correct=0;

            // Compare the pic array to the rightPic array to check how many match
            for (int a=0;a<5;a++) {
                for(int b=0;b<5;b++) {
                    if (pic[a][b].equals(rightPic[a][b])) {
                        correct++;
                    }
                }
            }

            trackArea.setText("Mixing Moves Made: " + moves+"\n");
            trackArea.appendText("Solving Steps Made: "+steps);
        }
        else if (turn.equals("mix")) {
            //moves++;
            trackArea.setText("Mixing Moves Made: "+moves);
        }

        // run this section if every tile except for the blank tile is in the correct position. 
        if (correct==24) {
            //remove(solve);
            // Swap the blank tile with the missing tile to complete
            pic[blankX][blankY]=rightPic[blankX][blankY];
            replaceTile(blankX, blankY, pic[blankX][blankY]);
            cheer.play();
            startSolve.setDisable(false);
            takeAway.setDisable(false);
            autoshuffle.setDisable(false);
        }
    }

    /**
     * run this section if the user presses one of the buttons
     */
    private void buttonPressed (ActionEvent event)
    {
        //Gets info about the clicked button
        Button button = (Button) event.getSource();

        //Get the text on the button
        String text = button.getText();

        if (text.equals("Remove a Tile")) {

            // Play the suffle sound.
            shuffle.play();
            correct=0; // number of correct pieces

            // Generate a random place for the blank space
            blankX=(int)(Math.random()*5);
            blankY=(int)(Math.random()*5);
            pic[blankX][blankY]=blank;

            // Replace the current tile with a blank.
            replaceTile(blankX, blankY, blank);

            // Disable the take away button.
            takeAway.setDisable(true);

            //set flag to keep track of mix up moves
            turn = "mix"; 
            
            // Notify the user that the moves they make will be counted.
            trackArea.setText("Mixing Moves Made: " + moves+"\n");
            trackArea.appendText("Solving Steps Made: "+steps);
        }
        else if (text.equals("Start to Solve")){
            // Set flag to keep track of solve moves
            turn = "solve"; 
            
            // Play the start game sound.
            startgame.play();

            // Disable the solve button.
            startSolve.setDisable(true);
        }
        else if (text.equals("Reset")){
            // Initiatize the number of moves and steps for tracking
            moves=0;
            steps=0;
            // reset text
            trackArea.setText("Mixing Moves Made: " + moves+"\n");
            trackArea.appendText("Solving Steps Made: "+steps);

            for (int i=0;i<5;i++) {
                for (int j=0;j<5;j++) {
                    replaceTile(i, j, rightPic[i][j]);
                    pic[i][j]=rightPic[i][j];
                }
            }
            // Set the take away button to be available.
            takeAway.setDisable(false);
            // Set the solve button to be available.
            startSolve.setDisable(false);
            // Set the auto shuffle button to be available.
            autoshuffle.setDisable(false);
            // Stop the concentration music.
            dream.stop();
        }
        else if (text.equals("Auto shuffle")){
            for (int i = 0 ; i < 20 ; i++) {
                int randomNum = ThreadLocalRandom.current().nextInt(0, 4);
                switch(randomNum) {
                    case 0: // UP
                        // Make sure move is possible, blank is to above
                        if (blankY < 4) {
                            // Swap the blank tile with the tile below to appear moving up
                            pic[blankX][blankY]=pic[blankX][blankY+1];
                            replaceTile(blankX, blankY, pic[blankX][blankY]);
                            blankY = blankY + 1;
                            replaceTile(blankX, blankY, blank);
                        }
                        break;
                    
                    case 1:  //DOWN
                        // Make sure move is possible, blank is to below
                        if (blankY > 0) {
                            // Swap the blank tile with the tile below to appear moving up
                            pic[blankX][blankY]=pic[blankX][blankY-1];
                            replaceTile(blankX, blankY, pic[blankX][blankY]);
                            blankY = blankY - 1;
                            replaceTile(blankX, blankY, blank);
                        }
                        break;
    
                    case 2:  //LEFT
                        // Make sure move is possible, blank is to left
                        if (blankX < 4) {
                            // Swap the blank tile with the tile to right to appear moving left
                            pic[blankX][blankY]=pic[blankX+1][blankY];
                            replaceTile(blankX, blankY, pic[blankX][blankY]);
                            blankX = blankX + 1;
                            replaceTile(blankX, blankY, blank);
                        }
                        break;
    
                    case 3: //RIGHT
                        // Make sure move is possible, blank is to right
                        if (blankX > 0) {
                            // Swap the blank tile with the tile below to appear moving up
                            pic[blankX][blankY]=pic[blankX-1][blankY];
                            replaceTile(blankX, blankY, pic[blankX][blankY]);
                            blankX = blankX - 1;
                            replaceTile(blankX, blankY, blank);
                        }
                        break;
                }
            }

            // Display the number of random moves made
            moves=20;
            trackArea.setText("Mixing Moves Made: " + moves+"\n");
            trackArea.appendText("Solving Steps Made: "+steps);
            
            // Disable the auto shuffle button.
            autoshuffle.setDisable(true);
        }
        else if (text.equals("Concentration music")){
            // play "dream" song
            dream.play();
        }
        else if (text.equals("Stop music")){
            // stop "dream" song
            dream.stop();
        }
    }

    /**
     * This method will replace an image in the gridPane with a new one. Use as is.
     */
    private void replaceTile (int col, int row, Image image)
    {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == col) {
                result = node;
                gridPane.getChildren().remove(result);
                ImageView imageView = new ImageView(image);
                gridPane.add(imageView,col,row);
                break;
            }
        }
    }
}