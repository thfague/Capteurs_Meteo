package Vue;

import Class_Metier.Capteur;
import Class_Metier.CapteurAbstrait;
import javafx.scene.control.ListCell;
import java.util.ArrayList;
import java.util.List;

public class DigitalFormatCell extends ListCell<CapteurAbstrait> {
    List<CapteurAbstrait> list = new ArrayList<>();

    public DigitalFormatCell(List<CapteurAbstrait> l) {
        for(int i = 0; i < l.size(); i++) {
            list.add(l.get(i));
        }
        System.out.println(list);
    }

    @Override
    public void updateItem(CapteurAbstrait item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.getNom());
        }
    }
}
