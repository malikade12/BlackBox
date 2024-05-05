package com.program;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

import static com.program.Arrow.*;
import static com.program.BoardItems.allArrows;
import static com.program.BoardItems.allHexagons;

/**
 * @author Elvis Okoh
 * @version 2.0 May 2024
 * @since Feburary 2024
 * Ray class containing methods for constructing rays and calculating reflection angles
 */
public class Rays {

    public static ArrayList<Line> rays;
    public static int exitId;

    public Rays(double midX, double midY, double directionAngle, ArrayList<Line> ray) {

        makeRays(midX, midY, directionAngle, ray);

    }

    /**
     * Calculates and returns the end x and y points of a ray
     *
     * @param midX           The x coordinate of the center point
     * @param midY           The y coordinate of the center point
     * @param directionAngle The angle of the ray
     * @param rayLength      The length of the ray
     * @return End point of the ray
     */
    private static double[] findEndPoint(double midX, double midY, double directionAngle, double rayLength) {
        double endX = midX + rayLength * Math.cos(directionAngle);
        double endY = midY + rayLength * Math.sin(directionAngle);
        //The method will keep calling itself and incrementing the endpoints
        //Until eventually the endpoint is not in a hexagon

        boolean inHexagon = false; //Flag to track if the endpoint is in a hexagon

        for (List<Hexagon> innerList : allHexagons) {
            for (Hexagon hexagon : innerList) {
                if (hexagon.shape.contains(endX, endY)) {
                    inHexagon = true; //Set the flag to true if the endpoint is in a hexagon
                    break; //Exit the loop since we found the hexagon
                }
            }
            if (inHexagon) {
                break; //Exit the outer loop if the endpoint is in a hexagon
            }
        }
        if (inHexagon) {
            //Check if incrementing or decrementing the endpoint would keep it inside a hexagon
            //By doing this we will know if we can increment the endpoint again or not
            //if we cannot then we just return the current endpoint
            //Thus the ray will not go out of the hexagon board
            double slightOffset = 25 / 1.3; //Offset for checking, this is half the of a hexagon
            boolean nextInHexagon = false;
            int hexid = 0;
            int rowid = -1;

            //Check with slight offsets in both x and y directions
            for (double offset : new double[]{-slightOffset, slightOffset}) {
                if (!nextInHexagon) {
                    double nextEndX = endX + rayLength * Math.cos(directionAngle);
                    double nextEndY = endY + rayLength * Math.sin(directionAngle);

                    for (List<Hexagon> innerList : allHexagons) {
                        rowid++;
                        for (Hexagon hexagon : innerList) {
                            hexid = hexagon.Id;
                            if (hexagon.shape.contains(nextEndX + offset, nextEndY + offset)) {
                                nextInHexagon = true; //Set the flag to true if the next endpoint with offset is in a hexagon
                                break; //Exit the loop since we found the hexagon
                            }
                        }
                        if (nextInHexagon) {
                            break; //Exit the outer loop if the next endpoint with offset is in a hexagon
                        }
                    }
                }
            }

            if (nextInHexagon) {
                //If the next endpoint with slight offset is also in a hexagon, recursively call the method again
                return findEndPoint(endX, endY, directionAngle, rayLength);
            } else {
                //If incrementing or decrementing the endpoint would move it out of a hexagon, return the current endpoint
                return new double[]{endX, endY};
            }
        } else {
            //If the endpoint is not in a hexagon from the beginning, return the current endpoint
            return new double[]{endX, endY};
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

    private static Point2D getCircleLineIntersection(Circle circle, double lineStartX, double lineStartY, double lineEndX, double lineEndY, double directionAngle) {
        //The way this method works is it makes a tangent or line from the start and end points
        //If this tangent would intersect an atom, it will return the intersection point
        lineStartX += 5 * Math.cos(directionAngle);
        lineStartY += 5 * Math.sin(directionAngle);
        double circleCenterX = circle.getCenterX();
        double circleCenterY = circle.getCenterY();
        double radius = circle.getRadius();

        double dx = lineEndX - lineStartX;
        double dy = lineEndY - lineStartY;

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
        //No intersection found, thus return null
        return null;
    }

    /**
     * calculates the angle at which to deflect the ray
     *
     * @param intersectionRegion The region the ray is fired from
     * @param OriginalDirection  The original direction the ray is headed
     * @return The new angle to reflect the ray
     */
    private static double calculateReflection1Atom(Arrow.Region intersectionRegion, double OriginalDirection) {
        //Switch statements which return the new direction/angle based on the side of the atom that is hit and
        //which direction the ray was originally coming from
        double directionAngle = 0;
        switch ((int) OriginalDirection) {
            case (int) West:
                switch (intersectionRegion) {
                    case MIDDLE_RIGHT -> directionAngle = -1;
                    case TOP_RIGHT -> directionAngle = Northwest;
                    case BOTTOM_RIGHT -> directionAngle = Southwest;
                }
                break;
            case (int) Southeast:
                switch (intersectionRegion) {
                    case TOP_LEFT -> directionAngle = -1;
                    case TOP_RIGHT -> directionAngle = East;
                    case MIDDLE_LEFT -> directionAngle = Southwest;
                }
                break;
            case (int) Northeast:
                switch (intersectionRegion) {
                    case BOTTOM_RIGHT -> directionAngle = East;
                    case BOTTOM_LEFT -> directionAngle = -1;
                    case MIDDLE_LEFT -> directionAngle = Northwest;
                }
                break;
            case (int) Northwest:
                switch (intersectionRegion) {
                    case MIDDLE_RIGHT -> directionAngle = Northeast;
                    case BOTTOM_RIGHT -> directionAngle = -1;
                    case BOTTOM_LEFT -> directionAngle = West;
                }
                break;
            case 0:
                switch (intersectionRegion) {
                    case MIDDLE_LEFT -> directionAngle = -1;
                    case TOP_LEFT -> directionAngle = Northeast;
                    case BOTTOM_LEFT -> directionAngle = Southeast;
                }
                break;
            case (int) Southwest:
                switch (intersectionRegion) {
                    case TOP_RIGHT -> directionAngle = -1;
                    case TOP_LEFT -> directionAngle = West;
                    case MIDDLE_RIGHT -> directionAngle = Southeast;
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
    private static double calculateReflection2Atoms(double initialX, double initialY, double endX, double endY, double directionAngle) {
        //This method calculates the new direction/angle in the case that the point that the intersection is
        //is a point on more than one atoms, meaning the ray hit multiple atoms
        Arrow.Region intersectionRegion1 = null;
        Arrow.Region intersectionRegion2 = null;
        double reflectionAngle = 0;
        for (Atoms atom : BoardItems.allAtoms) {
            //Looks through all the atoms and get the 2 intersections on the 2 separate atoms
            //Endpoint is given with a slight offset to make sure the getCircleLineIntersection method
            //can find the intersection
            Point2D intersection = getCircleLineIntersection(atom.orbit, initialX, initialY, endX + 5 * Math.cos(directionAngle), endY + 5 * Math.sin(directionAngle), directionAngle);
            if (intersection != null) {
                endX = intersection.getX();
                endY = intersection.getY();
                if (intersectionRegion1 == null) {
                    intersectionRegion1 = determineRegion(intersection, atom.orbit);
                } else if (intersectionRegion2 == null) {
                    intersectionRegion2 = determineRegion(intersection, atom.orbit);

                }
            }
        }
        //If statements for all the combination of ways that a ray can hit a point which is on 2 atoms
        //The if statements are grouped by the original direction of the ray

        //Northwest
        if (((intersectionRegion1 == Arrow.Region.BOTTOM_LEFT && intersectionRegion2 == Arrow.Region.MIDDLE_RIGHT) || (intersectionRegion1 == Arrow.Region.MIDDLE_RIGHT && intersectionRegion2 == Arrow.Region.BOTTOM_LEFT)) && directionAngle == Northwest) {
            reflectionAngle = -1;
        } else if (((intersectionRegion1 == Arrow.Region.BOTTOM_LEFT && intersectionRegion2 == Arrow.Region.BOTTOM_RIGHT) || (intersectionRegion1 == Arrow.Region.BOTTOM_RIGHT && intersectionRegion2 == Arrow.Region.BOTTOM_LEFT)) && directionAngle == Northwest) {
            reflectionAngle = Southwest;
        } else if (((intersectionRegion1 == Arrow.Region.BOTTOM_RIGHT && intersectionRegion2 == Arrow.Region.MIDDLE_RIGHT) || (intersectionRegion1 == Arrow.Region.MIDDLE_RIGHT && intersectionRegion2 == Arrow.Region.BOTTOM_RIGHT)) && directionAngle == Northwest) {
            reflectionAngle = East;
        }

        //Northeast
        else if (((intersectionRegion1 == Arrow.Region.MIDDLE_LEFT && intersectionRegion2 == Arrow.Region.BOTTOM_RIGHT) || (intersectionRegion1 == Arrow.Region.BOTTOM_RIGHT && intersectionRegion2 == Arrow.Region.MIDDLE_LEFT)) && directionAngle == Northeast) {
            reflectionAngle = -1;
        } else if (((intersectionRegion1 == Arrow.Region.MIDDLE_LEFT && intersectionRegion2 == Arrow.Region.BOTTOM_LEFT) || (intersectionRegion1 == Arrow.Region.BOTTOM_LEFT && intersectionRegion2 == Arrow.Region.MIDDLE_LEFT)) && directionAngle == Northeast) {
            reflectionAngle = West;
        } else if (((intersectionRegion1 == Arrow.Region.BOTTOM_LEFT && intersectionRegion2 == Arrow.Region.BOTTOM_RIGHT) || (intersectionRegion1 == Arrow.Region.BOTTOM_RIGHT && intersectionRegion2 == Arrow.Region.BOTTOM_LEFT)) && directionAngle == Northeast) {
            reflectionAngle = Southeast;
        }

        //Southwest
        else if (((intersectionRegion1 == Arrow.Region.TOP_LEFT && intersectionRegion2 == Arrow.Region.MIDDLE_RIGHT) || (intersectionRegion1 == Arrow.Region.MIDDLE_RIGHT && intersectionRegion2 == Arrow.Region.TOP_LEFT)) && directionAngle == Southwest) {
            reflectionAngle = -1;
        } else if (((intersectionRegion1 == Arrow.Region.TOP_LEFT && intersectionRegion2 == Arrow.Region.TOP_RIGHT) || (intersectionRegion1 == Arrow.Region.TOP_RIGHT && intersectionRegion2 == Arrow.Region.TOP_LEFT)) && directionAngle == Southwest) {
            reflectionAngle = Northwest;
        } else if (((intersectionRegion1 == Arrow.Region.TOP_RIGHT && intersectionRegion2 == Arrow.Region.MIDDLE_RIGHT) || (intersectionRegion1 == Arrow.Region.MIDDLE_RIGHT && intersectionRegion2 == Arrow.Region.TOP_RIGHT)) && directionAngle == Southwest) {
            reflectionAngle = East;
        }

        //Southeast
        else if (((intersectionRegion1 == Arrow.Region.MIDDLE_LEFT && intersectionRegion2 == Arrow.Region.TOP_RIGHT) || (intersectionRegion1 == Arrow.Region.TOP_RIGHT && intersectionRegion2 == Arrow.Region.MIDDLE_LEFT)) && directionAngle == Southeast) {
            reflectionAngle = -1;
        } else if (((intersectionRegion1 == Arrow.Region.TOP_LEFT && intersectionRegion2 == Arrow.Region.TOP_RIGHT) || (intersectionRegion1 == Arrow.Region.TOP_RIGHT && intersectionRegion2 == Arrow.Region.TOP_LEFT)) && directionAngle == Southeast) {
            reflectionAngle = Northeast;
        } else if (((intersectionRegion1 == Arrow.Region.MIDDLE_LEFT && intersectionRegion2 == Arrow.Region.TOP_LEFT) || (intersectionRegion1 == Arrow.Region.TOP_LEFT && intersectionRegion2 == Arrow.Region.MIDDLE_LEFT)) && directionAngle == Southeast) {
            reflectionAngle = West;
        }

        //West
        else if (((intersectionRegion1 == Arrow.Region.TOP_RIGHT && intersectionRegion2 == Arrow.Region.BOTTOM_RIGHT) || (intersectionRegion1 == Arrow.Region.BOTTOM_RIGHT && intersectionRegion2 == Arrow.Region.TOP_RIGHT)) && directionAngle == West) {
            reflectionAngle = -1;
        } else if (((intersectionRegion1 == Arrow.Region.TOP_RIGHT && intersectionRegion2 == Arrow.Region.MIDDLE_RIGHT) || (intersectionRegion1 == Arrow.Region.MIDDLE_RIGHT && intersectionRegion2 == Arrow.Region.TOP_RIGHT)) && directionAngle == West) {
            reflectionAngle = Northeast;
        } else if (((intersectionRegion1 == Arrow.Region.BOTTOM_RIGHT && intersectionRegion2 == Arrow.Region.MIDDLE_RIGHT) || (intersectionRegion1 == Arrow.Region.MIDDLE_RIGHT && intersectionRegion2 == Arrow.Region.BOTTOM_RIGHT)) && directionAngle == West) {
            reflectionAngle = Southeast;
        }

        //East
        else if (((intersectionRegion1 == Arrow.Region.TOP_LEFT && intersectionRegion2 == Arrow.Region.BOTTOM_LEFT) || (intersectionRegion1 == Arrow.Region.BOTTOM_LEFT && intersectionRegion2 == Arrow.Region.TOP_LEFT)) && directionAngle == East) {
            reflectionAngle = -1;
        } else if (((intersectionRegion1 == Arrow.Region.MIDDLE_LEFT && intersectionRegion2 == Arrow.Region.BOTTOM_LEFT) || (intersectionRegion1 == Arrow.Region.BOTTOM_LEFT && intersectionRegion2 == Arrow.Region.MIDDLE_LEFT)) && directionAngle == East) {
            reflectionAngle = Southwest;
        } else if (((intersectionRegion1 == Arrow.Region.TOP_LEFT && intersectionRegion2 == Arrow.Region.MIDDLE_LEFT) || (intersectionRegion1 == Arrow.Region.MIDDLE_LEFT && intersectionRegion2 == Arrow.Region.TOP_LEFT)) && directionAngle == East) {
            reflectionAngle = Northwest;
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
    private static Arrow.Region determineRegion(Point2D intersectionPoint, Circle circle) {
        double centerX = circle.getCenterX();
        double centerY = circle.getCenterY();
        double x = intersectionPoint.getX();
        double y = intersectionPoint.getY();
        //The method separates the atom into 6 sections (excluding the center region)
        //So when a ray hits an atom, we can use the intersection point determine
        //The region of the atom which was hit to then be able to calculate the
        //angle of deflection

        if (x == centerX && y == centerY) {
            return Arrow.Region.CENTER;
        } else if (x >= centerX && y < centerY - circle.getRadius() / 2) {
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
     * Counts the number of orbits the ray intersects
     *
     * @param lineStartX     The x coordinate of the start of the ray
     * @param lineStartY     The y coordinate of the start of the ray
     * @param lineEndX       The x coordinate of the end of the ray
     * @param lineEndY       The y coordinate of the end of the ray
     * @param directionAngle The direction the ray is fired from
     * @return The number of orbits the ray intersects
     */
    private static int countCircleLineIntersections(double lineStartX, double lineStartY, double lineEndX, double lineEndY, double directionAngle) {
        int intersectingAtomsCount = 0;
        for (Atoms atom : BoardItems.allAtoms) {
            // Get the point of intersection
            Point2D intersection = getCircleLineIntersection(atom.orbit, lineStartX, lineStartY, lineEndX, lineEndY, directionAngle);
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
    private static void makeRays(double initialX, double initialY, double directionAngle, List<Line> rays) {
        loops++;
        double rayLength = 5;
        //First we calculate the endpoint in the case that the ray hits no atoms
        double[] endPoint = findEndPoint(initialX, initialY, directionAngle, rayLength);
        double endX = endPoint[0];
        double endY = endPoint[1];
        double reflectionAngle = -1;
        double currentX = initialX;
        double currentY = initialY;
        //We use the currentX and currentY to be able to progressively make our way to the calculated endpoint
        //While also taking into accounts any deflections
        currentX += 25 * Math.cos(directionAngle);
        currentY += 25 * Math.sin(directionAngle);
        boolean found = false;
        for (int i = 0; i < 50; i++) {
            //This loop is to continue incrementing current coordinates until either we hit an atom and thus
            //the ray needs to be deflected or until we reach the calculated endpoint
            for (Atoms atom : BoardItems.allAtoms) {
                Point2D Intersection = getCircleLineIntersection(atom.orbit, initialX, initialY, currentX, currentY, directionAngle);
                if (Intersection != null) {
                    //If an intersection was found then we set the current coordinates to the center of the hexagon on which the atom is
                    for (List<Hexagon> innerList : allHexagons) {
                        for (Hexagon hexagon : innerList) {
                            if (hexagon.shape.contains(Intersection.getX(), Intersection.getY())) {
                                currentX = hexagon.x;
                                currentY = hexagon.y;
                            }
                        }
                    }
                    Arrow.Region intersectedRegion = determineRegion(Intersection, atom.orbit);
                    //Check how many atoms have that intersection point
                    int AtomsHit = countCircleLineIntersections(initialX, initialY, currentX + 25 * Math.cos(directionAngle), currentY + 25 * Math.sin(directionAngle), directionAngle);
                    //Then based on the count, we know how to calculate the angle of deflection
                    if (AtomsHit == 1) reflectionAngle = calculateReflection1Atom(intersectedRegion, directionAngle);
                    if (AtomsHit == 2) {
                        reflectionAngle = calculateReflection2Atoms(initialX, initialY, currentX + 25 * Math.cos(directionAngle), currentY + 25 * Math.sin(directionAngle), directionAngle);
                    }
                    //In the case where the ray hits a point which is on 3 atoms, it always results in a full deflection
                    if (AtomsHit == 3) {
                        reflectionAngle = -1;
                    }

                    found = true;
                    if (reflectionAngle == -1) {
                        break;
                    }
                    break;
                }
            }
            if (found) {
                break;
            }
            currentX += 25 * Math.cos(directionAngle);
            currentY += 25 * Math.sin(directionAngle);
        }
        if (!found) {
            //If an intersection was not found even after finishing incrementing current coordinates
            //we can conclude that the calculate endpoints were the correct endpoints
            currentX = endX;
            currentY = endY;
        }
        Line Ray = new Line(initialX, initialY, currentX, currentY); //Original ray from midpoint to intersection point
        Ray.setStroke(Color.CYAN);
        Ray.setStrokeWidth(6);
        exitId = FindArrow(Ray.getEndX(), Ray.getEndY());
        rays.add(Ray);
        if (reflectionAngle != -1) {
            makeRays(currentX, currentY, reflectionAngle, rays);
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
        for (Arrow a : allArrows) {
            //uses a slight offset to get the correct arrow
            for (int i = -5; i <= 5; i++) {
                for (int j = -5; j <= 5; j++) {
                    if (a.shape.contains(x + i, y + j)) return a.arrowid;
                }
            }
        }
        return 0;
    }

}
