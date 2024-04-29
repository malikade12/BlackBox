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
import java.util.List;

import static com.program.Main.*;


public class Arrow extends Polygon{

    private enum Region {
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
    private Polygon triangle;
    public static double[] midpoints;
    public static ArrayList<Line> rays;
    public int arrowid;
    public static int ExitId;


    public Object[] createArrow(double[] p1, double[] p2, Main.directions z, int[] hexid, int arrowid){
        this.arrowid=arrowid;
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
                if(!allHexagons.get(hexid[0]).get(hexid[1]).hasAtom && !Main.EndOfRound && !markerEnabled){



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
                        double rayLength = 5; // Adjust the desired length of the ray
                        double[] endPoint = findEndPoint(midX, midY, directionAngle, rayLength);
                        makeRays(midX,midY,directionAngle,rays);
                        ActualRayPoints.put(arrowid, ExitId);
                            MakeRaysVisible(false);
                            PauseTransition pause = new PauseTransition(Duration.seconds(3));
                            System.out.println("Switching to Setter.......");
                            Main.addLog("Switching to Setter.......");
                            pause.setOnFinished(event2 -> {
                                ChangeView.SetterButton.fire();
                                Scoring.SetterInput();
                            } );
                            pause.play();
                            root.getChildren().addAll(rays);
                    }
                } else if (EndOfRound) {
                    System.out.println("round is over please make your guesses   ");
                    Main.addLog("round is over please make your guesses   ");
                } else if(markerEnabled){

                }else if (!allHexagons.get(hexid[0]).get(hexid[1]).hasAtom){
                    System.out.println("Cant shoot ray because arrow in hexagon number " + allHexagons.get(hexid[0]).get(hexid[1]).Id + " because there is an atom here ");
                    Main.addLog("Cant shoot ray because arrow in hexagon number " + allHexagons.get(hexid[0]).get(hexid[1]).Id + " because there is an atom here ");
                }
            }
        });

        polygon.getPoints().addAll(new Double[]{
                x1, y1,
                x2, y2,
                x3, y3,});
        polygon.setFill(Color.YELLOW);
        return new Object[] {polygon,numberLabel};
    }
    //This method simply calculates and returns the end x and y points of a ray
    private static double[] findEndPoint(double midX, double midY, double directionAngle, double rayLength) {
        double endX = midX + rayLength * Math.cos(directionAngle);
        double endY = midY + rayLength * Math.sin(directionAngle);

        boolean inHexagon = false; // Flag to track if the endpoint is in a hexagon

        for (List<Hexagon> innerList : allHexagons) {
            for (Hexagon hexagon : innerList) {
                if (hexagon.shape.contains(endX, endY)) {
                    inHexagon = true; // Set the flag to true if the endpoint is in a hexagon
                    break; // Exit the loop since we found the hexagon
                }
            }
            if (inHexagon) {
                break; // Exit the outer loop if the endpoint is in a hexagon
            }
        }
        if (inHexagon) {
            // Check if incrementing or decrementing the endpoint would keep it inside a hexagon
            double slightOffset = 25 / 1.3; // Define a slight offset for checking
            boolean nextInHexagon = false;
            int hexid = 0;
            int rowid = -1;

            // Check with slight offsets in both x and y directions
            for (double offset : new double[]{-slightOffset, slightOffset}) {
                if (!nextInHexagon) {
                    double nextEndX = endX + rayLength * Math.cos(directionAngle);
                    double nextEndY = endY + rayLength * Math.sin(directionAngle);

                    for (List<Hexagon> innerList : allHexagons) {
                        rowid++;
                        for (Hexagon hexagon : innerList) {
                            hexid = hexagon.Id;
                            if (hexagon.shape.contains(nextEndX + offset, nextEndY + offset)) {
                                nextInHexagon = true; // Set the flag to true if the next endpoint with offset is in a hexagon
                                break; // Exit the loop since we found the hexagon
                            }
                        }
                        if (nextInHexagon) {
                            break; // Exit the outer loop if the next endpoint with offset is in a hexagon
                        }
                    }
                }
            }

            if (nextInHexagon) {
                // If the next endpoint with slight offset is also in a hexagon, recursively call the method again
                return findEndPoint(endX, endY, directionAngle, rayLength);
            } else {
                // If incrementing or decrementing the endpoint would move it out of a hexagon, return the current endpoint
                return new double[]{endX, endY};
            }
        } else {
            // If the endpoint is not in a hexagon from the beginning, return the current endpoint
            return new double[]{endX, endY};
        }
    }


    private static Point2D getCircleLineIntersection(Circle circle, double lineStartX, double lineStartY, double lineEndX, double lineEndY, double directionAngle) {
        lineStartX += 5 * Math.cos(directionAngle);
        lineStartY += 5 * Math.sin(directionAngle);
        double cx = circle.getCenterX();
        double cy = circle.getCenterY();
        double radius = circle.getRadius();

        double dx = lineEndX - lineStartX;
        double dy = lineEndY - lineStartY;

        double A = dx * dx + dy * dy;
        double B = 2 * (dx * (lineStartX - cx) + dy * (lineStartY - cy));
        double C = (lineStartX - cx) * (lineStartX - cx) + (lineStartY - cy) * (lineStartY - cy) - radius * radius;

        double discriminant = B * B - 4 * A * C;

        if (discriminant >= 0) {
            double t1 = (-B + Math.sqrt(discriminant)) / (2 * A);
            double t2 = (-B - Math.sqrt(discriminant)) / (2 * A);

            // Check if the intersection points are within the line segment
            if ((t1 > 0 && t1 <= 1) || (t2 > 0 && t2 <= 1)) {
                // Calculate the intersection points
                double intersectionX1 = lineStartX + t1 * dx;
                double intersectionY1 = lineStartY + t1 * dy;
                double intersectionX2 = lineStartX + t2 * dx;
                double intersectionY2 = lineStartY + t2 * dy;

                // Return the intersection point closest to the starting point of the ray
                if (t1 > 0 && t1 <= 1 && t2 > 0 && t2 <= 1) {
                    double dist1 = Math.sqrt(Math.pow(intersectionX1 - lineStartX, 2) + Math.pow(intersectionY1 - lineStartY, 2));
                    double dist2 = Math.sqrt(Math.pow(intersectionX2 - lineStartX, 2) + Math.pow(intersectionY2 - lineStartY, 2));
                    return (dist1 < dist2) ? new Point2D(intersectionX1, intersectionY1) : new Point2D(intersectionX2, intersectionY2);
                } else if (t1 >= 0 && t1 <= 1) {
                    return new Point2D(intersectionX1, intersectionY1);
                } else {
                    return new Point2D(intersectionX2, intersectionY2);
                }
            }
        }
        return null;
    }



    //This method calculates the angle at which to deflect the ray
    //It uses the initial direction of the ray and the region of the influence circle that is hit
    //Depending on these 2 variables, the nested switches determine which direction to give the new ray
    private static double calculateReflection1Atom(Region intersectionRegion, double OriginalDirection) {
        double directionAngle = 0;
        switch ((int) OriginalDirection) {
            case (int) West:
                switch (intersectionRegion){
                    case MIDDLE_RIGHT -> directionAngle = -1;
                    case TOP_RIGHT -> directionAngle = Northwest;
                    case BOTTOM_RIGHT -> directionAngle = Southwest;
                }
                break;
            case (int) Southeast:
                switch (intersectionRegion){
                    case TOP_LEFT -> directionAngle = -1;
                    case TOP_RIGHT -> directionAngle = East;
                    case MIDDLE_LEFT -> directionAngle = Southwest;
                }
                break;
            case (int) Northeast:
                switch (intersectionRegion){
                    case BOTTOM_RIGHT -> directionAngle = East;
                    case BOTTOM_LEFT -> directionAngle = -1;
                    case MIDDLE_LEFT -> directionAngle = Northwest;
                }
                break;
            case (int) Northwest:
                switch (intersectionRegion){
                    case MIDDLE_RIGHT -> directionAngle = Northeast;
                    case BOTTOM_RIGHT -> directionAngle = -1;
                    case BOTTOM_LEFT -> directionAngle = West;
                }
                break;
            case 0:
                switch (intersectionRegion){
                    case MIDDLE_LEFT -> directionAngle = -1;
                    case TOP_LEFT -> directionAngle = Northeast;
                    case BOTTOM_LEFT -> directionAngle = Southeast;
                }
                break;
            case (int) Southwest:
                switch (intersectionRegion){
                    case TOP_RIGHT -> directionAngle = -1;
                    case TOP_LEFT -> directionAngle = West;
                    case MIDDLE_RIGHT -> directionAngle = Southeast;
                }
                break;
            default:
                directionAngle = -1; // Default to east if direction is unknown
        }
        return directionAngle;
    }
    private static double calculateReflection2Atoms(double initialX, double initialY, double endX, double endY, double directionAngle) {
        Region intersectionRegion1 = null;
        Region intersectionRegion2 = null;
        double reflectionAngle = 0;
        for (Atoms atom : Main.allAtoms) {
            Point2D intersection = getCircleLineIntersection(atom.orbit, initialX, initialY, endX + 5 * Math.cos(directionAngle), endY + 5 * Math.sin(directionAngle), directionAngle);
            if (intersection!=null) {
                endX = intersection.getX();
                endY = intersection.getY();
                if(intersectionRegion1 == null){
                    intersectionRegion1 = determineRegion(intersection, atom.orbit);
                }
                else if(intersectionRegion2 == null){
                    intersectionRegion2 = determineRegion(intersection, atom.orbit);

                }
            }
        }


        //Northwest
        if (((intersectionRegion1 == Region.BOTTOM_LEFT && intersectionRegion2 == Region.MIDDLE_RIGHT) ||
                (intersectionRegion1 == Region.MIDDLE_RIGHT && intersectionRegion2 == Region.BOTTOM_LEFT))
                && directionAngle == Northwest){
            reflectionAngle = -1;
        }
        else if (((intersectionRegion1 == Region.BOTTOM_LEFT && intersectionRegion2 == Region.BOTTOM_RIGHT) ||
                (intersectionRegion1 == Region.BOTTOM_RIGHT && intersectionRegion2 == Region.BOTTOM_LEFT))
                && directionAngle == Northwest){
            reflectionAngle = Southwest;
        }
        else if (((intersectionRegion1 == Region.BOTTOM_RIGHT && intersectionRegion2 == Region.MIDDLE_RIGHT) ||
                (intersectionRegion1 == Region.MIDDLE_RIGHT && intersectionRegion2 == Region.BOTTOM_RIGHT))
                && directionAngle == Northwest){
            reflectionAngle = East;
        }

        //Northeast
        else if (((intersectionRegion1 == Region.MIDDLE_LEFT && intersectionRegion2 == Region.BOTTOM_RIGHT) ||
                (intersectionRegion1 == Region.BOTTOM_RIGHT && intersectionRegion2 == Region.MIDDLE_LEFT))
                && directionAngle == Northeast){
            reflectionAngle = -1;
        }
        else if (((intersectionRegion1 == Region.MIDDLE_LEFT && intersectionRegion2 == Region.BOTTOM_LEFT) ||
                (intersectionRegion1 == Region.BOTTOM_LEFT && intersectionRegion2 == Region.MIDDLE_LEFT))
                && directionAngle == Northeast){
            reflectionAngle = West;
        }
        else if (((intersectionRegion1 == Region.BOTTOM_LEFT && intersectionRegion2 == Region.BOTTOM_RIGHT) ||
                (intersectionRegion1 == Region.BOTTOM_RIGHT && intersectionRegion2 == Region.BOTTOM_LEFT))
                && directionAngle == Northeast){
            reflectionAngle = Southeast;
        }

        //Southwest
        else if (((intersectionRegion1 == Region.TOP_LEFT && intersectionRegion2 == Region.MIDDLE_RIGHT) ||
                (intersectionRegion1 == Region.MIDDLE_RIGHT && intersectionRegion2 == Region.TOP_LEFT))
                && directionAngle == Southwest){
            reflectionAngle = -1;
        }
        else if (((intersectionRegion1 == Region.TOP_LEFT && intersectionRegion2 == Region.TOP_RIGHT) ||
                (intersectionRegion1 == Region.TOP_RIGHT && intersectionRegion2 == Region.TOP_LEFT))
                && directionAngle == Southwest){
            reflectionAngle = Northwest;
        }
        else if (((intersectionRegion1 == Region.TOP_RIGHT && intersectionRegion2 == Region.MIDDLE_RIGHT) ||
                (intersectionRegion1 == Region.MIDDLE_RIGHT && intersectionRegion2 == Region.TOP_RIGHT))
                && directionAngle == Southwest){
            reflectionAngle = East;
        }

        //Southeast
        else if (((intersectionRegion1 == Region.MIDDLE_LEFT && intersectionRegion2 == Region.TOP_RIGHT) ||
                (intersectionRegion1 == Region.TOP_RIGHT && intersectionRegion2 == Region.MIDDLE_LEFT))
                && directionAngle == Southeast){
            reflectionAngle = -1;
        }
        else if (((intersectionRegion1 == Region.TOP_LEFT && intersectionRegion2 == Region.TOP_RIGHT) ||
                (intersectionRegion1 == Region.TOP_RIGHT && intersectionRegion2 == Region.TOP_LEFT))
                && directionAngle == Southeast){
            reflectionAngle = Northeast;
        }
        else if (((intersectionRegion1 == Region.MIDDLE_LEFT && intersectionRegion2 == Region.TOP_LEFT) ||
                (intersectionRegion1 == Region.TOP_LEFT && intersectionRegion2 == Region.MIDDLE_LEFT))
                && directionAngle == Southeast){
            reflectionAngle = West;
        }

        //West
        else if (((intersectionRegion1 == Region.TOP_RIGHT && intersectionRegion2 == Region.BOTTOM_RIGHT) ||
                (intersectionRegion1 == Region.BOTTOM_RIGHT && intersectionRegion2 == Region.TOP_RIGHT))
                && directionAngle == West){
            reflectionAngle = -1;
        }
        else if (((intersectionRegion1 == Region.TOP_RIGHT && intersectionRegion2 == Region.MIDDLE_RIGHT) ||
                (intersectionRegion1 == Region.MIDDLE_RIGHT && intersectionRegion2 == Region.TOP_RIGHT))
                && directionAngle == West){
            reflectionAngle = Northeast;
        }
        else if (((intersectionRegion1 == Region.BOTTOM_RIGHT && intersectionRegion2 == Region.MIDDLE_RIGHT) ||
                (intersectionRegion1 == Region.MIDDLE_RIGHT && intersectionRegion2 == Region.BOTTOM_RIGHT))
                && directionAngle == West){
            reflectionAngle = Southeast;
        }

        //East
        else if (((intersectionRegion1 == Region.TOP_LEFT && intersectionRegion2 == Region.BOTTOM_LEFT) ||
                (intersectionRegion1 == Region.BOTTOM_LEFT && intersectionRegion2 == Region.TOP_LEFT))
                && directionAngle == East){
            reflectionAngle = -1;
        }
        else if (((intersectionRegion1 == Region.MIDDLE_LEFT && intersectionRegion2 == Region.BOTTOM_LEFT) ||
                (intersectionRegion1 == Region.BOTTOM_LEFT && intersectionRegion2 == Region.MIDDLE_LEFT))
                && directionAngle == East){
            reflectionAngle = Southwest;
        }
        else if (((intersectionRegion1 == Region.TOP_LEFT && intersectionRegion2 == Region.MIDDLE_LEFT) ||
                (intersectionRegion1 == Region.MIDDLE_LEFT && intersectionRegion2 == Region.TOP_LEFT))
                && directionAngle == East){
            reflectionAngle = Northwest;
        }

        return reflectionAngle;
    }
        //This is the helper method which calculates which side of the influence circle was hit by the ray
    //We need this method to be able to reflect the ray correctly
    private static Region determineRegion(Point2D intersectionPoint, Circle circle) {
        double centerX = circle.getCenterX();
        double centerY = circle.getCenterY();
        double x = intersectionPoint.getX();
        double y = intersectionPoint.getY();

        if (x == centerX && y == centerY) {
            return Region.CENTER;
        } else if (x >= centerX && y < centerY - circle.getRadius() / 2) {
            return Region.TOP_RIGHT;
        } else if (x < centerX && y < centerY - circle.getRadius() / 2) {
            return Region.TOP_LEFT;
        } else if (x < centerX && y >= centerY - circle.getRadius() / 2 && y < centerY + circle.getRadius() / 2) {
            return Region.MIDDLE_LEFT;
        } else if (x >= centerX && y >= centerY - circle.getRadius() / 2 && y < centerY + circle.getRadius() / 2) {
            return Region.MIDDLE_RIGHT;
        } else if (x >= centerX && y >= centerY + circle.getRadius() / 2) {
            return Region.BOTTOM_RIGHT;
        } else {
            return Region.BOTTOM_LEFT;
        }
    }

    private static int countCircleLineIntersections(double lineStartX, double lineStartY, double lineEndX, double lineEndY, double directionAngle) {
        int intersectingAtomsCount = 0;
        for (Atoms atom : Main.allAtoms) {
            // Get the point of intersection
            Point2D intersection = getCircleLineIntersection(atom.orbit, lineStartX, lineStartY, lineEndX, lineEndY, directionAngle);
            if (intersection != null) {
                intersectingAtomsCount++; // Increment the count for each intersecting atom

            }
        }
        if (intersectingAtomsCount == 0) return 1;
        return intersectingAtomsCount;
    }

    private static void makeRays(double initialX, double initialY, double directionAngle, List<Line> rays) {
        loops++;
        double rayLength = 5;
        double[] endPoint = findEndPoint(initialX, initialY, directionAngle, rayLength);
        double endX = endPoint[0];
        double endY = endPoint[1];
        double reflectionAngle = -1;
        double currentX=initialX;
        double currentY=initialY;
        currentX += 25*Math.cos(directionAngle);
        currentY += 25*Math.sin(directionAngle);
        boolean found = false;
        for (int i = 0 ; i < 50 ; i++ ) {
            for (Atoms atom : Main.allAtoms) {
                Point2D Intersection = getCircleLineIntersection(atom.orbit, initialX, initialY, currentX, currentY, directionAngle);
                if (Intersection != null) {
                    for (List<Hexagon> innerList : allHexagons) {
                        for (Hexagon hexagon : innerList) {
                            if (hexagon.shape.contains(Intersection.getX(), Intersection.getY())) {
                                currentX = hexagon.x;
                                currentY = hexagon.y;
                            }
                        }
                    }
                    Region intersectedRegion = determineRegion(Intersection, atom.orbit);
                    int AtomsHit = countCircleLineIntersections(initialX, initialY, currentX+25*Math.cos(directionAngle), currentY+25*Math.sin(directionAngle), directionAngle);
                    if (AtomsHit==1) reflectionAngle = calculateReflection1Atom(intersectedRegion, directionAngle);
                    if (AtomsHit==2) {
                        reflectionAngle = calculateReflection2Atoms(initialX, initialY, currentX+25*Math.cos(directionAngle), currentY+25*Math.sin(directionAngle), directionAngle);
                    }
                    if (AtomsHit==3) {
                        reflectionAngle = -1;
                    }

                    found = true;
                    if (reflectionAngle == -1) {
                        break;
                    }
                    break;
                }
            }
            if(found){
                break;
            }
            currentX += 25*Math.cos(directionAngle);
            currentY += 25*Math.sin(directionAngle);
        }
        if(!found){
            currentX = endX;
            currentY = endY;
        }
        Line Ray = new Line(initialX, initialY, currentX, currentY); // Original ray from midpoint to intersection point
        Ray.setStroke(Color.CYAN);
        Ray.setStrokeWidth(6);
        ExitId = FindArrow(Ray.getEndX(), Ray.getEndY());
        rays.add(Ray);
        if (reflectionAngle != -1) {
            makeRays(currentX, currentY, reflectionAngle, rays);
        }
    }
    public static int FindArrow(double x, double y){
        for (Arrow a: allArrows){
          for (int i = -5; i <=5; i++){
              for (int j = -5; j <= 5; j++) {
                  if (a.contains(x + i, y + j)) return a.arrowid;
              }
          }
        }
        return 0;
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