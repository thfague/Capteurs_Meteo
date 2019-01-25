package vue.configCapteur;

import class_Metier.capteur.Capteur;
import class_Metier.capteur.CapteurAbstrait;
import class_Metier.capteur.CapteurComplexe;
import class_Metier.generateur.GenerationAleatoireBorne;
import class_Metier.generateur.GenerationAleatoireInfini;
import class_Metier.generateur.GenerationAleatoireReelle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AffichageConfigCapt {
    @FXML
    private GridPane gridConfig;

    public Capteur capteur;
    private Text nomCapt = new Text();
    private VBox vb = new VBox();
    private VBox vbOptionGenerateur = new VBox();
    private Text tps;
    private Text min = new Text();
    private Text max = new Text();
    private TextField minTF = new TextField();
    private TextField maxTF = new TextField();
    private TextField nomCaptTF = new TextField();
    private Spinner<Integer> tpsSpinner = new Spinner<>();
    private ComboBox<String> comboGenerateur = new ComboBox<>();
    private ObservableList<CapteurAbstrait> listeTotalCapteur;
    private Button validation = new Button("Valider la configuration");
    private Integer choixGenerateur;

    //Méthode appelée avant initialise()
    public AffichageConfigCapt(Capteur c, ObservableList<CapteurAbstrait> l) {
        capteur = c;
        listeTotalCapteur = l;
    }

    @FXML
    private void initialize() {
        tps = new Text("Temps de changement (en seconde) : ");
        SpinnerValueFactory<Integer> valSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, capteur.getTpsChangement() / 1000);
        tpsSpinner.setValueFactory(valSpinner);

        comboGenerateur.getItems().addAll("Generation Aleatoire Borne", "Generation Aleatoire Reelle", "Generation Aleatoire Infini");
        comboGenerateur.getSelectionModel().selectFirst();

        addNumericValidation(minTF);
        addNumericValidation(maxTF);

        validation.setOnAction((event) -> {
            validationCapteur();
        });

        reinit("");

        gridConfig.add(vb, 0, 0);
        gridConfig.add(vbOptionGenerateur, 0, 1);
    }

    //Méthode qui empêche d'écrire autre chose que des chiffres dans un textfield
    private static void addNumericValidation(TextField field) {
        field.getProperties().put("vkType", "numeric");
        field.setTextFormatter(new TextFormatter<>(c -> {
            if (c.isContentChange()) {
                if (c.getControlNewText().length() == 0) {
                    return c;
                }
                try {
                    Integer.parseInt(c.getControlNewText());
                    return c;
                } catch (NumberFormatException e) {
                }
                return null;

            }
            return c;
        }));
    }

    private void affichageCapteur() {
        vb.getChildren().clear();
        vbOptionGenerateur.getChildren().clear();

        vb.getChildren().add(nomCapt);
        vb.getChildren().add(nomCaptTF);
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

    private void affichageGenerateur() {
        vbOptionGenerateur.getChildren().clear();
        vbOptionGenerateur.getChildren().add(min);
        vbOptionGenerateur.getChildren().add(minTF);
        vbOptionGenerateur.getChildren().add(max);
        vbOptionGenerateur.getChildren().add(maxTF);
        vbOptionGenerateur.getChildren().add(validation);
    }

    private void validationCapteur() {
        for (int i = 0; i < listeTotalCapteur.size(); i++) {
            if (nomCaptTF.getText().equals(listeTotalCapteur.get(i).getNom())) {
                if (!nomCaptTF.getText().equals(capteur.getNom())) {
                    reinit("Nom de capteur déjà pris");
                    return;
                }
            }
        }
        if (nomCaptTF.getText().equals("")) {
            reinit("Un capteur doit avoir un nom");
            return;
        }
        if (choixGenerateur == 1) {
            if (minTF.getText().trim().isEmpty() || maxTF.getText().trim().isEmpty()) {
                reinit("Les valeurs minimum et maximum doivent être renseignées");
                return;
            }
            int valMin = Integer.parseInt(minTF.getText());
            int valMax = Integer.parseInt(maxTF.getText());
            if (valMin >= valMax) {
                reinit("La valeur minimum doit être inférieur à la valeur maximum");
                return;
            }
            if (listeTotalCapteur.contains(capteur)) {
                listeTotalCapteur.remove(capteur);
            }
            Capteur ca = new Capteur(0, nomCaptTF.getText(), tpsSpinner.getValue() * 1000, new GenerationAleatoireBorne(valMin, valMax));
            listeTotalCapteur.add(ca);
        } else if (choixGenerateur == 2) {
            if (minTF.getText().trim().isEmpty() || maxTF.getText().trim().isEmpty()) {
                reinit("La valeur de départ et la valeur de différence doivent être renseignées");
                return;
            }
            int valMin = Integer.parseInt(minTF.getText());
            int valMax = Integer.parseInt(maxTF.getText());
            if (listeTotalCapteur.contains(capteur)) {
                listeTotalCapteur.remove(capteur);
            }
            Capteur ca = new Capteur(0, nomCaptTF.getText(), tpsSpinner.getValue() * 1000, new GenerationAleatoireReelle(valMin, valMax));
            listeTotalCapteur.add(ca);
            configCapteur(ca);
        } else {
            if (listeTotalCapteur.contains(capteur)) {
                listeTotalCapteur.remove(capteur);
            }
            Capteur ca = new Capteur(0, nomCaptTF.getText(), tpsSpinner.getValue() * 1000, new GenerationAleatoireInfini());
            listeTotalCapteur.add(ca);
            configCapteur(ca);
        }


        Stage stage = (Stage) validation.getScene().getWindow();
        stage.close();
    }

    private void configCapteur(Capteur ca) {
        for (CapteurAbstrait c : listeTotalCapteur) {
            if (c instanceof CapteurComplexe) {
                if (((CapteurComplexe) c).getListeCapteur().containsKey(capteur)) {
                    ((CapteurComplexe) c).getListeCapteur().remove(capteur);
                    ((CapteurComplexe) c).ajoutCapteur(ca, 1);
                }
            }
        }
    }

    private void reinit(String messageErreur) {
        AffichageConfigCaptComp.reinit(capteur.getNom(), nomCapt, nomCaptTF);
        nomCapt.setFont(new Font("Arial", 18));
        affichageCapteur();
        comboGenerateur.getSelectionModel().selectFirst();
        minTF.setText("");
        maxTF.setText("");
        vbOptionGenerateur.getChildren().add(new Text(messageErreur));
    }
}
