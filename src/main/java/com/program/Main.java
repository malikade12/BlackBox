package com.program;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;

import java.util.*;

public class Main extends Application {
    Group root;
    @Override
    public void start(Stage primaryStage) {
         root = new Group();
        root.setMouseTransparent(false);
       ArrayList<ArrayList<Polygon>> AllRows = new ArrayList<>();
        Hexagon h1;
        int k=0;
        int l=0;
        for(int j=0;j<9;j++) {
            int ydefault = 100;
            int xdefault = 600;
            ydefault+=75*j;
            xdefault-=44*j;
            if (j>4) l++;
            xdefault+=87*l;
            ArrayList<Polygon> rows = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                 h1 = new Hexagon(xdefault + (87 * i), ydefault, j, k);
                 Polygon hex = h1.draw(root, 50); // Draw the hexagon with size 50
                 rows.add(hex);
            }
            if(j>0 && j !=8){
                 h1 = new Hexagon(xdefault + (87 * 5), ydefault, j, k);
                Polygon hex = h1.draw(root, 50); // Draw the hexagon with size 50
                rows.add(hex);
            }
            if(j>1&& j <7){
                 h1 = new Hexagon(xdefault + (87 * 6), ydefault, j, k);
                Polygon hex = h1.draw(root, 50); // Draw the hexagon with size 50
                rows.add(hex);
            }
            if(j>2&& j <6){
                 h1 = new Hexagon(xdefault + (87 * 7), ydefault, j, k);
                Polygon hex = h1.draw(root, 50); // Draw the hexagon with size 50
                rows.add(hex);
            }
            if(j == 4){
                 h1 = new Hexagon(xdefault + (87 * 8), ydefault, j, k);
                Polygon hex = h1.draw(root, 50); // Draw the hexagon with size 50
                rows.add(hex);;
            }
            k++;
            AllRows.add(rows);
        }

        Scene scene = new Scene(root, 400, 400, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class Hexagon extends Polygon {
        private double x;
        private double y;
        private int RowId;

        private int HexId;

         boolean HasAtom;
        public Hexagon(double x, double y, int Row, int id) {
            this.x = x;
            this.y = y;
            RowId = Row;
            HexId = id;
        }

        public void SetAtom(){
            this.HasAtom = true;
        }


        // Method to draw the hexagon with the bottom as an apex
        public Polygon draw(Group root, double size) {
            double[] points = new double[12];

            for (int i = 0; i < 6; i++) {
                // Rotate the hexagon by 30 degrees to make the bottom an apex
                double angle = Math.toRadians(60 * i + 30);
                points[i * 2] = x + size * Math.cos(angle);
                points[i * 2 + 1] = y + size * Math.sin(angle);
            }

            Polygon hexagon = new Polygon(points);
            hexagon.setFill(Color.BLACK);
            hexagon.setStroke(Color.RED);
            hexagon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    double[] center = calculatePolygonCenter(hexagon);
                    Atoms at = new Atoms(root, center[0], center[1]);
                }
            });
            root.getChildren().add(hexagon);
            return hexagon;
        }
        private double[] calculatePolygonCenter(Polygon polygon) {
            javafx.geometry.Bounds bounds = polygon.getBoundsInLocal();
            double centerX = (bounds.getMinX() + bounds.getMaxX()) / 2;
            double centerY = (bounds.getMinY() + bounds.getMaxY()) / 2;
            return new double[]{centerX, centerY};
        }
    }


}
