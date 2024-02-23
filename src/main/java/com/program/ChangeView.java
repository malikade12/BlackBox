package com.program;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
public class ChangeView {
    private Button button1;
    private Button button2;


    // EventHandler for the button click event
    EventHandler<ActionEvent> event = e -> Main.Atoms.makeAllAtomsInvisible();
    EventHandler<ActionEvent> event2 = e -> Main.Atoms.makeAllAtomsVisible();


    // Method to initialize the button
    public void testButton() {
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
