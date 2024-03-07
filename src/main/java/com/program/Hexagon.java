package com.program;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public  class Hexagon extends Polygon {
    public double x;
    public double y;
    private int rowId;
    static int mode;
    static int counter =0;
    public int Id;
    public Polygon shape;
    public double[] points;

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

        hexagon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (mode != 1 && counter < 6){
                    double[] center = calculatePolygonCenter(hexagon);
                    System.out.println(Id);
                    Atoms at = new Atoms(root, center[0], center[1]);
                    Main.allAtoms.add(at);  // Add the created Atom to the list
                    counter++;
                }
            }
        });

        root.getChildren().add(hexagon);
        shape = hexagon;
        return hexagon;
    }

    private double[] calculatePolygonCenter(Polygon polygon) {
        javafx.geometry.Bounds bounds = polygon.getBoundsInLocal();
        double centerX = (bounds.getMinX() + bounds.getMaxX()) / 2;
        double centerY = (bounds.getMinY() + bounds.getMaxY()) / 2;
        return new double[]{centerX, centerY};
    }

    @Override
    public boolean contains(double x, double y) {
        // Implement the logic to check if (x, y) is inside the hexagon
        // You can use your own logic based on the hexagon's geometry
        // For example, you can check if (x, y) is inside the bounding rectangle
        return getBoundsInLocal().contains(x, y);
    }
}
