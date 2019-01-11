package Vue;

import Class_Metier.Capteur.Capteur;
import Class_Metier.Capteur.CapteurAbstrait;
import Class_Metier.Capteur.CapteurComplexe;
import Class_Metier.Generateur.GenerationAleatoireBorne;
import Class_Metier.Generateur.GenerationAleatoireInfini;
import Class_Metier.Generateur.GenerationAleatoireReelle;
import Class_Metier.Generateur.GenerationValeurAbstrait;
import Vue.AffichageCapteur.AffichageDigital;
import Vue.AffichageCapteur.AffichageImgMeteo;
import Vue.AffichageCapteur.AffichageThermo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.util.Callback;

public class ControllerAccueil {
    @FXML
    private GridPane gridAccueil;

    private Text textCapteur = new Text();
    private List<CapteurAbstrait> listeCapteur = new ArrayList<>();
    private VBox vb = new VBox();
    private ObservableList<CapteurAbstrait> olCapteur;

    @FXML
    public void initialize (){
        creationCapteur();

        olCapteur = FXCollections.observableList(listeCapteur);
        ListView<CapteurAbstrait> listeVCapteur = new ListView<>(olCapteur);

        listeVCapteur.setCellFactory(new Callback<ListView<CapteurAbstrait>, ListCell<CapteurAbstrait>>() {
            @Override
            public ListCell<CapteurAbstrait> call(ListView<CapteurAbstrait> param) {
                return new DigitalFormatCell(listeCapteur);
            }
        });

        listeVCapteur.getSelectionModel().selectedItemProperty().addListener((l,oV,nV)->{
            textCapteur.setText(nV.getNom());
            vb.getChildren().clear();

            affichageBouton(nV, "Affichage digital", "AffichageCapteur/affichageDigital.fxml", "Affichage format digital");
            affichageBouton(nV, "Affichage par thermomètre", "AffichageCapteur/affichageThermo.fxml", "Affichage format thermomètre");
            affichageBouton(nV, "Affichage imagé", "AffichageCapteur/affichageImgMeteo.fxml", "Affichage format image");
            if(nV instanceof CapteurComplexe) {
                affichageBouton(nV, "Configuration", "affichageConfig.fxml", "Configuration Capteur Complexe");
            }
            affichageBoutonSupprimer(nV, olCapteur);
        });

        Button ajoutCapteur = new Button("Ajouter un capteur");
        ajoutCapteur.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ajoutCapteur.fxml"));
                    loader.setController(new AjoutCapteur(olCapteur));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.initOwner(gridAccueil.getScene().getWindow());
                    stage.setTitle(ajoutCapteur.getText());
                    stage.setScene(new Scene(root, 500, 400));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        gridAccueil.add(listeVCapteur, 0, 1);
        gridAccueil.add(ajoutCapteur, 0, 0);
        gridAccueil.add(textCapteur, 1, 0);
        gridAccueil.add(vb, 1, 1);

        vb.setAlignment(Pos.TOP_CENTER);
        gridAccueil.setHalignment(textCapteur, HPos.CENTER);
        gridAccueil.setHalignment(ajoutCapteur, HPos.CENTER);
        Font font = new Font("Arial",18);
        textCapteur.setFont(font);
    }

    private void creationCapteur() {
        GenerationValeurAbstrait g1;
        GenerationValeurAbstrait g2;
        GenerationValeurAbstrait g3;
        listeCapteur.add(new Capteur(30, "Capteur 1", 1000, g1 = new GenerationAleatoireBorne(10,20)));
        listeCapteur.add(new Capteur(10, "Capteur 2", 2000, g2 = new GenerationAleatoireInfini()));
        listeCapteur.add(new Capteur(2, "Capteur 3", 3000, g3 = new GenerationAleatoireReelle(0, 2)));
        Map<CapteurAbstrait,Integer> mapCapteur = new HashMap<>();
        CapteurComplexe capteurComplexe = new CapteurComplexe(mapCapteur,"CapteurComplexe 1");
        capteurComplexe.ajoutCapteur(listeCapteur.get(0), 1);
        capteurComplexe.ajoutCapteur(listeCapteur.get(1), 2);
        capteurComplexe.ajoutCapteur(listeCapteur.get(2), 3);
        listeCapteur.add(capteurComplexe);

    }

    //Création et ajout d'un bouton pour un type d'affichage
    private void affichageBouton(CapteurAbstrait capteur, String nomButton, String nomFichier, String titreFichier) {
        Button b = new Button(nomButton);
        vb.getChildren().add(b);
        b.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                newWindow(nomFichier, titreFichier, capteur);
            }
        });
    }

    private void affichageBoutonSupprimer(CapteurAbstrait capteur, ObservableList<CapteurAbstrait> listeCapteur) {
        Button button = new Button("Supprimer");
        vb.getChildren().add(button);
        button.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                listeCapteur.remove(capteur);
                for (int i=0; i < listeCapteur.size(); i++) {
                    if(listeCapteur.get(i) instanceof CapteurComplexe){
                        if(((CapteurComplexe) listeCapteur.get(i)).getListeCapteur().containsKey(capteur)){
                            ((CapteurComplexe) listeCapteur.get(i)).getListeCapteur().remove(capteur);
                        }
                    }
                }
                if(listeCapteur.size() == 0) {
                    textCapteur.setText("");
                    vb.getChildren().clear();
                }
            }
        });
    }

    private void newWindow(String nomFichier, String titre, CapteurAbstrait capteur)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nomFichier));
            switch (nomFichier) {
                case "AffichageCapteur/affichageDigital.fxml": loader.setController(new AffichageDigital(capteur)); break;
                case "AffichageCapteur/affichageThermo.fxml": loader.setController(new AffichageThermo(capteur)); break;
                case "AffichageCapteur/affichageImgMeteo.fxml": loader.setController(new AffichageImgMeteo(capteur)); break;
                case "affichageConfig.fxml": loader.setController(new AffichageConfig((CapteurComplexe)capteur, olCapteur)); break;
            }
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initOwner(gridAccueil.getScene().getWindow());
            stage.setTitle(titre);
            stage.setScene(new Scene(root, 500, 400));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
