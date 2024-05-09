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
    //row id number
    public int RowId;
    //position in row
    public int RowPositionId;
    //if the hexagon has an atom in it
    boolean HasAtom = false;
    static int GameMode;
    //counter for the total amount of atoms on the board
    static int AtomCounter = 0;
    //hexagon id
    public int Id;
    //hexagon shape
    public Polygon Shape;
    //points array
    public double[] Points;
    //if the hexagon was guessed
    public boolean Guessed = false;

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

        RowId = row;
        Id = id + 1;
        CalculatePosId(RowId, Id);
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
                total += BoardItems.AllHexagons.get(i).size();
            }
            RowPositionId = y - total;

        } else {
            RowPositionId = y;
        }

    }

    /**
     * Draws the hexagon on screen
     *
     * @param root The group the hexagon will be added to
     * @param size The scalar that will define the hexagon size
     * @return Returns the hexagon shape
     */
    public Polygon Draw(Group root, double size) {
        Points = new double[12];

        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i + 30);
            Points[i * 2] = x + size * Math.cos(angle);
            Points[i * 2 + 1] = y + size * Math.sin(angle);
        }
        Text numberText = createNumberText(Points, this.Id);
        root.getChildren().add(numberText);
        Polygon hexagon = new Polygon(Points);
        hexagon.setFill(Color.BLACK);
        hexagon.setStroke(Color.YELLOW);
        hexagon.setFill(Color.TRANSPARENT);
        hexagon.setStrokeWidth(3);


        hexagon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (GameMode != 1 && AtomCounter < 6 && !BoardItems.EndRound && !BoardItems.SetterSwitched) {
                    double[] center = calculatePolygonCenter(hexagon);
                    Atoms at = new Atoms(root, center[0], center[1], Id);
                    BoardItems.AllAtoms.add(0, at);  // Add the created Atom to the list
                    AtomCounter++;
                    HasAtom = true;

                }
                //doesnt allow setter to place atoms after they switch to the experimenter
                else if (BoardItems.SetterSwitched && BoardItems.IsSetter) {
                    BoardItems.addLog("You already let " + InitGame.PlayerOneName + " go, NO CHEATING!!!!!");

                } else if (BoardItems.EndRound) {
                    //if the hexagon was already guessed
                    if (Guessed) {
                        BoardItems.addLog("Hexagon guessed already");

                    } else if (HasAtom) {
                        Guessed = true;
                        //prints to the text area the amount of guesses the experimenter has made
                        if (BoardItems.RoundCount == 0){
                            //increments the player's correct and total amount of guesses made
                            BoardItems.PlayerOneGuesses[1]++;
                            BoardItems.PlayerOneGuesses[0]++;
                            //clears the text area as to have only one line that says guesses made instead of multiple
                            if (BoardItems.TextArea.getText().contains(InitGame.PlayerOneName + " Guesses"))BoardItems.TextArea.setText(BoardItems.TextArea.getText().substring(0, BoardItems.TextArea.getText().lastIndexOf(InitGame.PlayerOneName + " Guesses")));
                            BoardItems.addLog(InitGame.PlayerOneName + " Guesses made; " + BoardItems.PlayerOneGuesses[0]);
                        }else{
                            BoardItems.PlayerTwoGuesses[1]++;
                            BoardItems.PlayerTwoGuesses[0]++;
                            if (BoardItems.TextArea.getText().contains(InitGame.PlayerOneName + " Guesses"))BoardItems.TextArea.setText(BoardItems.TextArea.getText().substring(0, BoardItems.TextArea.getText().lastIndexOf(InitGame.PlayerOneName + " Guesses")));
                            BoardItems.addLog(InitGame.PlayerOneName + " Guesses made; " + BoardItems.PlayerTwoGuesses[0]);
                        }
                    } else if (!HasAtom) {
                        Guessed = true;
                        if (BoardItems.RoundCount == 0){
                            //increments the player's score and total amount of guesses made
                            BoardItems.PlayerOneScore += 5;
                            BoardItems.PlayerOneGuesses[0]++;
                            //clears the text area as to have only one line that says guesses made instead of multiple
                            if (BoardItems.TextArea.getText().contains(InitGame.PlayerOneName + " Guesses"))BoardItems.TextArea.setText(BoardItems.TextArea.getText().substring(0, BoardItems.TextArea.getText().lastIndexOf(InitGame.PlayerOneName + " Guesses")));
                            BoardItems.addLog(InitGame.PlayerOneName + " Guesses made; " + BoardItems.PlayerOneGuesses[0]);
                        }
                        else {
                            BoardItems.PlayerTwoScore += 5;
                            BoardItems.PlayerTwoGuesses[0]++;
                            if (BoardItems.TextArea.getText().contains(InitGame.PlayerOneName + " Guesses"))BoardItems.TextArea.setText(BoardItems.TextArea.getText().substring(0, BoardItems.TextArea.getText().lastIndexOf(InitGame.PlayerOneName + " Guesses")));
                            BoardItems.addLog(InitGame.PlayerOneName + " Guesses made; " + BoardItems.PlayerTwoGuesses[0]);

                        }

                    }
                }
            }
        });

        root.getChildren().add(hexagon);
        Shape = hexagon;
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
