package com.program;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
public class ChangeView {
    public static Button ExperimenterButton;
    public static Button SetterButton;
    public static Button GuessButton;
    public static Button EndButton;



    // EventHandler for the button click event
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
<<<<<<< Updated upstream
            System.out.println(InitGame.ExperimenterName + "'s turn..");
            System.out.println("Markers Press:\n Q - Purple X - Pink.\nPress key again to stop marking");
=======


            Main.addLog(InitGame.ExperimenterName + "'s turn..");
            Main.addLog("Markers Press:\n Q - Purple X - Pink.\nPress key again to stop marking");

>>>>>>> Stashed changes
            Main.IsSetter = false;
            Main.SetterSwitched = true;
            ExperimenterButton.setVisible(false);
            SetterButton.setVisible(true);
            GuessButton.setVisible(true);
            Atoms.makeAllAtomsInvisible();
            Arrow.MakeRaysVisible(false);
            Hexagon.mode = 1;
        }
    };
    EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            Main.IsSetter = true;
            Main.addLog(InitGame.SetterName + "'s turn..");
            ExperimenterButton.setVisible(true);
            SetterButton.setVisible(false);
            GuessButton.setVisible(false);
            Atoms.makeAllAtomsVisible();
            Arrow.MakeRaysVisible(true);
            Hexagon.mode=0;
        }
    };
    EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Main.EndOfRound = true;
            SetterButton.setVisible(false);
            GuessButton.setVisible(false);
            EndButton.setVisible(true);

        }
    };
    EventHandler<ActionEvent> event4 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
<<<<<<< Updated upstream
            System.out.println(Main.ExScore);
            //Call end Round method
=======
            EndButton.setVisible(false);
            Scoring.ValidatePoints();//Call end Round method
            Main.addLog("Cleaning");
            for (Atoms atom : Main.allAtoms) {
                Main.root.getChildren().remove(atom);
                Main.root.getChildren().remove(atom.orbit);
                // Optionally, remove it from the lis
            }
            Main.allAtoms.clear();
            Main.ActualRayPoints.clear();
            Main.SetterRayPoints.clear();

            String temp = InitGame.ExperimenterName;
            InitGame.ExperimenterName = InitGame.SetterName;
            InitGame.SetterName = temp;

          if (Arrow.rays != null) {
              for (Line ray : Arrow.rays) {

                  Main.root.getChildren().remove(ray);
                  // Optionally, remove it from the list
              }
              Arrow.rays.clear();
          }
            for (Circle marker : Main.markerList) {

                Main.root.getChildren().remove(marker);
                // Optionally, remove it from the list
            }

            Main.markerList.clear();

            Main.IsSetter = true;
            Main.SetterSwitched = false;
            Main.EndOfRound = false;
            Main.markerEnabled = false;
            Hexagon.counter=0;
            SetterButton.setVisible(true);
            for (List<Hexagon> a: Main.allHexagons
                 ) {
                for (Hexagon n:a
                     ) {
                    n.Guessed = false;
                }
            }

            if(Main.roundcount<1){
                Main.roundcount++;
                Scoring.EndRound();
                SetterButton.fire();
            }
            else{
                Scoring.EndRound2();
                Main.addLog("Thanks for playing!!!!");
            }



>>>>>>> Stashed changes
        }
    };
    public void Guess() {
        GuessButton = new Button("Make Guesses");
     GuessButton.setOnAction(event3);
     GuessButton.setVisible(false);

        GuessButton.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );


    }

    // Method to initialize the button
    public void experimenterButton() {
        ExperimenterButton = new Button("Change to Experimenter view");

        ExperimenterButton.setOnAction(event);

        ExperimenterButton.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );
    }


    public void setterButton() {
        SetterButton = new Button("Change to Setter view");

        SetterButton.setOnAction(event2);
        SetterButton.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );


    }
    public void EndRound() {
        EndButton = new Button("End Round");
        EndButton.setVisible(false);
        EndButton.setOnAction(event4);

        GuessButton.setVisible(false);

        EndButton.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: yellow; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: 'Lucida Console';"
        );


    }

    // Method to get the button
    public Button getButton1() {
        return ExperimenterButton;
    }
    public Button getButton2() {
        return SetterButton;
    }
    public Button getButton3(){return GuessButton;}
    public Button getButton4(){return EndButton;}

}
