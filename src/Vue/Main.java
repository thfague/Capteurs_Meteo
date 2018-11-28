package Vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Fenêtre d'accueil
        Parent root = FXMLLoader.load(getClass().getResource("FenetreAccueil.fxml"));
        primaryStage.setTitle("Meteo");
        primaryStage.setScene(new Scene(root, 400, 325));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
