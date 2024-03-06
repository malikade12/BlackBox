package com.program;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Arrow{
    private
    public static Polygon createArrow(double x, double y, double z){


        // Create a skinny arrow shape
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(
                0.0, 5.0,
                15.0, 5.0,
                15.0, 0.0,
                20.0, 10.0,
                15.0, 20.0,
                15.0, 15.0,
                0.0, 15.0
        );
        arrow.setFill(Color.YELLOW);
        arrow.setScaleY(0.3);
        arrow.setScaleX(2.6);
            // Update arrow position to the mouse click position
            arrow.setTranslateX(x);
            arrow.setTranslateY(y);
           switch ((int) z){
               case 0:
                  arrow.setRotate(45);

                  break;
               case 2:
                   arrow.setRotate(-45);
                   break;
               case 3:
                   arrow.setRotate(-120);
                   break;
               case 4:
                   arrow.setRotate(-180);
                   break;
               case 5:
                   arrow.setRotate(125);
           }
        arrow.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                System.out.println("Clicked at: (" + x + ", " + y + ")");


            }
        });

        return arrow;

    }


}
