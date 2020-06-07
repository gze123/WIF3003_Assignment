package main.java.controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.java.model.GameLogic;
import main.java.object.GameSetting;
import main.java.object.Point;

import java.net.URL;
import java.util.ResourceBundle;

public class GameProcessVisualisationController implements Initializable {
    public static Group group = new Group();
    public GameSetting gameSetting;
    GameLogic gameLogic;
//    public GameLogic gameLogic;
//    SettingModel settingModel = new SettingModel();
    Stage stage;

    public void initData(GameSetting gameSetting, Stage stage) {
        this.stage = stage;
        this.gameSetting = gameSetting;
    }

    public void startGame(){
        group = new Group();
        Scene scene = new Scene(group,700,700, Color.WHITE);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.centerOnScreen();
//        stage.setResizable(false);
        GameLogic gameLogic = new GameLogic();

        new Thread(
                () -> {
                    gameLogic.initGame(this,gameSetting);
                }
        ).start();
    }

    public void drawPoints(Point point){
//        System.out.println(point.getX());
        Sphere sphere = new Sphere();
        sphere.setRadius(5);
        sphere.setTranslateX((point.getX()/1000*1920));
        sphere.setTranslateY((point.getY()/1000*1080));
        sphere.setCullFace(CullFace.FRONT);
        Glow glow = new Glow();
        glow.setLevel(0.9);
        sphere.setEffect(glow);
        Platform.runLater(() -> {
            group.getChildren().add(sphere);
        });
    }

    public void drawLine(Point point1,Point point2, Color color) {
        Line line = new Line();
        line.setStartX((point1.getX()/1000*1920));
        line.setEndX((point1.getX()/1000*1920));
        line.setStartY((point1.getY()/1000*1080));
        line.setEndY((point1.getY()/1000*1080));
        line.setStroke(color);
        Glow glow = new Glow();
        glow.setLevel(0.9);
        line.setEffect(glow);
        line.setStrokeWidth(2);
        Platform.runLater(() -> {
            group.getChildren().add(line);
            Timeline timeline = new Timeline(
                    new KeyFrame(
                            Duration.seconds(1),
                            new KeyValue(
                                    line.endXProperty(),
                                    (point2.getX()/1000*1920),
                                    Interpolator.LINEAR
                            ),
                            new KeyValue(
                                    line.endYProperty(),
                                    (point2.getY()/1000*1080),
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
