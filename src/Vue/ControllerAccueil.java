package Vue;

import Class_Metier.Capteur;
import Class_Metier.CapteurAbstrait;
import Class_Metier.CapteurComplexe;
import com.sun.istack.internal.localization.NullLocalizable;
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
import java.util.*;

import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.util.Callback;

public class ControllerAccueil {
    @FXML
    GridPane gridAccueil;

    Text textCapteur = new Text();
    List<CapteurAbstrait> l = new ArrayList<>();
    GridPane gridAffich = new GridPane();
    GenerationValeur g = new GenerationValeur();

    @FXML
    public void initialize (){
        l.add(new Capteur(30, "Capteur 1", 1000));
        l.add(new Capteur(10, "Capteur 2", 2000));
        l.add(new Capteur(2, "Capteur 3", 3000));
        Map<CapteurAbstrait,Integer> m = new HashMap<>();
        CapteurComplexe capteurComplexe = new CapteurComplexe(m,"CapteurComplexe 1");
        capteurComplexe.ajoutCapteur(l.get(0), 1);
        capteurComplexe.ajoutCapteur(l.get(1), 2);
        capteurComplexe.ajoutCapteur(l.get(2), 3);
        l.add(capteurComplexe);

        ObservableList<CapteurAbstrait> olCapteur = FXCollections.observableList(l);
        ListView<CapteurAbstrait> listCapteur = new ListView<>(olCapteur);
        //ListProperty<CapteurAbstrait> lp = new SimpleListProperty<>(olCapteur);
        //listCapteur.itemsProperty().bind(lp);

        listCapteur.setCellFactory(new Callback<ListView<CapteurAbstrait>, ListCell<CapteurAbstrait>>() {
            @Override
            public ListCell<CapteurAbstrait> call(ListView<CapteurAbstrait> param) {
                return new DigitalFormatCell(l);
            }
        });

        listCapteur.getSelectionModel().selectedItemProperty().addListener((l,oV,nV)->{
            textCapteur.setText(nV.getNom());
            AffichageBouton(nV, "Affichage digital", "affichageDigital.fxml", "Affichage format digital", 1);
            AffichageBouton(nV, "Affichage par thermomètre", "affichageThermo.fxml", "Affichage format thermomètre", 2);
            AffichageBouton(nV, "Affichage imagé", "affichageImgMeteo.fxml", "Affichage format image", 3);
            if(nV instanceof CapteurComplexe) {
                AffichageBouton(nV, "Configuration", "affichageConfig.fxml", "Configuration Capteur Complexe", 5);
            }
            else {
                gridAffich.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 5);
            }
            AffichageBoutonSupprimer(nV, olCapteur, 4);
        });

        Font font = new Font("Arial",18);
        textCapteur.setFont(font);

        gridAccueil.add(gridAffich, 1, 0);
        gridAccueil.add(listCapteur, 0, 0);
        gridAffich.add(textCapteur,0,0);
        gridAccueil.setHalignment(textCapteur, HPos.CENTER);
    }

    //Création et ajout d'un bouton pour un type d'affichage
    public void AffichageBouton(CapteurAbstrait d, String nomButton, String nomFichier, String titreFichier, Integer line) {
        Button b = new Button(nomButton);
        gridAffich.add(b, 0, line);
        b.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                NewWindow(nomFichier, titreFichier, d);
            }
        });
    }

    public void AffichageBoutonSupprimer(CapteurAbstrait c, ObservableList<CapteurAbstrait> l, Integer line) {
        Button b = new Button("Supprimer");
        gridAffich.add(b, 0, line);
        b.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                l.remove(c);
                Integer i;
                for (i=0; i < l.size(); i++) {
                    if(l.get(i) instanceof CapteurComplexe){
                        if(((CapteurComplexe) l.get(i)).getListeCapteur().containsKey(c)){
                            ((CapteurComplexe) l.get(i)).getListeCapteur().remove(c);
                        }
                    }
                }
            }
        });
    }

    public void NewWindow(String nameFile, String title, CapteurAbstrait d)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nameFile));

            if(nameFile.equals("affichageDigital.fxml")) {
                loader.setController(new AffichageDigital(d));
            }
            if(nameFile.equals("affichageThermo.fxml")) {
               // loader.setController(new AffichageThermo(d));
            }
            if(nameFile.equals("affichageImgMeteo.fxml")) {
                loader.setController(new AffichageImgMeteo(d));
            }
            if(nameFile.equals("affichageConfig.fxml")) {
                loader.setController(new AffichageConfig((CapteurComplexe)d));
            }
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initOwner(gridAccueil.getScene().getWindow());
            stage.setTitle(title);
            stage.setScene(new Scene(root, 500, 400));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
