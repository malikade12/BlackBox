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
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void EnterGame(ActionEvent event) throws IOException {
        Parent root = Main.root;
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(Main.root.getScene());
        stage.show();
    }
    public void switchToRules(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Rules.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,1400,800);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

    }
    public void Quit(ActionEvent event) throws IOException {
        Platform.exit();

    }
    public void switchToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        scene  = new Scene(root,1400,800);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    Label playerOne = new Label("Player One scored "+String.valueOf(BoardItems.setScore)+" points");
    @FXML
    Label playerTwo = new Label("Player Two scored" +String.valueOf(BoardItems.expScore)+" points");

    @FXML
    Label winnerText = new Label(BoardItems.winnerMessage);



    public void switchToEndScreen() throws IOException {
        playerOne.setText(String.valueOf(BoardItems.expScore));
        playerTwo.setText(String.valueOf(BoardItems.setScore));

        Parent root = FXMLLoader.load(getClass().getResource("EndScreen.fxml"));
        stage = Main.home;
        scene = new Scene(root,1400,800);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void initialize() {
        // Initialize playerOne and playerTwo labels here
        playerOne.setText(InitGame.setterName+" scored "+String.valueOf(BoardItems.setScore)+" points");
        playerTwo.setText(InitGame.experimenterName+" scored " +String.valueOf(BoardItems.expScore)+" points");
        winnerText.setText(BoardItems.winnerMessage);
    }
}
