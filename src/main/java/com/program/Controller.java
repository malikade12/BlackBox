package com.program;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    private Stage Stage;
    private Scene Scene;

    //Event to change to game scene on button press
    public void EnterGame(ActionEvent event) throws IOException {
        Parent root = Main.Root;
        Stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Stage.setScene(Main.Root.getScene());
        Stage.show();
    }
    //Event to change scene to Rules
    public void switchToRules(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Rules.fxml"));
        Stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene = new Scene(root,1400,800);
        Scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Stage.setScene(Scene);
        Stage.show();

    }
    //Method used to close game
    public void Quit(ActionEvent event) throws IOException {
        Platform.exit();

    }
    //Method to switch scet to the menu
    public void switchToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        Scene = new Scene(root,1400,800);
        Scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Stage.setScene(Scene);
        Stage.show();
    }

    @FXML
    Label playerOne = new Label("Player One scored "+String.valueOf(BoardItems.PlayerTwoScore)+" points");
    @FXML
    Label playerTwo = new Label("Player Two scored" +String.valueOf(BoardItems.PlayerOneScore)+" points");
    @FXML
    Label pOneGuess = new Label(BoardItems.WinnerMessage);
    @FXML
    Label pTwoGuess = new Label(BoardItems.WinnerMessage);
    @FXML
    Label winnerText = new Label(BoardItems.WinnerMessage);



    //Switched to end screen on call
    public void switchToEndScreen() throws IOException {
        playerOne.setText(String.valueOf(BoardItems.PlayerOneScore));
        playerTwo.setText(String.valueOf(BoardItems.PlayerTwoScore));

        Parent root = FXMLLoader.load(getClass().getResource("EndScreen.fxml"));
        Stage = Main.Home;
        Scene = new Scene(root,1400,800);
        Scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Stage.setScene(Scene);
        Stage.show();

    }
//Initialize all variables before scene switches
    @FXML
    public void initialize() {
        // Initialize playerOne and playerTwo labels here
        playerOne.setText(InitGame.PlayerTwoName +" scored "+String.valueOf(BoardItems.PlayerTwoScore)+" points");
        playerTwo.setText(InitGame.PlayerOneName +" scored " +String.valueOf(BoardItems.PlayerOneScore)+" points");
        pOneGuess.setText(InitGame.PlayerOneName + " guessed "+BoardItems.PlayerOneGuesses[0]+" times\n\n"+BoardItems.PlayerOneGuesses[1]+" guesses were correct");
        pTwoGuess.setText(InitGame.PlayerTwoName + " guessed "+BoardItems.PlayerTwoGuesses[0]+" times\n\n"+BoardItems.PlayerTwoGuesses[1]+" guesses were correct");

        winnerText.setText(BoardItems.WinnerMessage);
    }
}
