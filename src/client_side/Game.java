package client_side;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server_side.ServerClient;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class Game extends Application{
    private Pane root = new Pane();
    private static Image rock = new Image("rock.jpg");
    private static Image paper = new Image("paper.jpg");
    private static Image scissors = new Image("scissors.jpg");

    private static Hand currentHand = new Hand();
    private Hand opponentHand = new Hand();

    private static Button btn1;
    private static Button btn2;
    private static Button btn3;

    String ready = "";
    public static Text timeText;

    @Override
    public void start(Stage primaryStage) throws Exception {

        ConnectingClient client = new ConnectingClient();

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        currentHand.setScaleY(-1);
        currentHand.setRotate(180);
        currentHand.setTranslateX(100);
        currentHand.setTranslateY(350);
        root.getChildren().add(currentHand);

        opponentHand.setTranslateX(500);
        opponentHand.setTranslateY(50);
        root.getChildren().add(opponentHand);

        btn1 = new Button("Rock");
        btn2 = new Button("Paper");
        btn3 = new Button("Scissors");
        Button readyBtn = new Button("Ready!");

        timeText = new Text("Time:");

        btn1.setPrefWidth(200);
        btn1.setPrefHeight(50);
        btn1.setLayoutX(30);
        btn1.setLayoutY(550);
        btn1.setOnMouseClicked(event -> {
            currentHand.setHandImage(rock);
        });

        btn2.setPrefWidth(200);
        btn2.setPrefHeight(50);
        btn2.setLayoutX(296);
        btn2.setLayoutY(550);
        btn2.setOnMouseClicked(event -> {
            currentHand.setHandImage(paper);
        });

        btn3.setPrefWidth(200);
        btn3.setPrefHeight(50);
        btn3.setLayoutX(563);
        btn3.setLayoutY(550);
        btn3.setOnMouseClicked(event -> {
            currentHand.setHandImage(scissors);
        });

        readyBtn.setPrefWidth(200);
        readyBtn.setPrefHeight(50);
        readyBtn.setOnMouseClicked(event -> {
            ready = "ready";
            client.readyUp(ready);
            readyBtn.setDisable(true);
        });


        timeText.setTranslateY(70);

        root.getChildren().addAll(btn1,btn2,btn3, readyBtn, timeText);



    }

    private void updateImageHand(Hand currentHand) {
        root.getChildren().add(currentHand);
    }

    public static String getCurrentHand() {
        if (currentHand.getTypeOfHandImage().equals(rock)){
            return "HAND:rock";
        }

        else if (currentHand.getTypeOfHandImage().equals(paper)){
            return "HAND:paper";
        }

        else if (currentHand.getTypeOfHandImage().equals(scissors)){
            return "HAND:scissors";
        }
        else {
            return "";
        }
    }

    public static void disableButtons(){
        btn1.setDisable(true);
        btn2.setDisable(true);
        btn3.setDisable(true);
    }
}
