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
import javafx.scene.shape.*;

import java.io.IOException;
import java.util.*;

import static com.program.BoardItems.*;

public class Main extends Application {
    public static Group root;
    private int markerColor = 0;
    public static Stage home;

    @Override
    public void start(Stage primaryStage) throws IOException {
        InitGame.Getnames();
        //START SCENE
        Parent startRoot = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        Scene startScene = new Scene(startRoot, 1400, 800);
        home = primaryStage;
        root = new Group();
        root.setMouseTransparent(false);

        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        addLog("*****TEXT CHANNEL*****");


        primaryStage.setScene(scene);
        primaryStage.setTitle("Log Viewer");
        primaryStage.show();

        ChangeView test = new ChangeView();
        test.Guess();
        test.experimenterButton();
        test.setterButton();
        test.EndRound();
        test.HelpPage();

        VBox container = new VBox(10); // 10 pixels spacing between components
        container.setPadding(new Insets(10));

        container.getChildren().add(test.getExperimenterButton());

        Pane spacer = new Pane();
        spacer.setMinHeight(10); // Set the desired space height
        container.getChildren().add(spacer);
        Pane spacer2 = new Pane();
        spacer2.setMinHeight(300); // Set the desired space height


        container.getChildren().add(test.getSetterButton());
        container.getChildren().add(test.getGuessButton());
        container.getChildren().add(test.getEndButton());
        container.getChildren().add(ChangeView.getHelpButton());
        container.getChildren().add(spacer2);
        container.getChildren().add(textArea);


        root.getChildren().add(container);

        BoardItems.allAtoms = new ArrayList<>();
        BoardItems.allHexagons = new ArrayList<>();
        BoardItems.allArrows = new ArrayList<>();
        rays = new ArrayList<>();
        actualRayPoints = new HashMap<>();
        setterRayPoints = new HashMap<>();

        Scene scene = new Scene(root, 1400, 800, Color.BLACK);


        test.getSetterButton().fire();
        Color[] colors = {Color.PURPLE, Color.HOTPINK, Color.ORANGE}; // Define your colors here
        startScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.Q && !isSetter) { // Change this to your desired key combination
                markerColor = 0;
                toggleMarkerFunctionality();
                event.consume(); // Consume the event to prevent it from being processed further
            } else if (event.getCode() == KeyCode.X) { // Change this to your desired key combination
                markerColor = 1;
                toggleMarkerFunctionality();
                event.consume(); // Consume the event to prevent it from being processed further
            }
        });
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.isStillSincePress() && BoardItems.markerEnabled && !isSetter) { // Check if the mouse click was not part of a drag gesture
                double x = event.getX();
                double y = event.getY();
                Color color = colors[markerColor];
                drawMarker(root, x, y, color);
                if (roundCount == 0) playerOneScore++;
                else playerTwoScore++;

            }
        });
        InitGame.print();


        primaryStage.setScene(startScene);
        primaryStage.setTitle("BlackBox Alpha");
        primaryStage.show();
    }

    private void toggleMarkerFunctionality() {
        BoardItems.markerEnabled = !BoardItems.markerEnabled; // Toggle the marker functionality flag
    }

    private void drawMarker(Group group, double x, double y, Color color) {
        Circle circle = new Circle(x, y, 5); // Adjust the radius as needed
        circle.setFill(color);
        BoardItems.markerList.add(circle); // Add the circle to the markerList
        group.getChildren().add(circle);
    }

    public static void main(String[] args) {
        launch(args);
    }


}