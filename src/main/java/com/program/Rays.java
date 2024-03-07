package com.program;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.program.Main.allAtoms;
import static com.program.Main.allHexagons;

public class Rays {

    public List<List<Hexagon>> reverseNestedList(List<List<Hexagon>> nestedList) {
        // Reverse the order of elements in each inner list
        for (List<Hexagon> innerList : nestedList) {
            Collections.reverse(innerList);
        }
        // Reverse the order of the inner lists themselves
        Collections.reverse(nestedList);

        return nestedList;
    }

    public List<List<Hexagon>> miniReverseNestedList(List<List<Hexagon>> nestedList) {
        // Reverse the order of elements in each inner list
        for (List<Hexagon> innerList : nestedList) {
            Collections.reverse(innerList);
        }
        return nestedList;
    }

    private void pointRay(Group root, int x, int y, double[] rayPoints) {//helper
        int index = rayPoints.length -2;
        rayPoints[index] = x;
        index++;
        rayPoints[index] = y;

        Polyline completeRay = new Polyline(rayPoints); // constructs ray connecting points
        completeRay.setStroke(Color.CYAN);
        completeRay.setStrokeWidth(7);
        root.getChildren().add(completeRay);
    }

    //NEAR DONE
    public void makeHorizontalRay(Group root, int x, int y) {//REWORKED
        // Use a separate variable for modification inside the loop
        ArrayList<Integer> rayPoints = new ArrayList<>();//Horizontal


        for (List<Hexagon> innerList : allHexagons) {
            System.out.println("1");
            for (Hexagon hexagon : innerList) {

                if ((x>370 && y>50) && (x<1160 && y <754)){

                    rayPoints.add(x);
                    rayPoints.add(y);

                    System.out.println("test presence at: x=" + x + " y=" + y);

                    x += 87; // Incrementing x value for the next iteration
                }
            }
        }

        double[] rayPointsArray = new double[rayPoints.size()+2];
        for (int i = 0; i < rayPoints.size(); i++) {
            rayPointsArray[i] = rayPoints.get(i);
        }
        pointRay(root, x, y, rayPointsArray);
    }


    //NEAR DONE
    public void makeDiagonalRayUpRight(Group root, int x, int y ) {//only works diagonally up right **
        ArrayList<Integer> rayPoints2 = new ArrayList<>();//UP->Right

        List<List<Hexagon>> reverseHexagons = reverseNestedList(allHexagons);

        for (List<Hexagon> innerList : reverseHexagons) {
            // Iterate through the inner list
            for (Hexagon hexagon : innerList) {
                // Perform operations on each Polygon
                if ((x>370 && y>50) && (x<1160 && y <754)){
                    rayPoints2.add(x);
                    rayPoints2.add(y);

                    System.out.println("test presence at: x="+x+" y="+y);

                    x+=44;
                    y-=75;
                }
            }
        }
        double[] rayPointsArray = new double[rayPoints2.size()+2];
        for (int i = 0; i < rayPoints2.size(); i++) {
            rayPointsArray[i] = rayPoints2.get(i);
        }

        pointRay(root, x, y, rayPointsArray);
    }

    public void makeDiagonalDownRay(Group root, int x, int y ) {//only works diagonally down right
        ArrayList<Integer> rayPoints3 = new ArrayList<>();//Down->Right

        for (List<Hexagon> innerList : allHexagons) {
            // Iterate through the inner list
            for (Hexagon hexagon : innerList) {
                // Perform operations on each Polygon
                if ((x>370 && y>50) && (x<1160 && y <754)){
                    rayPoints3.add(x);
                    rayPoints3.add(y);

                    System.out.println("test presence at: x="+x+" y="+y);

                    x+=44;
                    y+=76;
                }
            }
        }
        double[] rayPointsArray = new double[rayPoints3.size()+2];
        for (int i = 0; i < rayPoints3.size(); i++) {
            rayPointsArray[i] = rayPoints3.get(i);
        }

        pointRay(root, x, y, rayPointsArray);
    }


    public void makeHorizontalRay2(Group root, int initialX, int y) {//REWORKED
        ArrayList<Integer> rayPoints4 = new ArrayList<>();//Horizontal-> LEFT

        List<List<Hexagon>> reverseHexagons = miniReverseNestedList(allHexagons);

        int x = initialX; // Use a separate variable for modification inside the loop
        int index = 0;

        for (List<Hexagon> innerList : reverseHexagons) {
            for (Hexagon hexagon : innerList) {
                if ((x>370 && y>50) && (x<1165 && y <754)){

                    rayPoints4.add(x);
                    rayPoints4.add(y);

                    System.out.println("test presence at: x=" + x + " y=" + y);

                    x -= 87; // Incrementing x value for the next iteration
                }
            }
        }
        double[] rayPointsArray = new double[rayPoints4.size()+2];
        for (int i = 0; i < rayPoints4.size(); i++) {
            rayPointsArray[i] = rayPoints4.get(i);
        }

        pointRay(root, x, y, rayPointsArray);
    }



    public void makeDiagonalDownLeft(Group root, int x, int y ) {//only works diagonally up right **
        ArrayList<Integer> rayPoints5 = new ArrayList<>();//Down-> LEFT

        List<List<Hexagon>> reverseHexagons = miniReverseNestedList(allHexagons);

        for (List<Hexagon> innerList : reverseHexagons) {
            // Iterate through the inner list
            for (Hexagon hexagon : innerList) {
                // Perform operations on each Polygon
                if ((x>370 && y>50) && (x<1160 && y <754)){
                    rayPoints5.add(x);
                    rayPoints5.add(y);

                    System.out.println("test presence at: x="+x+" y="+y);

                    x-=44;
                    y+=75;
                }
            }
        }
        double[] rayPointsArray = new double[rayPoints5.size()+2];
        for (int i = 0; i < rayPoints5.size(); i++) {
            rayPointsArray[i] = rayPoints5.get(i);
        }

        pointRay(root, x, y, rayPointsArray);
    }

    public void makeDiagonalUpLeft(Group root, int x, int y ) {//only works diagonally up left
        ArrayList<Integer> rayPoints6 = new ArrayList<>();//Up-> LEFT

        List<List<Hexagon>> reverseHexagons = reverseNestedList(allHexagons);
        for (List<Hexagon> innerList : reverseHexagons) {
            // Iterate through the inner list
            for (Hexagon hexagon : innerList) {
                // Perform operations on each Polygon
                if ((x>370 && y>50) && (x<1160 && y <754)){
                    rayPoints6.add(x);
                    rayPoints6.add(y);


                    System.out.println("test presence at: x="+x+" y="+y);

                    x-=44;
                    y-=76;
                }
            }
        }
        double[] rayPointsArray = new double[rayPoints6.size()+2];
        for (int i = 0; i < rayPoints6.size(); i++) {
            rayPointsArray[i] = rayPoints6.get(i);
        }

        pointRay(root, x, y, rayPointsArray);
    }



}