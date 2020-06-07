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

//        Label numberOfPoints = new Label("Number of Points");
//        Label numberOfThread = new Label("Number of Thread");
//        Label timeLimit = new Label("Time Limit");
//        TextField tfNumberOfPoint = new TextField();
//        TextField tfNumberOfThread = new TextField();
//        TextField tfTimeLimit = new TextField();
//        Button b = new Button("Start");
//
//        GridPane root = new GridPane();
//        root.setHgap(10);
//        root.setVgap(10);
//        root.setPadding(new Insets(10, 10, 10, 10));
//        root.addRow(0, numberOfPoints, tfNumberOfPoint);
//        root.addRow(1, numberOfThread, tfNumberOfThread);
//        root.addRow(2, timeLimit, tfTimeLimit);
//        root.addRow(3, b);
//
//        Alert alert = new Alert(Alert.AlertType.NONE);
//        b.setOnAction(e -> {
//         if (tfNumberOfPoint.getText().isEmpty() || tfNumberOfThread.getText().isEmpty() || tfTimeLimit.getText().isEmpty()) {
//             System.out.println("Invalid input");
//             alert.setAlertType(Alert.AlertType.ERROR);
//             alert.setContentText("All field must be completed.");
//             alert.show();
//         }
//         else {
//             GridPane gridPane1 = new GridPane();
//             Scene scene2 = new Scene(gridPane1, 1100, 1000);
//             primaryStage.setScene(scene2);
//             primaryStage.setTitle("Running");
//             primaryStage.show();
//             System.out.println("Run");
//         }
//        });
//
//        Scene scene = new Scene(root, 600, 500);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Text Field Example");
//        primaryStage.show();
    }
}
