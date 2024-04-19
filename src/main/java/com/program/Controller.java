package com.program;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
}
