package com.program;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
public class ChangeView {
    public static Button button1;
    public static Button button2;
    public static Button button3;



    // EventHandler for the button click event
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            System.out.println(InitGame.ExperimenterName + "'s turn..");
            button1.setVisible(false);
            button2.setVisible(true);
            button3.setVisible(true);
            Atoms.makeAllAtomsInvisible();
            Arrow.MakeRaysVisible(false);
            Hexagon.mode = 1;
        }
    };
    EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            System.out.println(InitGame.SetterName + "'s turn..");
            button1.setVisible(true);
            button2.setVisible(false);
            button3.setVisible(false);
            Atoms.makeAllAtomsVisible();
            Arrow.MakeRaysVisible(true);
            Hexagon.mode=0;
        }
    };
    EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            button1.setVisible(false);
            button3.setVisible(true);
            System.out.println(Main.MarkerCounter);
        }
    };
    public void EndRound() {
        button3 = new Button("End Round");
     button3.setOnAction(event3);
     button3.setVisible(false);

        button3.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );


    }

    // Method to initialize the button
    public void experimenterButton() {
        button1 = new Button("Change to Experimenter view");

        button1.setOnAction(event);

        button1.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );
    }


    public void setterButton() {
        button2 = new Button("Change to Setter view");

        button2.setOnAction(event2);
        button2.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );


    }

    // Method to get the button
    public Button getButton1() {
        return button1;
    }
    public Button getButton2() {
        return button2;
    }
    public Button getButton3(){return button3;}

}
