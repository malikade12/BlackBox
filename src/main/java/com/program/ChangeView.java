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

public class ChangeView {
    public static Button experimenterButton;
    public static Button setterButton;
    public static Button guessButton;
    public static Button endButton;
    public static Button helpButton;

    // EventHandler for the button click event
    EventHandler<ActionEvent> experimenterTurn = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {

            BoardItems.addLog(InitGame.PlayerOneName + "'s turn..");

            BoardItems.addLog("Markers Press:\n Q - Purple X - Pink.\nPress key again to stop marking");
            BoardItems.isSetter = false;
            BoardItems.setterSwitched = true;
            experimenterButton.setVisible(false);
            setterButton.setVisible(true);
            guessButton.setVisible(true);
            Atoms.makeAllAtomsInvisible();
            Arrow.MakeRaysVisible(false);
            Hexagon.gameMode = 1;
        }
    };

    EventHandler<ActionEvent> setterTurn = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            BoardItems.isSetter = true;
            BoardItems.addLog(InitGame.PlayerTwoName + "'s turn..");
            experimenterButton.setVisible(true);
            setterButton.setVisible(false);
            guessButton.setVisible(false);
            Atoms.makeAllAtomsVisible();
            Arrow.MakeRaysVisible(true);
            Hexagon.gameMode = 0;
        }
    };
    //Event handler for the end round button
    EventHandler<ActionEvent> endRound = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            BoardItems.endRound = true;
            setterButton.setVisible(false);
            guessButton.setVisible(false);
            endButton.setVisible(true);

        }
    };
//EventHandler for The help button
    EventHandler<ActionEvent> help = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            BoardItems.endRound = true;
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
            endButton.setVisible(false);
            Scoring.ValidatePoints();
            //Call end Round method
            for (Atoms atom : BoardItems.allAtoms) {
                Main.root.getChildren().remove(atom);
                Main.root.getChildren().remove(atom.orbit);
                // Optionally, remove it from the lis
            }
            BoardItems.allAtoms.clear();
            BoardItems.actualRayPoints.clear();
            BoardItems.setterRayPoints.clear();
            String temp = InitGame.PlayerOneName;
            InitGame.PlayerOneName = InitGame.PlayerTwoName;
            InitGame.PlayerTwoName = temp;

            if (Arrow.rays != null) {
                for (Line ray : Arrow.rays) {

                    Main.root.getChildren().remove(ray);
                    // Optionally, remove it from the list
                }
                Arrow.rays.clear();
            }
            for (Circle marker : BoardItems.markerList) {

                Main.root.getChildren().remove(marker);
                // Optionally, remove it from the list
            }

            BoardItems.markerList.clear();

            BoardItems.isSetter = true;
            BoardItems.setterSwitched = false;
            BoardItems.endRound = false;
            BoardItems.markerEnabled = false;
            Hexagon.counter = 0;
            setterButton.setVisible(true);
            for (List<Hexagon> a : BoardItems.allHexagons
            ) {
                for (Hexagon n : a
                ) {
                    n.guessed = false;
                }
            }

            if (BoardItems.roundCount < 1) {
                BoardItems.roundCount++;
                Scoring.EndRound();
                setterButton.fire();
            } else {
                Scoring.EndRound2();
                BoardItems.addLog("Thanks for playing!!!!");
            }
        }
    };

//Button for ending turn and submitting guesses
    public void Guess() {
        guessButton = new Button("Make Guesses");
        guessButton.setOnAction(endRound);
        guessButton.setVisible(false);

        guessButton.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );


    }

    // Method to initialize the button
    public void experimenterButton() {
        experimenterButton = new Button("Change to Experimenter view");

        experimenterButton.setOnAction(experimenterTurn);

        experimenterButton.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );
    }

//Method to swap to setter view
    public void setterButton() {
        setterButton = new Button("Change to Setter view");

        setterButton.setOnAction(setterTurn);
        setterButton.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );


    }
//Provides logic for ending round to be used by eventHandler
    public void EndRound() {
        endButton = new Button("End Round");
        endButton.setVisible(false);
        endButton.setOnAction(clearBoard);
        guessButton.setVisible(false);

        endButton.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );


    }
//Method and style for help button
    public void HelpPage() {
        helpButton = new Button("Help");
        helpButton.setOnAction(help);

        helpButton.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );


    }


    // Method to get the button
    public Button getExperimenterButton() {
        return experimenterButton;
    }

    public Button getSetterButton() {
        return setterButton;
    }

    public Button getGuessButton() {
        return guessButton;
    }

    public Button getEndButton() {
        return endButton;
    }

    public static Button getHelpButton() {
        return helpButton;
    }
}
