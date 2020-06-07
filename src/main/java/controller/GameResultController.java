package main.java.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.object.ThreadResult;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

public class GameResultController {
    public static Group group = new Group();
    public List<ThreadResult> threadResults;
    Stage stage;

    @FXML
    private Label resultList;

    @FXML
    private Button restartButton;

    public void initData(List<ThreadResult> threadResults, Stage stage) {
        this.stage = stage;
        this.threadResults = threadResults;
    }

    public void printResult(List<ThreadResult> threadResults) {
        String result = "";
        for (int i = 0; i < threadResults.size(); i++) {
            result += i + ". " + threadResults.get(i).getName() + " Number of Edge Created: " + threadResults.get(i).getNumberOfEdgeCreated() + " Number of Failuer: " + threadResults.get(i).getNumberOfFailure() + "\n";
        }
        resultList.setText(result);
    }

    @FXML
    public void resetGame(MouseEvent event) {
        Platform.runLater(() -> {
            stage = new Stage();
            stage.setTitle("Game Menu");
            Pane root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/main/resources/view/GameStart.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(new Scene(root));
            stage.show();
        });
    }
}
