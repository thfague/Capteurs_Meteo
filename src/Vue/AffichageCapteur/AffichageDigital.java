package Vue.AffichageCapteur;

import Class_Metier.Capteur.Capteur;
import Class_Metier.Capteur.CapteurAbstrait;
import Class_Metier.Capteur.CapteurComplexe;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;


public class AffichageDigital {
    @FXML
    private Text nomCapteur;
    @FXML
    private TextField valeurCapteur;
    private CapteurAbstrait capteur;

    public AffichageDigital(CapteurAbstrait  c){
        capteur=c;
    }

    @FXML
    private void initialize(){
        nomCapteur.textProperty().bind(capteur.nomProperty());
        Font font = new Font("Arial", 18);
        nomCapteur.setFont(font);
        if (capteur instanceof Capteur) {
            valeurCapteur.textProperty().bindBidirectional(capteur.valeurProperty(), new NumberStringConverter());
        }
        if (capteur instanceof CapteurComplexe) {
            valeurCapteur.textProperty().bind(capteur.valeurProperty().asString());
        }
    }
}
