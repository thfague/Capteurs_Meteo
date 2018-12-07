package Vue;

import Class_Metier.Digital;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Observable;
import java.util.Observer;

public class AffichageDigital implements Observer {
    @FXML
    private Text NomCapteur;
    private Digital dig;
    @FXML
    private TextField ValeurCapteur;

    @FXML
    public void setDig(Digital d) {
        dig = d;
        ValeurCapteur.setText(dig.valeurProperty().toString());
      //  ValeurCapteur.textProperty().bind(dig.valeurProperty().asString());
        NomCapteur.textProperty().bind(d.nomProperty());
        Font font = new Font("Arial",18);
        NomCapteur.setFont(font);
    }

    @Override
    public void update(Observable o, Object arg) {

        System.out.println(dig.getNom() + " vaut " + ((Float) arg).floatValue() + " degré");
    }
}
