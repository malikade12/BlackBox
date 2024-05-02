package com.program;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class Scoring{

<<<<<<< Updated upstream
       public static void hi() {
              // Create a new stage for the dropdown menu

=======
       //getting setter feedback after a ray is shot
       public static void SetterInput(int EntryPoint) {
>>>>>>> Stashed changes
              Stage dropdownStage = new Stage();
              dropdownStage.initModality(Modality.APPLICATION_MODAL);
              dropdownStage.setTitle("Dropdown Menu");

              // Create labels
              Label titleLabel = new Label("What Best describes the movement of the Ray?");
              Label textLabel2 = new Label("Ray Exited At (or 0 if absorbed/NA):");

              // Create the dropdown menu
              ComboBox<String> comboBox = new ComboBox<>();

<<<<<<< Updated upstream
              // Create text fields
              TextField textField1 = new TextField();
              textField1.setPrefWidth(200); // Increase width

=======
>>>>>>> Stashed changes
              TextField textField2 = new TextField();
              textField2.setPrefWidth(200); // Increase width


              comboBox.setItems(FXCollections.observableArrayList("ABSORBED", "REFLECTED", "DEFLECTED", "PASSED THROUGH"));
              comboBox.setPrefWidth(200); // Increase width
              Button submitButton = new Button("Submit");
              submitButton.setOnAction(e -> {
                     String selectedValue = comboBox.getValue();
                     String exitValue = textField2.getText();
<<<<<<< Updated upstream
                     if (Integer.valueOf(entryValue) < 1 || Integer.valueOf(entryValue) > 54 || Integer.valueOf(exitValue) < 0 || Integer.valueOf(exitValue) > 54){
                            System.out.println("invalid input ");
                            dropdownStage.close();
                            hi();
                            return;
                     }
                     if (!Objects.equals(selectedValue, "Passed through")) System.out.println("Ray was " + selectedValue);
                     System.out.println("Entered at " + entryValue);
                     System.out.println("Exited at : " + exitValue);
                     if (!Objects.equals("0", exitValue)) Main.RayPoints.put(Integer.valueOf(entryValue), Integer.valueOf(exitValue));
=======
                     //check if they give an invalid arrow number
                     if ( Integer.valueOf(exitValue) < 0 || Integer.valueOf(exitValue) > 54 || (Objects.equals(selectedValue, "ABSORBED") && Integer.valueOf(exitValue) != 0)){
                            Main.addLog("INVALID INPUT");
                            dropdownStage.close();
                            SetterInput(EntryPoint);
                            return;
                     }
                     //give feedback to the experimenter
                     if (!Objects.equals(selectedValue, "PASSED THROUGH")) Main.addLog("Ray" + selectedValue);
                     Main.addLog("Entered at " + EntryPoint);
                     if (!Objects.equals("0", exitValue)) Main.addLog("Exited at : " + exitValue);
                     //add entry and exit values to a map to check for errors in the setters reporting
                      Main.SetterRayPoints.put(EntryPoint, Integer.valueOf(exitValue));
>>>>>>> Stashed changes
                     dropdownStage.close();
                     System.out.println("Switching to Experimenter....");
<<<<<<< Updated upstream
                     PauseTransition pause = new PauseTransition(Duration.seconds(3));
=======
                     Main.addLog("Switching to Experimenter....\"");
                     //switch back to the experimenter
                     PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
>>>>>>> Stashed changes
                     pause.setOnFinished(even -> ChangeView.ExperimenterButton.fire());
                     pause.play();
              });
              //Add labels and ComboBox to the VBox layout
              VBox dropdownLayout = new VBox();

              dropdownLayout.getChildren().addAll(titleLabel, comboBox, textLabel2, textField2, submitButton);


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