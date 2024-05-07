package com.program;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class Scoring {




    //getting setter feedback after a ray is shot
    public static void SetterInput(int EntryPoint) {
        Stage DropdownStage = new Stage();
        DropdownStage.initModality(Modality.APPLICATION_MODAL);
        DropdownStage.setTitle("Setter Feedback");
        Arrow EntryArrow = InitGame.FindArrow(EntryPoint);
        EntryArrow.Shape.setFill(Color.PURPLE);

        // Create labels
        Label Title_Label = new Label("What Best describes the movement of the Ray shot from the purple arrow?");
        Label Text_Label = new Label("Ray Exited At (or 0 if absorbed/NA):");

        // Create the dropdown menu
        ComboBox<String> ComboBox = new ComboBox<>();


        TextField TextField = new TextField();
        TextField.setPrefWidth(200); // Increase width


        ComboBox.setItems(FXCollections.observableArrayList("ABSORBED", "REFLECTED", "DEFLECTED", "PASSED THROUGH"));
        ComboBox.setPrefWidth(200); // Increase width
        Button SubmitButton = new Button("Submit");
        //initialing variables using the setter's feedback
        SubmitButton.setOnAction(e -> {
            //get the inputted values
            String SELECTED_VALUE = ComboBox.getValue();
            String EXIT_VALUE = TextField.getText();
            EntryArrow.Shape.setFill(Color.YELLOW);
            //check if they give an invalid arrow number
            if (Integer.valueOf(EXIT_VALUE) < 0 || Integer.valueOf(EXIT_VALUE) > 54 || (Objects.equals(SELECTED_VALUE, "ABSORBED") && Integer.valueOf(EXIT_VALUE) != 0) || Integer.valueOf(EXIT_VALUE) == EntryPoint
                    || (!Objects.equals(SELECTED_VALUE, "ABSORBED") && Integer.valueOf(EXIT_VALUE) == 0)) {
                BoardItems.addLog("INVALID INPUT");
                DropdownStage.close();
                SetterInput(EntryPoint);
                EntryArrow.Shape.setFill(Color.PURPLE);
                return;
            }
            //give feedback to the experimenter
            BoardItems.addLog(SELECTED_VALUE);
            BoardItems.addLog("Entered at " + EntryPoint);
            if (!Objects.equals("0", EXIT_VALUE)) BoardItems.addLog("Exited at : " + EXIT_VALUE);
            //add entry and exit values to a imap to check for errors in the setters reporting
            if (!Objects.equals("0", EXIT_VALUE)) BoardItems.SetterRayPoints.put(EntryPoint, Integer.valueOf(EXIT_VALUE));
            DropdownStage.close();
            BoardItems.addLog("Switching to Experimenter....\"");
            //switch back to the experimenter
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(even -> ChangeView.ExperimenterButton.fire());
            pause.play();
        });
        //Add labels and ComboBox to the VBox layout
        VBox DropdownLayout = new VBox();

        DropdownLayout.getChildren().addAll(Title_Label, ComboBox, Text_Label, TextField, SubmitButton);


        // Set spacing between elements in the VBox
        DropdownLayout.setSpacing(10);

        // Set the scene and show the stage
        DropdownStage.setScene(new Scene(DropdownLayout));
        DropdownStage.setX(100);
        DropdownStage.show();
    }

    public static void EndRound() {
        ValidatePoints();
        BoardItems.addLog("Switch Roles");
        BoardItems.addLog(InitGame.PlayerTwoName + " finished with " + BoardItems.PlayerOneScore + " points....");
    }

    public static void ValidatePoints() {
        //check if the setter made errors while reporting
        for (Map.Entry<Integer, Integer> x : BoardItems.ActualRayPoints.entrySet()) {
            for (Map.Entry<Integer, Integer> y : BoardItems.SetterRayPoints.entrySet()) {
                //if the setter made a mistake on calling out an entry point, 5 is deducted from the experimenters score
                if (BoardItems.ActualRayPoints.get(y.getKey()) == null) {
                    BoardItems.addLog(InitGame.PlayerOneName + " lied about ray entering from " + y.getKey());
                    if (BoardItems.RoundCount == 0) BoardItems.PlayerOneScore -= 5;
                    else {
                        BoardItems.PlayerTwoScore -= 5;
                    }

                }
                //if setter lied about an exit point, 5 is deducted from the experimenters score
                else if (Objects.equals(x.getKey(), y.getKey()) && (!Objects.equals(x.getValue(), y.getValue()))) {
                    if (BoardItems.RoundCount == 0) BoardItems.PlayerOneScore -= 5;
                    else {
                        BoardItems.PlayerTwoScore -= 5;
                    }
                }
            }
        }
        //making sure the scores are never negative
        if (BoardItems.PlayerOneScore < 0) BoardItems.PlayerOneScore = 0;
        if (BoardItems.PlayerTwoScore < 0) BoardItems.PlayerTwoScore = 0;
    }

    //end round two and announce the results
    public static void EndRound2() {

        BoardItems.addLog("Switch roles ....");
        BoardItems.addLog(InitGame.PlayerTwoName + " finished with " + BoardItems.PlayerTwoScore + " points....");

        if (BoardItems.PlayerOneScore < BoardItems.PlayerTwoScore) {
            BoardItems.addLog(InitGame.PlayerOneName + " WON!!");
            BoardItems.WinnerMessage = InitGame.PlayerOneName.toUpperCase() + " WON!!";
        } else if (BoardItems.PlayerOneScore > BoardItems.PlayerTwoScore){
            BoardItems.addLog(InitGame.PlayerTwoName + " WON!!");
            BoardItems.WinnerMessage = InitGame.PlayerTwoName.toUpperCase() + " WON!!";
        }else{
            BoardItems.addLog("Its a Draw!!");
            BoardItems.WinnerMessage =  "Its a Draw!!";
        }





        Controller switcher = new Controller();

        try {
            switcher.switchToEndScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    }


