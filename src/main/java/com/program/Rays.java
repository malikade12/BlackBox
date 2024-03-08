package com.program;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;

import static javafx.scene.CacheHint.SPEED;

public class Rays {

   public static void MakeRay(int x, int y, double[] start, Main.directions z){
       ArrayList<Double> rayPoints = new ArrayList<>();
         rayPoints.add(start[0]);
         rayPoints.add(start[1]);
           Polyline completeRay = new Polyline(rayPoints); // constructs ray connecting points
           completeRay.setStroke(Color.CYAN);
           completeRay.setStrokeWidth(7);


   }

}
