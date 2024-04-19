package com.program;

import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public  class InitGame {
    public static String ExperimenterName;
    public static String SetterName;
    public static void print(){
        PrintHexagons();
        PrintArrows();
    }
    public static void Getnames(){
         ExperimenterName = null;
         SetterName = null;
         System.out.println("Enter The players Name");


        do{
            System.out.println("Enter Experimenters Name:");
            Scanner myScanner = new Scanner(System.in, StandardCharsets.UTF_8.displayName());
            ExperimenterName = myScanner.nextLine();
            System.out.println("Enter Setters Name:");
            SetterName = myScanner.nextLine();
        }while (Objects.equals(ExperimenterName, "") || Objects.equals(SetterName, ""));
        System.out.println("initliasing Game....");
    }
    public static void PrintHexagons(){
        int k = 0;
        int l = 0;

        for (int j = 0; j < 9; j++) {
            double ydefault = 150;
            double xdefault = 650;
            ydefault += 75 * j / 1.3;
            xdefault -= 44 * j / 1.3;
            if (j > 4) l++;
            xdefault += 87 * l / 1.3;
            ArrayList<Hexagon> rows = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Hexagon h1 = new Hexagon(xdefault + ((double) (87 * i)/1.3), ydefault, j, k);
                h1.draw(Main.root, 50/1.3); // Draw the hexagon with size 50
                rows.add(h1);
                k++;
            }
            if (j > 0 && j != 8) {
                Hexagon h1 = new Hexagon(xdefault + ((double) (87 * 5)/1.3), ydefault, j, k);
                h1.draw(Main.root, 50/1.3); // Draw the hexagon with size 50
                rows.add(h1);
                k++;
            }
            if (j > 1 && j < 7) {
                Hexagon h1 = new Hexagon(xdefault + ((double) (87 * 6)/1.3), ydefault, j, k);
                h1.draw(Main.root, 50/1.3); // Draw the hexagon with size 50
                rows.add(h1);
                k++;
            }
            if (j > 2 && j < 6) {
                Hexagon h1 = new Hexagon(xdefault + ((double) (87 * 7)/1.3), ydefault, j, k);
                h1.draw(Main.root, 50/1.3); // Draw the hexagon with size 50
                rows.add(h1);
                k++;
            }
            if (j == 4) {
                Hexagon h1 = new Hexagon(xdefault + ((double) (87 * 8)/1.3), ydefault, j, k);
                h1.draw(Main.root, 50/1.3); // Draw the hexagon with size 50
                rows.add(h1);
                k++;
            }


            Main.allHexagons.add(rows);
        }
    }
    public static void PrintArrows(){
        int[][] rowIds = {{1, 6, 12, 19, 27}, {27, 36, 44, 51, 57}, {57, 58, 59, 60, 61}, {61, 56, 50, 43, 35}, {35, 26, 18, 11, 5}, {1, 2, 3, 4, 5}};
        int arrowid = 1;
        int arrowid2 = 10;
        int arrowid3 = 19;
        int arrowid4 = 37;
        int arrowid5 = 46;
        int arrowid6 = 1;

        for (List<Hexagon> rows: Main.allHexagons){
            for (Hexagon hex: rows){
                for (int[] x: rowIds){
                    for (int y: x) {
                        if (hex.Id == y) {
                            if(x == rowIds[0]) {
                                double[] p1 = {hex.points[8], hex.points[9]};
                                double[] p2 = {hex.points[6], hex.points[7]};

                                double[] p3 = {hex.points[4], hex.points[5]};
                                Object[] a1 = Arrow.createArrow(p1, p2, Main.directions.southEast, new int[]{hex.rowId, hex.rowPositionId - 1},arrowid);
                                arrowid++;
                                Object[] a2 = Arrow.createArrow(p2, p3, Main.directions.east, new int[]{hex.rowId, hex.rowPositionId - 1},arrowid);
                                arrowid++;



                                    Main.allNumbers.add((Label) a2[1]);
                                    Main.allArrows.add((Polygon) a1[0]);
                                    Main.allArrows.add((Polygon) a2[0]);
                                    Main.allNumbers.add((Label) a1[1]);



                            } else if (x == rowIds[1]) {
                                double[] p1 = {hex.points[6], hex.points[7]};
                                double[] p2 = {hex.points[4], hex.points[5]};
                                Object[] a1 = Arrow.createArrow(p1, p2, Main.directions.east, new int[]{hex.rowId, hex.rowPositionId - 1},arrowid2);
                                arrowid2++;
                                double[] p3 = {hex.points[2], hex.points[3]};
                                Object[] a2 = Arrow.createArrow(p2, p3, Main.directions.northEast, new int[]{hex.rowId, hex.rowPositionId - 1},arrowid2);
                                arrowid2++;
                                if (hex.Id != 27){
                                    Main.allArrows.add((Polygon) a1[0]);
                                    Main.allNumbers.add((Label) a1[1]);
                                }
                                if (!Main.allArrows.contains((Polygon) a2[0])){
                                    Main.allArrows.add((Polygon) a2[0]);
                                    Main.allNumbers.add((Label) a2[1]);

                                }
                            } else if (x == rowIds[2]) {
                                double[] p1 = {hex.points[4], hex.points[5]};
                                double[] p2 = {hex.points[2], hex.points[3]};
                                Object[] a1 = Arrow.createArrow(p1, p2, Main.directions.northEast, new int[]{hex.rowId , hex.rowPositionId - 1},arrowid3);
                                arrowid3++;
                                double[] p3 = {hex.points[0], hex.points[1]};
                                Object[] a2 = Arrow.createArrow(p2, p3, Main.directions.northWest, new int[]{hex.rowId , hex.rowPositionId - 1},arrowid3);
                                arrowid3++;
                                if (hex.Id != 57){
                                    Main.allArrows.add((Polygon) a1[0]);
                                    Main.allNumbers.add((Label) a1[1]);
                                }
                                if (!Main.allArrows.contains((Polygon) a2[0])){
                                    Main.allArrows.add((Polygon) a2[0]);
                                    Main.allNumbers.add((Label) a2[1]);
                                }
                            }
                            else if (x == rowIds[3]) {
                                double[] p1 = {hex.points[2], hex.points[3]};
                                double[] p2 = {hex.points[0], hex.points[1]};

                                double[] p3 = {hex.points[10], hex.points[11]};
                                Object[] a2 = Arrow.createArrow(p2, p3, Main.directions.west, new int[]{hex.rowId , hex.rowPositionId - 1},arrowid4);
                                arrowid4--;
                                Object[] a1 = Arrow.createArrow(p1, p2, Main.directions.northWest, new int[]{hex.rowId, hex.rowPositionId - 1},arrowid4);
                                arrowid4--;

                                if (hex.Id != 61){
                                    Main.allArrows.add((Polygon) a1[0]);
                                    if (arrowid4>26) {

                                        Main.allNumbers.add((Label) a1[1]);
                                    }
                                }
                                if (!Main.allArrows.contains((Polygon) a2[0])){
                                    Main.allArrows.add((Polygon) a2[0]);
                                    if(arrowid4>26) {

                                        Main.allNumbers.add((Label) a2[1]);
                                    }
                                }else {
                                    arrowid4--;
                                }
                            }else if (x == rowIds[4]) {
                                double[] p1 = {hex.points[0], hex.points[1]};
                                double[] p2 = {hex.points[10], hex.points[11]};

                                double[] p3 = {hex.points[8], hex.points[9]};
                                Object[] a2 = Arrow.createArrow(p2, p3, Main.directions.southWest, new int[]{hex.rowId , hex.rowPositionId - 1},arrowid5);
                                arrowid5--;
                                Object[] a1 = Arrow.createArrow(p1, p2, Main.directions.west, new int[]{hex.rowId, hex.rowPositionId - 1},arrowid5);
                                arrowid5--;
                                if (hex.Id != 35){
                                    Main.allArrows.add((Polygon) a1[0]);


                                        Main.allNumbers.add((Label) a1[1]);

                                }
                                if (!Main.allArrows.contains((Polygon) a2[0])){
                                    Main.allArrows.add((Polygon) a2[0]);


                                        Main.allNumbers.add((Label) a2[1]);

                                }
                            }else if (x == rowIds[5]) {
                                double[] p1 = {hex.points[8], hex.points[9]};
                                double[] p2 = {hex.points[6], hex.points[7]};
                                Object[] a1 = Arrow.createArrow(p1, p2, Main.directions.southEast, new int[]{hex.rowId , hex.rowPositionId - 1},arrowid6);
                                arrowid6--;
                                if(arrowid6 < 1) arrowid6 = 54;
                                double[] p3 = {hex.points[10], hex.points[11]};
                                Object[] a2 = Arrow.createArrow(p3, p1, Main.directions.southWest, new int[]{hex.rowId , hex.rowPositionId - 1},arrowid6);
                                arrowid6--;
                                if (hex.Id != 5 && hex.Id != 1){
                                    Main.allArrows.add((Polygon) a1[0]);
                                    Main.allNumbers.add((Label) a1[1]);
                                }
                                if (!Main.allArrows.contains((Polygon) a2[0])){
                                    Main.allArrows.add((Polygon) a2[0]);
                                    Main.allNumbers.add((Label) a2[1]);
                                }else{
                                        arrowid6--;
                                    }

                            }
                        }
                    }
                }
            }}
        System.out.println(Main.allArrows.size());
    }
}
