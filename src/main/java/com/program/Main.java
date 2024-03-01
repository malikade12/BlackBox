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
    static List<Atoms> allAtoms;


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

        Scene scene = new Scene(root, 400, 400, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}