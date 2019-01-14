package vue;

import class_Metier.capteur.Capteur;
import class_Metier.capteur.CapteurAbstrait;
import class_Metier.capteur.CapteurComplexe;
import class_Metier.generateur.GenerationAleatoireBorne;
import class_Metier.generateur.GenerationAleatoireInfini;
import class_Metier.generateur.GenerationAleatoireReelle;
import class_Metier.generateur.GenerationValeurAbstrait;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class AjoutCapteur {
    @FXML
    private GridPane gridAjoutCapteur;

    private VBox vb = new VBox();
    private VBox vbOptionGenerateur = new VBox();

    private Text nomCapteur = new Text();
    private Text tps = new Text("Temps de changement (en seconde) : ");
    private Text min = new Text();
    private Text max = new Text();

    private TextField minTF = new TextField();
    private TextField maxTF = new TextField();
    private TextField nomCapteurTF = new TextField();
    private Spinner<Integer> tpsSpinner = new Spinner<>();

    private ComboBox comboCapteur = new ComboBox();
    private ComboBox comboGenerateur = new ComboBox();

    private Button validation;

    private ObservableList<CapteurAbstrait> listeTotalCapteur;
    private Map<CapteurAbstrait,Integer> m = new HashMap<>();
    private CapteurAbstrait captComp = new CapteurComplexe(m,"");
    private int choix;

    AjoutCapteur(ObservableList<CapteurAbstrait> l){
        listeTotalCapteur = l;
    }

    @FXML
    public void initialize (){
        validation = new Button("Valider");
        tpsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1));
        validation.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(choix == 1 || choix == 2 || choix == 3) {
                    validationCapteur();
                }
                if(choix == 4) {
                    validationCapteurComplexe();
                }
            }
        });
        comboCapteur.getItems().addAll("capteur","capteurComplexe");
        comboCapteur.getSelectionModel().selectFirst();

        comboGenerateur.getItems().addAll("Generation Aleatoire Borne", "Generation Aleatoire Reelle", "Generation Aleatoire Infini");
        comboGenerateur.getSelectionModel().selectFirst();

        comboCapteur.getSelectionModel().selectedItemProperty().addListener((change, oV, nV) -> {
            if (change.getValue().equals("capteur")) {
                affichageCapteur();
            }
            if (change.getValue().equals("CapteurComplexe")) {
                affichageCapteurComplexe();
            }
        });

        affichageCapteur();

        gridAjoutCapteur.add(vb, 0, 0);
        //gridAjoutCapteur.add(vbOption, 1, 1);
        gridAjoutCapteur.add(vbOptionGenerateur, 0, 1);
    }

    private void affichageCapteur()
    {
        vb.getChildren().clear();
        //vbOption.getChildren().clear();
        vbOptionGenerateur.getChildren().clear();

        vb.getChildren().add(comboCapteur);
        nomCapteur.setText("Nom du capteur : ");
        vb.getChildren().add(nomCapteur);
        vb.getChildren().add(nomCapteurTF);
        vb.getChildren().add(tps);
        vb.getChildren().add(tpsSpinner);
        vb.getChildren().add(comboGenerateur);
        comboGenerateur.getSelectionModel().selectedItemProperty().addListener((change, oV, nV) -> {
            if (nV.equals("Generation Aleatoire Borne")) {
                choix = 1;
                min.setText("Minimum : ");
                max.setText("Maximum : ");
                affichageGenerateur();
            }
            if (change.getValue().equals("Generation Aleatoire Reelle")) {
                choix = 2;
                min.setText("Valeur de départ :");
                max.setText("Valeur de différence");
                affichageGenerateur();
            }
            if (change.getValue().equals("Generation Aleatoire Infini")) {
                choix = 3;
                vbOptionGenerateur.getChildren().clear();
                vbOptionGenerateur.getChildren().add(validation);
            }
        });
        min.setText("Minimum : ");
        max.setText("Maximum : ");
        affichageGenerateur();
        choix = 1;
    }

    private void affichageGenerateur()
    {
        vbOptionGenerateur.getChildren().clear();
        vbOptionGenerateur.getChildren().add(min);
        vbOptionGenerateur.getChildren().add(minTF);
        vbOptionGenerateur.getChildren().add(max);
        vbOptionGenerateur.getChildren().add(maxTF);
        vbOptionGenerateur.getChildren().add(validation);
    }

    private void affichageCapteurComplexe()
    {
        vb.getChildren().clear();
        //vbOption.getChildren().clear();
        vbOptionGenerateur.getChildren().clear();

        vb.getChildren().add(comboCapteur);
        nomCapteur.setText("Nom du capteur complexe : ");
        vb.getChildren().add(nomCapteur);
        vb.getChildren().add(nomCapteurTF);

        Button buttonConfig = new Button("Configurer la liste de capteur");
        vb.getChildren().add(buttonConfig);
        buttonConfig.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("affichageConfig.fxml"));
                    loader.setController(new AffichageConfigCaptComp((CapteurComplexe)captComp, listeTotalCapteur));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.initOwner(gridAjoutCapteur.getScene().getWindow());
                    stage.setTitle("Configuration capteur Complexe");
                    stage.setScene(new Scene(root, 500, 400));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        choix = 4;
        vb.getChildren().add(validation);
    }

    private void validationCapteurComplexe() {
        for(int i = 0; i < listeTotalCapteur.size(); i++) {
            if(nomCapteurTF.getText().equals(listeTotalCapteur.get(i).getNom())) {
                affichageCapteurComplexe();
                nomCapteurTF.setText("");
                vb.getChildren().add(new Text("Nom de capteur déjà pris"));
                return;
            }
        }
        captComp.setNom(nomCapteurTF.getText());
        listeTotalCapteur.add(captComp);
        Stage stage = (Stage) validation.getScene().getWindow();
        stage.close();
    }

    private void validationCapteur() {
        GenerationValeurAbstrait g;
        for(CapteurAbstrait c : listeTotalCapteur) {
            if(nomCapteurTF.getText().equals(c.getNom())) {
                affichageCapteur();
                nomCapteurTF.setText("");
                minTF.setText("");
                maxTF.setText("");
                vbOptionGenerateur.getChildren().add(new Text("Nom de capteur déjà pris"));
                return;
            }
        }
        if(choix == 1) {
            if(minTF.getText().trim().isEmpty() || maxTF.getText().trim().isEmpty()) {
                affichageCapteur();
                nomCapteurTF.setText("");
                minTF.setText("");
                maxTF.setText("");
                vbOptionGenerateur.getChildren().add(new Text("Les valeurs minimum et maximum doivent être renseignées"));
                return;
            }
            int valMin = Integer.parseInt(minTF.getText());
            int valMax = Integer.parseInt(maxTF.getText());
            if(valMin >= valMax) {
                affichageCapteur();
                nomCapteurTF.setText("");
                minTF.setText("");
                maxTF.setText("");
                vbOptionGenerateur.getChildren().add(new Text("La valeur minimum doit être inférieur à la valeur maximum"));
                return;
            }
            g = new GenerationAleatoireBorne(valMin, valMax);
        }
        else if(choix == 2) {
            if(minTF.getText().trim().isEmpty() || maxTF.getText().trim().isEmpty()) {
                affichageCapteur();
                nomCapteurTF.setText("");
                minTF.setText("");
                maxTF.setText("");
                vbOptionGenerateur.getChildren().add(new Text("Certaines valeurs ne sont pas renseignées"));
                return;
            }
            int valMin = Integer.parseInt(minTF.getText());
            int valMax = Integer.parseInt(maxTF.getText());
            g = new GenerationAleatoireReelle(valMin, valMax);
        }
        else {
            g = new GenerationAleatoireInfini();
        }
        CapteurAbstrait capteur = new Capteur(0,nomCapteurTF.getText(),tpsSpinner.getValue()*1000,g);
        listeTotalCapteur.add(capteur);
        Stage stage = (Stage) validation.getScene().getWindow();
        stage.close();
    }
}
