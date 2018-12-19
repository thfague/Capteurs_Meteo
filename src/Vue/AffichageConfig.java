package Vue;

import Class_Metier.CapteurAbstrait;
import Class_Metier.CapteurComplexe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class AffichageConfig {
    @FXML
    GridPane gridAccueil;
    private Text NomCapteur;
    private List<CapteurAbstrait> listeCapteur;
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
                AffichageBoutonAS(nV);
            });
            gridAccueil.add(NomCapteur,0, 0);

            gridAccueil.add(listC, 0, 1);
    }

    private void AffichageBoutonAS(CapteurAbstrait c) {
        Button buttonAjout = new Button("Ajouter un capteur");
        gridAccueil.add(buttonAjout, 1, 1 );
        buttonAjout.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
              //  NewWindow("affichageDigital.fxml", "Affichage format digital", d);
            }
        });
        Button buttonThermo = new Button("Supprimer" + c.getNom());
        gridAccueil.add(buttonThermo, 1, 3 );
        buttonThermo.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                digital.getListeCapteur().remove(c);
            }
        });
    }
}
