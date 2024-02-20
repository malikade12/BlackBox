package com.example.demo1;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;

public class HexagonalGridPrinter extends Application {

    private static final int HEX_SIZE = 50;
    private static final double HEX_VERTICAL_OFFSET = HEX_SIZE * Math.sqrt(3);

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 400, Color.WHITE);

        // Calculate the starting X position for the first row to center it
        double startXRow1 = calculateStartX(scene.getWidth(), 5);

        // Calculate the starting X position for the second row to center it
        double startXRow2 = calculateStartX(scene.getWidth(), 6);

        double startY = 100;

        // Draw the first row with 5 hexagons
        drawRow(root, ((scene.getWidth() - 5 * HEX_SIZE * 1.5) / 2.0), startY, 5);

        // Draw the second row with 6 hexagons
        drawRow(root, ((scene.getWidth() - 6 * HEX_SIZE * 1.5) / 2.0) - 6, startY + HEX_VERTICAL_OFFSET -11, 6);

        primaryStage.setTitle("Hexagonal Grid");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Bind the startX calculation to scene width changes
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            startXRow1.set((newVal.doubleValue() - 5 * HEX_SIZE * 1.5) / 2.0);
            startXRow2.set((newVal.doubleValue() - 6 * HEX_SIZE * 1.5) / 2.0);
            root.getChildren().clear(); // Clear previous hexagons
            // Draw the first row with 5 hexagons
            drawRow(root, startXRow1.get(), startY, 5);
            // Draw the second row with 6 hexagons
            drawRow(root, startXRow2.get() -6, startY + HEX_VERTICAL_OFFSET -11, 6);
        });
    }

    private void drawRow(Group root, double startX, double startY, int numHexagons) {
        for (int i = 0; i < numHexagons; i++) {
            drawHexagon(root, (startX + 12 * i) + i * HEX_SIZE * 1.5, startY);
        }
    }

    private void drawHexagon(Group root, double x, double y) {
        double[] points = new double[12];

        for (int i = 0; i < 6; i++) {
            points[i * 2] = x + HEX_SIZE * Math.cos(Math.toRadians(60 * i + 30));
            points[i * 2 + 1] = y + HEX_SIZE * Math.sin(Math.toRadians(60 * i + 30));
        }

        Polygon hexagon = new Polygon(points);
        hexagon.setFill(Color.TRANSPARENT);
        hexagon.setStroke(Color.BLACK);

        root.getChildren().add(hexagon);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
