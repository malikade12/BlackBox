package com.program;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class exp extends Application {

        @Override
        public void start(Stage primaryStage) {
            Group root = new Group();
            Scene scene = new Scene(root, 500, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hexagon Example");

            // Create a Hexagon instance
            Hexagon hexagon = new Hexagon(250, 250);
            hexagon.draw(root, 100); // Draw the hexagon

            // Add the hexagon to the root group
            root.getChildren().add(hexagon);

            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }

    class Hexagon extends javafx.scene.shape.Polygon {
        private double x;
        private double y;

        public Hexagon(double x, double y) {
            this.x = x;
            this.y = y;

            this.setOnMouseClicked(event -> {
                System.out.println("Hexagon clicked");
                double centerX = x;
                double centerY = y;

                // Create circle at centroid
                javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(centerX, centerY, 50, Color.RED);

                // Add circle to the parent
                ((Group) this.getParent()).getChildren().add(circle);
            });
        }

        // Method to draw the hexagon
        public void draw(Group root, double size) {
            double[] points = new double[12];

            for (int i = 0; i < 6; i++) {
                double angle = Math.toRadians(60 * i);
                points[i * 2] = x + size * Math.cos(angle);
                points[i * 2 + 1] = y + size * Math.sin(angle);
            }

            this.getPoints().addAll(points);
            this.setFill(Color.BLACK);
            this.setStroke(Color.RED);
        }
    }

}
