import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Vue/Accueil.fxml"));
        primaryStage.setTitle("Meteo");
        primaryStage.setScene(new Scene(root, 600, 350));
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}
