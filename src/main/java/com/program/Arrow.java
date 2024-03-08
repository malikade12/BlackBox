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
                double x3 = polygon.getPoints().get(4);
                double y3 = polygon.getPoints().get(5);

                // Calculate the midpoint of the line segment (x1, y1) to (x2, y2)
                double centerX = midX;
                double centerY = midY;

                double directionAngle;
                switch (z) {
                    case midRight:
                        directionAngle = 0; // East
                        break;
                    case southEast:
                        directionAngle = Math.PI / 4 + 0.245; // Southeast
                        break;
                    case northEast:
                        directionAngle = -Math.PI / 4 - 0.245; // Northeast
                        break;
                    case northWest:
                        directionAngle = -3 * Math.PI / 4 + 0.245; // Northwest
                        break;
                    case midLeft:
                        directionAngle = Math.PI; // West
                        break;
                    case southWest:
                        directionAngle = 3 * Math.PI / 4 - 0.245; // Southwest
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
                newRay.setStroke(Color.RED); // Customize the ray color
                rays.add(newRay);
                // Add all rays to the scene
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
        for (List<Hexagon> innerList : allHexagons) {
            for (Hexagon hexagon : innerList) {
                if (hexagon.shape.contains(endX, endY)) {
                    endX += rayLength * Math.cos(directionAngle);
                    endY += rayLength * Math.sin(directionAngle);
                    findEndPoint(endX,endY,directionAngle,rayLength);
                    break;
                }
                else if (hexagon.shape.contains(endX-1, endY-1)) {
                    endX += rayLength * Math.cos(directionAngle);
                    endY += rayLength * Math.sin(directionAngle);
                    findEndPoint(endX,endY,directionAngle,rayLength);
                    break;
                }
                else if (hexagon.shape.contains(endX+1, endY+1)) {
                    endX += rayLength * Math.cos(directionAngle);
                    endY += rayLength * Math.sin(directionAngle);
                    findEndPoint(endX,endY,directionAngle,rayLength);
                    break;
                }
            }
        }
        return new double[]{endX, endY};
    }


}