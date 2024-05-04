package com.program;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardItems {
    static List<Atoms> allAtoms;
    static List<Arrow> allArrows;
    static List<List<Hexagon>> allHexagons;
    static List<Label> allNumbers;
    static public List<Circle> markerList = new ArrayList<>();
    public static boolean markerEnabled = false; // Flag to track whether marker functionality is enabled
    public static int expScore; //Experimenter score
    public static int setScore; //Setter score
    public static boolean endRound = false;
    public static Scene scene;
    public static Map<Integer, Integer> setterRayPoints;
    public static Map<Integer, Integer> actualRayPoints;
    public static boolean setterSwitched = false;
    public static boolean isSetter = true;
    public static ArrayList<ArrayList<Line>> rays;
    public static int roundCount = 0;
    public static TextArea textArea;

    static enum directions {
        southEast, southWest, northEast, northWest, east, west;
    }

    public void setMarkerEnabled(boolean markerEnabled) {
        this.markerEnabled = markerEnabled;
    }

    public static void addLog(String logMessage) {//used to log messages onscreen
        textArea.appendText(logMessage + "\n");
        textArea.positionCaret(textArea.getText().length());

    }


}
