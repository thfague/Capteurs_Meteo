package Vue;

import Class_Metier.Capteur.CapteurAbstrait;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

public class AjoutCapteur {
    @FXML
    GridPane gridAccueil;
    @FXML
    TextField nomCapteur;
    //@FXML
    JComboBox comboCapteur;
    private JComboBox comboGenerateur;
    List<CapteurAbstrait> l = new ArrayList<>();

    public AjoutCapteur(List<CapteurAbstrait> l){
        this.l = l;
        comboGenerateur = new JComboBox();
        comboCapteur = new JComboBox();
        Button validation = new Button("Valider");
        comboCapteur.addItem("Capteur");
        comboCapteur.addItem("CapteurComplexe");

        comboGenerateur.addItem("GenerationAleatoireBorne");
        comboGenerateur.addItem("GenerationAleatoireReelle");
        comboGenerateur.addItem("GenerationAleatoireInfini");

        //gridAccueil.add(comboCapteur,0,0);
        gridAccueil.add(validation, 1, 2);
    }

    @FXML
    public void initialize (){




    }
}