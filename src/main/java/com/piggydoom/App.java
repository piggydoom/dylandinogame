package com.piggydoom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("primary.fxml"));
        Parent root = loader.load();
        controller controller = loader.getController();
        scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                    controller.jump();
                    break;
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

}