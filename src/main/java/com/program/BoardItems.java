package com.program;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardItems {
    static List<Atoms> AllAtoms;
    static List<Arrow> AllArrows;
    static String WinnerMessage;
    static List<List<Hexagon>> AllHexagons;
    static public List<Circle> MarkerList = new ArrayList<>();
    public static boolean MarkerEnabled = false; // Flag to track whether marker functionality is enabled
    public static int PlayerOneScore =0; //Experimenter score
    public static int PlayerTwoScore =0; //Setter score
    public static boolean EndRound = false;
    public static Scene Scene;
    public static Map<Integer, Integer> SetterRayPoints;
    public static Map<Integer, Integer> ActualRayPoints;
    public static boolean SetterSwitched = false;
    public static boolean IsSetter = true;
    public static ArrayList<ArrayList<Line>> Rays;
    public static int RoundCount = 0;
    public static TextArea TextArea;
    public static int[] PlayerOneGuesses = {0, 0};
    public static int[] PlayerTwoGuesses = {0, 0};
    public static int MarkerColor = 0;
    public static void InitVariables(){
        AllAtoms = new ArrayList<>();
        AllHexagons = new ArrayList<>();
        AllArrows = new ArrayList<>();
        Rays = new ArrayList<>();
        ActualRayPoints = new HashMap<>();
        SetterRayPoints = new HashMap<>();
    }


    enum directions {
         SouthEast, SouthWest, NorthEast, NorthWest, East, West;
    }


    public static void addLog(String LogMessage) {//used to log messages onscreen
        TextArea.appendText(LogMessage + "\n");
        TextArea.positionCaret(TextArea.getText().length());
        //textArea.setScrollTop(Double.MAX_VALUE);

    }

    static void toggleMarkerFunctionality() {
        BoardItems.MarkerEnabled = !BoardItems.MarkerEnabled; // Toggle the marker functionality flag
    }

    static void drawMarker(Group group, double x, double y, Color color) {
        Circle circle = new Circle(x, y, 5); // Adjust the radius as needed
        circle.setFill(color);
        BoardItems.MarkerList.add(circle); // Add the circle to the markerList
        group.getChildren().add(circle);
    }


}
