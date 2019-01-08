package Vue;

import Class_Metier.Capteur.CapteurAbstrait;
import Class_Metier.Capteur.CapteurComplexe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
    private Text NomCapteur = new Text();
    private List<CapteurAbstrait> listeCapteur = new ArrayList<>();
    private CapteurComplexe digital;
    private List<CapteurAbstrait> listTotalCapteur = new ArrayList<>();
    private List<CapteurAbstrait> listeCNonApproprie;
    private  ObservableList<CapteurAbstrait> olCapteur;


    public AffichageConfig(CapteurComplexe  d, List<CapteurAbstrait> l){
        digital=d;
        listTotalCapteur = l;
    }

    @FXML
    private void initialize(){
        NomCapteur.setText(digital.getNom());
        Font font = new Font("Arial", 18);
        NomCapteur.setFont(font);

        AffichageBoutonAC();
        ChargementListeC();
        gridConfig.add(NomCapteur,0, 0);
        gridConfig.add(vb, 1, 1);
        gridConfig.setHalignment(NomCapteur, HPos.CENTER);

    }

    private void ChargementListeC() {
        listeCapteur.clear();
        Set<Map.Entry<CapteurAbstrait, Integer>> setlisteCapteur = digital.getListeCapteur().entrySet();
        Iterator<Map.Entry<CapteurAbstrait, Integer>> it = setlisteCapteur.iterator();
        while (it.hasNext()) {
            Map.Entry<CapteurAbstrait, Integer> e = it.next();
            listeCapteur.add(e.getKey());
        }

        olCapteur = FXCollections.observableList(listeCapteur);
        ListView<CapteurAbstrait> listC = new ListView<>(olCapteur);


        listC.setCellFactory(new Callback<ListView<CapteurAbstrait>, ListCell<CapteurAbstrait>>() {
            @Override
            public ListCell<CapteurAbstrait> call(ListView<CapteurAbstrait> param) {
                return new DigitalFormatCell(listeCapteur);
            }
        });

        listC.getSelectionModel().selectedItemProperty().addListener((listeCapteur, oV, nV) -> {
            vb.getChildren().clear();
            AffichageBoutonAC();
            AffichageBoutonS(nV, olCapteur);
        });

        gridConfig.add(listC, 0, 1);
    }

    private void AffichageBoutonS(CapteurAbstrait c, ObservableList<CapteurAbstrait> olCapteur) {
        Button buttonSupp = new Button("Supprimer " + c.getNom());
        vb.getChildren().add(buttonSupp);
        buttonSupp.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                olCapteur.remove(c);
                digital.getListeCapteur().remove(c);
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

                for(int i=0; i < listTotalCapteur.size(); i++) {
                    if(!olCapteur.contains(listTotalCapteur.get(i))){
                        if(!listTotalCapteur.get(i).getNom().equals(digital.getNom())) {
                            listeCNonApproprie.add(listTotalCapteur.get(i));
                        }
                    }
                }

                ObservableList<CapteurAbstrait> oCapteur = FXCollections.observableList(listeCNonApproprie);
                ListView<CapteurAbstrait> listVCapteur = new ListView<>(oCapteur);


                listVCapteur.setCellFactory(new Callback<ListView<CapteurAbstrait>, ListCell<CapteurAbstrait>>() {
                    @Override
                    public ListCell<CapteurAbstrait> call(ListView<CapteurAbstrait> param) {
                        return new DigitalFormatCell(listeCNonApproprie);
                    }
                });

                listVCapteur.getSelectionModel().selectedItemProperty().addListener((listeCNonApproprie, oV, nV) -> {
                    vb.getChildren().clear();
                    AffichageBoutonAC();
                    vb.getChildren().add(listVCapteur);
                    AffichageBoutonA(nV, oCapteur);
                });

                vb.getChildren().clear();
                AffichageBoutonAC();
                vb.getChildren().add(listVCapteur);
            }
        });
    }

    private void AffichageBoutonA(CapteurAbstrait c, ObservableList<CapteurAbstrait> oCapteur) {
        Button buttonAjout = new Button("Ajouter " + c.getNom());
        vb.getChildren().add(buttonAjout);
        buttonAjout.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               digital.ajoutCapteur(c, 1);
               ChargementListeC();
               vb.getChildren().clear();
               AffichageBoutonAC();
            }
        });
    }
}
