package com.program;

import javafx.scene.control.Label;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public  class InitGame {
    //user names
    public static String experimenterName;
    public static String setterName;
    public static void print(){
        //print the hexagons and arrows to the screen
        PrintHexagons();
        PrintArrows();
    }
    //prompts users to enter their names
    public static void Getnames(){
         experimenterName = null;
         setterName = null;
         System.out.println("Enter The players Name");


        do{
            System.out.println("Enter Experimenters Name:");
            Scanner myScanner = new Scanner(System.in, StandardCharsets.UTF_8.displayName());
            experimenterName = myScanner.nextLine();
            System.out.println("Enter Setters Name:");
            setterName = myScanner.nextLine();
        }while (Objects.equals(experimenterName, "") || Objects.equals(setterName, ""));
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


            BoardItems.allHexagons.add(rows);
        }
    }
    public static void PrintArrows(){
        //array of hex ids that have arrows in them
        int[][] rowIds = {{1, 6, 12, 19, 27}, {27, 36, 44, 51, 57}, {57, 58, 59, 60, 61}, {61, 56, 50, 43, 35}, {35, 26, 18, 11, 5}, {1, 2, 3, 4, 5}};

        int arrowid = 1;
        int arrowid2 = 10;
        int arrowid3 = 19;
        int arrowid4 = 37;
        int arrowid5 = 46;
        int arrowid6 = 1;

        Arrow Arrow = new Arrow();
        //looping through the hexagon to find matches with rowIds arrays
        for (List<Hexagon> rows: BoardItems.allHexagons){
            for (Hexagon hex: rows){
                for (int[] x: rowIds){
                    for (int y: x) {
                        if (hex.Id == y) {
                            //if the id is in the first index this is the line of arrows on the upper left side of the bigger hexagon
                            if(x == rowIds[0]) {
                                //getting the coordinates of the upper left and mid left lines of the individual hexagon
                                double[] p1 = {hex.points[8], hex.points[9]};
                                double[] p2 = {hex.points[6], hex.points[7]};
                                double[] p3 = {hex.points[4], hex.points[5]};
                                //uses the points to draw the south-east facing arrow
                                Object[] a1 = Arrow.createArrow(p1, p2, BoardItems.directions.southEast, new int[]{hex.rowId, hex.rowPositionId - 1},arrowid);
                                arrowid++;
                                //uses the points to draw the east facing arrow
                                Object[] a2 = Arrow.createArrow(p2, p3, BoardItems.directions.east, new int[]{hex.rowId, hex.rowPositionId - 1},arrowid);
                                arrowid++;

                                    //adds the numbers and arrow to their respective arraylists
                                BoardItems.allNumbers.add((Label) a2[1]);
                                BoardItems.allArrows.add((Arrow) a1[0]);
                                BoardItems.allArrows.add((Arrow) a2[0]);
                                BoardItems.allNumbers.add((Label) a1[1]);



                            }
                            //if the id is in the second index this is the line of arrows on the lower left side of the bigger hexagon
                            else if (x == rowIds[1]) {
                                //getting the coordinates of the lower left and mid left lines of the individual hexagon
                                double[] p1 = {hex.points[6], hex.points[7]};
                                double[] p2 = {hex.points[4], hex.points[5]};
                                double[] p3 = {hex.points[2], hex.points[3]};
                                //uses the points to draw the east facing arrow
                                Object[] a1 = Arrow.createArrow(p1, p2, BoardItems.directions.east, new int[]{hex.rowId, hex.rowPositionId - 1},arrowid2);
                                arrowid2++;
                                //uses the points to draw the north-east facing arrow
                                Object[] a2 = Arrow.createArrow(p2, p3, BoardItems.directions.northEast, new int[]{hex.rowId, hex.rowPositionId - 1},arrowid2);
                                arrowid2++;
                                //if hex id is 27, dont draw the east facing arrow because it was previously drawn
                                //adds the numbers and arrow to their respective arraylists

                                if (hex.Id != 27){
                                    BoardItems.allArrows.add((Arrow) a1[0]);
                                    BoardItems.allNumbers.add((Label) a1[1]);
                                }
                                BoardItems.allArrows.add((Arrow) a2[0]);
                                BoardItems.allNumbers.add((Label) a2[1]);

                            }
                            //if the id is in the thrid index this is the line of arrows on the lower middle side of the bigger hexagon

                            else if (x == rowIds[2]) {
                                //getting the coordinates of the lower left and lower right lines of the individual hexagon

                                double[] p1 = {hex.points[4], hex.points[5]};
                                double[] p2 = {hex.points[2], hex.points[3]};
                                double[] p3 = {hex.points[0], hex.points[1]};
                                //uses the points to draw the north-east facing arrow
                                Object[] a1 = Arrow.createArrow(p1, p2, BoardItems.directions.northEast, new int[]{hex.rowId , hex.rowPositionId - 1},arrowid3);
                                arrowid3++;
                                //uses the points to draw the north-west facing arrow
                                Object[] a2 = Arrow.createArrow(p2, p3, BoardItems.directions.northWest, new int[]{hex.rowId , hex.rowPositionId - 1},arrowid3);
                                arrowid3++;
                                //if hex id is 57, dont draw the north-east facing arrow because it was previously drawn
                                //adds the numbers and arrow to their respective arraylists

                                if (hex.Id != 57){
                                    BoardItems.allArrows.add((Arrow) a1[0]);
                                    BoardItems.allNumbers.add((Label) a1[1]);
                                }
                                BoardItems.allArrows.add((Arrow) a2[0]);
                                BoardItems.allNumbers.add((Label) a2[1]);

                            }
                            //if the id is in the fourth index this is the line of arrows on the lower right side of the bigger hexagon

                            else if (x == rowIds[3]) {
                                //getting the coordinates of the lower right and mid right lines of the individual hexagon

                                double[] p1 = {hex.points[2], hex.points[3]};
                                double[] p2 = {hex.points[0], hex.points[1]};
                                double[] p3 = {hex.points[10], hex.points[11]};
                                //uses the points to draw the west facing arrow
                                Object[] a2 = Arrow.createArrow(p2, p3, BoardItems.directions.west, new int[]{hex.rowId , hex.rowPositionId - 1},arrowid4);
                                arrowid4--;
                                //uses the points to draw the north-west facing arrow
                                Object[] a1 = Arrow.createArrow(p1, p2, BoardItems.directions.northWest, new int[]{hex.rowId, hex.rowPositionId - 1},arrowid4);
                                arrowid4--;

                                if (hex.Id != 61){
                                    BoardItems.allArrows.add((Arrow) a1[0]);
                                    if (arrowid4>26) {

                                        BoardItems.allNumbers.add((Label) a1[1]);
                                    }
                                }
                                if (!BoardItems.allArrows.contains((Arrow) a2[0])){
                                    BoardItems.allArrows.add((Arrow) a2[0]);
                                    if(arrowid4>26) {

                                        BoardItems.allNumbers.add((Label) a2[1]);
                                    }
                                }else {
                                    arrowid4--;
                                }
                            }
                            //if the id is in the fifth index this is the line of arrows on the upper right side of the bigger hexagon

                            else if (x == rowIds[4]) {
                                //getting the coordinates of the middle right and upper right lines of the individual hexagon

                                double[] p1 = {hex.points[0], hex.points[1]};
                                double[] p2 = {hex.points[10], hex.points[11]};
                                double[] p3 = {hex.points[8], hex.points[9]};
                                //uses the points to draw the south-west facing arrow
                                Object[] a2 = Arrow.createArrow(p2, p3, BoardItems.directions.southWest, new int[]{hex.rowId , hex.rowPositionId - 1},arrowid5);
                                arrowid5--;
                                //uses the points to draw the west facing arrow
                                Object[] a1 = Arrow.createArrow(p1, p2, BoardItems.directions.west, new int[]{hex.rowId, hex.rowPositionId - 1},arrowid5);
                                arrowid5--;
                                if (hex.Id != 35){
                                    BoardItems.allArrows.add((Arrow) a1[0]);


                                    BoardItems.allNumbers.add((Label) a1[1]);

                                }
                                if (!BoardItems.allArrows.contains((Arrow) a2[0])){
                                    BoardItems.allArrows.add((Arrow) a2[0]);


                                    BoardItems.allNumbers.add((Label) a2[1]);

                                }
                            }
                            //if the id is in the sixth index this is the line of arrows on the upper middle side of the bigger hexagon

                            else if (x == rowIds[5]) {
                                //getting the coordinates of the upper left and upper right lines of the individual hexagon
                                double[] p1 = {hex.points[8], hex.points[9]};
                                double[] p2 = {hex.points[6], hex.points[7]};
                                double[] p3 = {hex.points[10], hex.points[11]};
                                //uses the points to draw the south-east facing arrow
                                Object[] a1 = Arrow.createArrow(p1, p2, BoardItems.directions.southEast, new int[]{hex.rowId , hex.rowPositionId - 1},arrowid6);
                                arrowid6--;
                                if(arrowid6 < 1) arrowid6 = 54;
                                //uses the points to draw the south-west facing arrow

                                Object[] a2 = Arrow.createArrow(p3, p1, BoardItems.directions.southWest, new int[]{hex.rowId , hex.rowPositionId - 1},arrowid6);
                                arrowid6--;
                                if (hex.Id != 1){
                                    BoardItems.allArrows.add((Arrow) a1[0]);
                                    BoardItems.allNumbers.add((Label) a1[1]);
                                }
                                if (!BoardItems.allArrows.contains((Arrow) a2[0])){
                                    BoardItems.allArrows.add((Arrow) a2[0]);
                                    BoardItems.allNumbers.add((Label) a2[1]);
                                }else{
                                        arrowid6--;
                                    }

                            }
                        }
                    }
                }
            }}
        Main.root.getChildren().addAll(BoardItems.allArrows);
        Main.root.getChildren().addAll(BoardItems.allNumbers);

    }
}
