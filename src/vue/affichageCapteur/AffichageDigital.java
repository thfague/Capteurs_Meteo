package vue.affichageCapteur;

import class_Metier.capteur.Capteur;
import class_Metier.capteur.CapteurAbstrait;
import class_Metier.capteur.CapteurComplexe;
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

    //Méthode appelée avant initialise()
    public AffichageDigital(CapteurAbstrait c) {
        capteur = c;
    }

    @FXML
    private void initialize() {
        nomCapteur.textProperty().bind(capteur.nomProperty());
        nomCapteur.setFont(new Font("Arial", 18));
        if (capteur instanceof Capteur) {
            //valeurCapteur.textProperty().bindBidirectional(capteur.valeurProperty(), new NumberStringConverter());
            valeurCapteur.textProperty().bind(capteur.valeurProperty().asString());
        }
        if (capteur instanceof CapteurComplexe) {
            valeurCapteur.textProperty().bind(capteur.valeurProperty().asString());
        }
    }
}
