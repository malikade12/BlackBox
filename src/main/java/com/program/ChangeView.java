package com.program;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
public class ChangeView {
    private Button button1;
    private Button button2;


    // EventHandler for the button click event
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            Main.Atoms.makeAllAtomsInvisible();
            Main.Hexagon.mode = 1;
        }
    };
    EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            Main.Atoms.makeAllAtomsVisible();
            Main.Hexagon.mode=0;
        }
    };

    // Method to initialize the button
    public void experimenterButton() {
        button1 = new Button("Change to Experimenter view");

        // Set the action for the button
        button1.setOnAction(event);

    }

    public void setterButton() {
        button2 = new Button("Change to Setter view");

        // Set the action for the button
        button2.setOnAction(event2);


    }

    // Method to get the button
    public Button getButton1() {
        return button1;
    }
    public Button getButton2() {
        return button2;
    }
}
