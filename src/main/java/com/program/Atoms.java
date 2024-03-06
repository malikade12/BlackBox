
package com.program;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;

public class Atoms extends Sphere {
    Circle orbit;
    public Atoms(Group root, double x, double y) {
        super(35); // Set the radius of the sphere
        setTranslateX(x); // Set the x position
        setTranslateY(y); // Set the y position
        setTranslateZ(0); // Set the z position (for 3D, 0 would represent the screen)
        // Create a circle to represent the orbit
        orbit = new Circle(x, y, 85); // Set the radius of the orbit
        orbit.setStroke(Color.WHITE); // Set the color of the orbit
        orbit.setFill(null); // Set the fill of the orbit to transparent
        orbit.setStrokeWidth(2); // Set the width of the orbit

        // Set the stroke type to DOTTED
        orbit.setStrokeType(StrokeType.OUTSIDE);
        orbit.getStrokeDashArray().addAll(5d, 5d); // Set the dash pattern for the dotted line
        orbit.setStrokeWidth(1);

        setMaterial(new javafx.scene.paint.PhongMaterial(Color.RED)); // Set the material and color
        root.getChildren().addAll(this, orbit); // Add the sphere to the scene
    }

    public static void makeAllAtomsInvisible() {
        for (Atoms atom : Main.allAtoms) {
            atom.setVisible(false);
            atom.orbit.setVisible(false);
        }
    }

    public static void makeAllAtomsVisible() {
        for (Atoms atom : Main.allAtoms) {
            atom.setVisible(true);
            atom.orbit.setVisible(true);
        }
    }
}