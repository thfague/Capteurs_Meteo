package Vue;

import Class_Metier.Digital;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;

public class AffichageImgMeteo {
    @FXML
    Text NomCapteur;

    private Digital dig;

    @FXML
    ImageView idImg;

    Image img1 = new Image("meteo/src/Vue/Ressources/cielbleu.jpeg");
    Image img2 = new Image("cielneige.jpg");
    Image img3 = new Image("cielnuage.jpg");

    @FXML
    public void setDig(Digital d) {
        dig = d;
        float val = d.getValeur();
        NomCapteur.textProperty().bind(d.nomProperty());
        if(val < 0) {
            idImg = new ImageView();
            idImg.setImage(img2);
        }
        else if(val > 22){
            idImg = new ImageView();
            idImg.setImage(img1);
        }
        else {
            idImg = new ImageView();
            idImg.setImage(img3);
        }

    }
}
