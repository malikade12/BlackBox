
package com.program;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;

public class Atoms extends Sphere {
    public Atoms(Group root, double x, double y) {
        super(20); // Set the radius of the sphere
        setTranslateX(x); // Set the x position
        setTranslateY(y); // Set the y position
        setTranslateZ(0); // Set the z position (for 3D, 0 would represent the screen)
        setMaterial(new javafx.scene.paint.PhongMaterial(Color.RED)); // Set the material and color
        root.getChildren().add(this); // Add the sphere to the scene
    }

    public static void makeAllAtomsInvisible() {
        for (Atoms atom : Main.allAtoms) {
            atom.setVisible(false);
        }
    }

    public static void makeAllAtomsVisible() {
        for (Atoms atom : Main.allAtoms) {
            atom.setVisible(true);
        }
    }
}