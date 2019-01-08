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
    private Text NomCapteur;
    @FXML
    private TextField ValeurCapteur;
    private CapteurAbstrait digital;

    public AffichageDigital(CapteurAbstrait  d){
        digital=d;
    }

    @FXML
    private void initialize(){
        NomCapteur.textProperty().bind(digital.nomProperty());
        Font font = new Font("Arial", 18);
        NomCapteur.setFont(font);
        if (digital instanceof Capteur) {
            ValeurCapteur.textProperty().bindBidirectional(digital.valeurProperty(), new NumberStringConverter());
        }
        if (digital instanceof CapteurComplexe) {
            ValeurCapteur.textProperty().bind(digital.valeurProperty().asString());
        }
    }
}
