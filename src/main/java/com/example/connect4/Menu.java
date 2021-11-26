package com.example.connect4;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        GameGUI gameGUI=new GameGUI();
        VBox root =new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        Button alphaBeta=new Button("Play with AlphaBeta");
        alphaBeta.setOnAction(e->{
            try {
                gameGUI.starter(stage,7,3,true,0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        Button withoutAlphaBeta=new Button("Play without AlphaBeta");
        withoutAlphaBeta.setOnAction(e->{
            try {
                gameGUI.starter(stage,7,3,false,0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        Button exit = new Button("Exit");
        exit.setOnAction(e->{
            System.exit(0);
        });
        root.getChildren().addAll(alphaBeta,withoutAlphaBeta,exit);
        Scene scene = new Scene(root, 200, 250);
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }
}
