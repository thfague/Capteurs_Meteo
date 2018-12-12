package Vue;

import Class_Metier.Capteur;
import javafx.scene.control.ListCell;
import java.util.ArrayList;
import java.util.List;

public class DigitalFormatCell extends ListCell<Capteur> {
    List<Capteur> list = new ArrayList<>();

    public DigitalFormatCell(List<Capteur> l) {
        for(int i = 0; i < l.size(); i++) {
            list.add(l.get(i));
        }
        System.out.println(list);
    }

    @Override
    public void updateItem(Capteur item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.getNom());
        }
    }
}
