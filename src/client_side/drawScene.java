package client_side;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class drawScene extends StackPane {

    drawScene(){
        Text text = new Text("It's a draw");
        text.setFont(new Font(30));

        this.setAlignment(text, Pos.CENTER);

    }
}
