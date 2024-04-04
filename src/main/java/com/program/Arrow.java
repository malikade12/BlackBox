package com.program;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;

import static com.program.Main.*;

public class Arrow {

    private enum Region {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        CENTER,
        MIDDLE_LEFT,
        MIDDLE_RIGHT
    }
    static final double Northeast = -Math.PI / 4 - 0.263;
    static final double Northwest = -3 * Math.PI / 4 + 0.263;
    static final double Southeast = Math.PI / 4 + 0.263;
    static final double Southwest = 3 * Math.PI / 4 - 0.263;
    static final double East = 0;
    static final double West = Math.PI;
    private Polygon triangle;
    public static double[] midpoints;

    public static Polygon createArrow(double[] p1, double[] p2, Main.directions z){
        double midX = (p1[0] + p2[0] ) / 2;
        double midY = (p1[1] + p2[1] ) / 2;
        double y1 = p1[1];
        double x1 = p1[0];
        double y2 = p2[1];
        double x2 = p2[0];
        double x3 = 0;
        double y3 = 0;
        switch (z){
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
        Polygon polygon = new Polygon();

        polygon.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
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
                            directionAngle = 0; // Default to east if direction is unknown
                    }
                    double rayLength = 5; // Adjust the desired length of the ray
                    List<Line> rays = new ArrayList<>();
                    //These 2 store the endpoints of the 1st deflected ray (the 2nd ray drawn)
                    double NewendX=0;
                    double NewendY=0;
                    //These 2 store the endpoints of the 2nd deflected ray (the 3rd ray drawn)
                    double NewendX2=0;
                    double NewendY2=0;
                    //Variable used to check if ray is deflected at all or not
                    int reflected = 0;
                    double[] endPoint = findEndPoint(midX, midY, directionAngle, rayLength);
                    double endX = endPoint[0];
                    double endY = endPoint[1];
                    Region intersectionRegion;
                    double reflectionAngle = 0;
                    //Variable used to check if ray has been deflected once or not
                    int onedeflection = 0;
                    Main.directions Direct2 = null;
                    Point2D closestIntersection = null; // Variable to store the closest intersection point
                    //Loop checks for the first intersection of the first ray
                    for (Atoms atom : Main.allAtoms) {
                        Point2D intersection1 = getCircleLineIntersection(atom.orbit, midX, midY, endX, endY);
                        if (intersection1 != null) {
                            double distanceToIntersection = Math.sqrt(Math.pow(intersection1.getX() - midX, 2) + Math.pow(intersection1.getY() - midY, 2));
                            if (closestIntersection == null || distanceToIntersection < Math.sqrt(Math.pow(closestIntersection.getX() - midX, 2) + Math.pow(closestIntersection.getY() - midY, 2))) {
                                closestIntersection = intersection1; // Update the closest intersection point
                                intersectionRegion = determineRegion(closestIntersection, atom.orbit);
                                reflectionAngle = calculateReflectionAngle(intersectionRegion, z);
                            }
                        }
                    }
                    if (closestIntersection != null){
                        endX = closestIntersection.getX();
                        endY = closestIntersection.getY();
                        //Increment the ray a bit further so that the getCircleLineIntersection method
                        //works correctly, otherwise it will give the start point of the ray as the
                        //intersection point
                        endX += rayLength * Math.cos(directionAngle);
                        endY += rayLength * Math.sin(directionAngle);
                        System.out.println("Intersection point1: " + closestIntersection.getX() + ", " + closestIntersection.getY());
                        //This switch statement is needed to give a new "z" value for the 2nd ray that is drawn since the
                        //2nd ray has a different direction than the first one we drew
                        switch ((int) reflectionAngle) {
                            case (int) (-Math.PI / 4 - 0.263):
                                Direct2 = Main.directions.northEast;
                                System.out.println("NorthEast");
                                break;
                            case (int) (-3 * Math.PI / 4 + 0.263):
                                Direct2 = Main.directions.northWest;
                                System.out.println("NorthWest");
                                break;
                            case (int) (Math.PI / 4 + 0.263):
                                Direct2 = Main.directions.southEast;
                                System.out.println("southEast");
                                break;
                            case (int) (3 * Math.PI / 4 - 0.263):
                                Direct2 = Main.directions.southWest;
                                System.out.println("southWest");
                                break;
                            case 0:
                                Direct2 = Main.directions.east;
                                System.out.println("East");
                                break;
                            case (int) (Math.PI):
                                Direct2 = Main.directions.west;
                                System.out.println("West");
                                break;
                        }
                        reflected = 1;
                        double[] NewendPoint = findEndPoint(endX, endY, reflectionAngle, rayLength);
                        NewendX = NewendPoint[0];
                        NewendY = NewendPoint[1];
                        Line originalRay = new Line(midX, midY, endX, endY); // Original ray from midpoint to intersection point
                        originalRay.setStroke(Color.CYAN);
                        originalRay.setStrokeWidth(7);
                        rays.add(originalRay);
                        Point2D NextIntersection = null;
                        Region intersectionRegion2 = null;
                        double[] NewendPoint2;
                        double reflectionAngle2 = -1;
                        //Again checks for the intersection of the 2nd ray
                        for (Atoms atom2 : Main.allAtoms) {
                            Point2D Intersection2 = getCircleLineIntersection(atom2.orbit, endX + rayLength * Math.cos(reflectionAngle), endY + rayLength * Math.sin(reflectionAngle), NewendX, NewendY);
                            if (Intersection2 != null) {
                                double distanceToIntersection = Math.sqrt(Math.pow(Intersection2.getX() - endX, 2) + Math.pow(Intersection2.getY() - endY, 2));
                                if (NextIntersection == null || distanceToIntersection < Math.sqrt(Math.pow(NextIntersection.getX() - endX, 2) + Math.pow(NextIntersection.getY() - endY, 2))) {
                                    NextIntersection = Intersection2; // Update the closest intersection point
                                    intersectionRegion2 = determineRegion(NextIntersection, atom2.orbit);
                                }
                            }
                        }
                        //If there is another intersection then reflect the 2nd ray
                        if (NextIntersection != null){
                            NewendX = NextIntersection.getX();
                            NewendY = NextIntersection.getY();
                            System.out.println("Intersection point2: " + NextIntersection.getX() + ", " + NextIntersection.getY());
                            reflectionAngle2 = calculateReflectionAngle(intersectionRegion2, Direct2);
                            NewendPoint2 = findEndPoint(NewendX, NewendY, reflectionAngle2, rayLength);
                            NewendX2 = NewendPoint2[0];
                            NewendY2 = NewendPoint2[1];
                        }
                        // Add the first reflected ray to the scene, only if the ray is deflected at all
                        if (reflectionAngle != -1) {
                            Line reflectedRay = new Line(endX, endY, NewendX, NewendY); // Reflected ray from midpoint to intersection point
                            reflectedRay.setStroke(Color.GREEN); // Adjust color if needed
                            reflectedRay.setStrokeWidth(7); // Adjust width if needed
                            rays.add(reflectedRay);
                            onedeflection = 1;
                        }

                        if (onedeflection == 1 && NextIntersection != null) {
                            // Add the second reflected ray to the scene
                            Line reflectedRay2 = new Line(NewendX, NewendY, NewendX2, NewendY2);
                            reflectedRay2.setStroke(Color.RED);
                            reflectedRay2.setStrokeWidth(7);
                            rays.add(reflectedRay2);
                        }
                    }
                    if (reflected==0){
                        //The case where there were no reflections and so just draw the first ray
                        Line newRay = new Line(midX, midY, endX, endY);
                        System.out.println(endX + "and" + endY + "\n");
                        newRay.setStroke(Color.CYAN);
                        newRay.setStrokeWidth(7);
                        rays.add(newRay);
                    }
                    root.getChildren().addAll(rays);
                }
            }
        });

        polygon.getPoints().addAll(new Double[]{
                x1, y1,
                x2, y2,
                x3, y3,});
        polygon.setFill(Color.YELLOW);
        return polygon;

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

            // Check with slight offsets in both x and y directions
            for (double offset : new double[]{-slightOffset, slightOffset}) {
                if (!nextInHexagon) {
                    double nextEndX = endX + rayLength * Math.cos(directionAngle);
                    double nextEndY = endY + rayLength * Math.sin(directionAngle);

                    for (List<Hexagon> innerList : allHexagons) {
                        for (Hexagon hexagon : innerList) {
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
    private static Point2D getCircleLineIntersection(Circle circle, double lineStartX, double lineStartY, double lineEndX, double lineEndY) {
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
            if ((t1 >= 0 && t1 <= 1) || (t2 >= 0 && t2 <= 1)) {
                // Calculate the intersection points
                double intersectionX1 = lineStartX + t1 * dx;
                double intersectionY1 = lineStartY + t1 * dy;
                double intersectionX2 = lineStartX + t2 * dx;
                double intersectionY2 = lineStartY + t2 * dy;

                // Return the intersection point closest to the starting point of the ray
                if (t1 >= 0 && t1 <= 1 && t2 >= 0 && t2 <= 1) {
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
    private static double calculateReflectionAngle(Region intersectionRegion, Main.directions z) {
        double directionAngle = 0;
        switch (z) {
            case west:
                switch (intersectionRegion){
                    case MIDDLE_RIGHT -> directionAngle = -1;
                    case TOP_RIGHT -> directionAngle = Northwest;
                    case BOTTOM_RIGHT -> directionAngle = Southwest;
                }
                break;
            case southEast:
                switch (intersectionRegion){
                    case TOP_LEFT -> directionAngle = -1;
                    case TOP_RIGHT -> directionAngle = East;
                    case MIDDLE_LEFT -> directionAngle = Southwest;
                }
                break;
            case northEast:
                switch (intersectionRegion){
                    case BOTTOM_RIGHT -> directionAngle = East;
                    case BOTTOM_LEFT -> directionAngle = -1;
                    case MIDDLE_LEFT -> directionAngle = Northwest;
                }
                break;
            case northWest:
                switch (intersectionRegion){
                    case MIDDLE_RIGHT -> directionAngle = Northeast;
                    case BOTTOM_RIGHT -> directionAngle = -1;
                    case BOTTOM_LEFT -> directionAngle = West;
                }
                break;
            case east:
                switch (intersectionRegion){
                    case MIDDLE_LEFT -> directionAngle = -1;
                    case TOP_LEFT -> directionAngle = Northeast;
                    case BOTTOM_LEFT -> directionAngle = Southeast;
                }
                break;
            case southWest:
                switch (intersectionRegion){
                    case TOP_RIGHT -> directionAngle = -1;
                    case TOP_LEFT -> directionAngle = West;
                    case MIDDLE_RIGHT -> directionAngle = Southeast;
                }
                break;
            default:
                directionAngle = 0; // Default to east if direction is unknown
        }
        return directionAngle;
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
}
