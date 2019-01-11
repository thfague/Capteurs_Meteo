package Vue.AffichageCapteur;

import Class_Metier.Capteur.CapteurAbstrait;
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
