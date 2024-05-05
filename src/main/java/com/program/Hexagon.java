package com.program;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Abdulmalik Adeyemo
 * @version 1.0 May 2024
 * Class containing hexagon and methods to draw the hexagon on the screen
 */
public class Hexagon extends Polygon {
    public double x;
    public double y;
    public int rowId;
    public int rowPositionId;
    boolean hasAtom = false;
    static int gameMode;
    static int counter = 0;
    public int Id;
    public Polygon shape;
    public double[] points;
    public boolean guessed = false;

    /**
     * *
     * Constructor for hexagon
     *
     * @param x   The x coordinate for the center point
     * @param y   The y coordinate of the center point
     * @param row The row the hexagon will be in
     * @param id  The id of the hexagon
     */
    public Hexagon(double x, double y, int row, int id) {
        this.x = x;
        this.y = y;

        rowId = row;
        Id = id + 1;
        CalculatePosId(rowId, Id);
    }

    /**
     * Calculates the position ID
     *
     * @param x The x coordinate of the center point
     * @param y The y coordinate of the center point
     */
    public void CalculatePosId(int x, int y) {
        if (x != 0) {
            int total = 0;
            for (int i = x - 1; i >= 0; i--) {
                total += BoardItems.allHexagons.get(i).size();
            }
            rowPositionId = y - total;

        } else {
            rowPositionId = y;
        }

    }

    /**
     * Draws the hexagon on screen
     *
     * @param root The group the hexagon will be added to
     * @param size The scalar that will define the hexagon size
     * @return Returns the hexagon shape
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
                if (gameMode != 1 && counter < 6 && !BoardItems.endRound && !BoardItems.setterSwitched) {
                    double[] center = calculatePolygonCenter(hexagon);
                    Atoms at = new Atoms(root, center[0], center[1], Id);
                    BoardItems.allAtoms.add(0, at);  // Add the created Atom to the list
                    counter++;
                    hasAtom = true;

                } else if (BoardItems.setterSwitched && BoardItems.isSetter) {
                    BoardItems.addLog("You already let " + InitGame.PlayerOneName + " go, NO CHEATING!!!!!");

                } else if (BoardItems.endRound) {
                    if (guessed) {
                        BoardItems.addLog("Hexagon guessed already");

                    } else if (hasAtom) {
                        guessed = true;
                        if (BoardItems.roundCount == 0){
                            BoardItems.playerOneGuesses[1]++;
                            BoardItems.playerOneGuesses[0]++;
                            if (BoardItems.textArea.getText().contains(InitGame.PlayerOneName + " Guesses"))BoardItems.textArea.setText(BoardItems.textArea.getText().substring(0, BoardItems.textArea.getText().lastIndexOf(InitGame.PlayerOneName + " Guesses")));
                            BoardItems.addLog(InitGame.PlayerOneName + " Guesses made; " + BoardItems.playerOneGuesses[0]);
                        }else{
                            BoardItems.playerTwoGuesses[1]++;
                            BoardItems.playerTwoGuesses[0]++;
                            if (BoardItems.textArea.getText().contains(InitGame.PlayerOneName + " Guesses"))BoardItems.textArea.setText(BoardItems.textArea.getText().substring(0, BoardItems.textArea.getText().lastIndexOf(InitGame.PlayerOneName + " Guesses")));
                            BoardItems.addLog(InitGame.PlayerOneName + " Guesses made; " + BoardItems.playerTwoGuesses[0]);
                        }
                    } else if (!hasAtom) {
                        guessed = true;
                        if (BoardItems.roundCount == 0){
                            BoardItems.playerOneScore += 5;
                            BoardItems.playerOneGuesses[0]++;
                            if (BoardItems.textArea.getText().contains(InitGame.PlayerOneName + " Guesses"))BoardItems.textArea.setText(BoardItems.textArea.getText().substring(0, BoardItems.textArea.getText().lastIndexOf(InitGame.PlayerOneName + " Guesses")));
                            BoardItems.addLog(InitGame.PlayerOneName + " Guesses made; " + BoardItems.playerOneGuesses[0]);
                        }
                        else {
                            BoardItems.playerTwoScore += 5;
                            BoardItems.playerTwoGuesses[0]++;
                            if (BoardItems.textArea.getText().contains(InitGame.PlayerOneName + " Guesses"))BoardItems.textArea.setText(BoardItems.textArea.getText().substring(0, BoardItems.textArea.getText().lastIndexOf(InitGame.PlayerOneName + " Guesses")));
                            BoardItems.addLog(InitGame.PlayerOneName + " Guesses made; " + BoardItems.playerTwoGuesses[0]);

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
     * Calculates the center point of the shape
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

    /**
     * Create a Text node with a number in the middle of the hexagon
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
