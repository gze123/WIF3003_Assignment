package main.java.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.EdgeWorker;

import java.io.IOException;
import java.util.List;

public class GameResultController {
    public static Group group = new Group();
    public List<EdgeWorker> threadResults;
    Stage stage;

    @FXML
    private ListView<String> resultList;

    @FXML
    private Button restartButton;

    public void initData(List<EdgeWorker> threadResults, Stage stage) {
        this.stage = stage;
        this.threadResults = threadResults;
    }

    public void printResult(List<EdgeWorker> threadResults, Stage stage, boolean timeout) {
        this.stage = stage;
        ObservableList result = FXCollections.observableArrayList();
        String resultView = "";
        if (timeout){
            result.add("Timeout! Threads are stop due to time limit");
        }else {
            for (int i = 0; i < threadResults.size(); i++){
                if (threadResults.get(i).getAttempt() >= 20 ){
                    result.add(threadResults.get(i).getName() + " has reached 20 failed attempts. All threads stopped pairing.");
                }
            }
        }
        for (int i = 0; i < threadResults.size(); i++) {
            resultView = (i+1) + ". " + threadResults.get(i).getName() + " Number of Edge Created: " + threadResults.get(i).getNumberOfEdgeFormed() + " Number of Failure: " + threadResults.get(i).getAttempt() + "\n";
            result.add(resultView);
        }
        resultList.setItems(result);
    }

    public void restartGame(javafx.scene.input.MouseEvent mouseEvent) {
        Platform.runLater(() -> {
            Pane root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/GameStart.fxml"));
                root = loader.load();
                Scene gameStartScene = new Scene(root);
                Stage window = (Stage)(this.stage.getScene().getWindow());
                window.setScene(gameStartScene);
                window.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
