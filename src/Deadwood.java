/*
    Deadwood Class
    Purpose: Initiates the game, asks how many players, then calls
             system manager to setup everything.
*/
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Deadwood extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/Board.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setTitle("Deadwood");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
