import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // We will link this to your MovieBookingSystem logic
        MovieBookingSystem system = new MovieBookingSystem();
        Scene scene = new Scene(system.createView(), 1200, 800);

        primaryStage.setTitle("Cinema Management System 2025");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}