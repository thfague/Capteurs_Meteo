package vue;

import class_Metier.capteur.Capteur;
import class_Metier.capteur.CapteurAbstrait;
import class_Metier.capteur.CapteurComplexe;
import class_Metier.generateur.GenerationAleatoireBorne;
import class_Metier.generateur.GenerationAleatoireInfini;
import class_Metier.generateur.GenerationAleatoireReelle;
import class_Metier.generateur.GenerationValeurAbstrait;
import javafx.scene.control.ComboBox;
import vue.configCapteur.AffichageConfigCapt;
import vue.configCapteur.AffichageConfigCaptComp;
import vue.affichageCapteur.AffichageDigital;
import vue.affichageCapteur.AffichageImgMeteo;
import vue.affichageCapteur.AffichageThermo;
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
import javafx.scene.layout.VBox;
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
    private VBox vbDroite = new VBox();
    private VBox vbGauche = new VBox();
    private ObservableList<CapteurAbstrait> olCapteur;
    private ComboBox comboCapteur = new ComboBox();

    @FXML
    public void initialize (){
        creationCapteur();

        comboCapteur.getItems().addAll("capteur","capteurComplexe");
        comboCapteur.getSelectionModel().selectFirst();
        comboCapteur.getSelectionModel().selectedItemProperty().addListener((change, oV, nV) -> {
            if (nV.equals("capteur")) {
                boutonAjouterCapteur(new Capteur(0,"", 1000, new GenerationAleatoireBorne(10,30)), "Ajouter un capteur");
            }
            if (nV.equals("capteurComplexe")) {
                boutonAjouterCapteur(new CapteurComplexe(new HashMap<>(), ""), "Ajouter un capteur complexe");
            }
        });
        boutonAjouterCapteur(new Capteur(0,"", 1000, new GenerationAleatoireBorne(10,30)), "Ajouter un capteur");

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
            vbDroite.getChildren().clear();

            affichageBouton(nV, "Affichage digital", "affichageCapteur/affichageDigital.fxml", "Affichage format digital");
            affichageBouton(nV, "Affichage par thermomètre", "affichageCapteur/affichageThermo.fxml", "Affichage format thermomètre");
            affichageBouton(nV, "Affichage imagé", "affichageCapteur/affichageImgMeteo.fxml", "Affichage format image");
            affichageBouton(nV, "Configuration", "configCapteur/affichageConfig.fxml", "Configuration");
            affichageBoutonSupprimer(nV, olCapteur);
        });

        gridAccueil.add(comboCapteur, 0, 0);
        gridAccueil.add(vbGauche, 0, 1);
        gridAccueil.add(listeVCapteur, 0, 2);
        gridAccueil.add(textCapteur, 1, 0);
        gridAccueil.add(vbDroite, 1, 2);

        vbDroite.setAlignment(Pos.TOP_CENTER);
        Font font = new Font("Arial",18);
        textCapteur.setFont(font);
    }

    private void boutonAjouterCapteur(CapteurAbstrait c, String titre) {
        Button ajoutCapteur = new Button(titre);
        vbGauche.getChildren().clear();
        vbGauche.getChildren().add(ajoutCapteur);
        ajoutCapteur.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                newWindow("configCapteur/affichageConfig.fxml", "Configuration", c);
            }
        });
    }

    private void creationCapteur() {
        GenerationValeurAbstrait g1;
        GenerationValeurAbstrait g2;
        GenerationValeurAbstrait g3;
        listeCapteur.add(new Capteur(30, "capteur 1", 1000, g1 = new GenerationAleatoireBorne(10,20)));
        listeCapteur.add(new Capteur(10, "capteur 2", 2000, g2 = new GenerationAleatoireInfini()));
        listeCapteur.add(new Capteur(2, "capteur 3", 3000, g3 = new GenerationAleatoireReelle(0, 2)));
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
        vbDroite.getChildren().add(b);
        b.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                newWindow(nomFichier, titreFichier, capteur);
            }
        });
    }

    private void affichageBoutonSupprimer(CapteurAbstrait capteur, ObservableList<CapteurAbstrait> listeCapteur) {
        Button button = new Button("Supprimer");
        vbDroite.getChildren().add(button);
        button.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                listeCapteur.remove(capteur);
                for(CapteurAbstrait c : listeCapteur) {
                    if(c instanceof CapteurComplexe){
                        if(((CapteurComplexe) c).getListeCapteur().containsKey(capteur)){
                            ((CapteurComplexe) c).getListeCapteur().remove(capteur);
                        }
                    }
                }
                if(listeCapteur.size() == 0) {
                    textCapteur.setText("");
                    vbDroite.getChildren().clear();
                }
            }
        });
    }

    private void newWindow(String nomFichier, String titre, CapteurAbstrait capteur)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nomFichier));
            switch (nomFichier) {
                case "affichageCapteur/affichageDigital.fxml": loader.setController(new AffichageDigital(capteur)); break;
                case "affichageCapteur/affichageThermo.fxml": loader.setController(new AffichageThermo(capteur)); break;
                case "affichageCapteur/affichageImgMeteo.fxml": loader.setController(new AffichageImgMeteo(capteur)); break;
                case "configCapteur/affichageConfig.fxml":
                    if(capteur instanceof CapteurComplexe) { loader.setController(new AffichageConfigCaptComp((CapteurComplexe) capteur, olCapteur)); }
                    else { loader.setController(new AffichageConfigCapt((Capteur) capteur, olCapteur)); }
                    break;
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
