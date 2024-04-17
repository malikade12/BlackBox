package com.program;

import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

public  class Printobjects {
    public static void main(){
        PrintHexagons();
        PrintArrows();
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
        for (List<Hexagon> rows: Main.allHexagons){
            for (Hexagon hex: rows){
                for (int[] x: rowIds){
                    for (int y: x) {
                        if (hex.Id == y) {
                            if(x == rowIds[0]) {
                                double[] p1 = {hex.points[8], hex.points[9]};
                                double[] p2 = {hex.points[6], hex.points[7]};
                                Polygon a1 = Arrow.createArrow(p1, p2, Main.directions.southEast, new int[]{hex.rowId, hex.rowPositionId - 1});
                                double[] p3 = {hex.points[4], hex.points[5]};
                                Polygon a2 = Arrow.createArrow(p2, p3, Main.directions.east, new int[]{hex.rowId, hex.rowPositionId - 1});
                                Main.allArrows.add(a1);
                                Main.allArrows.add(a2);


                            } else if (x == rowIds[1]) {
                                double[] p1 = {hex.points[6], hex.points[7]};
                                double[] p2 = {hex.points[4], hex.points[5]};
                                Polygon a1 = Arrow.createArrow(p1, p2, Main.directions.east, new int[]{hex.rowId, hex.rowPositionId - 1});
                                double[] p3 = {hex.points[2], hex.points[3]};
                                Polygon a2 = Arrow.createArrow(p2, p3, Main.directions.northEast, new int[]{hex.rowId, hex.rowPositionId - 1});
                                Main.allArrows.add(a1);
                                Main.allArrows.add(a2);
                            } else if (x == rowIds[2]) {
                                double[] p1 = {hex.points[4], hex.points[5]};
                                double[] p2 = {hex.points[2], hex.points[3]};
                                Polygon a1 = Arrow.createArrow(p1, p2, Main.directions.northEast, new int[]{hex.rowId , hex.rowPositionId - 1});
                                double[] p3 = {hex.points[0], hex.points[1]};
                                Polygon a2 = Arrow.createArrow(p2, p3, Main.directions.northWest, new int[]{hex.rowId , hex.rowPositionId - 1});
                                Main.allArrows.add(a1);
                                Main.allArrows.add(a2);
                            }
                            else if (x == rowIds[3]) {
                                double[] p1 = {hex.points[2], hex.points[3]};
                                double[] p2 = {hex.points[0], hex.points[1]};
                                Polygon a1 = Arrow.createArrow(p1, p2, Main.directions.northWest, new int[]{hex.rowId, hex.rowPositionId - 1});
                                double[] p3 = {hex.points[10], hex.points[11]};
                                Polygon a2 = Arrow.createArrow(p2, p3, Main.directions.west, new int[]{hex.rowId , hex.rowPositionId - 1});
                                Main.allArrows.add(a1);
                                Main.allArrows.add(a2);
                            }else if (x == rowIds[4]) {
                                double[] p1 = {hex.points[0], hex.points[1]};
                                double[] p2 = {hex.points[10], hex.points[11]};
                                Polygon a1 = Arrow.createArrow(p1, p2, Main.directions.west, new int[]{hex.rowId, hex.rowPositionId - 1});
                                double[] p3 = {hex.points[8], hex.points[9]};
                                Polygon a2 = Arrow.createArrow(p2, p3, Main.directions.southWest, new int[]{hex.rowId , hex.rowPositionId - 1});
                                Main.allArrows.add(a1);
                                Main.allArrows.add(a2);
                            }else if (x == rowIds[5]) {
                                double[] p1 = {hex.points[8], hex.points[9]};
                                double[] p2 = {hex.points[6], hex.points[7]};
                                Polygon a1 = Arrow.createArrow(p1, p2, Main.directions.southEast, new int[]{hex.rowId , hex.rowPositionId - 1});
                                double[] p3 = {hex.points[10], hex.points[11]};
                                Polygon a2 = Arrow.createArrow(p3, p1, Main.directions.southWest, new int[]{hex.rowId , hex.rowPositionId - 1});
                                Main.allArrows.add(a1);
                                Main.allArrows.add(a2);
                            }
                        }
                    }
                }
            }}
    }
}
