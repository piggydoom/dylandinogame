package com.piggydoom;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javafx.util.Duration;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

import javafx.scene.paint.Color;

public class controller {
    @FXML
    public StackPane stackPane;
    @FXML
    public Canvas canvas;

    Random random = new Random();
    GraphicsContext ctx;
    int groundHeight = 32;
    int gameSpeed = 50;
    double dY = groundHeight;
    Image dylanImg = new Image(getClass().getResource("/com/piggydoom/assets/dylan.png").toExternalForm());
    double dImgW = dylanImg.getWidth() / 18;
    double dImgH = dylanImg.getHeight() / 18;

    public void initialize() {
        ctx = canvas.getGraphicsContext2D();
        canvas.heightProperty().bind(stackPane.heightProperty());
        canvas.widthProperty().bind(stackPane.widthProperty());
        timeline.setCycleCount(Animation.INDEFINITE);
        javafx.application.Platform.runLater(() -> {
            initGame();
        });
    }

    public class groundTex {
        public double x;
        public double y;
        public double w;
        public double h;

        public groundTex(double x, double y, double w, double h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        @Override
        public String toString() {
            return "groundTex{" +
                    "x=" + x +
                    ", y=" + y +
                    ", w=" + w +
                    ", h=" + h +
                    '}';
        }
    }

    ObservableList<groundTex> groundTexList = FXCollections.observableArrayList();

    public void initGame() {
        ctx.setFill(Color.hsb(0, 0, 0.6));
        ctx.fillRect(0, canvas.getHeight() - groundHeight, canvas.getWidth(), groundHeight);
        ctx.drawImage(dylanImg, 150, canvas.getHeight() - groundHeight - dImgH, dImgW, dImgH);
        timeline.play();
    }

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(gameSpeed), event -> {
            if (random.nextInt(25) <= 1) {
                
                groundTexList.add(new groundTex(canvas.getWidth(), ThreadLocalRandom.current().nextDouble(5, 10), 10.0, 10.0));
            }
            System.out.println(groundTexList);

        }));

}
