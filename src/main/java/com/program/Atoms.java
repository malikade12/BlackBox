package com.program;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;
public class Atoms extends Circle{
    private int x;
    private int y;

    public Atoms(Group root,int x,int y){
          Circle c = new Circle(x, y, 10);
          root.getChildren().add(c);
    }

}
