package sample;

import Class_Metier.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        List<Capteur> listCapteur = new ArrayList<>();
        listCapteur.add(new Digital(20));
        listCapteur.add(new Digital(6));
        listCapteur.add(new Digital(18));
        for (int i = 0; i<?????(listCapteur); i++) { //Créer et afficher un bouton pour chaque capteur pour ensuite cliquer dessus et choisir le type d'affichage, il faut avoir la longueur de la liste
            Button button = new Button("Test");
            grid.add(button,0,i);
        }

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Meteo");
        primaryStage.setScene(new Scene(grid, 800, 650)); //Si tu veux réutiliser le root pour utiliser le fxml, enlève le commentaire et change "grid" en "root"
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
