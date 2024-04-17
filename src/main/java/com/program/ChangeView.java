package com.program;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
public class ChangeView {
    private Button button1;
    private Button button2;
    private Button button3;



    // EventHandler for the button click event
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            button3.setVisible(true);
            Atoms.makeAllAtomsInvisible();
            Hexagon.mode = 1;
        }
    };
    EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            button3.setVisible(false);
            Atoms.makeAllAtomsVisible();
            Hexagon.mode=0;
        }
    };
    EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            System.out.println(Main.MarkerCounter);
        }
    };
    public void EndRound() {
        button3 = new Button("End Round");
     button3.setOnAction(event3);

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
