package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class JavaFXApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root= FXMLLoader.load(getClass().getResource("AppLayout.fxml"));
        Scene scene=new Scene(root,1096,400);
        scene.getStylesheets().add(getClass().getResource("styles.css").toString());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
