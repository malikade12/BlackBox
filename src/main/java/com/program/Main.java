package com.program;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

public class Main extends Application {
    public static  Group root;
    static List<Atoms> allAtoms;
    static List<Arrow> allArrows;
    static List<List<Hexagon>> allHexagons;
    static List<Label> allNumbers;
    static public List<Circle> markerList = new ArrayList<>();
    public static boolean markerEnabled = false; // Flag to track whether marker functionality is enabled
    private int curr = 0;
    public static int ExScore;
    public static int SetScore;
    public static boolean EndOfRound = false;
    public static Scene scene;
    public static Map<Integer, Integer> SetterRayPoints;
    public static Map<Integer, Integer> ActualRayPoints;
    public static boolean SetterSwitched = false;
    public static boolean IsSetter = true;
    public static ArrayList<ArrayList<Line>> rays;
    public static int roundcount = 0;


    static enum directions  {
        southEast, southWest,northEast, northWest, east, west; }

    public void setMarkerEnabled(boolean markerEnabled) {
        this.markerEnabled = markerEnabled;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        InitGame.Getnames();
        //START SCENE
        Parent startRoot = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        Scene startScene = new Scene(startRoot,1400,800);
        root = new Group();
        root.setMouseTransparent(false);

        ChangeView test = new ChangeView();
        test.Guess();
        test.experimenterButton();
        test.setterButton();
        test.EndRound();




        VBox container = new VBox(10); // 10 pixels spacing between components
        container.setPadding(new Insets(10));

        container.getChildren().add(test.getButton1());

        Pane spacer = new Pane();
        spacer.setMinHeight(10); // Set the desired space height
        container.getChildren().add(spacer);

        container.getChildren().add(test.getButton2());
        container.getChildren().add(test.getButton3());
        container.getChildren().add(test.getButton4());


        root.getChildren().add(container);

        allAtoms = new ArrayList<>();
        allHexagons = new ArrayList<>();
        allArrows = new ArrayList<>();
        allNumbers = new ArrayList<>();
        rays = new ArrayList<>();
        ActualRayPoints = new HashMap<>();
        SetterRayPoints = new HashMap<>();

        Scene scene = new Scene(root, 1400, 800, Color.BLACK);



        test.getButton2().fire();
        Color[] colors = {Color.PURPLE, Color.HOTPINK, Color.ORANGE}; // Define your colors here
        startScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.Q && !IsSetter) { // Change this to your desired key combination
                curr = 0;
                toggleMarkerFunctionality();
                event.consume(); // Consume the event to prevent it from being processed further
            } else if (event.getCode() == KeyCode.X) { // Change this to your desired key combination
                curr = 1;
                toggleMarkerFunctionality();
                event.consume(); // Consume the event to prevent it from being processed further
            }
        });
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.isStillSincePress() && markerEnabled && !IsSetter) { // Check if the mouse click was not part of a drag gesture
                double x = event.getX();
                double y = event.getY();
                Color color = colors[curr];
                drawMarker(root, x, y, color);
                if (roundcount == 0)ExScore++;
                else SetScore++;

            }
        });
       InitGame.print();


        primaryStage.setScene(startScene);
        primaryStage.setTitle("BlackBox Alpha");
        primaryStage.show();
    }
    private void toggleMarkerFunctionality() {
        markerEnabled = !markerEnabled; // Toggle the marker functionality flag
    }
    private void drawMarker(Group group, double x, double y, Color color) {
        Circle circle = new Circle(x, y, 5); // Adjust the radius as needed
        circle.setFill(color);
        markerList.add(circle); // Add the circle to the markerList
        group.getChildren().add(circle);
    }
    public static void main(String[] args) {
        launch(args);
    }



}