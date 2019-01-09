package Vue;

import Class_Metier.Capteur.CapteurAbstrait;
import Class_Metier.Capteur.CapteurComplexe;
import Vue.AffichageCapteur.AffichageDigital;
import Vue.AffichageCapteur.AffichageImgMeteo;
import javafx.collections.FXCollections;
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
import javafx.util.Callback;

import java.io.IOException;
import java.util.*;

public class AjoutCapteur {
    @FXML
    GridPane gridAjoutC;

    private VBox vb = new VBox();
    private VBox vbOption = new VBox();
    private VBox vbOptionGenerateur = new VBox();

    private Text nomC = new Text();
    private Text tps = new Text("Temps de changement (en seconde) : ");
    private Text min = new Text();
    private Text max = new Text();

    private TextField minTF = new TextField();
    private TextField maxTF = new TextField();
    private TextField nomCapteur = new TextField();
    private TextField tpsTF = new TextField();

    private ComboBox comboCapteur = new ComboBox();
    private ComboBox comboGenerateur = new ComboBox();

    private Button validation = new Button("Valider");

    private List<CapteurAbstrait> listTotalCapteur = new ArrayList<>();
    private Map<CapteurAbstrait,Integer> m = new HashMap<>();
    private CapteurComplexe cc = new CapteurComplexe(m,"");
    private int choix;

    public AjoutCapteur(List<CapteurAbstrait> l){
        listTotalCapteur = l;
    }

    @FXML
    public void initialize (){

        validation.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(choix == 1 || choix == 2 || choix == 3) {
                    validationCapteur();
                }
                else {
                    validationCapteurComplexe();
                }
            }
        });

        comboCapteur.getItems().addAll("Capteur", "CapteurComplexe");
        comboCapteur.getSelectionModel().selectFirst();

        comboGenerateur.getItems().addAll("GenerationAleatoireBorne", "GenerationAleatoireReelle", "GenerationAleatoireInfini");
        comboGenerateur.getSelectionModel().selectFirst();

        comboCapteur.getSelectionModel().selectedItemProperty().addListener((change, oV, nV) -> {
            if (change.getValue().equals("Capteur")) {
                AffichageCapteur();
            }
            if (change.getValue().equals("CapteurComplexe")) {
                AffichageCapteurComplexe();
            }
        });

        AffichageCapteur();

        gridAjoutC.add(vb, 0, 0);
        gridAjoutC.add(vbOption, 1, 1);
        gridAjoutC.add(vbOptionGenerateur, 0, 1);
    }

    public void AffichageCapteur()
    {
        vb.getChildren().clear();
        vbOption.getChildren().clear();
        vbOptionGenerateur.getChildren().clear();

        vb.getChildren().add(comboCapteur);
        nomC.setText("Nom du capteur : ");
        vb.getChildren().add(nomC);
        vb.getChildren().add(nomCapteur);
        vb.getChildren().add(tps);
        vb.getChildren().add(tpsTF);
        vb.getChildren().add(comboGenerateur);
        comboGenerateur.getSelectionModel().selectedItemProperty().addListener((change, oV, nV) -> {
            if (nV.equals("GenerationAleatoireBorne")) {
                min.setText("Minimum : ");
                max.setText("Maximum : ");
                AffichageGenerateur();
                choix = 1;
            }
            if (change.getValue().equals("GenerationAleatoireReelle")) {
                min.setText("Valeur de départ :");
                max.setText("Valeur de différence");
                AffichageGenerateur();
                choix = 2;
            }
            if (change.getValue().equals("GenerationAleatoireInfini")) {
                vbOptionGenerateur.getChildren().clear();
                vbOptionGenerateur.getChildren().add(validation);
                choix = 3;
            }
        });
        min.setText("Minimum : ");
        max.setText("Maximum : ");
        AffichageGenerateur();
    }

    public void AffichageGenerateur()
    {
        vbOptionGenerateur.getChildren().clear();
        vbOptionGenerateur.getChildren().add(min);
        vbOptionGenerateur.getChildren().add(minTF);
        vbOptionGenerateur.getChildren().add(max);
        vbOptionGenerateur.getChildren().add(maxTF);
        vbOptionGenerateur.getChildren().add(validation);
    }

    public void AffichageCapteurComplexe()
    {
        vb.getChildren().clear();
        vbOption.getChildren().clear();
        vbOptionGenerateur.getChildren().clear();

        vb.getChildren().add(comboCapteur);
        nomC.setText("Nom du capteur complexe : ");
        vb.getChildren().add(nomC);
        vb.getChildren().add(nomCapteur);

        Button buttonConfig = new Button("Configurer la liste de capteur");
        vb.getChildren().add(buttonConfig);
        buttonConfig.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("affichageConfig.fxml"));
                    loader.setController(new AffichageConfig((CapteurComplexe)cc, listTotalCapteur));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.initOwner(gridAjoutC.getScene().getWindow());
                    stage.setTitle("Configuration Capteur Complexe");
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

    }

    private void validationCapteur() {

    }


}