package com.program;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;

import java.util.*;

public class Main extends Application {
    private Group root;
    private static List<Atoms> allAtoms;

    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        root.setMouseTransparent(false);

        ChangeView test = new ChangeView();
        test.testButton();
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

        int k = 0;
        int l = 0;
        for (int j = 0; j < 9; j++) {
            int ydefault = 100;
            int xdefault = 600;
            ydefault += 75 * j;
            xdefault -= 44 * j;
            if (j > 4) l++;
            xdefault += 87 * l;
            ArrayList<Polygon> rows = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Hexagon h1 = new Hexagon(xdefault + (87 * i), ydefault, j, k);
                Polygon hex = h1.draw(root, 50); // Draw the hexagon with size 50
                rows.add(hex);
            }
            if (j > 0 && j != 8) {
                Hexagon h1 = new Hexagon(xdefault + (87 * 5), ydefault, j, k);
                Polygon hex = h1.draw(root, 50); // Draw the hexagon with size 50
                rows.add(hex);
            }
            if (j > 1 && j < 7) {
                Hexagon h1 = new Hexagon(xdefault + (87 * 6), ydefault, j, k);
                Polygon hex = h1.draw(root, 50); // Draw the hexagon with size 50
                rows.add(hex);
            }
            if (j > 2 && j < 6) {
                Hexagon h1 = new Hexagon(xdefault + (87 * 7), ydefault, j, k);
                Polygon hex = h1.draw(root, 50); // Draw the hexagon with size 50
                rows.add(hex);
            }
            if (j == 4) {
                Hexagon h1 = new Hexagon(xdefault + (87 * 8), ydefault, j, k);
                Polygon hex = h1.draw(root, 50); // Draw the hexagon with size 50
                rows.add(hex);
            }
            k++;
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
        private int rowId;

        public Hexagon(double x, double y, int row, int id) {
            this.x = x;
            this.y = y;
            rowId = row;
        }

        public Polygon draw(Group root, double size) {
            double[] points = new double[12];

            for (int i = 0; i < 6; i++) {
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
                    allAtoms.add(at);  // Add the created Atom to the list
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

    public static class Atoms extends Circle {
        public Atoms(Group root, double x, double y) {
            super(x, y, 20, Color.RED);
            root.getChildren().add(this);
        }

        public static void makeAllAtomsInvisible() {
            for (Atoms atom : allAtoms) {
                atom.setVisible(false);
            }
        }

        public static void makeAllAtomsVisible() {
            for (Atoms atom : allAtoms) {
                atom.setVisible(true);
            }
        }
    }
}