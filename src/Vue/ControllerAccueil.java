package Vue;

import Class_Metier.Capteur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.util.Callback;

public class ControllerAccueil {
    @FXML
    GridPane gridAccueil;

    Text textCapteur = new Text();
    List<Capteur> l = new ArrayList<>();
    GridPane gridAffich = new GridPane();
    GenerationValeur g = new GenerationValeur();
    @FXML
    public void initialize (){

        l.add(new Capteur(g.valAleatoireBorne(10,30), "Capteur 1"));
        l.add(new Capteur(g.valAleatoireInfini(), "Capteur 2"));
        l.add(new Capteur(g.valAleatoireReelle(20,2), "Capteur 3"));

        ObservableList<Capteur> olCapteur = FXCollections.observableList(l);
        ListView<Capteur> listCapteur = new ListView<>(olCapteur);
        //ListProperty<Digital> lp = new SimpleListProperty<>(olCapteur);
        //listCapteur.itemsProperty().bind(lp);

        listCapteur.setCellFactory(new Callback<ListView<Capteur>, ListCell<Capteur>>() {
            @Override
            public ListCell<Capteur> call(ListView<Capteur> param) {
                return new DigitalFormatCell(l);
            }
        });

        listCapteur.getSelectionModel().selectedItemProperty().addListener((l,oV,nV)->{
            textCapteur.setText(nV.getNom());
            AffichageBouton(nV);
        });

        //Faire une classe à part pour ce bouton je pense(j'ai pas eut le temps)
        /*Button ajoutCapteurButton = new Button("Ajouter un capteur");
        ajoutCapteurButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Random r = new Random();
                float random = 10 + r.nextFloat() * (30 - 10);
                l.add(new Digital(random, "?"));
            }
        });
*/
        Font font = new Font("Arial",18);
        textCapteur.setFont(font);

        gridAccueil.add(gridAffich, 1, 0);
        gridAccueil.add(listCapteur, 0, 0);
        gridAffich.add(textCapteur,0,0);
        gridAccueil.setHalignment(textCapteur, HPos.CENTER);

        //gridAccueil.add(ajoutCapteurButton,0,1);
    }

    //Création et ajout d'un bouton pour chaque type d'affichage
    public void AffichageBouton(Capteur d) {
        Button buttonDigit = new Button("Affichage digital");
        gridAffich.add(buttonDigit, 0, 1 );
        buttonDigit.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                NewWindow("affichageDigital.fxml", "Affichage format digital", d);
            }
        });
        Button buttonThermo = new Button("Affichage par thermomètre");
        gridAffich.add(buttonThermo, 0, 2 );
        buttonThermo.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                NewWindow("affichageThermo.fxml", "Affichage format thermomètre", d);
            }
        });
        Button buttonImg = new Button("Affichage imagé");
        gridAffich.add(buttonImg, 0, 3 );
        buttonImg.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                NewWindow("affichageImgMeteo.fxml", "Affichage format image", d);
            }
        });
    }


    public void NewWindow(String nameFile, String title, Capteur d)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nameFile));

            if(nameFile.equals("affichageDigital.fxml")) {
                loader.setController(new AffichageDigital(d));
                new Thread(d).start();
            }
            if(nameFile.equals("affichageThermo.fxml")) {
               // loader.setController(new AffichageThermo(d));
            }
            if(nameFile.equals("affichageImgMeteo.fxml")) {
                loader.setController(new AffichageImgMeteo(d));
            }
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initOwner(gridAccueil.getScene().getWindow());
            stage.setTitle(title);
            stage.setScene(new Scene(root, 400, 287));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
