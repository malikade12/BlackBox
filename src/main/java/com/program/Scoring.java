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

import java.util.Map;
import java.util.Objects;

public class Scoring{

       //getting setter feedback after a ray is shot
       public static void SetterInput(int EntryPoint) {
              Stage dropdownStage = new Stage();
              dropdownStage.initModality(Modality.APPLICATION_MODAL);
              dropdownStage.setTitle("Setter Feedback");

              // Create labels
              Label titleLabel = new Label("What Best describes the movement of the Ray?");
              Label textLabel2 = new Label("Ray Exited At (or 0 if absorbed/NA):");

              // Create the dropdown menu
              ComboBox<String> comboBox = new ComboBox<>();


              TextField textField2 = new TextField();
              textField2.setPrefWidth(200); // Increase width


              comboBox.setItems(FXCollections.observableArrayList("ABSORBED", "REFLECTED", "DEFLECTED", "PASSED THROUGH"));
              comboBox.setPrefWidth(200); // Increase width
              Button submitButton = new Button("Submit");
              //initialing variables using the setter's feedback
              submitButton.setOnAction(e -> {
                     String selectedValue = comboBox.getValue();
                     String exitValue = textField2.getText();
                     //check if they give an invalid arrow number
                     if ( Integer.valueOf(exitValue) < 0 || Integer.valueOf(exitValue) > 54 || (Objects.equals(selectedValue, "ABSORBED") && Integer.valueOf(exitValue) != 0)){
                            BoardItems.addLog("INVALID INPUT");
                            dropdownStage.close();
                            SetterInput(EntryPoint);
                            return;
                     }
                     //give feedback to the experimenter
                     BoardItems.addLog(selectedValue);
                     BoardItems.addLog("Entered at " + EntryPoint);
                     if (!Objects.equals("0", exitValue)) BoardItems.addLog("Exited at : " + exitValue);
                     //add entry and exit values to a imap to check for errors in the setters reporting
                     if (!Objects.equals("0", exitValue)) BoardItems.SetterRayPoints.put(EntryPoint, Integer.valueOf(exitValue));
                     dropdownStage.close();
                     BoardItems.addLog("Switching to Experimenter....\"");
                     //switch back to the experimenter
                     PauseTransition pause = new PauseTransition(Duration.seconds(3));
                     pause.setOnFinished(even -> ChangeView.ExperimenterButton.fire());
                     pause.play();
              });
              //Add labels and ComboBox to the VBox layout
              VBox dropdownLayout = new VBox();

              dropdownLayout.getChildren().addAll(titleLabel, comboBox, textLabel2, textField2, submitButton);


              // Set spacing between elements in the VBox
              dropdownLayout.setSpacing(10);

              // Set the scene and show the stage
              dropdownStage.setScene(new Scene(dropdownLayout));
              dropdownStage.setX(100);
              dropdownStage.show();
       }
       public static void EndRound(){
              ValidatePoints();

              BoardItems.addLog("Switch Roles");
              BoardItems.addLog(InitGame.SetterName + " finished with " + BoardItems.ExScore + " points....");
       }
       public static void ValidatePoints(){
              //check if the setter made errors while reporting
              for (Map.Entry<Integer, Integer> x: BoardItems.ActualRayPoints.entrySet()){
                     for (Map.Entry<Integer,Integer> y: BoardItems.SetterRayPoints.entrySet()){
                            //if the setter made a mistake on calling out an entry point, 5 is deducted from the experimenters score
                            if (BoardItems.ActualRayPoints.get(y.getKey()) == null ){
                                   BoardItems.addLog(InitGame.ExperimenterName + " lied about ray entering from " + y.getKey() );
                                   if(BoardItems.roundcount == 0) BoardItems.ExScore -= 5;
                                   else{
                                          BoardItems.SetScore -=5;
                                   }

                            }
                            //if setter lied about an exit point, 5 is deducted from the experimenters score
                            else if (Objects.equals(x.getKey(), y.getKey()) && (!Objects.equals(x.getValue(), y.getValue()))){
                                   BoardItems.addLog(InitGame.ExperimenterName + " lied about ray enting from " + x.getKey() + " and exiting from " + y.getValue());
                                   if(BoardItems.roundcount == 0) BoardItems.ExScore -= 5;
                                   else{
                                          BoardItems.SetScore -=5;
                                   }
                            }
                     }
              }
              //making sure the scores are never negative
              if (BoardItems.ExScore < 0) BoardItems.ExScore = 0;
              if (BoardItems.SetScore < 0) BoardItems.SetScore = 0;
       }
       //end round two and announce the results
       public static void EndRound2(){
              BoardItems.addLog("Switch roles ....");
              BoardItems.addLog(InitGame.SetterName + " finished with " + BoardItems.SetScore + " points....");

              if(BoardItems.ExScore < BoardItems.SetScore) {
                     BoardItems.addLog(InitGame.ExperimenterName+" WON!!");
              }
              else{
                     BoardItems.addLog(InitGame.SetterName+" WON!!");
              }

       }


}