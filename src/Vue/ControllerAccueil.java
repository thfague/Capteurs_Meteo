package Vue;

import Class_Metier.Capteur;
import Class_Metier.Digital;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.util.Callback;


public class ControllerAccueil {
    @FXML
    GridPane gridAccueil;
    Text textCapteur = new Text();



    @FXML
    public void initialize (){
        List<Digital> l = new ArrayList<>();
        l.add(new Digital(20));
        l.add(new Digital(10));
        l.add(new Digital(14));

        ObservableList<Digital> olCapteur = FXCollections.observableList(l);
        ListView<Digital> listCapteur = new ListView<>(olCapteur);
        ListProperty<Digital> lp = new SimpleListProperty<>(olCapteur);
        listCapteur.itemsProperty().bind(lp);

        /*******************************Ce que j'ai fais (en gros)********************************/
        listCapteur.setCellFactory(new Callback<ListView<Digital>, ListCell<Digital>>() {
            @Override
            public ListCell<Digital> call(ListView<Digital> param) {
                return new DigitalFormatCell();
            }
        });

        //Faire une classe à part pour ce bouton je pense(j'ai pas eut le temps)
        Button ajoutCapteurButton = new Button("Ajouter un capteur");
        ajoutCapteurButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Random r = new Random();
                float random = 10 + r.nextFloat() * (30 - 10);
                l.add(new Digital(random));
            }
        });

        gridAccueil.add(ajoutCapteurButton,0,1);
        gridAccueil.add(listCapteur, 0, 0);
        /***********************************************************************/
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
        gridAccueil.add(buttonCapteur, 0, 1);
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
