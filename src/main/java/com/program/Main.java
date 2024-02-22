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
        ChangeView test = new ChangeView();
        test.testButton();
        root.getChildren().add(test.getButton1());

        Scene scene = new Scene(root, 400, 400, Color.WHITE);
        Hexagon[] hexagons = new Hexagon[300];
        int k=0;
        int l=0;
        int id = 0;
        for(int j=0;j<9;j++) {
            int ydefault = 100;
            int xdefault = 600;
            ydefault+=75*j;
            xdefault-=44*j;
            if (j>4) l++;
            xdefault+=87*l;
            for (int i = 0; i < 5; i++) {
                Hexagon h1 = new Hexagon(xdefault + (87 * i), ydefault,id++);
                h1.draw(root, 50); // Draw the hexagon with size 50
                hexagons[k++] = h1;
            }
            if(j>0 && j !=8){
                Hexagon h1 = new Hexagon(xdefault + (87 * 5), ydefault,id++);
                h1.draw(root, 50); // Draw the hexagon with size 50
                hexagons[k++] = h1;
            }
            if(j>1&& j <7){
                Hexagon h1 = new Hexagon(xdefault + (87 * 6), ydefault,id++);
                h1.draw(root, 50); // Draw the hexagon with size 50
                hexagons[k++] = h1;
            }
            if(j>2&& j <6){
                Hexagon h1 = new Hexagon(xdefault + (87 * 7), ydefault,id++);
                h1.draw(root, 50); // Draw the hexagon with size 50
                hexagons[k++] = h1;
            }
            if(j == 4){
                Hexagon h1 = new Hexagon(xdefault + (87 * 8), ydefault,id++);
                h1.draw(root, 50); // Draw the hexagon with size 50
                hexagons[k++] = h1;
            }
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
        private int id;

        public Hexagon(double x, double y,int id) {
            this.x = x;
            this.y = y;
            this.id = id;
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

        public int getId(){
            return this.id;
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
