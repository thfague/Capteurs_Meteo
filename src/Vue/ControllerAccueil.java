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
    GridPane gridAccueil;

    Text textCapteur = new Text();
    List<CapteurAbstrait> l = new ArrayList<>();
    VBox vb = new VBox();
    ObservableList<CapteurAbstrait> olCapteur;

    @FXML
    public void initialize (){
        GenerationValeurAbstrait g1;
        GenerationValeurAbstrait g2;
        GenerationValeurAbstrait g3;
        l.add(new Capteur(30, "Capteur 1", 1000, g1 = new GenerationAleatoireBorne(10,20)));
        l.add(new Capteur(10, "Capteur 2", 2000, g2 = new GenerationAleatoireInfini()));
        l.add(new Capteur(2, "Capteur 3", 3000, g3 = new GenerationAleatoireReelle(0, 2)));
        Map<CapteurAbstrait,Integer> m = new HashMap<>();
        CapteurComplexe capteurComplexe = new CapteurComplexe(m,"CapteurComplexe 1");
        capteurComplexe.ajoutCapteur(l.get(0), 1);
        capteurComplexe.ajoutCapteur(l.get(1), 2);
        capteurComplexe.ajoutCapteur(l.get(2), 3);
        l.add(capteurComplexe);

        olCapteur = FXCollections.observableList(l);
        ListView<CapteurAbstrait> listCapteur = new ListView<>(olCapteur);

        listCapteur.setCellFactory(new Callback<ListView<CapteurAbstrait>, ListCell<CapteurAbstrait>>() {
            @Override
            public ListCell<CapteurAbstrait> call(ListView<CapteurAbstrait> param) {
                return new DigitalFormatCell(l);
            }
        });

        listCapteur.getSelectionModel().selectedItemProperty().addListener((l,oV,nV)->{
            textCapteur.setText(nV.getNom());
            vb.getChildren().clear();

            AffichageBouton(nV, "Affichage digital", "AffichageCapteur/affichageDigital.fxml", "Affichage format digital");
            AffichageBouton(nV, "Affichage par thermomètre", "AffichageCapteur/affichageThermo.fxml", "Affichage format thermomètre");
            AffichageBouton(nV, "Affichage imagé", "AffichageCapteur/affichageImgMeteo.fxml", "Affichage format image");
            if(nV instanceof CapteurComplexe) {
                AffichageBouton(nV, "Configuration", "affichageConfig.fxml", "Configuration Capteur Complexe");
            }
            AffichageBoutonSupprimer(nV, olCapteur);
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

        gridAccueil.add(listCapteur, 0, 1);
        gridAccueil.add(ajoutCapteur, 0, 0);
        gridAccueil.add(textCapteur, 1, 0);
        gridAccueil.add(vb, 1, 1);

        vb.setAlignment(Pos.TOP_CENTER);
        gridAccueil.setHalignment(textCapteur, HPos.CENTER);
        gridAccueil.setHalignment(ajoutCapteur, HPos.CENTER);
        Font font = new Font("Arial",18);
        textCapteur.setFont(font);
    }

    //Création et ajout d'un bouton pour un type d'affichage
    public void AffichageBouton(CapteurAbstrait d, String nomButton, String nomFichier, String titreFichier) {
        Button b = new Button(nomButton);
        vb.getChildren().add(b);
        b.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                NewWindow(nomFichier, titreFichier, d);
            }
        });
    }

    public void AffichageBoutonSupprimer(CapteurAbstrait c, ObservableList<CapteurAbstrait> l) {
        Button b = new Button("Supprimer");
        vb.getChildren().add(b);
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
                if(l.size() == 0) {
                    textCapteur.setText("");
                    vb.getChildren().clear();
                }
            }
        });
    }

    public void NewWindow(String nameFile, String title, CapteurAbstrait d)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nameFile));
            switch (nameFile) {
                case "AffichageCapteur/affichageDigital.fxml": loader.setController(new AffichageDigital(d)); break;
                //case "AffichageCapteur/affichageThermo.fxml": loader.setController(new AffichageThermo(d)); break;
                case "AffichageCapteur/affichageImgMeteo.fxml": loader.setController(new AffichageImgMeteo(d)); break;
                case "affichageConfig.fxml": loader.setController(new AffichageConfig((CapteurComplexe)d, olCapteur)); break;
            }
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initOwner(gridAccueil.getScene().getWindow());
            stage.setTitle(title);
            stage.setScene(new Scene(root, 500, 400));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
