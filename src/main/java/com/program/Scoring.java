package com.program;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class Scoring {


    private static Controller controller;


    public static void setController(Controller controller) {
        Scoring.controller = controller;
    }



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
            if (Integer.valueOf(exitValue) < 0 || Integer.valueOf(exitValue) > 54 || (Objects.equals(selectedValue, "ABSORBED") && Integer.valueOf(exitValue) != 0)) {
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
            if (!Objects.equals("0", exitValue)) BoardItems.setterRayPoints.put(EntryPoint, Integer.valueOf(exitValue));
            dropdownStage.close();
            BoardItems.addLog("Switching to Experimenter....\"");
            //switch back to the experimenter
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(even -> ChangeView.experimenterButton.fire());
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

    public static void EndRound() {
        ValidatePoints();

        BoardItems.addLog("Switch Roles");
        BoardItems.addLog(InitGame.setterName + " finished with " + BoardItems.expScore + " points....");
    }

    public static void ValidatePoints() {
        //check if the setter made errors while reporting
        for (Map.Entry<Integer, Integer> x : BoardItems.actualRayPoints.entrySet()) {
            for (Map.Entry<Integer, Integer> y : BoardItems.setterRayPoints.entrySet()) {
                //if the setter made a mistake on calling out an entry point, 5 is deducted from the experimenters score
                if (BoardItems.actualRayPoints.get(y.getKey()) == null) {
                    BoardItems.addLog(InitGame.experimenterName + " lied about ray entering from " + y.getKey());
                    if (BoardItems.roundCount == 0) BoardItems.expScore -= 5;
                    else {
                        BoardItems.setScore -= 5;
                    }

                }
                //if setter lied about an exit point, 5 is deducted from the experimenters score
                else if (Objects.equals(x.getKey(), y.getKey()) && (!Objects.equals(x.getValue(), y.getValue()))) {
                    BoardItems.addLog(InitGame.experimenterName + " lied about ray enting from " + x.getKey() + " and exiting from " + y.getValue());
                    if (BoardItems.roundCount == 0) BoardItems.expScore -= 5;
                    else {
                        BoardItems.setScore -= 5;
                    }
                }
            }
        }
        //making sure the scores are never negative
        if (BoardItems.expScore < 0) BoardItems.expScore = 0;
        if (BoardItems.setScore < 0) BoardItems.setScore = 0;
    }

    //end round two and announce the results
    public static void EndRound2() {

        BoardItems.addLog("Switch roles ....");
        BoardItems.addLog(InitGame.setterName + " finished with " + BoardItems.setScore + " points....");

        if (BoardItems.expScore < BoardItems.setScore) {
            BoardItems.addLog(InitGame.experimenterName + " WON!!");
            BoardItems.winnerMessage = InitGame.experimenterName + " WON!!";
        } else {
            BoardItems.addLog(InitGame.setterName + " WON!!");
            BoardItems.winnerMessage = InitGame.setterName + " WON!!";
        }





        Controller switcher = new Controller();

        try {
            switcher.switchToEndScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    }


