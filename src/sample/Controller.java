package sample;

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
    GridPane gridAcceuil;

        //Liste des capteurs
        /*List<Capteur> listCapteur = new ArrayList<>();
        listCapteur.add(new Digital(20));
        listCapteur.add(new Digital(6));
        listCapteur.add(new Digital(25));*/
        //Création et ajout d'un bouton pour chaque capteur
        /*for (int i = 0; i<listCapteur.size(); i++) {
            Button buttonCapteur = new Button("Capteur " + (i + 1));
            buttonCapteur.onActionProperty();
            grid.add(buttonCapteur,0,i);
        }*/

    @FXML
    public void ClickCapteur1(ActionEvent actionEvent) {
        Text text = new Text("Capteur 1");
        gridAcceuil.add(text, 1, 0 );
        AffichageBouton();
    }

    @FXML
    public void ClickCapteur2(ActionEvent actionEvent) {
        Text text = new Text("Capteur 2");
        gridAcceuil.add(text, 1, 0 );
        AffichageBouton();
    }

    @FXML
    public void ClickCapteur3(ActionEvent actionEvent) {
        Text text = new Text("Capteur 3");
        gridAcceuil.add(text, 1, 0 );
        AffichageBouton();
    }

    //Création et ajout d'un bouton pour chaque type d'affichage
    public void AffichageBouton() {
        Button buttonDigit = new Button("Affichage digital");
        gridAcceuil.add(buttonDigit, 1, 1 );
        /*buttonDigit.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override public void handle(ActionEvent e) {
                                        NewWindow("digital.fxml", "Affichage format Digital");
                                    }
                                });*/
        Button buttonThermo = new Button("Affichage par thermomètre");
        gridAcceuil.add(buttonThermo, 1, 2 );
        Button buttonImg = new Button("Affichage imagé");
        gridAcceuil.add(buttonImg, 1, 3 );
    }

    public void OpenDigit() {

    }

    public void OpenThermo() {
        NewWindow("thermo.fxml", "Affichage format Thermomètre");
    }

    public void OpenImg() {
        NewWindow("imgMeteo.fxml", "Affichage format Image");
    }

    public void NewWindow(String nameFile, String title)
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
