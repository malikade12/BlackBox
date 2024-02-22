package com.program;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;
public class Atoms extends Circle{

    public Atoms(Group root,double x,double y){
          Circle c = new Circle(x, y, 20, Color.RED);
          root.getChildren().add(c);
    }

}
