package com.program;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.List;

import static com.program.BoardItems.*;

public class ChangeView {
    public static Button ExperimenterButton;
    public static Button SetterButton;
    public static Button GuessButton;
    public static Button EndButton;
    public static Button HelpButton;
    public ChangeView(){
        Guess();
        ExperimenterButton();
        SetterButton();
        EndRound();
        HelpPage();
    }

    // EventHandler for the button click event
    EventHandler<ActionEvent> experimenterTurn = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {

            addLog(InitGame.PlayerOneName + "'s turn..");
            IsSetter = false;
            SetterSwitched = true;
            if (AllAtoms.size() == 0) SetterSwitched = false;
            ExperimenterButton.setVisible(false);
            SetterButton.setVisible(true);
            GuessButton.setVisible(true);
            HelpButton.setVisible(true);
            Atoms.MakeAllAtomsInvisible();
            Arrow.MakeRaysVisible(false);
            Hexagon.GameMode = 1;
        }
    };

    EventHandler<ActionEvent> setterTurn = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            IsSetter = true;
            addLog(InitGame.PlayerTwoName + "'s turn..");
            ExperimenterButton.setVisible(true);
            SetterButton.setVisible(false);
            GuessButton.setVisible(false);
            HelpButton.setVisible(false);
            Atoms.MakeAllAtomsVisible();
            Arrow.MakeRaysVisible(true);
            Hexagon.GameMode = 0;
        }
    };
    //Event handler for the end round button
    EventHandler<ActionEvent> endRound = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            BoardItems.EndRound = true;
            SetterButton.setVisible(false);
            GuessButton.setVisible(false);
            EndButton.setVisible(true);

        }
    };
//EventHandler for The help button
    EventHandler<ActionEvent> help = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {

            try {
                // Load the FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HelpPage.fxml"));
                Parent root = loader.load();

                // Create a new scene
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());//styles the scene


                // Create a new stage and set the scene
                Stage stage = new Stage();
                stage.setScene(scene);

                // Show the stage
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        };
    //eventHandler to clear the board after each turn
    EventHandler<ActionEvent> clearBoard = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            EndButton.setVisible(false);
            Scoring.ValidatePoints();
            //Call end Round method
            ResetVariables();

            if (RoundCount < 1) {
                RoundCount++;
                Scoring.EndRound();
                SetterButton.fire();
            } else {
                Scoring.EndRound2();
                addLog("Thanks for playing!!!!");
            }
        }
    };
    public void ResetVariables(){
        for (Atoms atom : AllAtoms) {
            Main.Root.getChildren().remove(atom);
            Main.Root.getChildren().remove(atom.Orbit);
            // Optionally, remove it from the lis
        }
        AllAtoms.clear();
        ActualRayPoints.clear();
        SetterRayPoints.clear();
        String temp = InitGame.PlayerOneName;
        InitGame.PlayerOneName = InitGame.PlayerTwoName;
        InitGame.PlayerTwoName = temp;

        if (Arrow.rays != null) {
            for (Line ray : Arrow.rays) {

                Main.Root.getChildren().remove(ray);
                // Optionally, remove it from the list
            }
            Arrow.rays.clear();
        }
        for (Circle marker : MarkerList) {

            Main.Root.getChildren().remove(marker);
            // Optionally, remove it from the list
        }

        MarkerList.clear();

        IsSetter = true;
        SetterSwitched = false;
        BoardItems.EndRound = false;
        MarkerEnabled = false;
        Hexagon.AtomCounter = 0;
        SetterButton.setVisible(true);
        for (List<Hexagon> a : AllHexagons
        ) {
            for (Hexagon n : a
            ) {
                n.Guessed = false;
            }
        }
    }

//Button for ending turn and submitting guesses
    public void Guess() {
        GuessButton = new Button("Make Guesses");
        GuessButton.setOnAction(endRound);
        GuessButton.setVisible(false);

        GuessButton.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );


    }

    // Method to initialize the button
    public void ExperimenterButton() {
        ExperimenterButton = new Button("Change to Experimenter view");

        ExperimenterButton.setOnAction(experimenterTurn);

        ExperimenterButton.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );
    }

//Method to swap to setter view
    public void SetterButton() {
        SetterButton = new Button("Change to Setter view");

        SetterButton.setOnAction(setterTurn);
        SetterButton.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );


    }
//Provides logic for ending round to be used by eventHandler
    public void EndRound() {
        EndButton = new Button("End Round");
        EndButton.setVisible(false);
        EndButton.setOnAction(clearBoard);
        GuessButton.setVisible(false);

        EndButton.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );


    }
//Method and style for help button
    public void HelpPage() {
        HelpButton = new Button("Help");
        HelpButton.setOnAction(help);

        HelpButton.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );


    }


    // Method to get the button
    public Button GetExperimenterButton() {
        return ExperimenterButton;
    }

    public Button GetSetterButton() {
        return SetterButton;
    }

    public Button GetGuessButton() {
        return GuessButton;
    }

    public Button GetEndButton() {
        return EndButton;
    }

    public static Button GetHelpButton() {
        return HelpButton;
    }
}
