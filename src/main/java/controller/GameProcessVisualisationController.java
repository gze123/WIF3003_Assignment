package main.java.controller;

import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.stage.Stage;
import main.java.object.GameSetting;

import java.net.URL;
import java.util.ResourceBundle;

public class GameProcessVisualisationController implements Initializable {
    public static Group group = new Group();
//    SettingModel settingModel = new SettingModel();
    Stage stage;

    public void initData(GameSetting gameSetting) {
        System.out.println(gameSetting.getNumberOfPoint());
        //action start
        

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
