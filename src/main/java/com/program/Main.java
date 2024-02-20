package com.program;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 400, 400, Color.WHITE);
        Hexagon[] hexagons = new Hexagon[300];

        for (int i = 0;i< 5;i++) {
            Hexagon h1 = new Hexagon(600+(87*i), 200);
            h1.draw(root, 50); // Draw the hexagon with size 50
            hexagons[i] = h1;
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    static class Hexagon {
        private double x;
        private double y;

        public Hexagon(double x, double y) {
            this.x = x;
            this.y = y;
        }

        // Getters and setters for x and y coordinates
        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        // Method to draw the hexagon with the bottom as an apex
        public void draw(Group root, double size) {
            double[] points = new double[12];

            for (int i = 0; i < 6; i++) {
                // Rotate the hexagon by 30 degrees to make the bottom an apex
                double angle = Math.toRadians(60 * i + 30);
                points[i * 2] = x + size * Math.cos(angle);
                points[i * 2 + 1] = y + size * Math.sin(angle);
            }

            Polygon hexagon = new javafx.scene.shape.Polygon(points);
            hexagon.setFill(Color.YELLOW);
            hexagon.setStroke(Color.BLACK);

            root.getChildren().add(hexagon);
        }
    }
}