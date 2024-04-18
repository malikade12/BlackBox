package com.program;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Scoring{

       public static void hi() {
              // Create a new stage for the dropdown menu

              Stage dropdownStage = new Stage();
              dropdownStage.initModality(Modality.APPLICATION_MODAL);
              dropdownStage.setTitle("Dropdown Menu");

              // Create labels
              Label titleLabel = new Label("What Best describes the movement of the Ray?");
              Label textLabel1 = new Label("Ray Entered At");
              Label textLabel2 = new Label("Ray Exited At (or 0 if absorbed):");

              // Create the dropdown menu
              ComboBox<String> comboBox = new ComboBox<>();

              // Create text fields
              TextField textField1 = new TextField();
              textField1.setPrefWidth(200); // Increase width

              TextField textField2 = new TextField();
              textField2.setPrefWidth(200); // Increase width


              comboBox.setItems(FXCollections.observableArrayList("Absorbed", "Reflected", "Deflected"));
              comboBox.setPrefWidth(200); // Increase width
              Button submitButton = new Button("Submit");
              submitButton.setOnAction(e -> {
                     String selectedValue = comboBox.getValue();
                     String entryValue = textField1.getText();
                     String exitValue = textField2.getText();
                     System.out.println("Ray was " + selectedValue);
                     System.out.println("Entered at " + entryValue);
                     System.out.println("Exited at : " + exitValue);
                     if (!Objects.equals("0", exitValue)) Main.RayPoints.put(Integer.valueOf(entryValue), Integer.valueOf(exitValue));
                     dropdownStage.close();
                     System.out.println("Switching to Experimenter....");
                     PauseTransition pause = new PauseTransition(Duration.seconds(5));
                     pause.setOnFinished(even -> ChangeView.button1.fire());
                     pause.play();
              });
              //Add labels and ComboBox to the VBox layout
              VBox dropdownLayout = new VBox();

              dropdownLayout.getChildren().addAll(titleLabel, comboBox, textLabel1, textField1, textLabel2, textField2, submitButton);


              // Set spacing between elements in the VBox
              dropdownLayout.setSpacing(10);
              //dropdownLayout.setScaleX(50);
              //dropdownLayout.setScaleY(200);

              // Set the scene and show the stage
              dropdownStage.setScene(new Scene(dropdownLayout));
              dropdownStage.show();
       }

       public static void main(String[] args) {
              hi();
       }
}