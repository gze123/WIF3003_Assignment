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
import main.java.object.ThreadResult;

import java.io.IOException;
import java.util.List;

public class GameResultController {
    public static Group group = new Group();
    public List<ThreadResult> threadResults;
    Stage stage;

    @FXML
    private ListView<String> resultList;

    @FXML
    private Button restartButton;

    public void initData(List<ThreadResult> threadResults, Stage stage) {
        this.stage = stage;
        this.threadResults = threadResults;
    }

    public void printResult(List<ThreadResult> threadResults, Stage stage) {
        this.stage = stage;
        ObservableList result = FXCollections.observableArrayList();
        String resultView = "";
        for (int i = 0; i < threadResults.size(); i++) {
            resultView = (i+1) + ". " + threadResults.get(i).getName() + " Number of Edge Created: " + threadResults.get(i).getNumberOfEdgeCreated() + " Number of Failuer: " + threadResults.get(i).getNumberOfFailure() + "\n";
            result.add(resultView);
        }
        resultList.setItems(result);
    }

    public void restartGame(javafx.scene.input.MouseEvent mouseEvent) {
        Platform.runLater(() -> {
//            stage = new Stage();
//            stage.setTitle("Game Menu");
            Pane root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/GameStart.fxml"));
                root = loader.load();
                GameStartController gameStartController = loader.getController();
                Scene gameStartScene = new Scene(root);
                Stage window = (Stage)(this.stage.getScene().getWindow());
                window.setScene(gameStartScene);
                window.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
//            stage.setScene(new Scene(root));
//            stage.show();
        });
    }
}
