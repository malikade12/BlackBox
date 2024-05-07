package com.program;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static com.program.BoardItems.*;

public class Main extends Application {
    public static Group Root;
    public static Stage Home;

    @Override
    public void start(Stage primaryStage) throws IOException {
        InitGame.Getnames();
        //START SCENE
        Parent startRoot = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        Scene startScene = new Scene(startRoot, 1400, 800);
        Home = primaryStage;
        Root = new Group();
        Root.setMouseTransparent(false);

        TextArea = new TextArea();
        TextArea.setEditable(false);
        TextArea.setWrapText(true);
        addLog("*****TEXT CHANNEL*****");


        primaryStage.setScene(Scene);
        primaryStage.setTitle("Log Viewer");
        primaryStage.show();

        ChangeView test = new ChangeView();

        VBox container = new VBox(10); // 10 pixels spacing between components
        container.setPadding(new Insets(10));

        container.getChildren().add(test.GetExperimenterButton());

        Pane spacer = new Pane();
        spacer.setMinHeight(10); // Set the desired space height
        container.getChildren().add(spacer);
        Pane spacer2 = new Pane();
        spacer2.setMinHeight(300); // Set the desired space height


        container.getChildren().add(test.GetSetterButton());
        container.getChildren().add(test.GetGuessButton());
        container.getChildren().add(test.GetEndButton());
        container.getChildren().add(ChangeView.GetHelpButton());
        container.getChildren().add(spacer2);
        container.getChildren().add(TextArea);


        Root.getChildren().add(container);
        InitVariables();


        Scene scene = new Scene(Root, 1400, 800, Color.BLACK);


        test.GetSetterButton().fire();
        Color[] colors = {Color.PURPLE, Color.HOTPINK, Color.ORANGE}; // Define your colors here
        startScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.Q && !IsSetter) { // Change this to your desired key combination
                MarkerColor = 0;
                toggleMarkerFunctionality();
                event.consume(); // Consume the event to prevent it from being processed further
            } else if (event.getCode() == KeyCode.X) { // Change this to your desired key combination
                MarkerColor = 1;
                toggleMarkerFunctionality();
                event.consume(); // Consume the event to prevent it from being processed further
            }
        });
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.isStillSincePress() && MarkerEnabled && !IsSetter) { // Check if the mouse click was not part of a drag gesture
                double x = event.getX();
                double y = event.getY();
                Color color = colors[MarkerColor];
                drawMarker(Root, x, y, color);
                if (RoundCount == 0) PlayerOneScore++;
                else PlayerTwoScore++;

            }
        });
        InitGame.print();

        primaryStage.setScene(startScene);
        primaryStage.setTitle("BlackBox Alpha");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}