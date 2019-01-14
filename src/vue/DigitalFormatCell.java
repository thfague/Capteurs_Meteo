package vue;

import class_Metier.capteur.CapteurAbstrait;
import javafx.scene.control.ListCell;

import java.util.ArrayList;
import java.util.List;

public class DigitalFormatCell extends ListCell<CapteurAbstrait> {
    private List<CapteurAbstrait> listeCapteur = new ArrayList<>();

    public DigitalFormatCell(List<CapteurAbstrait> listeC) {
        for(int i = 0; i < listeC.size(); i++) {
            listeCapteur.add(listeC.get(i));
        }
    }

    @Override
    public void updateItem(CapteurAbstrait capteur, boolean vide) {
        super.updateItem(capteur, vide);

        if (vide || capteur == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(capteur.getNom());
        }
    }
}
