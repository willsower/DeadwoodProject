/*
    UserInterface class
    Purpose: Display user prompts
    Singleton = true
*/

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ResourceBundle;

public class UserInterfaceDisplay implements Initializable {

    private static UserInterfaceDisplay instance = null;
    // Create instance
    public static UserInterfaceDisplay getInstance() {
        if (instance == null) {
            instance = new UserInterfaceDisplay();
        }
        return instance;
    }

    @FXML private ImageView boardImage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("RUN HEREE???");
        boardImage.setImage(Board.getInstance().getBoardImage());
        boardImage.setVisible(false);
    }

    @FXML private Label displayText;
    @FXML private TextField userInput;
    @FXML private TextField displayNum; //to test take out later
    @FXML private Button submitButton;
    //String playerNum;

    //want this function to set the number of players and continue to the game
    //right now all it does is print the string the user inputs then display board image
    public void submitPlayers(ActionEvent event) {
        String playerNum = userInput.getText();
        //setPlayerNum(playerNum);
        displayNum.setText("number of players is " + playerNum);

        submitButton.setVisible(false); //may also nee to disable all of these
        displayText.setVisible(false);
        userInput.setVisible(false);
        displayNum.setVisible(false);
        //userInput.setDisable(true);

        boardImage.setVisible(true);

        //close window --take out when continuing to run game
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();

        systemManager(Integer.parseInt(playerNum));

    }

//    public String getPlayerNum() {
//        return playerNum;
//    }
//    public void setPlayerNum(String num) {
//        playerNum = num;
//    }

    // Create system for players then run functionality
    public static void systemManager(int numPlayers) {
        SystemManager systemManager = new SystemManager(numPlayers);
        systemManager.run();
    }


    // Display winners of game
    public void displayWinner(Integer[] finals) {
        System.out.println("The winner(s) are: ");
        for (int i = 0; i < finals.length; i++) {
            if (finals[i] != 0) {
                System.out.println("  Player " + finals[i]);
            }
        }
    }

    public void playerUpgrade(Player player) {
        System.out.println("\nJust upgraded!");
        System.out.println("  Player rank: " + player.getLevel());
        System.out.println("  Player dollars: " + player.getDollar());
        System.out.println("  Player credits: " + player.getCredit());
    }

    // Display Casting Office Level upgrades
    public void displayCastingOffice(Player player) {
        System.out.println("Welcome to the Casting Office!");
        System.out.println("  Level 2 | Dollar: " + Upgrade.getInstance().levelTwo.dollar + " Credit: "
                + Upgrade.getInstance().levelTwo.credit);
        System.out.println("  Level 3 | Dollar: " + Upgrade.getInstance().levelThree.dollar + " Credit: "
                + Upgrade.getInstance().levelThree.credit);
        System.out.println("  Level 4 | Dollar: " + Upgrade.getInstance().levelFour.dollar + " Credit: "
                + Upgrade.getInstance().levelFour.credit);
        System.out.println("  Level 5 | Dollar: " + Upgrade.getInstance().levelFive.dollar + " Credit: "
                + Upgrade.getInstance().levelFive.credit);
        System.out.println("  Level 6 | Dollar: " + Upgrade.getInstance().levelSix.dollar + " Credit: "
                + Upgrade.getInstance().levelSix.credit);

        displayPlayerInfo(player);
    }

    // Display player info
    public void displayPlayerInfo(Player player) {
        System.out.println("  Player " + player.getPlayerPriority());
        System.out.println("    Player level: " + player.getLevel());
        System.out.println("    Player dollars: " + player.getDollar());
        System.out.println("    Player credits: " + player.getCredit());
        System.out.println("    Player practice chips " + player.getPracticeChip());
    }
}
