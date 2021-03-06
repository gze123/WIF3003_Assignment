package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.java.object.GameSetting;

import java.io.IOException;

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

        public static boolean isNumeric(String str) {
                try {
                        Integer.parseInt(str);
                        return true;
                } catch(NumberFormatException e){
                        return false;
                }
        }

        public void onMouseClick(MouseEvent mouseEvent) throws IOException {
                if (inputNumberOfPoint.getText().isEmpty() || inputNumberOfThread.getText().isEmpty() || inputTimeLimit.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Invalid Input");
                        alert.setContentText("Please type again. All field must be filled with integer.");
                        alert.show();
                } else if ( (!isNumeric(inputNumberOfPoint.getText()) || (Integer.parseInt(inputNumberOfPoint.getText()) <= 0 ) )||
                        ( !isNumeric(inputNumberOfThread.getText()) || (Integer.parseInt(inputNumberOfThread.getText()) <= 0) )||
                        ( !isNumeric(inputTimeLimit.getText())) || (Integer.parseInt(inputTimeLimit.getText()) <= 0) ){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Invalid Input");
                        alert.setContentText("Please type again. Only positive integer is allowed.");
                        alert.show();
                }
                else {
                        int numberOfPoint = Integer.parseInt(inputNumberOfPoint.getText());
                        int numberOfThread = Integer.parseInt(inputNumberOfThread.getText());
                        int timeLimit = Integer.parseInt(inputTimeLimit.getText());
                        GameSetting gameSetting = new GameSetting(numberOfPoint,numberOfThread,timeLimit);


                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/main/resources/view/GameProcessVisualisation.fxml"));
                        Parent root = loader.load();
                        GameProcessVisualisationController controller = loader.getController();
                        controller.initData(gameSetting,(Stage)inputTimeLimit.getScene().getWindow());
                        controller.startGame();
                }

        }

}
