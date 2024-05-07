package com.program;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

import static com.program.Arrow.*;
import static com.program.BoardItems.AllArrows;
import static com.program.BoardItems.AllHexagons;

/**
 * @author Elvis Okoh
 * @version 2.0 May 2024
 * @since Feburary 2024
 * Ray class containing methods for constructing rays and calculating reflection angles
 */
public class Rays {

    public static int ExitId;

    public Rays(double MidX, double MidY, double DirectionAngle, ArrayList<Line> Ray) {

        MakeRays(MidX, MidY, DirectionAngle, Ray);

    }

    /**
     * Calculates and returns the end x and y points of a ray
     *
     * @param MidX           The x coordinate of the center point
     * @param MidY           The y coordinate of the center point
     * @param DirectionAngle The angle of the ray
     * @param RayLength      The length of the ray
     * @return End point of the ray
     */
    private static double[] FindEndPoint(double MidX, double MidY, double DirectionAngle, double RayLength) {
        double EndX = MidX + RayLength * Math.cos(DirectionAngle);
        double EndY = MidY + RayLength * Math.sin(DirectionAngle);
        //Progressively keep incrementing the endpoint

        boolean inHexagon = false; //Flag to track if the endpoint is in a hexagon
        //Look through all the hexagons to check if the current endpoint is in a hexagon or not
        for (List<Hexagon> innerList : AllHexagons) {
            for (Hexagon hexagon : innerList) {
                if (hexagon.Shape.contains(EndX, EndY)) {
                    inHexagon = true; //Set the flag to true if the endpoint is in a hexagon
                    break; //Exit the loop since we found the hexagon
                }
            }
            if (inHexagon) {
                break;
            }
        }
        if (inHexagon) {
            //If current endpoint is in a hexagon we then check
            //if incrementing or decrementing the endpoint would keep it inside a hexagon
            //If a slight offset to the endpoint would put the endpoint outside any hexagon
            //then we know that we can stop incrementing
            double slightOffset = 25 / 1.3; //Offset is half the hexagon size
            boolean nextInHexagon = false;
            int hexid = 0;
            int rowid = -1;

            //Check with slight offsets in both x and y directions
            for (double offset : new double[]{-slightOffset, slightOffset}) {
                if (!nextInHexagon) {
                    double nextEndX = EndX + RayLength * Math.cos(DirectionAngle);
                    double nextEndY = EndY + RayLength * Math.sin(DirectionAngle);

                    for (List<Hexagon> innerList : AllHexagons) {
                        rowid++;
                        for (Hexagon hexagon : innerList) {
                            hexid = hexagon.Id;
                            if (hexagon.Shape.contains(nextEndX + offset, nextEndY + offset)) {
                                nextInHexagon = true; //Set the flag to true if the next endpoint with offset is in a hexagon
                                break; //Exit the loop since we found the hexagon
                            }
                        }
                        if (nextInHexagon) {
                            break;
                        }
                    }
                }
            }

            if (nextInHexagon) {
                //If the next endpoint with slight offset is also in a hexagon
                //then we can recursively call the method again
                return FindEndPoint(EndX, EndY, DirectionAngle, RayLength);
            } else {
                //If incrementing or decrementing the endpoint would move it out of a hexagon
                //then return the current endpoint
                return new double[]{EndX, EndY};
            }
        } else {
            //If the endpoint is not in a hexagon at all, return the current endpoint
            return new double[]{EndX, EndY};
        }
    }

    /**
     * Gets the point where an orbit and line intersect
     *
     * @param circle         The orbit whose line intersection is to be found
     * @param lineStartX     X coordinate of the start of the line
     * @param lineStartY     Y coordinate of the start of the line
     * @param lineEndX       X coordinate of the end of the line
     * @param lineEndY       Y coordinate of the end of the line
     * @param directionAngle The angle of the line
     * @return The point that the line intersects the orbit
     */

