package main.java.controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.java.EdgeWorker;
import main.java.model.GameLogic;
import main.java.object.GameSetting;
import main.java.object.Point;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameProcessVisualisationController implements Initializable {
    public static Group group = new Group();
    public GameSetting gameSetting;
    Stage stage;

    //scale to 75% of the screen
    Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    private double screenHeight = screensize.getHeight() * 75 /100;
    private double screenWidth = screensize.getWidth() *75 /100;

    public void initData(GameSetting gameSetting, Stage stage) {
        this.stage = stage;
        this.gameSetting = gameSetting;
    }

    public void startGame() {
        group = new Group();
        Scene scene = new Scene(group, screenWidth, screenHeight, Color.WHITE);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        GameLogic gameLogic = new GameLogic();

        new Thread(
                () -> {
                    try {
                        gameLogic.initGame(this, gameSetting);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }

    public void showResult(List<EdgeWorker> threadResult) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/GameResult.fxml"));
                Parent gameResultParent = loader.load();
                GameResultController gameResultController = loader.getController();
                gameResultController.printResult(threadResult, (Stage) stage.getScene().getWindow());
                Scene gameResultScene= new Scene(gameResultParent);
                Stage window = (Stage)(this.stage.getScene().getWindow());
                window.setScene(gameResultScene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void drawPoints(Point point) {
        Circle circle = new Circle(point.getX()/1000 *screenWidth, point.getY()/1000*screenHeight,3);
        Platform.runLater(() -> {
            group.getChildren().add(circle);
        });
    }

    public void drawLine(Point point1, Point point2, Color color) {
        double point1_X = point1.getX() / 1000 * screenWidth;
        double point1_Y = point1.getY() / 1000 * screenHeight;
        double point2_X = point2.getX() / 1000 * screenWidth;
        double point2_Y = point2.getY() / 1000 * screenHeight;
        Line line = new Line(point1_X, point1_Y, point1_X, point1_Y);
        line.setStroke(color.darker());
        line.setStrokeWidth(2);
        Platform.runLater(() -> {
            group.getChildren().add(line);
            Timeline timeline = new Timeline();
            KeyValue kv1 = new KeyValue(line.endXProperty(),point2_X,Interpolator.LINEAR);
            KeyValue kv2 = new KeyValue(line.endYProperty(),point2_Y,Interpolator.LINEAR);
            KeyFrame kf = new KeyFrame(Duration.seconds(1),kv1,kv2);
            timeline.getKeyFrames().add(kf);
            timeline.play();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
