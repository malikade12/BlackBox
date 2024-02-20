package com.program;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 400, 400, Color.WHITE);

        Hexagon hexagon = new Hexagon(200, 200);
        hexagon.draw(root, 50); // Draw the hexagon with size 50

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

        // Method to draw the hexagon
        public void draw(Group root, double size) {
            double[] points = new double[12];

            for (int i = 0; i < 6; i++) {
                points[i * 2] = x + size * Math.cos(Math.toRadians(60 * i));
                points[i * 2 + 1] = y + size * Math.sin(Math.toRadians(60 * i));
            }

            javafx.scene.shape.Polygon hexagon = new javafx.scene.shape.Polygon(points);
            hexagon.setFill(Color.TRANSPARENT);
            hexagon.setStroke(Color.BLACK);

            root.getChildren().add(hexagon);
        }
    }
}
