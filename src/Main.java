import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(Main.class);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Game Menu");
        Pane root = FXMLLoader.load(getClass().getResource("/main/resources/view/GameStart.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
