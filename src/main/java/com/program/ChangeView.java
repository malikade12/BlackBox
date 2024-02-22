package com.program;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
public class ChangeView {
    private Button button1;

    // EventHandler for the button click event
    EventHandler<ActionEvent> event = e -> System.out.println("Button selected");

    // Method to initialize the button
    public void testButton() {
        button1 = new Button("Change view");

        // Set the action for the button
        button1.setOnAction(event);

    }

    // Method to get the button
    public Button getButton1() {
        return button1;
    }
}