    private static Point2D GetCircleLineIntersection(Circle circle, double lineStartX, double lineStartY, double lineEndX, double lineEndY, double directionAngle) {
        //Method makes a tangent or line segment between start and end point of the ray
        lineStartX += 5 * Math.cos(directionAngle);
        lineStartY += 5 * Math.sin(directionAngle);
        double circleCenterX = circle.getCenterX();
        double circleCenterY = circle.getCenterY();
        double radius = circle.getRadius();

        double dx = lineEndX - lineStartX;
        double dy = lineEndY - lineStartY;

        //Calculations
        double A = dx * dx + dy * dy;
        double B = 2 * (dx * (lineStartX - circleCenterX) + dy * (lineStartY - circleCenterY));
        double C = (lineStartX - circleCenterX) * (lineStartX - circleCenterX) + (lineStartY - circleCenterY) * (lineStartY - circleCenterY) - radius * radius;

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

                //Return the intersection point closest to the starting point of the ray
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

    /**
     * calculates the angle at which to deflect the ray
     *
     * @param intersectionRegion The region the ray is fired from
     * @param OriginalDirection  The original direction the ray is headed
     * @return The new angle to reflect the ray
     */
    private static double CalculateReflection1Atom(Arrow.Region intersectionRegion, double OriginalDirection) {
        //In the case that the ray hits a point on only 1 atom
        //Simple switch which determines reflection angle based on the region of the atom hit
        //and the original direction
        double directionAngle = 0;
        switch ((int) OriginalDirection) {
            case (int) WEST:
                switch (intersectionRegion) {
                    case MIDDLE_RIGHT -> directionAngle = -1;
                    case TOP_RIGHT -> directionAngle = NORTH_WEST;
                    case BOTTOM_RIGHT -> directionAngle = SOUTH_WEST;
                }
                break;
            case (int) SOUTH_EAST:
                switch (intersectionRegion) {
                    case TOP_LEFT -> directionAngle = -1;
                    case TOP_RIGHT -> directionAngle = EAST;
                    case MIDDLE_LEFT -> directionAngle = SOUTH_WEST;
                }
                break;
            case (int) NORTH_EAST:
                switch (intersectionRegion) {
                    case BOTTOM_RIGHT -> directionAngle = EAST;
                    case BOTTOM_LEFT -> directionAngle = -1;
                    case MIDDLE_LEFT -> directionAngle = NORTH_WEST;
                }
                break;
            case (int) NORTH_WEST:
                switch (intersectionRegion) {
                    case MIDDLE_RIGHT -> directionAngle = NORTH_EAST;
                    case BOTTOM_RIGHT -> directionAngle = -1;
                    case BOTTOM_LEFT -> directionAngle = WEST;
                }
                break;
            case 0:
                switch (intersectionRegion) {
                    case MIDDLE_LEFT -> directionAngle = -1;
                    case TOP_LEFT -> directionAngle = NORTH_EAST;
                    case BOTTOM_LEFT -> directionAngle = SOUTH_EAST;
                }
                break;
            case (int) SOUTH_WEST:
                switch (intersectionRegion) {
                    case TOP_RIGHT -> directionAngle = -1;
                    case TOP_LEFT -> directionAngle = WEST;
                    case MIDDLE_RIGHT -> directionAngle = SOUTH_EAST;
                }
                break;
            default:
                directionAngle = -1;
        }
        return directionAngle;
    }

    /**
     * Calculates the reflection when meeting 2 atoms
     *
     * @param initialX       The initial x cordinate of the ray
     * @param initialY       The initial y coordinate of the ray
     * @param endX           The endpoints X of the ray
     * @param endY           The endpoints Y of the ray
     * @param directionAngle The angle the ray is being shot at
     * @return The new reflection angle of the ray
     */
    private static double CalculateReflection2Atoms(double initialX, double initialY, double endX, double endY, double directionAngle) {
        //In the case that the ray hits a point on 2 atoms
        //We first calculate the region hit on each atom
        Arrow.Region intersectionRegion1 = null;
        Arrow.Region intersectionRegion2 = null;
        double reflectionAngle = 0;
        for (Atoms atom : BoardItems.AllAtoms) {
            //Look through all the atoms and find the intersection
            Point2D intersection = GetCircleLineIntersection(atom.Orbit, initialX, initialY, endX + 5 * Math.cos(directionAngle), endY + 5 * Math.sin(directionAngle), directionAngle);
            if (intersection != null) {
                endX = intersection.getX();
                endY = intersection.getY();
                if (intersectionRegion1 == null) {
                    intersectionRegion1 = DetermineRegion(intersection, atom.Orbit);
                } else if (intersectionRegion2 == null) {
                    intersectionRegion2 = DetermineRegion(intersection, atom.Orbit);

                }
            }
        }
        //If statements then determine the reflection angle based on the original direction and
        //the region of each of the 2 atoms that was hit
        //The statements are grouped by original direction

        //Northwest
        if (((intersectionRegion1 == Arrow.Region.BOTTOM_LEFT && intersectionRegion2 == Arrow.Region.MIDDLE_RIGHT) || (intersectionRegion1 == Arrow.Region.MIDDLE_RIGHT && intersectionRegion2 == Arrow.Region.BOTTOM_LEFT)) && directionAngle == NORTH_WEST) {
            reflectionAngle = -1;
        } else if (((intersectionRegion1 == Arrow.Region.BOTTOM_LEFT && intersectionRegion2 == Arrow.Region.BOTTOM_RIGHT) || (intersectionRegion1 == Arrow.Region.BOTTOM_RIGHT && intersectionRegion2 == Arrow.Region.BOTTOM_LEFT)) && directionAngle == NORTH_WEST) {
            reflectionAngle = SOUTH_WEST;
        } else if (((intersectionRegion1 == Arrow.Region.BOTTOM_RIGHT && intersectionRegion2 == Arrow.Region.MIDDLE_RIGHT) || (intersectionRegion1 == Arrow.Region.MIDDLE_RIGHT && intersectionRegion2 == Arrow.Region.BOTTOM_RIGHT)) && directionAngle == NORTH_WEST) {
            reflectionAngle = EAST;
        }

        //Northeast
        else if (((intersectionRegion1 == Arrow.Region.MIDDLE_LEFT && intersectionRegion2 == Arrow.Region.BOTTOM_RIGHT) || (intersectionRegion1 == Arrow.Region.BOTTOM_RIGHT && intersectionRegion2 == Arrow.Region.MIDDLE_LEFT)) && directionAngle == NORTH_EAST) {
            reflectionAngle = -1;
        } else if (((intersectionRegion1 == Arrow.Region.MIDDLE_LEFT && intersectionRegion2 == Arrow.Region.BOTTOM_LEFT) || (intersectionRegion1 == Arrow.Region.BOTTOM_LEFT && intersectionRegion2 == Arrow.Region.MIDDLE_LEFT)) && directionAngle == NORTH_EAST) {
            reflectionAngle = WEST;
        } else if (((intersectionRegion1 == Arrow.Region.BOTTOM_LEFT && intersectionRegion2 == Arrow.Region.BOTTOM_RIGHT) || (intersectionRegion1 == Arrow.Region.BOTTOM_RIGHT && intersectionRegion2 == Arrow.Region.BOTTOM_LEFT)) && directionAngle == NORTH_EAST) {
            reflectionAngle = SOUTH_EAST;
        }

        //Southwest
        else if (((intersectionRegion1 == Arrow.Region.TOP_LEFT && intersectionRegion2 == Arrow.Region.MIDDLE_RIGHT) || (intersectionRegion1 == Arrow.Region.MIDDLE_RIGHT && intersectionRegion2 == Arrow.Region.TOP_LEFT)) && directionAngle == SOUTH_WEST) {
            reflectionAngle = -1;
        } else if (((intersectionRegion1 == Arrow.Region.TOP_LEFT && intersectionRegion2 == Arrow.Region.TOP_RIGHT) || (intersectionRegion1 == Arrow.Region.TOP_RIGHT && intersectionRegion2 == Arrow.Region.TOP_LEFT)) && directionAngle == SOUTH_WEST) {
            reflectionAngle = NORTH_WEST;
        } else if (((intersectionRegion1 == Arrow.Region.TOP_RIGHT && intersectionRegion2 == Arrow.Region.MIDDLE_RIGHT) || (intersectionRegion1 == Arrow.Region.MIDDLE_RIGHT && intersectionRegion2 == Arrow.Region.TOP_RIGHT)) && directionAngle == SOUTH_WEST) {
            reflectionAngle = EAST;
        }

        //Southeast
        else if (((intersectionRegion1 == Arrow.Region.MIDDLE_LEFT && intersectionRegion2 == Arrow.Region.TOP_RIGHT) || (intersectionRegion1 == Arrow.Region.TOP_RIGHT && intersectionRegion2 == Arrow.Region.MIDDLE_LEFT)) && directionAngle == SOUTH_EAST) {
            reflectionAngle = -1;
        } else if (((intersectionRegion1 == Arrow.Region.TOP_LEFT && intersectionRegion2 == Arrow.Region.TOP_RIGHT) || (intersectionRegion1 == Arrow.Region.TOP_RIGHT && intersectionRegion2 == Arrow.Region.TOP_LEFT)) && directionAngle == SOUTH_EAST) {
            reflectionAngle = NORTH_EAST;
        } else if (((intersectionRegion1 == Arrow.Region.MIDDLE_LEFT && intersectionRegion2 == Arrow.Region.TOP_LEFT) || (intersectionRegion1 == Arrow.Region.TOP_LEFT && intersectionRegion2 == Arrow.Region.MIDDLE_LEFT)) && directionAngle == SOUTH_EAST) {
            reflectionAngle = WEST;
        }

        //West
        else if (((intersectionRegion1 == Arrow.Region.TOP_RIGHT && intersectionRegion2 == Arrow.Region.BOTTOM_RIGHT) || (intersectionRegion1 == Arrow.Region.BOTTOM_RIGHT && intersectionRegion2 == Arrow.Region.TOP_RIGHT)) && directionAngle == WEST) {
            reflectionAngle = -1;
        } else if (((intersectionRegion1 == Arrow.Region.TOP_RIGHT && intersectionRegion2 == Arrow.Region.MIDDLE_RIGHT) || (intersectionRegion1 == Arrow.Region.MIDDLE_RIGHT && intersectionRegion2 == Arrow.Region.TOP_RIGHT)) && directionAngle == WEST) {
            reflectionAngle = NORTH_EAST;
        } else if (((intersectionRegion1 == Arrow.Region.BOTTOM_RIGHT && intersectionRegion2 == Arrow.Region.MIDDLE_RIGHT) || (intersectionRegion1 == Arrow.Region.MIDDLE_RIGHT && intersectionRegion2 == Arrow.Region.BOTTOM_RIGHT)) && directionAngle == WEST) {
            reflectionAngle = SOUTH_EAST;
        }

        //East
        else if (((intersectionRegion1 == Arrow.Region.TOP_LEFT && intersectionRegion2 == Arrow.Region.BOTTOM_LEFT) || (intersectionRegion1 == Arrow.Region.BOTTOM_LEFT && intersectionRegion2 == Arrow.Region.TOP_LEFT)) && directionAngle == EAST) {
            reflectionAngle = -1;
        } else if (((intersectionRegion1 == Arrow.Region.MIDDLE_LEFT && intersectionRegion2 == Arrow.Region.BOTTOM_LEFT) || (intersectionRegion1 == Arrow.Region.BOTTOM_LEFT && intersectionRegion2 == Arrow.Region.MIDDLE_LEFT)) && directionAngle == EAST) {
            reflectionAngle = SOUTH_WEST;
        } else if (((intersectionRegion1 == Arrow.Region.TOP_LEFT && intersectionRegion2 == Arrow.Region.MIDDLE_LEFT) || (intersectionRegion1 == Arrow.Region.MIDDLE_LEFT && intersectionRegion2 == Arrow.Region.TOP_LEFT)) && directionAngle == EAST) {
            reflectionAngle = NORTH_WEST;
        }

        return reflectionAngle;
    }

    /**
     * helper method which calculates which side of the influence circle was hit by the ray
     *
     * @param intersectionPoint the points where the ray intersects the orbit
     * @param circle            the orbit that is being intersected
     * @return the region of orbit intersected
     */
    private static Arrow.Region DetermineRegion(Point2D intersectionPoint, Circle circle) {
        //Split the atom into 6 regions so that we can determine the reflection angle
        double centerX = circle.getCenterX();
        double centerY = circle.getCenterY();
        double x = intersectionPoint.getX();
        double y = intersectionPoint.getY();
        //Splits the atom by using the center point of the atom
        //and then based on that it uses the intersection point
        if (x >= centerX && y < centerY - circle.getRadius() / 2) {
            return Arrow.Region.TOP_RIGHT;
        } else if (x < centerX && y < centerY - circle.getRadius() / 2) {
            return Arrow.Region.TOP_LEFT;
        } else if (x < centerX && y >= centerY - circle.getRadius() / 2 && y < centerY + circle.getRadius() / 2) {
            return Arrow.Region.MIDDLE_LEFT;
        } else if (x >= centerX && y >= centerY - circle.getRadius() / 2 && y < centerY + circle.getRadius() / 2) {
            return Arrow.Region.MIDDLE_RIGHT;
        } else if (x >= centerX && y >= centerY + circle.getRadius() / 2) {
            return Arrow.Region.BOTTOM_RIGHT;
        } else {
            return Arrow.Region.BOTTOM_LEFT;
        }
    }

    /**
     * Conuts the number of orbits the ray intersects
     *
     * @param lineStartX     The x cordinate of the start of the ray
     * @param lineStartY     The y coordinate of the start of the ray
     * @param lineEndX       The x coordinate of the end of the ray
     * @param lineEndY       The y coordinate of the end of the ray
     * @param directionAngle The direction the ray is fired from
     * @return The number of orbits the ray intersects
     */
    private static int CountCircleLineIntersections(double lineStartX, double lineStartY, double lineEndX, double lineEndY, double directionAngle) {
        //Counts the number of atoms that are on the point of intersection so that we can determine
        //which method to use when calculating the angle of reflection
        int intersectingAtomsCount = 0;
        for (Atoms atom : BoardItems.AllAtoms) {
            //Get the point of intersection
            Point2D intersection = GetCircleLineIntersection(atom.Orbit, lineStartX, lineStartY, lineEndX, lineEndY, directionAngle);
            if (intersection != null) {
                intersectingAtomsCount++; //Increment the count for each intersecting atom

            }
        }
        if (intersectingAtomsCount == 0) return 1;
        return intersectingAtomsCount;
    }

    /**
     * Makes the rays
     *
     * @param initialX       The initial x coordinate of the ray
     * @param initialY       The initial y coordinate of the ray
     * @param directionAngle The angle of the direction of the ray
     * @param rays           The total list of lines to be combined for the ray
     */
    private static void MakeRays(double initialX, double initialY, double directionAngle, List<Line> rays) {
        double rayLength = 5;
        double[] endPoint = FindEndPoint(initialX, initialY, directionAngle, rayLength);
        //First we find the endpoint with the assumption that the ray hits no atoms
        double endX = endPoint[0];
        double endY = endPoint[1];
        double reflectionAngle = -1;
        double currentX = initialX; //Store current x value
        double currentY = initialY; //Store current y value
        currentX += 25 * Math.cos(directionAngle);  //Start with 1 increment
        currentY += 25 * Math.sin(directionAngle);  //Start with 1 increment
        boolean found = false; //Flag to check if a deflection has occurred
        for (int i = 0; i < 50; i++) {
            //This loop will keep incrementing the current coordinates
            for (Atoms atom : BoardItems.AllAtoms) {
                //Check for intersections between the start and current coordinates
                Point2D Intersection = GetCircleLineIntersection(atom.Orbit, initialX, initialY, currentX, currentY, directionAngle);
                if (Intersection != null) {
                    for (List<Hexagon> innerList : AllHexagons) {
                        for (Hexagon hexagon : innerList) {
                            if (hexagon.Shape.contains(Intersection.getX(), Intersection.getY())) {
                                //If there is an intersection then we set the current coordinates to the
                                //center of the hexagon
                                currentX = hexagon.x;
                                currentY = hexagon.y;
                            }
                        }
                    }
                    Arrow.Region intersectedRegion = DetermineRegion(Intersection, atom.Orbit);
                    //Check how many atoms are on the point of intersection
                    int AtomsHit = CountCircleLineIntersections(initialX, initialY, currentX + 25 * Math.cos(directionAngle), currentY + 25 * Math.sin(directionAngle), directionAngle);
                    if (AtomsHit == 1) reflectionAngle = CalculateReflection1Atom(intersectedRegion, directionAngle);
                    if (AtomsHit == 2) {
                        reflectionAngle = CalculateReflection2Atoms(initialX, initialY, currentX + 25 * Math.cos(directionAngle), currentY + 25 * Math.sin(directionAngle), directionAngle);
                    }
                    if (AtomsHit == 3) {
                        //In the case of 3 atoms, the result is always a full deflection
                        reflectionAngle = -1;
                    }

                    found = true; //Deflected so set flag to true
                    if (reflectionAngle == -1) {
                        break;
                    }
                    break;
                }
            }
            if (found) {
                //Stop incrementing current points since intersection was already found
                break;
            }
            //If no intersection is found then we can keep incrementing
            currentX += 25 * Math.cos(directionAngle);
            currentY += 25 * Math.sin(directionAngle);
        }
        if (!found) {
            //If an intersection was never found then we can conclude
            //that the calculated endpoint was the true endpoint
            currentX = endX;
            currentY = endY;
        }
        Line Ray = new Line(initialX, initialY, currentX, currentY); //Draw the ray to the current coordinates
        Ray.setStroke(Color.CYAN);
        Ray.setStrokeWidth(6);
        ExitId = FindArrow(Ray.getEndX(), Ray.getEndY());
        rays.add(Ray);
        if (reflectionAngle != -1) {
            //Recursively call the method again to draw the reflected ray unless it was fully deflected,
            //absorbed or never deflected
            MakeRays(currentX, currentY, reflectionAngle, rays);
        }
    }

    /**
     * Finds the arrow id of an arrow when give coordinates
     *
     * @param x The x coordinate of the arrow
     * @param y The y coordinate of the arrow
     * @return The arrow id of the arrow that contains the points
     */
    public static int FindArrow(double x, double y) {
        for (Arrow a : AllArrows) {
            //uses a slight offset to get the correct arrow
            for (int i = -5; i <= 5; i++) {
                for (int j = -5; j <= 5; j++) {
                    if (a.Shape.contains(x + i, y + j)) return a.ArrowId;
                }
            }
        }
        return 0;
    }

}
