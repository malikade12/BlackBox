package com.program;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.program.Main.allHexagons;

public  class Hexagon extends Polygon {
    public double x;
    public double y;
    private int rowId;
    boolean hasAtom = false;
    static int mode;
    static int counter =0;
    public int Id;
    public Polygon shape;
    public double[] points;
    public int[] influence= new int[6];
    public Hexagon[] influencer = new Hexagon[6];
    Set<Integer> excludedIds = new HashSet<>(Arrays.asList(1, 5, 6, 11, 12, 18, 19, 26, 27, 35));

    public Hexagon(double x, double y, int row, int id) {
        this.x = x;
        this.y = y;

        rowId = row;
        Id = id + 1;
    }

    public Polygon draw(Group root, double size) {
         points = new double[12];

        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i + 30);
            points[i * 2] = x + size * Math.cos(angle);
            points[i * 2 + 1] = y + size * Math.sin(angle);
        }

        Polygon hexagon = new Polygon(points);
        hexagon.setFill(Color.BLACK);
        hexagon.setStroke(Color.YELLOW);
        hexagon.setStrokeWidth(3);
        if (mode!=0) {
            hexagon.setOnMouseEntered(e -> hexagon.setFill(Color.RED));
            hexagon.setOnMouseExited(e -> hexagon.setFill(Color.BLACK));
        }
        hexagon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (mode != 1 && counter < 6) {
                    double[] center = calculatePolygonCenter(hexagon);
                    System.out.println(Id);
                    Atoms at = new Atoms(root, center[0], center[1]);
                    Main.allAtoms.add(0,at);  // Add the created Atom to the list
                    counter++;
                    hasAtom = true;
                    int space = 6;
                    int decreaseSpace = 13;
                    int influenceCount = 0;


                    for (List<Hexagon> innerList : allHexagons) { //PARTIALLY COMPLETE ARRAYS FOR HEXAGON'S ORBIT HEXAGONS
                        for (Hexagon hexagon : innerList) {

                            if (hexagon.Id <= 35) {

                                if (Id - space == hexagon.Id || Id - space + 1 == hexagon.Id || Id == hexagon.Id + 1 || Id == hexagon.Id - 1) {
                                    influence[influenceCount] = hexagon.Id;
                                    influencer[influenceCount] = hexagon;
                                    if (influenceCount < 5) influenceCount++;
                                }
                                else if(Id + space-1 == hexagon.Id || Id + space - 2 == hexagon.Id  ){
                                    influence[influenceCount] = hexagon.Id;
                                    influencer[influenceCount] = hexagon;
                                    if (influenceCount < 5) influenceCount++;
                                }

                            }
                            if (Id > 35) {
                                if (Id - decreaseSpace == hexagon.Id || Id - decreaseSpace + 1 == hexagon.Id || Id == hexagon.Id + 1 || Id == hexagon.Id - 1) {
                                    influence[influenceCount] = hexagon.Id;
                                    influencer[influenceCount] = hexagon;
                                    if (influenceCount < 5) influenceCount++;
                                }
                                else if(Id + decreaseSpace == hexagon.Id || Id + decreaseSpace + 1 == hexagon.Id  ){
                                    influence[influenceCount] = hexagon.Id;
                                    influencer[influenceCount] = hexagon;
                                    if (influenceCount < 5) influenceCount++;

                                }

                            }
                        }
                        space++;
                        decreaseSpace--;
                        }


                for (int i: influence
                     ) {
                    System.out.println("hexagon at "+i);
                }
                }
            }
        });

        root.getChildren().add(hexagon);
        shape = hexagon;
        return hexagon;
    }

    public static double[] calculatePolygonCenter(Polygon polygon) {
        javafx.geometry.Bounds bounds = polygon.getBoundsInLocal();
        double centerX = (bounds.getMinX() + bounds.getMaxX()) / 2;
        double centerY = (bounds.getMinY() + bounds.getMaxY()) / 2;
        return new double[]{centerX, centerY};
    }
}
