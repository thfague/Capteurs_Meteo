package vue.configCapteur;

import class_Metier.capteur.Capteur;
import class_Metier.capteur.CapteurAbstrait;
import class_Metier.capteur.CapteurComplexe;
import class_Metier.generateur.GenerationAleatoireBorne;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import vue.DigitalFormatCell;
import vue.affichageCapteur.AffichageDigital;
import vue.affichageCapteur.AffichageImgMeteo;
import vue.affichageCapteur.AffichageThermo;

import java.io.IOException;
import java.util.*;

import static java.sql.Types.NULL;

public class AffichageConfigCaptComp {
    @FXML
    private GridPane gridConfig;

    private CapteurComplexe capteur;
    private List<CapteurAbstrait> listeTotalCapteur;
    private VBox vbGauche = new VBox();
    private VBox vbDroite = new VBox();
    private List<CapteurAbstrait> listeCapteurLie;
    private List<Integer> listeCapteurLieCoeff;
    private ObservableList<CapteurAbstrait> observablelListeCapteur;
    private ObservableList<CapteurAbstrait> oCapteur;
    private Text coeffTxt = new Text("Coefficient : ");
    private Spinner<Integer> coeffSpinner = new Spinner<>();
    private Text nomCapteur = new Text();
    private TextField nomCapteurTF = new TextField();
    private Button validation;
    private Button ajoutCapteur;
    private Map<CapteurAbstrait, Integer> m = new HashMap<>();
    private CapteurAbstrait captComp = new CapteurComplexe(m, "");
    private ComboBox<String> comboCapteur = new ComboBox<>();
    private Integer choixTypeCapteur;

    //Méthode appelée avant initialise()
    public AffichageConfigCaptComp(CapteurComplexe c, List<CapteurAbstrait> l) {
        capteur = c;
        listeTotalCapteur = l;
    }

    @FXML
    private void initialize() {
        creationComboCapteur();

        coeffSpinner.valueFactoryProperty().setValue(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1));

        validation = new Button("Valider la configuration");
        validation.setOnAction((event) -> {
            validationCapteurComplexe();
        });

        reinitScene("");

