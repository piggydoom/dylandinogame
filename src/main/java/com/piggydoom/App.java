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
        Parent root = loadFXML("primary");
        scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));

        // 2. Load the scene graph

        // 3. Get the controller instance
        Controller controller = loader.getController();

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                    controller.jump();
                    break;
            }
        });
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}