/*
    SystemManager Class
    Purpose: This class is the power of this game. It will create the player objects, manage
             how many days will be played, and will trigger the onTurn functions for each 
             player when it's their turn. Will also calculate end of game metrics by calling
             ScoringManager and will reset each day by calling helper functions
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.*;


public class SystemManager implements Initializable {
    private Player[] players;
    private int numPlayer;
    private static SystemManager instance = null;

    // create instance
    public static SystemManager getInstance() {
        if (instance == null) {
            instance = new SystemManager();
        }
        return instance;
    }

    @FXML
    private ImageView boardImage;
    @FXML private ImageView deck;

    // Card Image Views
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

    // Player's display information
    @FXML private Label currentPlayer;
    @FXML private Label playerDieColor;
    @FXML private Label playerDollar;
    @FXML private Label playerCredit;
    @FXML private Label playerPracticeChip;

    // Text Display
    @FXML private Label dayDisplay; // Display current day
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
        String val = userInput.getText();
        //setPlayerNum(playerNum);

        //String val;
        int numberPlayers = 0;

        val = userInput.getText();
        try {
            numberPlayers = Integer.parseInt(val);
        } catch (NumberFormatException e) {
        }

        if (numberPlayers >= 2 && numberPlayers <= 8) {
            displayNum.setText("number of players is " + val);

            submitButton.setVisible(false); //may also nee to disable all of these
            displayText.setVisible(false);
            userInput.setVisible(false);
            displayNum.setVisible(false);

            init(Integer.parseInt(val));
            run();
        }
    }

    // Sets board up at each day
    public void setUpBoard(int day) {
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

        dayDisplay.setText("Day " + day);

        //Add players into trailers
        for (int i = 0; i < numPlayer; i++) {
            ImageView imageView = new ImageView();
            //Setting image to the image view
            imageView.setImage(players[i].getPlayerImage());
            //Setting the image view parameters
            imageView.setX(10);
            imageView.setY(10);
            imageView.setFitWidth(575);
            imageView.setPreserveRatio(true);
        }

        //close window --take out when continuing to run game
//        Stage stage = (Stage) submitButton.getScene().getWindow();
//        stage.close();
    }

    // Turn manager initializes all players
    public void init(int numPlayer) {
        this.numPlayer = numPlayer;

        // Init num players array
        players = new Player[numPlayer];

        // Array of dice colors
        String[] playerDie = new String[]{"b", "c", "g", "o", "p", "r", "v", "w", "y"};

        // Populate players
        for (int i = 0; i < numPlayer; i++) {
            switch (numPlayer) {
                case 5:
                    players[i] = new Player(i + 1, 1, 0, 2, "trailer", playerDie[i]);
                    break;
                case 6:
                    players[i] = new Player(i + 1, 1, 0, 4, "trailer", playerDie[i]);
                    break;
                case 7:
                    players[i] = new Player(i + 1, 2, 0, 0, "trailer", playerDie[i]);
                    break;
                case 8:
                    players[i] = new Player(i + 1, 2, 0, 0, "trailer", playerDie[i]);
                    break;
                default:
                    players[i] = new Player(i + 1, 1, 0, 0, "trailer", playerDie[i]);
                    break;
            }
        }
    }

    // Get function
    public int getNumPlayer() {
        return numPlayer;
    }

    public Player[] getPlayerList() {
        return players;
    }

    // Calculate days of play
    private int calculateDaysPlayed() {
        if (getNumPlayer() == 2 || getNumPlayer() == 3) {
            return 3;
        }
        return 4;
    }

    // Set zero array to 0
    public Integer[] zero(Integer[] curr) {
        for (int i = 0; i < curr.length; i++) {
            curr[i] = 0;
        }
        return curr;
    }

    // Get the final scores
    public void endFunction() {
        Player[] player = getPlayerList();
        Integer[] whoWon = new Integer[getNumPlayer()];
        int index = 0;

        System.out.println("\n=========");
        System.out.println("Calculating end score");
        for (int i = 0; i < player.length; i++) {
/**/            UserInterfaceDisplay.getInstance().displayPlayerInfo(player[i]);
        }
        System.out.println("\n\n");

        // Set everything to 0
        whoWon = zero(whoWon);

        // Set final score for players
        for (int i = 0; i < getNumPlayer(); i++) {
            player[i].setFinalScore(ScoringManager.getInstance().finalScore(player[i].getLevel(), player[i].getDollar(),
                    player[i].getCredit()));

            // First player goes in
            if (whoWon[0] == 0) {
                whoWon[0] = 1;

                // If player score is higher than current
            } else if (player[i].getFinalScore() != 0
                    && player[i].getFinalScore() > player[whoWon[index] - 1].getFinalScore()) {
                // If there are no ties
                if (whoWon[1] == 0) {
                    whoWon[index] = i + 1;
                    // Else there is a tie
                } else {
                    index = 0;
                    whoWon = zero(whoWon);
                    whoWon[index] = i + 1;
                }
                // Else if player has a tie with another player, put them in list
            } else if (player[i].getFinalScore() == player[whoWon[index] - 1].getFinalScore()) {
                index++;
                whoWon[index] = i + 1;
            }
        }

/**/        UserInterfaceDisplay.getInstance().displayWinner(whoWon);
    }

    // Resetall function will be called at the start of each game
    // It will reset the variables in other classes, put players back into trailers
    // Put cards on the appropriate sets
    public void resetAll(Player[] list, int day) {
        // Reset player info
        for (int i = 0; i < getNumPlayer(); i++) {
            list[i].resetPlayers(true); // parameter is for notEndOfCard
        }

        Hashtable<String, Set> board = Board.getInstance().getBoard();

        Enumeration<Set> values = board.elements();
        int ind = 0;
        // iterate through values
        while (values.hasMoreElements()) {
            Set set = values.nextElement();
            if (ind < 10) {
                set.resetSetDay();
            }
            ind++;
        }
        Board.getInstance().assignCardToSet(Deck.getInstance().getCardShuffle(), day);
        setUpBoard(day);
    }

    // This is the run function, will play for x amount of days
    // and iterate through a do-while loop until the amount of cards
    // have finished for that day.
    public void run() {
        OnTurn turn = new OnTurn();
        Player[] list = getPlayerList();
        int player = 0;

        int days = calculateDaysPlayed();

        // Run for each day
//        for (int i = 0; i < days; i++) {
            int cardsFinished = 0;
        resetAll(list, 1);
//            resetAll(list, i + 1);

//            do {
//                // If card has finished increment cards finished
//                if (turn.turn(list[player])) {
//                    cardsFinished++;
//                }
//
//                player++; // Next player turn
//
//                // Reset back to player 1
//                if (player == list.length) {
//                    player = 0;
//                }
//
//            } while (cardsFinished < 9); /* !9/10 cards */
//        }
        // Calculate end score
        endFunction();
    }
}
