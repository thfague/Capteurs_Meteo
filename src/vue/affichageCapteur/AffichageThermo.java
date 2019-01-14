package vue.affichageCapteur;

import class_Metier.capteur.CapteurAbstrait;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;

public class AffichageThermo {
    private CapteurAbstrait capteur;

    @FXML
    private Slider thermoSlider;

    public AffichageThermo(CapteurAbstrait c) { capteur=c; }

    @FXML
    private void initialize(){
        thermoSlider.valueProperty().bind(capteur.valeurProperty());
    }
}
