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
    ArrayList<Integer> Xpoints  = new ArrayList<>();
    ArrayList<Integer> Ypoints  = new ArrayList<>();
    ArrayList<Integer> rayPoints = new ArrayList<>();
    double[] diagRayPoints = new double[20];
    double[] diagRayPoints2 = new double[20];
    int index;

    List<List<Polygon>> reverseList = new ArrayList<>(allHexagons);//reverse

    public List<List<Polygon>> reverseNestedList(List<List<Polygon>> nestedList) {
        // Reverse the order of elements in each inner list
        for (List<Polygon> innerList : nestedList) {
            Collections.reverse(innerList);
        }

        // Reverse the order of the inner lists themselves
        Collections.reverse(nestedList);

        return nestedList;
    }

    public List<List<Polygon>> miniReverseNestedList(List<List<Polygon>> nestedList) {
        // Reverse the order of elements in each inner list
        for (List<Polygon> innerList : nestedList) {
            Collections.reverse(innerList);
        }



        return nestedList;
    }

    public void makeHorizontalRay(Group root, int initialX, int y) {//REWORKED
        int x = initialX; // Use a separate variable for modification inside the loop
        index = 0;

        for (List<Polygon> innerList : allHexagons) {
            for (Polygon polygon : innerList) {
                if (polygon.contains(x, y)) {
                    for (Atoms atom : allAtoms) {
                        if (atom.orbit.contains(x, y)) {
                            System.out.println("Ray has entered orbit at x=" + x + " y=" + y);
                        }
                    }

                    rayPoints.add(x);
                    rayPoints.add(y);

                    System.out.println("test presence at: x=" + x + " y=" + y);

                    x += 87; // Incrementing x value for the next iteration
                }
            }
        }

        double[] rayPointsArray = new double[rayPoints.size()];
        for (int i = 0; i < rayPoints.size(); i++) {
            rayPointsArray[i] = rayPoints.get(i);
        }
        pointRay(root, initialX, y, rayPointsArray);
    }

    private void pointRay(Group root, int x, int y, double[] rayPoints) {
        rayPoints[index] = x;
        index++;
        rayPoints[index] = y;

        Polyline completeRay = new Polyline(rayPoints); // constructs ray connecting points
        System.out.println(rayPoints);
        completeRay.setStroke(Color.CYAN);
        completeRay.setStrokeWidth(7);
        root.getChildren().add(completeRay);
    }

    public void makeDiagonalRay(Group root, int x, int y ) {//only works diagonally down right **NEED TO CHANGE
        index = 0;

        for (List<Polygon> innerList : allHexagons) {
            // Iterate through the inner list
            for (Polygon polygon : innerList) {
                // Perform operations on each Polygon
                if (polygon.contains(x,y)){
                    diagRayPoints[index] = x;
                    index++;
                    diagRayPoints[index] = y;
                    index++;

                    System.out.println("test presence at: x="+x+" y="+y);

                    x+=44;
                    y+=76;
                }
            }
        }

        pointRay(root, x, y, diagRayPoints);
    }

    public void makeDiagonalRayUpRight(Group root, int x, int y ) {//only works diagonally up right **
        List<List<Polygon>> reverseHexagons = reverseNestedList(allHexagons);

        index = 0;

        for (List<Polygon> innerList : reverseHexagons) {
            // Iterate through the inner list
            for (Polygon polygon : innerList) {
                // Perform operations on each Polygon
                if (polygon.contains(x,y)){
                    diagRayPoints2[index] = x;
                    index++;
                    diagRayPoints2[index] = y;
                    index++;

                    System.out.println("test presence at: x="+x+" y="+y);

                    x+=44;
                    y-=75;
                }
            }
        }
        pointRay(root, x, y, diagRayPoints2);
    }



    public void makeHorizontalRay2(Group root, int initialX, int y) {//REWORKED
        List<List<Polygon>> reverseHexagons = miniReverseNestedList(allHexagons);

        int x = initialX; // Use a separate variable for modification inside the loop
        index = 0;

        for (List<Polygon> innerList : reverseHexagons) {
            for (Polygon polygon : innerList) {
                System.out.println(polygon.getLayoutX());//ISSUE HERE FOR SOME REASON IDK
                if (polygon.contains(x, y)) {
                    for (Atoms atom : allAtoms) {
                        if (atom.orbit.contains(x, y)) {
                            System.out.println("Ray has entered orbit at x=" + x + " y=" + y);
                        }
                    }

                    rayPoints.add(x);
                    rayPoints.add(y);
                    Circle point = new Circle(x, y, 15, Color.RED);
                    root.getChildren().add(point);



                    System.out.println("test presence at: x=" + x + " y=" + y);

                    x -= 87; // Incrementing x value for the next iteration
                }
            }
        }

}}
