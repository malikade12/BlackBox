package com.program;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    static List<List<Hexagon>> allHexagons;
    static List<Label> allNumbers;
    static public List<Circle> markerList = new ArrayList<>();
    public static boolean markerEnabled = false; // Flag to track whether marker functionality is enabled
    public static int ExScore; //Experimenter score
    public static int SetScore; //Setter score
    public static boolean EndOfRound = false;
    public static Scene scene;
    public static Map<Integer, Integer> SetterRayPoints;
    public static Map<Integer, Integer> ActualRayPoints;
    public static boolean SetterSwitched = false;
    public static boolean IsSetter = true;
    public static ArrayList<ArrayList<Line>> rays;
    public static int roundcount = 0;
    public static TextArea logTextArea;

    static enum directions  {
        southEast, southWest,northEast, northWest, east, west; }

    public void setMarkerEnabled(boolean markerEnabled) {
        this.markerEnabled = markerEnabled;
    }

    public static void addLog(String logMessage) {//used to log messages onscreen
        logTextArea.appendText(logMessage + "\n");
        logTextArea.positionCaret(logTextArea.getText().length());

    }


}
