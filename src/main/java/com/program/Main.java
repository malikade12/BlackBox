package com.program;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;

import java.util.*;

public class Main extends Application {
    public static   Group root;
    static List<Atoms> allAtoms;
    static List<Polygon> allArrows;

    static List<List<Hexagon>> allHexagons;
    static enum directions  {
        southEast, southWest,northEast, northWest, midRight, midLeft ; }

    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        root.setMouseTransparent(false);

        ChangeView test = new ChangeView();

        test.experimenterButton();
        test.setterButton();

        VBox container = new VBox(10); // 10 pixels spacing between components
        container.setPadding(new Insets(10));

        container.getChildren().add(test.getButton1());

        Pane spacer = new Pane();
        spacer.setMinHeight(10); // Set the desired space height
        container.getChildren().add(spacer);

        container.getChildren().add(test.getButton2());

        root.getChildren().add(container);

        allAtoms = new ArrayList<>();
        allHexagons = new ArrayList<>();
        allArrows = new ArrayList<>();
        int k = 0;
        int l = 0;

        for (int j = 0; j < 9; j++) {
            int ydefault = 100;
            int xdefault = 600;
            ydefault += 75 * j;
            xdefault -= 44 * j;
            if (j > 4) l++;
            xdefault += 87 * l;
            ArrayList<Hexagon> rows = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Hexagon h1 = new Hexagon(xdefault + (87 * i), ydefault, j, k);
                h1.draw(root, 50); // Draw the hexagon with size 50
                rows.add(h1);
                k++;
            }
            if (j > 0 && j != 8) {
                Hexagon h1 = new Hexagon(xdefault + (87 * 5), ydefault, j, k);
                h1.draw(root, 50); // Draw the hexagon with size 50
                rows.add(h1);
                k++;
            }
            if (j > 1 && j < 7) {
                Hexagon h1 = new Hexagon(xdefault + (87 * 6), ydefault, j, k);
                h1.draw(root, 50); // Draw the hexagon with size 50
                rows.add(h1);
                k++;
            }
            if (j > 2 && j < 6) {
                Hexagon h1 = new Hexagon(xdefault + (87 * 7), ydefault, j, k);
                h1.draw(root, 50); // Draw the hexagon with size 50
                rows.add(h1);
                k++;
            }
            if (j == 4) {
                Hexagon h1 = new Hexagon(xdefault + (87 * 8), ydefault, j, k);
                h1.draw(root, 50); // Draw the hexagon with size 50
                rows.add(h1);
                k++;
            }


            allHexagons.add(rows);
        }
        Scene scene = new Scene(root, 400, 400, Color.BLACK);
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                System.out.println(Math.round(x) + "  " + Math.round(y));
            }
        });
        int[][] rowIds = {{1, 6, 12, 19, 27}, {27, 36, 44, 51, 57}, {57, 58, 59, 60, 61}, {61, 56, 50, 43, 35}, {35, 26, 18, 11, 5}, {1, 2, 3, 4, 5}};
        for (List<Hexagon> rows: allHexagons){
            for (Hexagon hex: rows){
                for (int[] x: rowIds){
                    for (int y: x) {
                        if (hex.Id == y) {
                            if(x == rowIds[0]) {
                                double[] p1 = {hex.points[8], hex.points[9]};
                                double[] p2 = {hex.points[6], hex.points[7]};
                                Polygon a1 = Arrow.createArrow(p1, p2, directions.southEast);
                                double[] p3 = {hex.points[4], hex.points[5]};
                                Polygon a2 = Arrow.createArrow(p2, p3, directions.midRight);
                                allArrows.add(a1);
                                allArrows.add(a2);


                            } else if (x == rowIds[1]) {
                                double[] p1 = {hex.points[6], hex.points[7]};
                                double[] p2 = {hex.points[4], hex.points[5]};
                                Polygon a1 = Arrow.createArrow(p1, p2, directions.midRight);
                                double[] p3 = {hex.points[2], hex.points[3]};
                                Polygon a2 = Arrow.createArrow(p2, p3, directions.northEast);
                                allArrows.add(a1);
                                allArrows.add(a2);
                            } else if (x == rowIds[2]) {
                                double[] p1 = {hex.points[4], hex.points[5]};
                                double[] p2 = {hex.points[2], hex.points[3]};
                                Polygon a1 = Arrow.createArrow(p1, p2, directions.northEast);
                                double[] p3 = {hex.points[0], hex.points[1]};
                                Polygon a2 = Arrow.createArrow(p2, p3, directions.northWest);
                                allArrows.add(a1);
                                allArrows.add(a2);
                            }
                            else if (x == rowIds[3]) {
                                double[] p1 = {hex.points[2], hex.points[3]};
                                double[] p2 = {hex.points[0], hex.points[1]};
                                Polygon a1 = Arrow.createArrow(p1, p2, directions.northWest);
                                double[] p3 = {hex.points[10], hex.points[11]};
                                Polygon a2 = Arrow.createArrow(p2, p3, directions.midLeft);
                                allArrows.add(a1);
                                allArrows.add(a2);
                            }else if (x == rowIds[4]) {
                                double[] p1 = {hex.points[0], hex.points[1]};
                                double[] p2 = {hex.points[10], hex.points[11]};
                                Polygon a1 = Arrow.createArrow(p1, p2, directions.midLeft);
                                double[] p3 = {hex.points[8], hex.points[9]};
                                Polygon a2 = Arrow.createArrow(p2, p3, directions.southWest);
                                allArrows.add(a1);
                                allArrows.add(a2);
                            }else if (x == rowIds[5]) {
                                double[] p1 = {hex.points[8], hex.points[9]};
                                double[] p2 = {hex.points[6], hex.points[7]};
                                Polygon a1 = Arrow.createArrow(p1, p2, directions.southEast);
                                double[] p3 = {hex.points[10], hex.points[11]};
                                Polygon a2 = Arrow.createArrow(p3, p1, directions.southWest);
                                allArrows.add(a1);
                                allArrows.add(a2);
                            }
                        }
                    }
                }
            }}

        root.getChildren().addAll(allArrows);

        primaryStage.setScene(scene);
        primaryStage.setTitle("BlackBox Alpha");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }



}