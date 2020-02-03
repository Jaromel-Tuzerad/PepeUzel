package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        int width = 350;
        int height = 200;

        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.setMinHeight(height+height/9);
        primaryStage.setMinWidth(width+width/9);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
