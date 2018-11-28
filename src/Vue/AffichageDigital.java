package Vue;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AffichageDigital {

    @FXML
    GridPane gridDigit;

    @FXML
    public void initialize (float valeur){
        javafx.scene.control.TextField f = new TextField();
        gridDigit.add(f, 0,1);
        String s = Float.toString(valeur);
        f.setText(s);

    }
}
