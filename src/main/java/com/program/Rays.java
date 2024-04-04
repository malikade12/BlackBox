package com.program;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rays {

    public static void MakeRay(double startX, double startY, int Xincrement, int Yincrement, Main.directions z){
        ArrayList<Double> rayPoints = new ArrayList<>();
        ArrayList<Integer> rowIds = new ArrayList<>(Arrays.asList(1, 6, 12, 19, 36, 44, 51, 57, 58, 59, 60, 61, 56, 50, 43, 35, 26, 18, 11, 2, 3, 4, 5));

        int currId = 0;
        while(!rowIds.contains(currId)) {
            for (List<Hexagon> rows : Main.allHexagons) {
                for (Hexagon hex : rows) {
                    if (hex.shape.contains(startX, startY)) {
                        currId = 0;
                    }
                }
            }

            rayPoints.add(startX);
            rayPoints.add(startY);
            System.out.println(startX + "  " + startY);
            startX += Xincrement;
            startY += Yincrement;
        }



        double[] rayPointsArray = new double[rayPoints.size()+2];
        for (int i = 0; i < rayPoints.size(); i++) {
            rayPointsArray[i] = rayPoints.get(i);
        }
        Polyline completeRay = new Polyline(rayPointsArray); // constructs ray connecting rayPoints
        completeRay.setStroke(Color.CYAN);
        completeRay.setStrokeWidth(7);
        Main.root.getChildren().add(completeRay);


    }

}
