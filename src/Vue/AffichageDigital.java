package Vue;

import Class_Metier.Digital;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class AffichageDigital {
    @FXML
    Text NomCapteur;

    @FXML
    TextField ValeurCapteur;

    @FXML
    public void initialize (Digital d){
        NomCapteur.setText(d.getNom());
        ValeurCapteur.setText("d.getValeur()");
    }
}
