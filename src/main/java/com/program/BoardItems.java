package com.program;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardItems {
    static List<Atoms> allAtoms;
    static List<Arrow> allArrows;
    static String winnerMessage;
    static List<List<Hexagon>> allHexagons;
    static public List<Circle> markerList = new ArrayList<>();
    public static boolean markerEnabled = false; // Flag to track whether marker functionality is enabled
    public static int PlayerOneScore =0; //Experimenter score
    public static int PlayerTwoScore =0; //Setter score
    public static boolean endRound = false;
    public static Scene scene;
    public static Map<Integer, Integer> setterRayPoints;
    public static Map<Integer, Integer> actualRayPoints;
    public static boolean setterSwitched = false;
    public static boolean isSetter = true;
    public static ArrayList<ArrayList<Line>> rays;
    public static int roundCount = 0;
    public static TextArea textArea;
    public static int[] PlayerOneGuesses = {0, 0};
    public static int[] PlayerTwoGuesses = {0, 0};

     enum directions {
        southEast, southWest, northEast, northWest, east, west;
    }


    public static void addLog(String logMessage) {//used to log messages onscreen
        textArea.appendText(logMessage + "\n");
        textArea.positionCaret(textArea.getText().length());
        textArea.setScrollTop(Double.MAX_VALUE);

    }

    static void toggleMarkerFunctionality() {
        BoardItems.markerEnabled = !BoardItems.markerEnabled; // Toggle the marker functionality flag
    }

    static void drawMarker(Group group, double x, double y, Color color) {
        Circle circle = new Circle(x, y, 5); // Adjust the radius as needed
        circle.setFill(color);
        BoardItems.markerList.add(circle); // Add the circle to the markerList
        group.getChildren().add(circle);
    }


}
