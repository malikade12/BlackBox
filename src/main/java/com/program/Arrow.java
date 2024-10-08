package com.program;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Arrow extends Polygon{
    private  Polygon triangle;
    public static double[] midpoints;

    public Polygon createArrow(double[] p1, double[] p2, Main.directions z){
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
        polygon.getPoints().addAll(new Double[]{
                x1, y1,   // Point 1
                x2, y2,   // Point 2
                x3, y3}); // Point 3
        polygon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switch (z){
                    case midRight -> {
                      Rays.MakeRay(midX, midY, 1, 0, z);
                    }
                    case southEast -> {
                        Rays.MakeRay(midX, midY, 1, 1, z);

                    }
                    case northEast -> {
                        Rays.MakeRay(midX, midY, 1, -1, z);
                    }
                    case northWest -> {
                        Rays.MakeRay(midX, midY, -1, -1, z);

                    }
                    case midLeft -> {
                        Rays.MakeRay(midX, midY, -1, 0, z);

                    }
                    case southWest -> {
                        Rays.MakeRay(midX, midY, -1, 1, z);

                    }

                }
            }
        });
        polygon.setFill(Color.YELLOW);
        triangle = polygon;
        Main.root.getChildren().add(triangle);
        polygon.setFill(Color.YELLOW);
       return polygon;
    }
}
