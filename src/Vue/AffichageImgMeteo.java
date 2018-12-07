package Vue;

import Class_Metier.Digital;
import javafx.beans.property.FloatProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;

public class AffichageImgMeteo {
    @FXML
    Text NomCapteur;

    private Digital dig;

    @FXML
    ImageView idImg = new ImageView();

    public void setImg(Digital d) {
        dig = d;
        Font font = new Font("Arial",18);
        NomCapteur.setFont(font);
        Image img1 = new Image("/cielbleu.jpeg");
        Image img2 = new Image("/cielneige.jpg");
        Image img3 = new Image("/cielnuage.jpg");
        NomCapteur.textProperty().bind(d.nomProperty());

       // val.textProperty().bind(AffichageDigital.d.valeurProperty.textProperty());

        float val = d.getValeur();
        if(val < 0) {
            idImg.setImage(img2);
        }
        else if(val > 22){
            idImg.setImage(img1);
        }
        else {
            idImg.setImage(img3);
        }

    }
}
