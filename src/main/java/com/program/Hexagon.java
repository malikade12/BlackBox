package com.program;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


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
    public boolean Guessed = false;
    public Hexagon[] influencer = new Hexagon[6];

    /**
     * *
     * @param x The x coordinate for the center point
     * @param y The y coordinate of the center point
     * @param row The row the hexagon will be in
     * @param id The id of the hexagon
     */
    public Hexagon(double x, double y, int row, int id) {
        this.x = x;
        this.y = y;

        rowId = row;
        Id = id + 1;
        CalculatePosId(rowId, Id);
    }

    /**
     *
     * @param x The x coordinate of the center point
     * @param y The y coordinate of the center point
     */
    public void CalculatePosId(int x, int y){
        if (x != 0){
            int total = 0;
            for (int i = x - 1; i >= 0 ; i--) {
                total += BoardItems.allHexagons.get(i).size();
            }
            rowPositionId = y - total;

        }else{
            rowPositionId = y;
        }

    }

    /**
     *
     * @param root The group the hexagon will be added to
     * @param size The scalar that will define the hexagon size
     * @return The hexagon shape
     */
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


        hexagon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (mode != 1 && counter < 6 && !BoardItems.EndOfRound && !BoardItems.SetterSwitched) {
                    double[] center = calculatePolygonCenter(hexagon);
                    Atoms at = new Atoms(root, center[0], center[1], Id);
                    BoardItems.allAtoms.add(0,at);  // Add the created Atom to the list
                    counter++;
                    hasAtom = true;

                } else if (BoardItems.SetterSwitched && BoardItems.IsSetter) {
                    BoardItems.addLog("You already let " + InitGame.ExperimenterName + " go, NO CHEATING!!!!!");
                    
                } else if (BoardItems.EndOfRound){
                    if(Guessed){
                        BoardItems.addLog("Hexcagon guessed already");
                    }else if (hasAtom){
                        Guessed = true;
                    } else if (!hasAtom) {
                        Guessed = true;
                        if (BoardItems.roundcount==0)BoardItems.ExScore += 5;
                        else{
                            BoardItems.SetScore+=5;
                        }

                    }
                }
            }
        });

        root.getChildren().add(hexagon);
        shape = hexagon;
        return hexagon;
    }

    /**
     *
     * @param polygon The Polygon whose center will be calculated
     * @return An array containing the coordinated of the center of the polygon
     */
    public static double[] calculatePolygonCenter(Polygon polygon) {
        javafx.geometry.Bounds bounds = polygon.getBoundsInLocal();
        double centerX = (bounds.getMinX() + bounds.getMaxX()) / 2;
        double centerY = (bounds.getMinY() + bounds.getMaxY()) / 2;
        return new double[]{centerX, centerY};
    }
    // Method to create a Text node with a number in the middle of the hexagon

    /**
     *
     * @param points Array containing the points of the polygon that will contain the number
     * @param number The number to be added to the polygon
     * @return The number in text to be added to the polygon
     */
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
