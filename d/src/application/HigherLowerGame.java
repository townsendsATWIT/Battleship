package application;

import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class HigherLowerGame extends Application {

 
    Button Higher;
   Button Lower;
    Rectangle resultBox;
    Text resultText;

    @Override
    public void start(Stage primaryStage) {
        //initialize buttons and pane
    	StackPane root = new StackPane();
        Higher = new Button("Higher");
        Higher.setOnAction(event -> checkGuess(true));
        StackPane.setAlignment(Higher, Pos.BOTTOM_RIGHT);
        Lower = new Button("Lower");
        StackPane.setAlignment(Lower, Pos.BOTTOM_LEFT);
        Lower.setOnAction(event -> checkGuess(false));
        //makes box in middle 
        resultBox = new Rectangle(275, 275);
        resultBox.setFill(Color.BLACK);
        //text for results
        resultText = new Text("");
        resultText.setFont(Font.font(24));
        StackPane.setAlignment(resultText, Pos.CENTER);
        //title at top
        Text topText = new Text("Higher or Lower?");
        topText.setFont(Font.font(24));
        StackPane.setAlignment(topText, Pos.TOP_CENTER);
        //text for bottom middle
        Text bottomText = new Text("Guess!");
        bottomText.setFont(Font.font(24));
        StackPane.setAlignment(bottomText, Pos.BOTTOM_CENTER);

        //add all to pane
        
        root.getChildren().addAll(resultBox, resultText, Higher, Lower, topText, bottomText);
        
        
        
        
//set scene
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setTitle("Higher or Lower?");
        primaryStage.setScene(scene);
        primaryStage.show();

        start();
    }
//start the game, iinitialize both buttons to be enabled, color in box 
    private void start() {
        resultBox.setFill(Color.BLACK);
        resultText.setText("");
    }
// when a button is pressed, game goes here. first generate number, then checks what button the player pressed, then confirms win or loss
    private void checkGuess(boolean selectedHigh) {
        
            

            Random random = new Random();
            int value = random.nextInt(6)+1 ;
            

            if ((selectedHigh && value >= 4) || (!selectedHigh && value <= 3)) {
                
                resultBox.setFill(Color.GREEN);
                resultText.setText("You won!Number was : " + value);
                
            } else {
                
                resultBox.setFill(Color.RED);
                resultText.setText("You lost. Number was : " + value);
                
            }

            
        
    }
//run 
    public static void main(String[] args) {
        launch(args);
    }
}
