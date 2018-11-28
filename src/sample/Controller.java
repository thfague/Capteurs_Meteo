package sample;

import Class_Metier.Digital;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.text.Text;
import javafx.event.EventHandler;

public class Controller {
    @FXML
    GridPane gridAccueil;
    Text textCapteur = new Text();

    @FXML
    public void initialize (){
        AjoutCapteur("Capteur 1", 20, 0);
        AjoutCapteur("Capteur 2", 6, 1);
        AjoutCapteur("Capteur 3", 25, 2);
        gridAccueil.add(textCapteur, 1, 0);
    }

    public void AjoutCapteur(String nom, float valeur, int row) {
        Digital d = new Digital(valeur);
        Button buttonCapteur = new Button();
        buttonCapteur.setText(nom);
        buttonCapteur.setId("capteur" + (row + 1));
        buttonCapteur.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ClickCapteur(buttonCapteur.getId(), valeur);
            }
        });
        gridAccueil.add(buttonCapteur, 0, row);
    }

    @FXML
    public void ClickCapteur(String id, float valeur) {
        textCapteur.setText(id);
        AffichageBouton(valeur);
    }

    //Création et ajout d'un bouton pour chaque type d'affichage
    public void AffichageBouton(float valeur) {
        Button buttonDigit = new Button("Affichage digital");
        gridAccueil.add(buttonDigit, 1, 1 );
        buttonDigit.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                OpenDigit(valeur);
            }
        });
        Button buttonThermo = new Button("Affichage par thermomètre");
        gridAccueil.add(buttonThermo, 1, 2 );
        buttonThermo.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                OpenThermo(valeur);
            }
        });
        Button buttonImg = new Button("Affichage imagé");
        gridAccueil.add(buttonImg, 1, 3 );
        buttonImg.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                OpenImg(valeur);
            }
        });
    }

    public void OpenDigit(float valeur) {
        NewWindow("affichageDigital.fxml", "Affichage format digital", valeur);
    }

    public void OpenThermo(float valeur) {
        NewWindow("affichageThermo.fxml", "Affichage format thermomètre", valeur);
    }

    public void OpenImg(float valeur) {
        NewWindow("affichageImgMeteo.fxml", "Affichage format image", valeur);
    }

    public void NewWindow(String nameFile, String title, float valeur)
    {
        try {

            Parent root = FXMLLoader.load(getClass().getResource(nameFile));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 400, 275));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
