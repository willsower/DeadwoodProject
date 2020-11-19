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
        Parent root = FXMLLoader.load(getClass().getResource("View/Board.fxml"));
        Scene scene = new Scene(root);

//        systemManager(2);
        stage.setTitle("Board");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Create system for players then run functionality
    public static void systemManager(int numPlayers) {
        SystemManager systemManager = new SystemManager(numPlayers);
        systemManager.run();
    }
}
