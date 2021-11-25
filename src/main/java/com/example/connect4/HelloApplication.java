package com.example.connect4;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GameBoard board = new GameBoard(7, 6);
        VBox root=new VBox();
        root.setSpacing(5);
        HBox statusBox=new HBox();
        statusBox.setSpacing(50);
        Label turn=new Label("Your Turn (◕ε◕)");
        turn.setFont(new Font(40));
        Label score=new Label("Score: 0");
        score.setFont(new Font(40));
        statusBox.getChildren().addAll(turn,score);
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
        for (int i = 0; i < 7; i++) {
            VBox vBox = new VBox();
            vBox.setPadding(new Insets(20, 5, 5, 5));
            vBox.setSpacing(15);
            hBox.getChildren().add(vBox);
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
                int col = hBox.getChildren().indexOf(vBox);
                if (!board.isColumnHasSpace(col)) {
                    shakeStage(stage);
                } else {
                    board.addChip(new Piece(Piece.PieceType.RED), col);
                    updateBoard(board,vBox,col);
                    score.setText("Score: "+String.valueOf(board.getBoardScore()));
                    turn.setText("NOT YOUR TURN (◣_◢)");
                    //call ai play
                    //updateBoard(board,vBox,col);
                    turn.setText("YOUR TURN (◕ε◕)");
                    score.setText("Score: "+String.valueOf(board.getBoardScore()));
                }
            });
        }
        root.getChildren().addAll(statusBox,hBox);
        Scene scene = new Scene(root, 800, 760);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private void updateBoard(GameBoard board,VBox vBox,int col) {

        for (int r = 0; r < board.getBoard().get(col).size(); r++) {
            if (board.getBoard().get(col).get(r).equals(new Piece(Piece.PieceType.RED))) {
                vBox.getChildren().get(vBox.getChildren().size() - 1 - r).setStyle("-fx-fill: red;");
            } else if (board.getBoard().get(col).get(r).equals(new Piece(Piece.PieceType.YELLOW))) {
                vBox.getChildren().get(vBox.getChildren().size() - 1 - r).setStyle("-fx-fill: yellow;");
            }
        }
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