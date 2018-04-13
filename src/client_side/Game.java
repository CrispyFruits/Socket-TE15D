package client_side;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        ConnectingClient client = new ConnectingClient();

        Group root = new Group();
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();


        scene.setOnKeyPressed(event -> {
            String key = event.getCode().toString();
            client.pressKey(key);
        }
        );



    }

}
