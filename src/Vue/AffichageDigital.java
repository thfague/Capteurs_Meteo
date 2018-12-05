package Vue;

import Class_Metier.Digital;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class AffichageDigital {
    @FXML
    private Text NomCapteur;
    private Digital dig;
    @FXML
    private TextField ValeurCapteur;

    @FXML
    public void setDig(Digital d) {
        dig = d;
        ValeurCapteur.textProperty().bind((d.valeurProperty().asString()));
        NomCapteur.textProperty().bind(d.nomProperty());
    }
}
