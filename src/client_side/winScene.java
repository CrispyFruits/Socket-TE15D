package client_side;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class winScene extends StackPane {

    winScene(){
        Text text = new Text("You won!");
        text.setFont(new Font(30));

        this.setAlignment(text, Pos.CENTER);

    }
}