        gridConfig.add(nomCapteur, 0, 0);
        gridConfig.add(nomCapteurTF, 0, 1);
        gridConfig.add(new Text("Configurer la liste de capteur liés:"), 0, 2);
        gridConfig.add(vbGauche, 0, 3);
        gridConfig.add(vbDroite, 1, 3);
    }

    private void creationComboCapteur() {
        comboCapteur.getItems().addAll("capteur", "capteurComplexe");
        comboCapteur.getSelectionModel().selectFirst();
        comboCapteur.getSelectionModel().selectedItemProperty().addListener((change, oV, nV) -> {
            if (nV.equals("capteur")) {
                choixTypeCapteur = 0;
            } else if (nV.equals("capteurComplexe")) {
                choixTypeCapteur = 1;
            }
        });
        choixTypeCapteur = 0;

        ajoutCapteur = new Button("Ajouter un capteur");
        ajoutCapteur.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("affichageConfig.fxml"));
                    switch (choixTypeCapteur) {
                        case 0:
                            loader.setController(new AffichageConfigCapt(new Capteur(0, "", 1000, new GenerationAleatoireBorne(10, 30)), oCapteur));
                            break;
                        case 1:
                            loader.setController(new AffichageConfigCaptComp(new CapteurComplexe(new HashMap<>(), ""), oCapteur));
                            break;
                    }
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.initOwner(gridConfig.getScene().getWindow());
                    stage.setTitle("Configuration");
                    stage.setScene(new Scene(root, 500, 400));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Méthode qui affiche le bouton ajouter qui affiche la liste des capteurs non liés
    private void boutonAjouterCAuCC() {
        Button buttonAjout = new Button("Ajouter un capteur");
        vbDroite.getChildren().add(buttonAjout);
        buttonAjout.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                List<CapteurAbstrait> listeCapteurNonLie = new ArrayList<>();

                for (int i = 0; i < listeTotalCapteur.size(); i++) {
                    if (!observablelListeCapteur.contains(listeTotalCapteur.get(i))) {
                        if (!listeTotalCapteur.get(i).getNom().equals(capteur.getNom())) {
                            if (listeTotalCapteur.get(i) instanceof CapteurComplexe) {
                                if (!((CapteurComplexe) listeTotalCapteur.get(i)).getListeCapteur().containsKey(capteur)) {
                                    listeCapteurNonLie.add(listeTotalCapteur.get(i));
                                }
                            } else {
                                listeCapteurNonLie.add(listeTotalCapteur.get(i));
                            }
                        }
                    }
                }

                oCapteur = FXCollections.observableList(listeCapteurNonLie);
                ListView<CapteurAbstrait> listeViewCapteur = new ListView<>(oCapteur);

                listeViewCapteur.setCellFactory(new Callback<ListView<CapteurAbstrait>, ListCell<CapteurAbstrait>>() {
                    @Override
                    public ListCell<CapteurAbstrait> call(ListView<CapteurAbstrait> param) {
                        return new DigitalFormatCell(listeCapteurNonLie);
                    }
                });

                listeViewCapteur.getSelectionModel().selectedItemProperty().addListener((listeCNonApproprie, oV, nV) -> {
                    vbDroite.getChildren().clear();
                    vbDroite.getChildren().add(comboCapteur);
                    vbDroite.getChildren().add(ajoutCapteur);
                    vbDroite.getChildren().add(listeViewCapteur);
                    vbDroite.getChildren().add(coeffTxt);
                    vbDroite.getChildren().add(coeffSpinner);
                    ajouterLeCapteur(nV, oCapteur);
                });

                vbDroite.getChildren().clear();
                vbDroite.getChildren().add(comboCapteur);
                vbDroite.getChildren().add(ajoutCapteur);
                vbDroite.getChildren().add(listeViewCapteur);
            }
        });
    }


    //Méthode qui charge tous les capteurs liés au capteur complexe
    private void chargementCapteurLie() {
        listeCapteurLieCoeff = new ArrayList<>();
        listeCapteurLie = new ArrayList<>();
        Set<Map.Entry<CapteurAbstrait, Integer>> setListeCapteur = capteur.getListeCapteur().entrySet();
        Iterator<Map.Entry<CapteurAbstrait, Integer>> it = setListeCapteur.iterator();
        while (it.hasNext()) {
            Map.Entry<CapteurAbstrait, Integer> e = it.next();
            listeCapteurLie.add(e.getKey());
            listeCapteurLieCoeff.add(e.getValue());
        }

        observablelListeCapteur = FXCollections.observableList(listeCapteurLie);
        ListView<CapteurAbstrait> listeVCapteur = new ListView<>(observablelListeCapteur);

        listeVCapteur.setCellFactory(new Callback<ListView<CapteurAbstrait>, ListCell<CapteurAbstrait>>() {
            @Override
            public ListCell<CapteurAbstrait> call(ListView<CapteurAbstrait> param) {
                return new DigitalFormatCell(listeCapteurLie);
            }
        });

        listeVCapteur.getSelectionModel().selectedItemProperty().addListener((listeCapteur, oV, nV) -> {
            vbDroite.getChildren().clear();
            boutonAjouterCAuCC();
            if (listeCapteurLie.size() > 0) {
                changementCoeff(nV, observablelListeCapteur);
                boutonSupprimerC(nV, observablelListeCapteur);
            }
        });

        vbGauche.getChildren().add(listeVCapteur);
        vbGauche.getChildren().add(validation);
    }

    //Méthode qui affiche le bouton supprimer
    private void boutonSupprimerC(CapteurAbstrait c, ObservableList<CapteurAbstrait> olCapteur) {
        Button buttonSupp = new Button("Supprimer " + c.getNom());
        vbDroite.getChildren().add(buttonSupp);
        buttonSupp.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (olCapteur.size() == 1) {
                    olCapteur.clear();

                }
                olCapteur.remove(c);
                capteur.getListeCapteur().remove(c);
            }
        });
    }


    //Affiche et ajoute un capteur existant non lié au capteur complexe
    private void ajouterLeCapteur(CapteurAbstrait c, ObservableList<CapteurAbstrait> oCapteur) {
        Button buttonAjout = new Button("Ajouter " + c.getNom());
        vbDroite.getChildren().add(buttonAjout);
        buttonAjout.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                capteur.ajoutCapteur(c, coeffSpinner.getValue());
                vbDroite.getChildren().clear();
                vbGauche.getChildren().clear();
                chargementCapteurLie();
                boutonAjouterCAuCC();
            }
        });
    }

    private void changementCoeff(CapteurAbstrait c, ObservableList<CapteurAbstrait> oCapteur) {
        coeffTxt.setText("Coefficent : ");
        Spinner<Integer> chgmtCoeffSpinner = new Spinner<>();
        chgmtCoeffSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, listeCapteurLieCoeff.get(listeCapteurLie.indexOf(c))));
        vbDroite.getChildren().add(coeffTxt);
        vbDroite.getChildren().add(chgmtCoeffSpinner);
        Button buttonModif = new Button("Modifier coeff " + c.getNom());
        vbDroite.getChildren().add(buttonModif);
        buttonModif.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                capteur.getListeCapteur().remove(c);
                capteur.ajoutCapteur(c, chgmtCoeffSpinner.getValue());
                reinitScene("");
            }
        });
    }

    private void validationCapteurComplexe() {
        for (int i = 0; i < listeTotalCapteur.size(); i++) {
            if (nomCapteurTF.getText().equals(listeTotalCapteur.get(i).getNom())) {
                if (!nomCapteurTF.getText().equals(capteur.getNom())) {
                    reinitScene("Nom de capteur déjà pris");
                    return;
                }
            }
        }
        if (nomCapteurTF.getText().equals("")) {
            reinitScene("Un capteur doit avoir un nom");
            return;
        }
        for (int i = 0; i < listeCapteurLie.size(); ++i) {
            m.put(listeCapteurLie.get(i), listeCapteurLieCoeff.get(i));
        }
        if (listeTotalCapteur.contains(capteur)) {
            listeTotalCapteur.remove(capteur);
        }
        CapteurComplexe ca = new CapteurComplexe(m, nomCapteurTF.getText());
        listeTotalCapteur.add(ca);
        for (CapteurAbstrait c : listeTotalCapteur) {
            if (c instanceof CapteurComplexe) {
                if (((CapteurComplexe) c).getListeCapteur().containsKey(capteur)) {
                    ((CapteurComplexe) c).getListeCapteur().remove(capteur);
                    ((CapteurComplexe) c).ajoutCapteur(ca, 1);
                }
            }
        }


        Stage stage = (Stage) validation.getScene().getWindow();
        stage.close();
    }

    private void reinitScene(String messageErreur) {
        vbDroite.getChildren().clear();
        vbGauche.getChildren().clear();
        reinit(capteur.getNom(), nomCapteur, nomCapteurTF);
        nomCapteur.setFont(new Font("Arial", 18));
        chargementCapteurLie();
        boutonAjouterCAuCC();
        vbGauche.getChildren().add(new Text(messageErreur));
    }

    static void reinit(String nom, Text nomCapteur, TextField nomCapteurTF) {
        if (nom.equals("")) {
            nomCapteur.setText("Nom du capteur :");
            nomCapteurTF.setText("");
        } else {
            nomCapteur.setText(nom);
            nomCapteurTF.setText(nom);
        }
    }
}
