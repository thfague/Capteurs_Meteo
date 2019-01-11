package Vue;

import Class_Metier.Capteur.CapteurAbstrait;
import Class_Metier.Capteur.CapteurComplexe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import java.util.*;

public class AffichageConfig {
    @FXML
    GridPane gridConfig;

    VBox vb = new VBox();
    private Text nomCapteur = new Text();
    private List<CapteurAbstrait> listeCapteur = new ArrayList<>();
    private CapteurComplexe capteur;
    private List<CapteurAbstrait> listeTotalCapteur = new ArrayList<>();
    private List<CapteurAbstrait> listeCNonApproprie;
    private  ObservableList<CapteurAbstrait> olCapteur;
    private Text coeffTxt = new Text();
    private Spinner<Integer> coeffSpinner = new Spinner<>();
    private SpinnerValueFactory valSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);

    public AffichageConfig(CapteurComplexe  c, List<CapteurAbstrait> l){
        capteur=c;
        listeTotalCapteur = l;
    }

    @FXML
    private void initialize() {
        coeffSpinner.valueFactoryProperty().setValue(valSpinner);
        coeffTxt.setText("Coefficient : ");
        nomCapteur.setText(capteur.getNom());
        Font font = new Font("Arial", 18);
        nomCapteur.setFont(font);

        AffichageBoutonAC();
        ChargementListeC();
        gridConfig.add(nomCapteur,0, 0);
        gridConfig.add(vb, 1, 1);
        gridConfig.setHalignment(nomCapteur, HPos.CENTER);

    }

    private void ChargementListeC() {
        listeCapteur.clear();
        Set<Map.Entry<CapteurAbstrait, Integer>> setListeCapteur = capteur.getListeCapteur().entrySet();
        Iterator<Map.Entry<CapteurAbstrait, Integer>> it = setListeCapteur.iterator();
        while (it.hasNext()) {
            Map.Entry<CapteurAbstrait, Integer> e = it.next();
            listeCapteur.add(e.getKey());
        }

        olCapteur = FXCollections.observableList(listeCapteur);
        ListView<CapteurAbstrait> listeVCapteur = new ListView<>(olCapteur);


        listeVCapteur.setCellFactory(new Callback<ListView<CapteurAbstrait>, ListCell<CapteurAbstrait>>() {
            @Override
            public ListCell<CapteurAbstrait> call(ListView<CapteurAbstrait> param) {
                return new DigitalFormatCell(listeCapteur);
            }
        });

        listeVCapteur.getSelectionModel().selectedItemProperty().addListener((listeCapteur, oV, nV) -> {
            vb.getChildren().clear();
            AffichageBoutonAC();
            AffichageBoutonS(nV, olCapteur);
            AffichageChgmtCoeff(nV, olCapteur);
        });

        gridConfig.add(listeVCapteur, 0, 1);
    }

    private void AffichageBoutonS(CapteurAbstrait c, ObservableList<CapteurAbstrait> olCapteur) {
        Button buttonSupp = new Button("Supprimer " + c.getNom());
        vb.getChildren().add(buttonSupp);
        buttonSupp.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                olCapteur.remove(c);
                capteur.getListeCapteur().remove(c);
            }
        });
    }

    private void AffichageBoutonAC() {
        Button buttonAjout = new Button("Ajouter un capteur");
        vb.getChildren().add(buttonAjout);
        buttonAjout.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                listeCNonApproprie = new ArrayList<>();

                for(int i=0; i < listeTotalCapteur.size(); i++) {
                    if(!olCapteur.contains(listeTotalCapteur.get(i))){
                        if(!listeTotalCapteur.get(i).getNom().equals(capteur.getNom())) {
                            listeCNonApproprie.add(listeTotalCapteur.get(i));
                        }
                    }
                }

                ObservableList<CapteurAbstrait> oCapteur = FXCollections.observableList(listeCNonApproprie);
                ListView<CapteurAbstrait> listeVCapteur = new ListView<>(oCapteur);


                listeVCapteur.setCellFactory(new Callback<ListView<CapteurAbstrait>, ListCell<CapteurAbstrait>>() {
                    @Override
                    public ListCell<CapteurAbstrait> call(ListView<CapteurAbstrait> param) {
                        return new DigitalFormatCell(listeCNonApproprie);
                    }
                });

                listeVCapteur.getSelectionModel().selectedItemProperty().addListener((listeCNonApproprie, oV, nV) -> {
                    vb.getChildren().clear();
                    AffichageBoutonAC();
                    vb.getChildren().add(listeVCapteur);
                    vb.getChildren().add(coeffTxt);
                    vb.getChildren().add(coeffSpinner);
                    AffichageBoutonA(nV, oCapteur);
                });

                vb.getChildren().clear();
                AffichageBoutonAC();
                vb.getChildren().add(listeVCapteur);
            }
        });
    }

    private void AffichageBoutonA(CapteurAbstrait c, ObservableList<CapteurAbstrait> oCapteur) {
        Button buttonAjout = new Button("Ajouter " + c.getNom());
        vb.getChildren().add(buttonAjout);
        buttonAjout.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               capteur.ajoutCapteur(c, coeffSpinner.getValue());
               ChargementListeC();
               vb.getChildren().clear();
               AffichageBoutonAC();
            }
        });
    }

    private void AffichageChgmtCoeff(CapteurAbstrait c, ObservableList<CapteurAbstrait> oCapteur) {
        Label coeffTxt = new Label("Coeff:");
        Spinner<Integer> chgmtCoeffSpinner = new Spinner();
        SpinnerValueFactory valCoeffSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,20,1);
        chgmtCoeffSpinner.setValueFactory(valCoeffSpinner);
        vb.getChildren().add(coeffTxt);
        vb.getChildren().add(chgmtCoeffSpinner);
        Button buttonModif = new Button("Modifier coeff " + c.getNom());
        vb.getChildren().add(buttonModif);
        buttonModif.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                capteur.ajoutCapteur(c, chgmtCoeffSpinner.getValue());
            }
        });
    }
}
