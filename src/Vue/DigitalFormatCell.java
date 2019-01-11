package Vue;

import Class_Metier.Capteur.CapteurAbstrait;
import javafx.scene.control.ListCell;
import java.util.ArrayList;
import java.util.List;

public class DigitalFormatCell extends ListCell<CapteurAbstrait> {
    List<CapteurAbstrait> listeCapteur = new ArrayList<>();

    public DigitalFormatCell(List<CapteurAbstrait> listeCapteur2) {
        for(int i = 0; i < listeCapteur2.size(); i++) {
            listeCapteur.add(listeCapteur2.get(i));
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
