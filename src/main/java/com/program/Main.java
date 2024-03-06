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
    public static   Group root;
   public static List<Atoms> allAtoms;
   static List<Polygon> allArrows;

   public static List<List<Polygon>> allHexagons;

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
            allHexagons.add(rows);
        }
        System.out.println(allHexagons.get(0).size());
        Scene scene = new Scene(root, 400, 400, Color.BLACK);
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                System.out.println(Math.round(x) + "  " + Math.round(y));


            }
        });
        double initX = 564;
        double initY = 43;

        double angleArrow = 0;
        for (int i = 1; i <= 10; i++) {
            Polygon a1 = Arrow.createArrow(initX, initY, angleArrow);
            allArrows.add(a1);
            if (i < 9) {
                if (i % 2 == 1) {
                    initX -= (33 + (i * 2));
                    initY += (56 + (i * 1.3));
                    angleArrow = 1;
                } else {
                    initX -= 7;
                    initY += 13;
                    angleArrow = 0;
                }
            } else if (i == 9) {
                initX -= 18;
                initY += 60;
                angleArrow = 1;
            }

        } initX = 380;
                initY = 455;
                angleArrow = 2;
        for (int i = 0; i < 9; i++) {
            Polygon a1 = Arrow.createArrow(initX, initY, angleArrow);
            allArrows.add(a1);
            if (i % 2 == 1) {
                initX += (30 + (i / 2));
                initY += (59 + (i / 1.3));
                angleArrow = 2;
            } else {
                initX += 7;
                initY += 13;
                angleArrow = 1;
            }
        }


        initX = 617;
        initY = 753;
        angleArrow = 3;
        for (int i = 0; i < 9; i++) {
            Polygon a1 = Arrow.createArrow(initX, initY, angleArrow);
            allArrows.add(a1);
            if (i == 7){
                initX = 975;
                initY = 749

                ;
                angleArrow= 3;
            }
            else if (i % 2 == 1) {
                initX += (52 + (i * 5.4));
                angleArrow = 3;
            } else {
                initX += 20;
                angleArrow = 2;
            }
        }

        initX = 1027;
        initY = 693;
        angleArrow = 4;

        Rays ray = new Rays();
        ray.makeHorizontalRay(root,518,627);//near done for coming from right
        ray.makeDiagonalRay(root,578,65);
        ray.makeDiagonalRayUpRight(root,571,737);
        ray.makeHorizontalRay2(root,1077,249);


        for (int i = 0; i < 9; i++) {
            Polygon a1 = Arrow.createArrow(initX, initY, angleArrow);
            allArrows.add(a1);
            if (i % 2 == 1) {
                initX += (30 + (i / 2));
                initY -= (59 + (i / 1.3));
                angleArrow = 4;
            } else {
                initX += 7;
                initY -= 13;
                angleArrow = 3;
            }
        }

        initX = 1151;
        initY = 349;
        angleArrow = 5;

        for (int i = 0; i < 9; i++) {
            Polygon a1 = Arrow.createArrow(initX, initY, angleArrow);
            allArrows.add(a1);
            if (i % 2 == 1) {
                initX -= (33 + (i * 2));
                initY -= (56 -
                        (i * 1.3));
                angleArrow = 5
                ;
            } else {
                initX += 17;
                initY -= 23;
                angleArrow = 4;
            }

        }







        /*initX = 374;
        initY = 336;
            for (int j = 0; j < ; j++) {

            }
               initX = 374;
               initY = 336;
           }
            if(i >= 10){
                if (i % 2 == 1) {
                    initX += (34 + (i / 2));
                    initY += (53 + (i * 1.3));
                    angleArrow = 1;
                } else {
                    initX += 7;
                    initY += 13;
                    angleArrow = 2;
                }
            }*/



        root.getChildren().addAll(allArrows);


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}