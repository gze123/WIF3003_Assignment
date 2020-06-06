import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class AcceptInputInterface extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label numberOfPoints = new Label("Number of Points");
        Label numberOfThread = new Label("Number of Thread");
        Label timeLimit = new Label("Time Limit");
        TextField tfNumberOfPoint = new TextField();
        TextField tfNumberOfThread = new TextField();
        TextField tfTimeLimit = new TextField();
        Button b = new Button("Start");

        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.addRow(0, numberOfPoints, tfNumberOfPoint);
        root.addRow(1, numberOfThread, tfNumberOfThread);
        root.addRow(2, timeLimit, tfTimeLimit);
        root.addRow(3, b);

        Alert alert = new Alert(Alert.AlertType.NONE);
        b.setOnAction(e -> {
         if (tfNumberOfPoint.getText().isEmpty() || tfNumberOfThread.getText().isEmpty() || tfTimeLimit.getText().isEmpty()) {
             System.out.println("Invalid input");
             alert.setAlertType(Alert.AlertType.ERROR);
             alert.setContentText("All field must be completed.");
             alert.show();
         }
         else {
             GridPane gridPane1 = new GridPane();
             Scene scene2 = new Scene(gridPane1, 1100, 1000);
             primaryStage.setScene(scene2);
             primaryStage.setTitle("Running");
             primaryStage.show();
             System.out.println("Run");
         }
        });

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Text Field Example");
        primaryStage.show();
    }
}
