package Vue;

import Class_Metier.Digital;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;

public class DigitalFormatCell extends ListCell<Digital> {

    @Override
    public void updateItem(Digital item, boolean empty) {
        super.updateItem(item, empty);
        //float value = item.getValeur();

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText("Capteur n° ?");
        }
    }
}
