package Vue;

import Class_Metier.CapteurAbstrait;
import Class_Metier.CapteurComplexe;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.*;


public class AffichageConfig {
    @FXML
    GridPane gridAccueil;
    GridPane gridAffich = new GridPane();;
    private Text NomCapteur = new Text();
    private List<CapteurAbstrait> listeCapteur = new ArrayList<>();
    private CapteurComplexe digital;


    public AffichageConfig(CapteurComplexe  d){
        digital=d;
    }

    @FXML
    private void initialize(){
        NomCapteur.setText(digital.getNom());
        Font font = new Font("Arial", 18);
        NomCapteur.setFont(font);

        Set<Map.Entry<CapteurAbstrait, Integer>> setlisteCapteur = digital.getListeCapteur().entrySet();
        Iterator<Map.Entry<CapteurAbstrait, Integer>> it = setlisteCapteur.iterator();
        while (it.hasNext()) {
            Map.Entry<CapteurAbstrait, Integer> e = it.next();
            listeCapteur.add(e.getKey());
        }

        ObservableList<CapteurAbstrait> olCapteur = FXCollections.observableList(listeCapteur);
        ListView<CapteurAbstrait> listC = new ListView<>(olCapteur);


        listC.setCellFactory(new Callback<ListView<CapteurAbstrait>, ListCell<CapteurAbstrait>>() {
           @Override
           public ListCell<CapteurAbstrait> call(ListView<CapteurAbstrait> param) {
               return new DigitalFormatCell(listeCapteur);
           }
        });

        listC.getSelectionModel().selectedItemProperty().addListener((listeCapteur, oV, nV) -> {
           AffichageBoutonS(nV, olCapteur);
        });

        gridAccueil.add(NomCapteur,0, 0);
        gridAccueil.add(gridAffich, 1, 1);
        gridAccueil.setHalignment(NomCapteur, HPos.CENTER);
        gridAccueil.add(listC, 0, 1);

        Button buttonAjout = new Button("Ajouter un capteur");
        gridAffich.add(buttonAjout, 0, 1 );
        buttonAjout.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //  NewWindow("affichageDigital.fxml", "Affichage format digital", d);
            }
        });
    }

    private void AffichageBoutonS(CapteurAbstrait c, ObservableList<CapteurAbstrait> olCapteur) {
        Button buttonSupp = new Button("Supprimer " + c.getNom());
        gridAffich.add(buttonSupp, 0, 3 );
        buttonSupp.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(olCapteur.size() == 1)
                {
                    gridAffich.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 3);
                }
                olCapteur.remove(c);
                digital.getListeCapteur().remove(c);
            }
        });
    }
}
