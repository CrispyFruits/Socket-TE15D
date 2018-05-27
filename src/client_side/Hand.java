package client_side;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Hand extends Rectangle{
    private Image typeOfHandImage;

    public Hand(){
        this.setFill(new ImagePattern(new Image("question_mark.jpg")));
        this.setHeight(150);
        this.setWidth(200);

    }

    public void setHandImage(Image typeOfHandImage){
        this.typeOfHandImage = typeOfHandImage;
        this.setFill(new ImagePattern(typeOfHandImage));
    }

    public Image getTypeOfHandImage() {
        return typeOfHandImage;
    }
}
