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
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.java.model.GameLogic;
import main.java.object.GameSetting;
import main.java.object.Point;
import main.java.object.ThreadResult;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameProcessVisualisationController implements Initializable {
    public static Group group = new Group();
    public GameSetting gameSetting;
    GameLogic gameLogic;
    Stage stage;

    public void initData(GameSetting gameSetting, Stage stage) {
        this.stage = stage;
        this.gameSetting = gameSetting;
    }

    public void startGame() {
        group = new Group();
        Scene scene = new Scene(group, 700, 700, Color.WHITE);
        stage.setScene(scene);
        stage.setMaximized(true);
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

    public void showResult(List<ThreadResult> threadResult) {
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
        Sphere sphere = new Sphere();
        sphere.setRadius(5);
        sphere.setTranslateX((point.getX() / 1000 * 1920));
        sphere.setTranslateY((point.getY() / 1000 * 1080));
        sphere.setCullFace(CullFace.FRONT);
        Platform.runLater(() -> {
            group.getChildren().add(sphere);
        });
    }

    public void drawLine(Point point1, Point point2, Color color) {
        double point1_X = point1.getX() / 1000 * 1920;
        double point1_Y = point1.getY() / 1000 * 1080;
        double point2_X = point2.getX() / 1000 * 1920;
        double point2_Y = point2.getY() / 1000 * 1080;
        Line line = new Line(point1_X, point1_Y, point1_X, point1_Y);
        line.setStroke(color.darker());
        line.setStrokeWidth(1);
        Platform.runLater(() -> {
            group.getChildren().add(line);
            Timeline timeline = new Timeline(
                    new KeyFrame(
                            Duration.seconds(1),
                            new KeyValue(
                                    line.endXProperty(),
                                    point2_X,
                                    Interpolator.LINEAR
                            ),
                            new KeyValue(
                                    line.endYProperty(),
                                    point2_Y,
                                    Interpolator.LINEAR
                            )
                    )
            );
            timeline.play();
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
