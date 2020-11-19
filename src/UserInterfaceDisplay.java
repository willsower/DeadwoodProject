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
    @FXML private ImageView deck;
    @FXML private ImageView trainStationCard;
    @FXML private ImageView jailCard;
    @FXML private ImageView mainStreetCard;
    @FXML private ImageView generalStoreCard;
    @FXML private ImageView saloonCard;
    @FXML private ImageView ranchCard;
    @FXML private ImageView bankCard;
    @FXML private ImageView secretHideoutCard;
    @FXML private ImageView churchCard;
    @FXML private ImageView hotelCard;
    @FXML private Label displayText;
    @FXML private TextField userInput;
    @FXML private TextField displayNum; //to test take out later
    @FXML private Button submitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boardImage.setImage(Board.getInstance().getBoardImage());
        boardImage.setVisible(false);
        deck.setVisible(false);
    }

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

        setUpBoard(Integer.parseInt(playerNum));
    }

    public void setUpBoard(int playerNum) {
        boardImage.setVisible(true);
        deck.setVisible(true);

        trainStationCard.setImage(Deck.getInstance().getBackOfCardSmall());
        jailCard.setImage(Deck.getInstance().getBackOfCardSmall());
        mainStreetCard.setImage(Deck.getInstance().getBackOfCardSmall());
        generalStoreCard.setImage(Deck.getInstance().getBackOfCardSmall());
        saloonCard.setImage(Deck.getInstance().getBackOfCardSmall());
        ranchCard.setImage(Deck.getInstance().getBackOfCardSmall());
        bankCard.setImage(Deck.getInstance().getBackOfCardSmall());
        secretHideoutCard.setImage(Deck.getInstance().getBackOfCardSmall());
        churchCard.setImage(Deck.getInstance().getBackOfCardSmall());
        hotelCard.setImage(Deck.getInstance().getBackOfCardSmall());

        //close window --take out when continuing to run game
//        Stage stage = (Stage) submitButton.getScene().getWindow();
//        stage.close();

//        systemManager(playerNum);
    }

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
