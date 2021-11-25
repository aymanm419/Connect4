package com.example.connect4;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GameBoard board = new GameBoard(7, 6);
        HBox root = new HBox();
        root.setSpacing(5);
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
        for (int i = 0; i < 7; i++) {
            VBox vBox = new VBox();
            vBox.setPadding(new Insets(50, 5, 5, 5));
            vBox.setSpacing(15);
            root.getChildren().add(vBox);
            for (int j = 0; j < 6; j++) {
                Circle circle = new Circle(50);
                circle.setStyle("-fx-fill: grey;");
                vBox.getChildren().add(circle);
            }
            vBox.setOnMouseEntered(e -> {
                vBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
            });
            vBox.setOnMouseExited(e -> {
                vBox.setBackground(new Background(new BackgroundFill(null, null, null)));
            });

            vBox.setOnMouseClicked(e -> {
                if (e.getButton() != MouseButton.PRIMARY)
                    return;
                int col = root.getChildren().indexOf(vBox);
                if (!board.isColumnHasSpace(col)) {
                    shakeStage(stage);
                } else {
                    board.addChip(new Piece(Piece.PieceType.RED), col);
                    for (int r = 0; r < board.getBoard().get(col).size(); r++) {
                        if (board.getBoard().get(col).get(r).equals(new Piece(Piece.PieceType.RED))) {
                            vBox.getChildren().get(vBox.getChildren().size() - 1 - r).setStyle("-fx-fill: red;");
                        } else if (board.getBoard().get(col).get(r).equals(new Piece(Piece.PieceType.YELLOW))) {
                            vBox.getChildren().get(vBox.getChildren().size() - 1 - r).setStyle("-fx-fill: yellow;");
                        }
                    }
                }

            });
        }
        Scene scene = new Scene(root, 800, 730);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    int x = 0;
    int y = 0;

    public void shakeStage(Stage primaryStage) {
        Timeline timelineX = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (x == 0) {
                    primaryStage.setX(primaryStage.getX() + 10);
                    x = 1;
                } else {
                    primaryStage.setX(primaryStage.getX() - 10);
                    x = 0;
                }
            }
        }));

        timelineX.setCycleCount(5);
        timelineX.setAutoReverse(false);
        timelineX.play();

        Timeline timelineY = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (y == 0) {
                    primaryStage.setY(primaryStage.getY() + 10);
                    y = 1;
                } else {
                    primaryStage.setY(primaryStage.getY() - 10);
                    y = 0;
                }
            }
        }));

        timelineY.setCycleCount(5);
        timelineY.setAutoReverse(false);
        timelineY.play();
    }
}