package com.program;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
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


public class Arrow extends Polygon{

    enum Region {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        CENTER,
        MIDDLE_LEFT,
        MIDDLE_RIGHT
    }
    static int loops=0;
    static final double Northeast = -Math.PI / 4 - 0.263;
    static final double Northwest = -3 * Math.PI / 4 + 0.263;
    static final double Southeast = Math.PI / 4 + 0.263;
    static final double Southwest = 3 * Math.PI / 4 - 0.263;
    static final double East = 0;
    static final double West = Math.PI;

    public static double[] midpoints;
    public static ArrayList<Line> rays;
    //arrow id variable
    public int arrowid;
    //exit point for the ray that started at the arrow
    public static int ExitId;
    //boolean to see if experimenter clicked on the arrow already
    public static boolean ClickedByExp;

    /**
     *
     * @param p1
     * @param p2
     * @param z
     * @param hexid
     * @param arrowid
     * @return
     */
    public Object[] createArrow(double[] p1, double[] p2, BoardItems.directions z, int[] hexid, int arrowid){
        this.arrowid=arrowid;
        ClickedByExp = false;
        double midX = (p1[0] + p2[0] ) / 2;
        double midY = (p1[1] + p2[1] ) / 2;
        double y1 = p1[1];
        double x1 = p1[0];
        double y2 = p2[1];
        double x2 = p2[0];
        double x3 = 0;
        double y3 = 0;


        switch (z){
            //calculating the offsets for the arrow to be drawn based on the direction its facing
            case east -> {
                y3 = midY - 3;
                x3 = midX + 20;
                y1 += 9;
                y2 -= 5;
                break;
            }
            case southEast -> {
                y1 += 3;
                y2 -= 3;
                x1 = x1 - 7;
                x2 += 7;
                x3 = midX + 15;
                y3 = midY + 20;
            }
            case northEast -> {
                y1 += 3;
                y2 -= 3;
                x1 = x1 + 9;
                x2 -= 7;
                x3 = midX + 15;
                y3 = midY - 20;
            }
            case northWest -> {
                y1 += 4;
                y2 += 3;
                x2 -= 9;
                x3 = midX - 15;
                y3 = midY - 20;
            }
            case west -> {
                y3 = midY + 3;
                x3 = midX - 20;
                y1 -= 9;
                y2 += 5;
            }
            case southWest -> {

                x1 -= 9;
                y1 -= 3;
                x2 += 9;
                y2 += 2;
                x3 = midX - 10;
                y3 = midY + 18;
            }

        }
        midpoints = new double[]{midX, midY};
        Arrow polygon = new Arrow();
        polygon.arrowid=arrowid;


        //ARROW ID IS FOR THE NUMBERS
        String arrowIDstring = Integer.toString(arrowid);
        Label numberLabel = new Label(arrowIDstring);
        int addIndex1 = 0;
        int addIndexX = 0;

        if((arrowid > 45 && arrowid < 80)||arrowid==1 ){
            addIndex1= 45;
            addIndexX = 10;
        }
        else if(arrowid > 36 && arrowid < 80){
            addIndexX = -20;
            addIndex1 =20;
            if (arrowid%2==0){
                addIndex1 = 30;
                addIndexX = -10;
            }
        } else if(arrowid >= 28 && arrowid < 80){
            addIndex1= -20;
            addIndexX = -20;

            if(arrowid%2==1){
                addIndexX = -20;
                addIndex1 = 0;
            }

        } else if (arrowid > 18 && arrowid < 80) {
            addIndex1 = -30;
            addIndexX=0;
        }
        else if (arrowid > 10 && arrowid < 80) {
            addIndexX = 20;
            addIndex1 = -1;
            if(arrowid%2==1){
                addIndex1 = -20;
                addIndexX = 15;
            }
        }
        else {
            addIndex1 = 40;
            addIndexX = 20;
            if(arrowid%2==0){
                addIndex1 = 0;
                addIndexX = 20;
            }
        }



        numberLabel.setLayoutX(x1-addIndexX);
        numberLabel.setLayoutY(y3-addIndex1);
        numberLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15)); // You can adjust the font family and size as needed

        numberLabel.setTextFill(Color.WHITE);


        polygon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!allHexagons.get(hexid[0]).get(hexid[1]).hasAtom && !BoardItems.EndOfRound && !markerEnabled && !ClickedByExp){
                  ClickedByExp = true;
                    if (Hexagon.mode != 0) {
                        double directionAngle;
                        switch (z) {
                            case east:
                                directionAngle = 0; // East
                                break;
                            case southEast:
                                directionAngle = Math.PI / 4 + 0.263; // Southeast
                                break;
                            case northEast:
                                directionAngle = -Math.PI / 4 - 0.263; // Northeast
                                break;
                            case northWest:
                                directionAngle = -3 * Math.PI / 4 + 0.263; // Northwest
                                break;
                            case west:
                                directionAngle = Math.PI; // West
                                break;
                            case southWest:
                                directionAngle = 3 * Math.PI / 4 - 0.263; // Southwest
                                break;
                            default:
                                directionAngle = -1; // Default to east if direction is unknown
                        }
                         rays = new ArrayList<>();
                        Rays NewRay = new Rays(midX,midY,directionAngle,rays);
                        ExitId = NewRay.ExitId;
                        ActualRayPoints.put(arrowid, ExitId);
                            Arrow.MakeRaysVisible(false);
                            PauseTransition pause = new PauseTransition(Duration.seconds(3));
                        BoardItems.addLog("Switching to Setter.......");
                            pause.setOnFinished(event2 -> {
                                ChangeView.SetterButton.fire();
                                Scoring.SetterInput(arrowid);
                            } );
                            pause.play();
                            root.getChildren().addAll(rays);
                    }
                } else if (EndOfRound) {
                    BoardItems.addLog("round is over please make your guesses   ");
                } else if(markerEnabled){

                }else if (!allHexagons.get(hexid[0]).get(hexid[1]).hasAtom){
                    BoardItems.addLog("Cant shoot ray because arrow in hexagon number " + allHexagons.get(hexid[0]).get(hexid[1]).Id + " because there is an atom here ");
                }else BoardItems.addLog("YOU ALREADY SHOT AN ARROW FROM HERE!!!!");
            }
        });

        polygon.getPoints().addAll(new Double[]{
                x1, y1,
                x2, y2,
                x3, y3,});
        polygon.setFill(Color.YELLOW);
        return new Object[] {polygon,numberLabel};
    }



    public static void MakeRaysVisible(boolean x){
        if (rays != null) {
            if (x) {
                for (Line r : rays) r.setVisible(true);
            } else {
                for (Line r : rays) r.setVisible(false);
            }

        }
    }

}