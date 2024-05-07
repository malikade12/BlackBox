package com.program;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.program.BoardItems.*;
import static com.program.Main.*;

/**
 * @author Bhavnyash Singh
 * @version 1.0 May 2024
 * Constructs arrows and their corresponding numbers <p>
 * Has a handler that projects rays when arrows are pressed
 */
public class Arrow extends Polygon {

    enum Region {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        CENTER,
        MIDDLE_LEFT,
        MIDDLE_RIGHT
    }

    static int loops = 0;
    static final double NORTH_EAST = -Math.PI / 4 - 0.263;
    static final double NORTH_WEST = -3 * Math.PI / 4 + 0.263;
    static final double SOUTH_EAST = Math.PI / 4 + 0.263;
    static final double SOUTH_WEST = 3 * Math.PI / 4 - 0.263;
    static final double EAST = 0;
    static final double WEST = Math.PI;

    public static double[] midpoints;
    public static ArrayList<Line> rays;
    //arrow id variable
    public int ArrowId;
    //exit point for the ray that started at the arrow
    public static int ExitId;
    //boolean to see if experimenter clicked on the arrow already
    public  boolean ShotAlready = false;
    //the actual triangle shape is used to initialise this
    public Polygon Shape;

    /**
     * @param CoOrdinates1 the first set of CoOrds for the line
     * @param CoOrdinates2 the second set of CoOrds for the line
     * @param z The direction of the arrow
     * @param hexid the id of the hexagons
     * @param arrowid the id of the arrow
     * @return  An object array of an arrow and its id
     */
    public void CreateArrow(double[] CoOrdinates1, double[] CoOrdinates2, BoardItems.directions z, int[] hexid, int arrowid) {
        this.ArrowId = arrowid;
        //getting the points for drawing the arrow
        //the x and y points for the middle of the line
        double midX = (CoOrdinates1[0] + CoOrdinates2[0]) / 2;
        double midY = (CoOrdinates1[1] + CoOrdinates2[1]) / 2;
        double y1 = CoOrdinates1[1];
        double x1 = CoOrdinates1[0];
        double y2 = CoOrdinates2[1];
        double x2 = CoOrdinates2[0];
        double x3 = 0;
        double y3 = 0;
        switch (z) {
            //calculating the offsets for the arrow to be drawn based on the direction its facing
            case East -> {
                y3 = midY - 3;
                x3 = midX + 20;
                y1 += 9;
                y2 -= 5;
            }
            case SouthEast -> {
                y1 += 3;
                y2 -= 3;
                x1 = x1 - 7;
                x2 += 7;
                x3 = midX + 15;
                y3 = midY + 20;
            }
            case NorthEast -> {
                y1 += 3;
                y2 -= 3;
                x1 = x1 + 9;
                x2 -= 7;
                x3 = midX + 15;
                y3 = midY - 20;
            }
            case NorthWest -> {
                y1 += 4;
                y2 += 3;
                x2 -= 9;
                x3 = midX - 15;
                y3 = midY - 20;
            }
            case West -> {
                y3 = midY + 3;
                x3 = midX - 20;
                y1 -= 9;
                y2 += 5;
            }
            case SouthWest -> {

                x1 -= 9;
                y1 -= 3;
                x2 += 9;
                y2 += 2;
                x3 = midX - 10;
                y3 = midY + 18;
            }

        }

       DrawNumber(x1, y3);
       Polygon polygon = new Polygon();




        polygon.setOnMouseEntered(e->
        {
            polygon.setFill(Color.PURPLE);
            polygon.setStroke(Color.YELLOW);
        });
        polygon.setOnMouseExited(e-> {
            polygon.setFill(Color.YELLOW);

        });
        polygon.setOnMouseClicked(new EventHandler<MouseEvent>() {


            @Override
            public void handle(MouseEvent event) {
                //makes sure the arrow isnt in a hexagon that has an atom, experimenter isnt guessing or marking and a ray hasnt been shot already
                if (!AllHexagons.get(hexid[0]).get(hexid[1]).HasAtom && !BoardItems.EndRound && !MarkerEnabled && !ShotAlready) {
                    ShotAlready = true;
                    polygon.setFill(Color.PURPLE);
                    System.out.println(arrowid);
                    if (Hexagon.GameMode != 0) {
                        double directionAngle;
                        switch (z) {
                            case East:
                                directionAngle = 0; // East
                                break;
                            case SouthEast:
                                directionAngle = Math.PI / 4 + 0.263; // Southeast
                                break;
                            case NorthEast:
                                directionAngle = -Math.PI / 4 - 0.263; // Northeast
                                break;
                            case NorthWest:
                                directionAngle = -3 * Math.PI / 4 + 0.263; // Northwest
                                break;
                            case West:
                                directionAngle = Math.PI; // West
                                break;
                            case SouthWest:
                                directionAngle = 3 * Math.PI / 4 - 0.263; // Southwest
                                break;
                            default:
                                directionAngle = -1; // Default to east if direction is unknown
                        }
                        rays = new ArrayList<>();
                        Rays NewRay = new Rays(midX, midY, directionAngle, rays);
                        //getting the exit arrowid for the ray that was shot
                        ExitId = NewRay.ExitId;
                        //putting the arrowid and exitid into a map to check for false reporting by the setter
                        ActualRayPoints.put(arrowid, ExitId);
                        //makes sure the ray isnt visible for the experimenter
                        Arrow.MakeRaysVisible(false);
                        //switches to the setter to get his report on how do ray moved
                        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                        BoardItems.addLog("Switching to Setter.......");
                        pause.setOnFinished(event2 -> {
                            ChangeView.SetterButton.fire();
                            Scoring.SetterInput(arrowid);
                        });
                        pause.play();
                        //adds ray to the root scene
                        Root.getChildren().addAll(rays);
                    }
                }
                //if the setter clicks on the on arrow when they should be guessing
                else if (EndRound) {
                    BoardItems.addLog("round is over please make your guesses   ");
                } else if (MarkerEnabled) {

                }
                //if a ray was already shot from the arrow
                else if (ShotAlready) {
                    BoardItems.addLog("ALREADY SHOT ARROW FROM HERE!!!");
                    //if the arrow has an atom and a ray is attempted to be shot from it
                } else if (!AllHexagons.get(hexid[0]).get(hexid[1]).HasAtom) {
                    BoardItems.addLog("Cant shoot ray because arrow in hexagon number " + AllHexagons.get(hexid[0]).get(hexid[1]).Id + " because there is an atom here ");
                }
            }
        });

        polygon.getPoints().addAll(new Double[]{
                x1, y1,
                x2, y2,
                x3, y3,});
        polygon.setFill(Color.YELLOW);
        Root.getChildren().add(polygon);
        Shape = polygon;
    }
    public void DrawNumber(double x1, double y3){
        //ARROW ID IS FOR THE NUMBERS
        Label numberLabel = new Label(Integer.toString(ArrowId));
        //Calculating X and Y offset for the Ids of the arrows on the board
        int addOffsetY = 0;
        int addOffsetX = 0;

        //Conditions for the arrow offset
        if ((ArrowId > 45 && ArrowId < 80) || ArrowId == 1) {
            addOffsetY = 45;
            addOffsetX = 10;
        } else if (ArrowId > 36 && ArrowId < 80) {
            addOffsetX = -20;
            addOffsetY = 20;
            if (ArrowId % 2 == 0) {
                addOffsetY = 30;
                addOffsetX = -10;
            }
        } else if (ArrowId >= 28 && ArrowId < 80) {
            addOffsetY = -20;
            addOffsetX = -20;

            if (ArrowId % 2 == 1) {
                addOffsetY = 0;
            }

        } else if (ArrowId > 18 && ArrowId < 80) {
            addOffsetY = -30;
        } else if (ArrowId > 10 && ArrowId < 80) {
            addOffsetX = 20;
            addOffsetY = -1;
            if (ArrowId % 2 == 1) {
                addOffsetY = -20;
                addOffsetX = 15;
            }
        } else {
            addOffsetY = 40;
            addOffsetX = 20;
            if (ArrowId % 2 == 0) {
                addOffsetY = 0;
            }
        }


        numberLabel.setLayoutX(x1 - addOffsetX);//Adjusting the placement of the number lables based of the X and Y offset
        numberLabel.setLayoutY(y3 - addOffsetY);
        numberLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15)); // You can adjust the font family and size as needed

        numberLabel.setTextFill(Color.WHITE);
        Root.getChildren().add(numberLabel);
    }



    /**
     * Decides whether rays should be visible
     * @param x boolean to decide ray visibility
     */
    public static void MakeRaysVisible(boolean x) {
        if (rays != null) {
            if (x) {
                for (Line r : rays) r.setVisible(true);
            } else {
                for (Line r : rays) r.setVisible(false);
            }

        }
    }

}