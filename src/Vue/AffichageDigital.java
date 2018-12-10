package Vue;

import Class_Metier.Digital;
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
    private Digital digital;

    public AffichageDigital(Digital d){
        digital=d;
    }

    @FXML
    private void initialize(){
        //ValeurCapteur.valueFactoryProperty().bind(digital.valeurProperty());
        //ValeurCapteur.getValueFactory().valueProperty().bind(digital.valeurProperty().asObject());
        ValeurCapteur.textProperty().bindBidirectional(digital.valeurProperty(), new NumberStringConverter());
        NomCapteur.textProperty().bind(digital.nomProperty());
        Font font = new Font("Arial",18);
        NomCapteur.setFont(font);

    }
}
