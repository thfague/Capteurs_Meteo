package Vue;

import Class_Metier.Digital;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.objects.ArrayBufferView.length;

public class DigitalFormatCell extends ListCell<Digital> {
    List<Digital> list = new ArrayList<>();

    public DigitalFormatCell(List<Digital> l) {
        for(int i = 0; i < l.size(); i++) {
            list.add(l.get(i));
        }
        System.out.println(list);
    }

    @Override
    public void updateItem(Digital item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.getNom());
        }
    }



}
