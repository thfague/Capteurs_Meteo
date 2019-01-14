package vue;

import class_Metier.capteur.CapteurAbstrait;
import class_Metier.capteur.CapteurComplexe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.*;

public class AffichageConfigCaptComp {
    @FXML
    private GridPane gridConfig;

    private CapteurComplexe capteur;
    private List<CapteurAbstrait> listeTotalCapteur;

    private VBox vb = new VBox();
    private Text nomCapteur = new Text();
    private List<CapteurAbstrait> listeCapteurLié = new ArrayList<>();
    private List<Integer> listeCapteurLiéCoeff = new ArrayList<>();
    private List<CapteurAbstrait> listeCapteurNonLié;
    private ObservableList<CapteurAbstrait> observablelListeCapteur;
    private ObservableList<CapteurAbstrait> oCapteur;
    private Text coeffTxt = new Text("Coefficient : ");

    private Spinner<Integer> coeffSpinner = new Spinner<>();
    private SpinnerValueFactory valSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);

    public AffichageConfigCaptComp(CapteurComplexe  c, List<CapteurAbstrait> l){
        capteur=c;
        listeTotalCapteur = l;
    }

    @FXML
    private void initialize() {
        nomCapteur.setText(capteur.getNom());
        Font font = new Font("Arial", 18);
        nomCapteur.setFont(font);

        coeffSpinner.valueFactoryProperty().setValue(valSpinner);

        boutonAjouterC();
        chargementCapteurLie();

        gridConfig.add(nomCapteur,0, 0);
        gridConfig.add(vb, 1, 1);
        gridConfig.setHalignment(nomCapteur, HPos.CENTER);

    }

    //Méthode qui charge tous les capteurs liés au capteur complexe
    private void chargementCapteurLie() {
        listeCapteurLié.clear();
        listeCapteurLiéCoeff.clear();
        Set<Map.Entry<CapteurAbstrait, Integer>> setListeCapteur = capteur.getListeCapteur().entrySet();
        Iterator<Map.Entry<CapteurAbstrait, Integer>> it = setListeCapteur.iterator();
        while (it.hasNext()) {
            Map.Entry<CapteurAbstrait, Integer> e = it.next();
            listeCapteurLié.add(e.getKey());
            listeCapteurLiéCoeff.add(e.getValue());
        }

        observablelListeCapteur = FXCollections.observableList(listeCapteurLié);
        ListView<CapteurAbstrait> listeVCapteur = new ListView<>(observablelListeCapteur);

        listeVCapteur.setCellFactory(new Callback<ListView<CapteurAbstrait>, ListCell<CapteurAbstrait>>() {
            @Override
            public ListCell<CapteurAbstrait> call(ListView<CapteurAbstrait> param) {
                return new DigitalFormatCell(listeCapteurLié);
            }
        });

        listeVCapteur.getSelectionModel().selectedItemProperty().addListener((listeCapteur, oV, nV) -> {
            vb.getChildren().clear();
            boutonAjouterC();
            changementCoeff(nV, observablelListeCapteur);
            boutonSupprimerC(nV, observablelListeCapteur);
        });

        gridConfig.add(listeVCapteur, 0, 1);
    }

    //Méthode qui affiche le bouton supprimer
    private void boutonSupprimerC(CapteurAbstrait c, ObservableList<CapteurAbstrait> olCapteur) {
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

    //Méthode qui affiche le bouton ajouter qui affiche la liste des capteurs non liés
    private void boutonAjouterC() {
        Button buttonAjout = new Button("Ajouter un capteur");
        vb.getChildren().add(buttonAjout);
        buttonAjout.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                listeCapteurNonLié = new ArrayList<>();

                for(int i=0; i < listeTotalCapteur.size(); i++) {
                    if(!observablelListeCapteur.contains(listeTotalCapteur.get(i))){
                        if(!listeTotalCapteur.get(i).getNom().equals(capteur.getNom())) {
                            if(listeTotalCapteur.get(i) instanceof CapteurComplexe){
                                if(!((CapteurComplexe) listeTotalCapteur.get(i)).getListeCapteur().containsKey(capteur)) {
                                    listeCapteurNonLié.add(listeTotalCapteur.get(i));
                                }
                            }
                            else {
                                listeCapteurNonLié.add(listeTotalCapteur.get(i));
                            }
                        }
                    }
                }

                oCapteur = FXCollections.observableList(listeCapteurNonLié);
                ListView<CapteurAbstrait> listeViewCapteur = new ListView<>(oCapteur);

                listeViewCapteur.setCellFactory(new Callback<ListView<CapteurAbstrait>, ListCell<CapteurAbstrait>>() {
                    @Override
                    public ListCell<CapteurAbstrait> call(ListView<CapteurAbstrait> param) {
                        return new DigitalFormatCell(listeCapteurNonLié);
                    }
                });

                listeViewCapteur.getSelectionModel().selectedItemProperty().addListener((listeCNonApproprie, oV, nV) -> {
                    vb.getChildren().clear();
                    ajouterCapteurInexistant();
                    vb.getChildren().add(listeViewCapteur);
                    vb.getChildren().add(coeffTxt);
                    vb.getChildren().add(coeffSpinner);
                    ajoutCapteur(nV, oCapteur);
                });

                vb.getChildren().clear();
                ajouterCapteurInexistant();
                vb.getChildren().add(listeViewCapteur);
            }
        });
    }

    //Affiche et ajoute un capteur existant non lié au capteur complexe
    private void ajoutCapteur(CapteurAbstrait c, ObservableList<CapteurAbstrait> oCapteur) {
        Button buttonAjout = new Button("Ajouter " + c.getNom());
        vb.getChildren().add(buttonAjout);
        buttonAjout.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                capteur.ajoutCapteur(c, coeffSpinner.getValue());
                chargementCapteurLie();
                vb.getChildren().clear();
                boutonAjouterC();
            }
        });
    }

    private void changementCoeff(CapteurAbstrait c, ObservableList<CapteurAbstrait> oCapteur) {
        Text coeffTxt = new Text("Coefficent : ");
        Spinner<Integer> chgmtCoeffSpinner = new Spinner();
        SpinnerValueFactory valCoeffSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,20,listeCapteurLiéCoeff.get(listeCapteurLié.indexOf(c)));
        chgmtCoeffSpinner.setValueFactory(valCoeffSpinner);
        vb.getChildren().add(coeffTxt);
        vb.getChildren().add(chgmtCoeffSpinner);
        Button buttonModif = new Button("Modifier coeff " + c.getNom());
        vb.getChildren().add(buttonModif);
        buttonModif.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                capteur.getListeCapteur().remove(c);
                capteur.ajoutCapteur(c, chgmtCoeffSpinner.getValue());
                chargementCapteurLie();
                vb.getChildren().clear();
                boutonAjouterC();
            }
        });
    }

    private void ajouterCapteurInexistant() {
        Button ajoutCInex = new Button("Ajouter un nouveau capteur");
        vb.getChildren().add(ajoutCInex);
        ajoutCInex.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent actionEvent){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ajoutCapteur.fxml"));
                    loader.setController(new AjoutCapteur(oCapteur));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.initOwner(gridConfig.getScene().getWindow());
                    stage.setTitle(ajoutCInex.getText());
                    stage.setScene(new Scene(root, 500, 400));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();

                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
