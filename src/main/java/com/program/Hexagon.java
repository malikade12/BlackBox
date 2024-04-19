package com.program;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.program.Main.*;

public  class Hexagon extends Polygon {
    public double x;
    public double y;
    public int rowId;
    public int rowPositionId;
    boolean hasAtom = false;
    static int mode;
    static int counter =0;
    public int Id;
    public Polygon shape;
    public double[] points;
    public int[] influence= new int[6];
    public boolean Guessed = false;
    public Hexagon[] influencer = new Hexagon[6];
    Set<Integer> excludedIds = new HashSet<>(Arrays.asList(1, 5, 6, 11, 12, 18, 19, 26, 27, 35));

    public Hexagon(double x, double y, int row, int id) {
        this.x = x;
        this.y = y;

        rowId = row;
        Id = id + 1;
        CalculatePosId(rowId, Id);
    }
    public void CalculatePosId(int x, int y){
        if (x != 0){
            int total = 0;
            for (int i = x - 1; i >= 0 ; i--) {
                total += allHexagons.get(i).size();
            }
            rowPositionId = y - total;

        }else{
            rowPositionId = y;
        }

    }
    public Polygon draw(Group root, double size) {
         points = new double[12];

        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i + 30);
            points[i * 2] = x + size * Math.cos(angle);
            points[i * 2 + 1] = y + size * Math.sin(angle);
        }
        Text numberText = createNumberText(points, this.Id);
        root.getChildren().add(numberText);
        Polygon hexagon = new Polygon(points);
        hexagon.setFill(Color.BLACK);
        hexagon.setStroke(Color.YELLOW);
        hexagon.setFill(Color.TRANSPARENT);
        hexagon.setStrokeWidth(3);
        if (mode!=0) {
            hexagon.setOnMouseEntered(e -> hexagon.setFill(Color.RED));
            hexagon.setOnMouseExited(e -> hexagon.setFill(Color.BLACK));
        }
        hexagon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (mode != 1 && counter < 6 && !Main.EndOfRound && !SetterSwitched) {
                    double[] center = calculatePolygonCenter(hexagon);
                    Atoms at = new Atoms(root, center[0], center[1], Id);
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
                } else if (SetterSwitched && IsSetter) {
                    System.out.println("You already let " + InitGame.ExperimenterName + " go, NO CHEATING!!!!!");
                    
                } else if (Main.EndOfRound){
                    if(Guessed){
                        System.out.println("Hexcagon guessed already");
                    }else if (hasAtom){
                        Guessed = true;
                    } else if (!hasAtom) {
                        Guessed = true;
                        if (roundcount==0)Main.ExScore += 5;
                        else{
                            Main.SetScore+=5;
                        }

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
    // Method to create a Text node with a number in the middle of the hexagon
    private Text createNumberText(double[] points, int number) {
        double centerX = 0;
        double centerY = 0;
        for (int i = 0; i < points.length; i += 2) {
            centerX += points[i];
            centerY += points[i + 1];
        }
        centerX /= (double) points.length / 2;
        centerY /= (double) points.length / 2;

        Text text = new Text(Integer.toString(number));
        text.setFont(Font.font(20));
        text.setFill(Color.YELLOW);
        text.setX(centerX - 10); // Adjust position as needed
        text.setY(centerY + 5); // Adjust position as needed
        return text;
    }
}
