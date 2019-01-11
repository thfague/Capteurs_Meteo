package Vue.AffichageCapteur;

import Class_Metier.Capteur.CapteurAbstrait;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;

public class AffichageImgMeteo {
    @FXML
    Text nomCapteur;
    @FXML
    ImageView idImg = new ImageView();
    private FloatProperty val;
    private CapteurAbstrait capteur;

    public AffichageImgMeteo(CapteurAbstrait c){
        capteur=c;
        val = new SimpleFloatProperty();
    }

    @FXML
    private void initialize(){
        Font font = new Font("Arial",18);
        nomCapteur.setFont(font);
        Image img1 = new Image("/cielbleu.jpeg");
        Image img2 = new Image("/cielneige.jpg");
        Image img3 = new Image("/cielnuage.jpg");
        nomCapteur.textProperty().bind(capteur.nomProperty());

        val.bind(capteur.valeurProperty());
        val.addListener(e-> {
            calculValeur(val, img2, img1, img3);
        });
        calculValeur(val, img2, img1, img3);
    }

    private void calculValeur(FloatProperty val, Image img2, Image img1, Image img3){
        if (val.get() < 0) {
            idImg.setImage(img2);
        } else if (val.get() > 22) {
            idImg.setImage(img1);
        } else {
            idImg.setImage(img3);
        }
    }

}
