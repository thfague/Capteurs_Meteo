package Vue;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.swing.text.html.ImageView;

public class AffichageImgMeteo {
    @FXML
    Text NomCapteur;

    @FXML
    ImageView idImg;

    @FXML
    public void initialize (){
        NomCapteur.setText("");

    }
}
