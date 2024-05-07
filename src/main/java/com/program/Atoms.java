
package com.program;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Atoms extends Sphere {
    Circle Orbit;
    public int HexId;
    public Atoms(Group root, double x, double y, int id) {
        super((double) 35/1.3); // Set the radius of the sphere
        HexId = id;
        setTranslateX(x); // Set the x position
        setTranslateY(y); // Set the y position
        setTranslateZ(0); // Set the z position (for 3D, 0 would represent the screen)
        // Create a circle to represent the orbit
         Orbit = new Circle(x, y, (double) 85/1.3); // Set the radius of the orbit
        Orbit.setStroke(Color.WHITE); // Set the color of the orbit
        Orbit.setFill(null); // Set the fill of the orbit to transparent
        Orbit.setStrokeWidth(2); // Set the width of the orbit

        // Set the stroke type to DOTTED
        Orbit.setStrokeType(StrokeType.OUTSIDE);
        Orbit.getStrokeDashArray().addAll(5d, 5d); // Set the dash pattern for the dotted line
        Orbit.setStrokeWidth(1);

        setMaterial(new javafx.scene.paint.PhongMaterial(Color.RED)); // Set the material and color
        root.getChildren().addAll(this, Orbit); // Add the sphere to the scene
    }

    public static void MakeAllAtomsInvisible() {
        for (Atoms atom : BoardItems.AllAtoms) {
            atom.setVisible(false);
            atom.Orbit.setVisible(false);
        }
    }

    public static void MakeAllAtomsVisible() {
        for (Atoms atom : BoardItems.AllAtoms) {
            atom.setVisible(true);
            atom.Orbit.setVisible(true);
        }
    }
}
