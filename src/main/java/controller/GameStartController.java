package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class GameStartController {

        @FXML
        private TextField inputNumberOfPoint;

        @FXML
        private TextField inputNumberOfThread;

        @FXML
        private TextField inputTimeLimit;

        @FXML
        private TextField points;

        @FXML
        private Button startButton;

        public void onMouseClick(MouseEvent mouseEvent) {
                if (inputNumberOfPoint.getText().isEmpty() || inputNumberOfThread.getText().isEmpty() || inputTimeLimit.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Invalid Input");
                        alert.setContentText("Please type again. Only integer is allowed.");
                        alert.show();
                } else {
                        int numberOfPoint = Integer.parseInt(inputNumberOfPoint.getText());
                        int numberOfThread = Integer.parseInt(inputNumberOfThread.getText());
                        int timeLimit = Integer.parseInt(inputTimeLimit.getText());

                        System.out.println(numberOfPoint + " " + numberOfThread +  " " +  timeLimit);
                }

        }

}
