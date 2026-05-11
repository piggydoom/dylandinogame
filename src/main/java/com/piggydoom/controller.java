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
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class controller {

    @FXML
    public Pane pane;
    @FXML
    public Canvas canvas;
    @FXML
    public ImageView dylanIV;

    Random random = new Random();
    GraphicsContext ctx;
    int groundHeight = 32;
    int gameSpeed = 20;
    double dY = 0;
    Image dylanImg = new Image(getClass().getResource("/com/piggydoom/assets/dylan.png").toExternalForm());
    Image spriteSheet = new Image(getClass().getResource("/com/piggydoom/assets/sprite.png").toExternalForm());
    PixelReader spriteReader = spriteSheet.getPixelReader();
    double dImgW = dylanImg.getWidth() / 18;
    double dImgH = dylanImg.getHeight() / 18;
    double v = 0;
    double DGY;

    public void initialize() {
        ctx = canvas.getGraphicsContext2D();
        dylanIV.setImage(dylanImg);
        dylanIV.setFitWidth(dImgW);
        // StackPane.clearConstraints(dylanIV);
        canvas.heightProperty().bind(pane.heightProperty());
        canvas.widthProperty().bind(pane.widthProperty());
        timeline.setCycleCount(Animation.INDEFINITE);
        obstacleTimeline.setCycleCount(Animation.INDEFINITE);
        pane.setStyle("-fx-background-color: #cccccc;");
        javafx.application.Platform.runLater(() -> {
            DGY = canvas.getHeight() - groundHeight - dImgH;
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

    public class obstacle {
        public double x;
        public double type;
        public double width;

        public obstacle(double x, double type, double width) {
            this.x = x;
            this.type = type;
            this.width = width;
        }

        @Override
        public String toString() {
            return "obstacle{" +
                    "x=" + x +
                    ", type=" + type +
                    ", width=" + width +
                    '}';
        }
    }

    ObservableList<groundTex> groundTexList = FXCollections.observableArrayList();
    ObservableList<obstacle> obstacleList = FXCollections.observableArrayList();

    public void initGame() {
        ctx.setFill(Color.hsb(0, 0, 0.6));
        ctx.fillRect(0, canvas.getHeight() - groundHeight, canvas.getWidth(), groundHeight);
        dylanIV.setX(150);
        dylanIV.setY(DGY);
        dY = DGY;
        timeline.play();
        obstacleTimeline.play();
    }

    public void jump() {
        if (dY >= DGY) {
            v = -10;
        }
    }

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(gameSpeed), event -> {

        for (groundTex gt : groundTexList) {
            ctx.setFill(Color.hsb(0, 0, 0.6));
            ctx.fillRect(gt.x, gt.y - 1, gt.w + 1, gt.h + 2);
            gt.x -= 10;
            ctx.setFill(Color.WHITE);
            ctx.fillRect(gt.x, gt.y, gt.w, gt.h);
        }

        if (random.nextInt(13) <= 1) {

            groundTexList.add(new groundTex(canvas.getWidth(),
                    ThreadLocalRandom.current().nextDouble(canvas.getHeight() - groundHeight, canvas.getHeight() - 1),
                    ThreadLocalRandom.current().nextDouble(6, 12),
                    ThreadLocalRandom.current().nextDouble(4, 6)));
        }
        groundTexList.removeIf(gt -> gt.x + gt.w <= 0);

        if (dY < DGY) {
            v += 0.75;
        }

        if ((dY += v) > DGY) {
            v = 0;
            dY = DGY;
        } else {
            dY += v;
        }
        dylanIV.setY(dY);
    }));

    Timeline obstacleTimeline = new Timeline(
            new KeyFrame(Duration.millis(gameSpeed * 2.5 + random.nextInt(1500)), event -> {

                if (random.nextInt(3) == 2) {

                    drawSprite("cactus");
                }

            }));

    public void drawSprite(String type) {
        ImageView spriteIV = new ImageView();
        pane.getChildren().add(spriteIV);
        int WiX = 0;
        int WiY = 0;
        int WiW = 0;
        int WiH = 0;
        switch (type) {
            case "cactus":
                WiX = 446;
                WiY = 0;
                WiW = 33;
                WiH = 71;
                break;
                
        }

        WritableImage sprite = new WritableImage(spriteReader, WiX, WiY, WiW, WiH);
                spriteIV.setImage(sprite);
                spriteIV.setX(canvas.getWidth());
                spriteIV.setY(canvas.getHeight() - groundHeight - WiH);

                Timeline obstacleMoveTimeline = new Timeline(new KeyFrame(Duration.millis(gameSpeed / 1.2), event -> {
                    spriteIV.setX(spriteIV.getX() - 10);

                    if (spriteIV.getX() <= 150 + dImgW) {
                        Rectangle hitbox = new Rectangle(spriteIV.getX(), canvas.getHeight() - groundHeight - 71, 33, 71);
                        Circle playerHitbox = new Circle(150, dY, dImgH / 2);
                        if (hitbox.getBoundsInParent().intersects(playerHitbox.getBoundsInParent())) {
                            System.out.print("collide");
                        }

                    }

                }

                ));
                obstacleMoveTimeline.setCycleCount(120);
                obstacleMoveTimeline.play();

    }
}
