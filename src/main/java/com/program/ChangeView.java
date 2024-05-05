package com.program;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.List;

public class ChangeView {
    public static Button experimenterButton;
    public static Button setterButton;
    public static Button guessButton;
    public static Button endButton;
    public ChangeView() {
        GuessButton();
        experimenterButton();
        setterButton();
        EndRoundButton();
    }

    // EventHandler for the button click event
    EventHandler<ActionEvent> experimenterTurn = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {

            BoardItems.addLog(InitGame.PlayerOneName + "'s turn..");

            BoardItems.addLog("Markers Press:\n Q - Purple X - Pink.\nPress key again to stop marking");
            BoardItems.isSetter = false;
            BoardItems.setterSwitched = true;
            if (BoardItems.allAtoms.size() == 0) BoardItems.setterSwitched = false;
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
    EventHandler<ActionEvent> endRound = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            BoardItems.endRound = true;
            setterButton.setVisible(false);
            guessButton.setVisible(false);
            endButton.setVisible(true);

        }
    };
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
            for (Arrow a: BoardItems.allArrows){
                a.ShotAlready = false;
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


    public void GuessButton() {
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

    public void EndRoundButton() {
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


    // Method to get the button
    public Button getButton1() {
        return experimenterButton;
    }

    public Button getButton2() {
        return setterButton;
    }

    public Button getButton3() {
        return guessButton;
    }

    public Button getButton4() {
        return endButton;
    }


}
