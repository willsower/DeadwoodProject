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
    //Stage startWindow;
    //Button submit;

    @Override
    public void start(Stage stage) throws Exception {
        //Parent root1 = FXMLLoader.load(getClass().getResource("View/playerNumber.fxml"));
        //Scene scene2 = new Scene(root1);

        Parent root = FXMLLoader.load(getClass().getResource("View/Board.fxml"));
        Scene scene = new Scene(root);
        //Board.getInstance();
        //@FXML boardImage;
        //boardImage.setImage(Board.getInstance().getBoardImage());
//        systemManager(2);
        //stage.setTitle("playerNum");
        //stage.setScene(scene2);
        //stage.show();

        stage.setTitle("Board");
        stage.setScene(scene);
        stage.show();


        //create window
        //startWindow = stage;
        //startWindow.setTitle("Player Button");

        //TextField playerNumInput = new TextField();

        //button = new Button("Submit");
        //button.setOnAction(e -> System.out.println(playerNumInput.getText()));

    }

    public static void main(String[] args) {
        launch(args);
//        Scanner myOb = new Scanner(System.in);
//        String val = args[0];
//        int numberPlayers = 0;
//	    try {
//            numberPlayers = Integer.parseInt(val);
//        } catch (NumberFormatException nfe) {
//        }
//
//        while (!(numberPlayers >= 2 && numberPlayers <= 8)) {
//            System.out.println("How many players? (2 - 8) ");
//            val = myOb.nextLine();
//            try {
//                numberPlayers = Integer.parseInt(val);
//            } catch (NumberFormatException e) {
//            }
//        }
//        systemManager(numberPlayers);

    }

    // Create system for players then run functionality
    public static void systemManager(int numPlayers) {
        SystemManager systemManager = new SystemManager(numPlayers);
        systemManager.run();
    }
}
