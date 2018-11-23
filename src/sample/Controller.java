package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Controller {
    @FXML
    public void ClickCapteur(ActionEvent actionEvent) {
        Button button1 = new Button("Digital");
        button1.setAlignment(Pos.TOP_RIGHT);
        
    }


    @FXML
    protected void ClickOpen(ActionEvent actionEvent) {

        /*NewWindow("digital.fxml", "Affichage format Digital");
        NewWindow("thermo.fxml", "Affichage format Thermomètre");
        NewWindow("imgMeteo.fxml", "Affichage format Image");*/

    }


    public void NewWindow(String nameFile, String title)
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(nameFile));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 300, 275));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
