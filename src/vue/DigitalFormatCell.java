package vue;

import class_Metier.capteur.CapteurAbstrait;
import javafx.scene.control.ListCell;
import java.util.ArrayList;
import java.util.List;

public class DigitalFormatCell extends ListCell<CapteurAbstrait> {

    public DigitalFormatCell(List<CapteurAbstrait> listeC) {
        List<CapteurAbstrait> listeCapteur = new ArrayList<>();
        listeCapteur = listeC;
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
