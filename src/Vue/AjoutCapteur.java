package Vue;

import Class_Metier.Capteur.CapteurAbstrait;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;

public class AjoutCapteur {
    @FXML
    GridPane gridAjoutC;

    private VBox vb = new VBox();
    private VBox vbOption = new VBox();
    private VBox vbOptionGenerateur = new VBox();
    private Text nomC = new Text("Nom du capteur : ");
    private TextField nomCapteur = new TextField();
    private Text tps = new Text("Temps de changement (en seconde) : ");
    private TextField tpsTF = new TextField();
    private ComboBox comboCapteur = new ComboBox();
    private ComboBox comboGenerateur = new ComboBox();
    private List<CapteurAbstrait> l = new ArrayList<>();
    private Button validation = new Button("Valider");
    Text min = new Text();
    Text max = new Text();
    TextField minTF = new TextField();
    TextField maxTF = new TextField();

    public AjoutCapteur(List<CapteurAbstrait> l){
        this.l = l;
    }

    @FXML
    public void initialize (){
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
        vb.getChildren().add(comboCapteur);
        vb.getChildren().add(nomC);
        vb.getChildren().add(nomCapteur);
        vb.getChildren().add(tps);
        vb.getChildren().add(tpsTF);
        AffichageCapteur();
        vbOptionGenerateur.getChildren().clear();
        min.setText("Minimum : ");
        vbOptionGenerateur.getChildren().add(min);
        vbOptionGenerateur.getChildren().add(minTF);
        max.setText("Maximum : ");
        vbOptionGenerateur.getChildren().add(max);
        vbOptionGenerateur.getChildren().add(maxTF);
        vbOptionGenerateur.getChildren().add(validation);

        gridAjoutC.add(vb, 0, 0);
        gridAjoutC.add(vbOption, 0, 1);
        gridAjoutC.add(vbOptionGenerateur, 0, 2);
    }

    public void AffichageCapteur()
    {
        vbOption.getChildren().clear();
        vbOption.getChildren().add(comboGenerateur);
        comboGenerateur.getSelectionModel().selectedItemProperty().addListener((change, oV, nV) -> {
            if (nV.equals("GenerationAleatoireBorne")) {
                vbOptionGenerateur.getChildren().clear();
                min.setText("Minimum : ");
                vbOptionGenerateur.getChildren().add(min);
                vbOptionGenerateur.getChildren().add(minTF);
                max.setText("Maximum : ");
                vbOptionGenerateur.getChildren().add(max);
                vbOptionGenerateur.getChildren().add(maxTF);
                vbOptionGenerateur.getChildren().add(validation);
            }
            if (change.getValue().equals("GenerationAleatoireReelle")) {
                vbOptionGenerateur.getChildren().clear();
                min.setText("Valeur de départ :");
                vbOptionGenerateur.getChildren().add(min);
                vbOptionGenerateur.getChildren().add(minTF);
                max.setText("Valeur de différence");
                vbOptionGenerateur.getChildren().add(max);
                vbOptionGenerateur.getChildren().add(maxTF);
                vbOptionGenerateur.getChildren().add(validation);
            }
            if (change.getValue().equals("GenerationAleatoireInfini")) {
                vbOptionGenerateur.getChildren().clear();
                vbOptionGenerateur.getChildren().add(validation);
            }
        });

    }

    public void AffichageCapteurComplexe()
    {

    }

}