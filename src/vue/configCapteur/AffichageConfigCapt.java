package vue.configCapteur;

import class_Metier.capteur.Capteur;
import class_Metier.capteur.CapteurAbstrait;
import class_Metier.generateur.GenerationAleatoireBorne;
import class_Metier.generateur.GenerationAleatoireInfini;
import class_Metier.generateur.GenerationAleatoireReelle;
import class_Metier.generateur.GenerationValeurAbstrait;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

import static java.sql.Types.NULL;

public class AffichageConfigCapt {
    @FXML
    private GridPane gridConfig;

    public Capteur capteur;
    private Text nomCapteur = new Text();
    private VBox vb = new VBox();
    private VBox vbOptionGenerateur = new VBox();
    private Text tps;
    private Text min = new Text();
    private Text max = new Text();
    private TextField minTF = new TextField();
    private TextField maxTF = new TextField();
    private TextField nomCapteurTF = new TextField();
    private Spinner<Integer> tpsSpinner = new Spinner<>();
    private ComboBox<String> comboGenerateur = new ComboBox<>();
    private ObservableList<CapteurAbstrait> listeTotalCapteur;
    private Button validation = new Button("Valider la configuration");
    private Integer choixGenerateur;

    public AffichageConfigCapt(Capteur  c, ObservableList<CapteurAbstrait> l){
        capteur=c;
        listeTotalCapteur = l;
    }

    @FXML
    private void initialize() {
        tps = new Text("Temps de changement (en seconde) : ");
        SpinnerValueFactory<Integer> valSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,capteur.getTpsChangement()/1000);
        tpsSpinner.setValueFactory(valSpinner);

        comboGenerateur.getItems().addAll("Generation Aleatoire Borne", "Generation Aleatoire Reelle", "Generation Aleatoire Infini");
        comboGenerateur.getSelectionModel().selectFirst();

        validation.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                validationCapteur();
            }
        });

        reinit("");

        gridConfig.add(vb,0,0);
        gridConfig.add(vbOptionGenerateur, 0, 1);
    }

    private void affichageCapteur()
    {
        vb.getChildren().clear();
        vbOptionGenerateur.getChildren().clear();

        vb.getChildren().add(nomCapteur);
        vb.getChildren().add(nomCapteurTF);
        vb.getChildren().add(tps);
        vb.getChildren().add(tpsSpinner);
        vb.getChildren().add(comboGenerateur);
        comboGenerateur.getSelectionModel().selectedItemProperty().addListener((change, oV, nV) -> {
            if (nV.equals("Generation Aleatoire Borne")) {
                choixGenerateur = 1;
                min.setText("Minimum : ");
                max.setText("Maximum : ");
                affichageGenerateur();
            }
            if (change.getValue().equals("Generation Aleatoire Reelle")) {
                choixGenerateur = 2;
                min.setText("Valeur de départ :");
                max.setText("Valeur de différence");
                affichageGenerateur();
            }
            if (change.getValue().equals("Generation Aleatoire Infini")) {
                choixGenerateur = 3;
                vbOptionGenerateur.getChildren().clear();
                vbOptionGenerateur.getChildren().add(validation);
            }
        });
        choixGenerateur = 1;
        min.setText("Minimum : ");
        max.setText("Maximum : ");
        affichageGenerateur();
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

    private void validationCapteur() {
        for(int i = 0; i < listeTotalCapteur.size(); i++) {
            if(nomCapteurTF.getText().equals(listeTotalCapteur.get(i).getNom())) {
                if(!nomCapteurTF.getText().equals(capteur.getNom())) {
                    reinit("Nom de capteur déjà pris");
                    return;
                }
            }
        }
        if(nomCapteurTF.getText().equals("")) {
            reinit("Un capteur doit avoir un nom");
            return;
        }
        GenerationValeurAbstrait g;
        if(choixGenerateur == 1) {
            if(minTF.getText().trim().isEmpty() || maxTF.getText().trim().isEmpty()) {
                reinit("Les valeurs minimum et maximum doivent être renseignées");
                return;
            }
            int valMin = Integer.parseInt(minTF.getText());
            int valMax = Integer.parseInt(maxTF.getText());
            if(valMin >= valMax) {
                reinit("La valeur minimum doit être inférieur à la valeur maximum");
                return;
            }
            g = new GenerationAleatoireBorne(valMin, valMax);
        }
        else if(choixGenerateur == 2) {
            if(minTF.getText().trim().isEmpty() || maxTF.getText().trim().isEmpty()) {
                reinit("La valeur de départ et la valeur de différence doivent être renseignées");
                return;
            }
            int valMin = Integer.parseInt(minTF.getText());
            int valMax = Integer.parseInt(maxTF.getText());
            g = new GenerationAleatoireReelle(valMin, valMax);
        }
        else {
            g = new GenerationAleatoireInfini();
        }
        if(listeTotalCapteur.contains(capteur)) {
            listeTotalCapteur.remove(capteur);
        }
        listeTotalCapteur.add(new Capteur(0, nomCapteurTF.getText(),tpsSpinner.getValue()*1000, g));

        Stage stage = (Stage) validation.getScene().getWindow();
        stage.close();
    }

    private void reinit(String messageErreur) {
        if(capteur.getNom().equals("")) {
            nomCapteur.setText("Nom du capteur :");
            nomCapteurTF.setText("");
        }
        else {
            nomCapteur.setText(capteur.getNom());
            nomCapteurTF.setText(capteur.getNom());
        }
        Font font = new Font("Arial", 18);
        nomCapteur.setFont(font);
        affichageCapteur();
        comboGenerateur.getSelectionModel().selectFirst();
        minTF.setText("");
        maxTF.setText("");
        vbOptionGenerateur.getChildren().add(new Text(messageErreur));
    }
}
