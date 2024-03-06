package com.program;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import static javafx.scene.CacheHint.SPEED;

public class Rays {

    public Rays(Group root, int x, int y){
        // Create a line
        Line line = new Line(100, 100, 300, 100);
        line.setStroke(Color.BLUE);
        line.setStrokeWidth(3);

        // Add the line to the scene
        root.getChildren().add(line);

        // Create an animation timer
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Update line position
                line.setEndX(line.getEndX() + 2);

                // Check if the line intersects with the end of the row
                if (line.getEndX() >= 400) { // Adjust 400 to the end of the row
                    stop(); // Stop the animation
                }
            }

};
    }

}
