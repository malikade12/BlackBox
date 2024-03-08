package com.program;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;

import static com.program.Main.*;

public class Arrow {
    private Polygon triangle;
    public static double[] midpoints;

    public static Polygon createArrow(double[] p1, double[] p2, Main.directions z){
        double midX = (p1[0] + p2[0] ) / 2;
        double midY = (p1[1] + p2[1] ) / 2;
        double y1 = p1[1];
        double x1 = p1[0];
        double y2 = p2[1];
        double x2 = p2[0];
        double x3 = 0;
        double y3 = 0;
        switch (z){
            case midRight -> {
                y3 = midY - 3;
                x3 = midX + 20;
                y1 += 9;
                y2 -= 5;
                break;
            }
            case southEast -> {
                y1 += 3;
                y2 -= 3;
                x1 = x1 - 7;
                x2 += 7;
                x3 = midX + 15;
                y3 = midY + 20;
            }
            case northEast -> {
                y1 += 3;
                y2 -= 3;
                x1 = x1 + 9;
                x2 -= 7;
                x3 = midX + 15;
                y3 = midY - 20;
            }
            case northWest -> {
                y1 += 4;
                y2 += 3;
                x2 -= 9;
                x3 = midX - 15;
                y3 = midY - 20;
            }
            case midLeft -> {
                y3 = midY + 3;
                x3 = midX - 20;
                y1 -= 9;
                y2 += 5;
            }
            case southWest -> {

                x1 -= 9;
                y1 -= 3;
                x2 += 9;
                y2 += 2;
                x3 = midX - 10;
                y3 = midY + 18;
            }

        }
        midpoints = new double[]{midX, midY};
        Polygon polygon = new Polygon();

        polygon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double directionAngle;
                switch (z) {
                    case midRight:
                        directionAngle = 0; // East
                        break;
                    case southEast:
                        directionAngle = Math.PI / 4 + 0.263; // Southeast
                        break;
                    case northEast:
                        directionAngle = -Math.PI / 4 - 0.263; // Northeast
                        break;
                    case northWest:
                        directionAngle = -3 * Math.PI / 4 + 0.263; // Northwest
                        break;
                    case midLeft:
                        directionAngle = Math.PI; // West
                        break;
                    case southWest:
                        directionAngle = 3 * Math.PI / 4 - 0.263; // Southwest
                        break;
                    default:
                        directionAngle = 0; // Default to east if direction is unknown
                }
                double rayLength = 87; // Adjust the desired length of the ray
                List<Line> rays = new ArrayList<>();

                double[] endPoint = findEndPoint(midX, midY, directionAngle, rayLength);
                double endX = endPoint[0];
                double endY = endPoint[1];

                Line newRay = new Line(midX, midY, endX, endY);
                System.out.println(endX +"and" + endY + "\n");
                newRay.setStroke(Color.RED);
                rays.add(newRay);
                root.getChildren().addAll(rays);
            }
        });

        polygon.getPoints().addAll(new Double[]{
                x1, y1,
                x2, y2,
                x3, y3,});
        polygon.setFill(Color.YELLOW);

        polygon.setFill(Color.YELLOW);
        return polygon;
    }
    private static double[] findEndPoint(double midX, double midY, double directionAngle, double rayLength) {
        double endX = midX + rayLength * Math.cos(directionAngle);
        double endY = midY + rayLength * Math.sin(directionAngle);

        boolean found = false; // Flag to track if the endpoint is found
        boolean inHexagon = false; // Flag to track if the endpoint is in a hexagon

        for (List<Hexagon> innerList : allHexagons) {
            for (Hexagon hexagon : innerList) {
                if (hexagon.shape.contains(endX, endY)) {
                    inHexagon = true; // Set the flag to true if the endpoint is in a hexagon
                    break; // Exit the loop since we found the hexagon
                }
            }
            if (inHexagon) {
                break; // Exit the outer loop if the endpoint is in a hexagon
            }
        }

        if (inHexagon) {
            // If the endpoint is in a hexagon, check if incrementing or decrementing would still keep it in a hexagon
            double nextEndX = endX + rayLength * Math.cos(directionAngle);
            double nextEndY = endY + rayLength * Math.sin(directionAngle);
            double slightOffset = 25; // Define a slight offset for checking

            // Check with slight offsets in both x and y directions
            boolean nextInHexagon = false;
            for (double offset : new double[]{-slightOffset, slightOffset}) {
                if (!nextInHexagon) {
                    for (List<Hexagon> innerList : allHexagons) {
                        for (Hexagon hexagon : innerList) {
                            if (hexagon.shape.contains(nextEndX + offset, nextEndY + offset)) {
                                nextInHexagon = true; // Set the flag to true if the next endpoint with offset is in a hexagon
                                break; // Exit the loop since we found the hexagon
                            }
                        }
                        if (nextInHexagon) {
                            break; // Exit the outer loop if the next endpoint with offset is in a hexagon
                        }
                    }
                }
            }

            if (nextInHexagon) {
                // If the next endpoint with slight offset is also in a hexagon, recursively call the method again
                return findEndPoint(endX, endY, directionAngle, rayLength);
            } else {
                // If incrementing or decrementing the endpoint would move it out of a hexagon, return the current endpoint
                return new double[]{endX, endY};
            }
        } else {
            // If the endpoint is not in a hexagon from the beginning, return the current endpoint
            return new double[]{endX, endY};
        }
    }


}