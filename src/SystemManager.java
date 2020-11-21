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
import javafx.scene.Node;

import java.net.URL;
import java.util.*;

public class SystemManager implements Initializable {
    private Player[] players;
    private int numPlayer;
    int cardsFinished = 0;
    private Player currentP;
    private static SystemManager instance = null;
    private Double lastX = null;
    private Double lastY = null;

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
    @FXML private Button submitButton;

    //Action Buttons
    @FXML private Button actButton;
    @FXML private Button rehearseButton;
    @FXML private Button upgradeButton;
    @FXML private Button rollDieButton;
    @FXML private Label actPrintLabel;
    @FXML private Button totrailersFromMainStreet;
    @FXML private Button toSaloonFromMainStreet;
    @FXML private Button toJailFromMainStreet;
    @FXML private Button toMainStreetFromtrailers;
    @FXML private Button toSaloonFromtrailers;
    @FXML private Button toHotelFromtrailers;
    @FXML private Button toChurchFromHotel;
    @FXML private Button totrailersFromHotel;
    @FXML private Button toBankFromChurch;
    @FXML private Button toSecretHideoutFromChurch;
    @FXML private Button toHotelFromChurch;
    @FXML private Button toSaloonFromBank;
    @FXML private Button toRanchFromBank;
    @FXML private Button toChurchFromBank;
    @FXML private Button toHotelFromBank;
    @FXML private Button totrailersFromSaloon;
    @FXML private Button toBankFromSaloon;
    @FXML private Button toGeneralStoreFromSaloon;
    @FXML private Button toMainStreetFromSaloon;
    @FXML private Button toChurchFromSecretHideout;
    @FXML private Button toRanchFromSecretHideout;
    @FXML private Button toofficeFromSecretHideout;
    @FXML private Button toBankFromRanch;
    @FXML private Button toGeneralStoreFromRanch;
    @FXML private Button toofficeFromRanch;
    @FXML private Button toSecretHideoutFromRanch;
    @FXML private Button toRanchFromGeneralStore;
    @FXML private Button toSaloonFromGeneralStore;
    @FXML private Button toJailFromGeneralStore;
    @FXML private Button toTrainStationFromGeneralStore;
    @FXML private Button toMainStreetFromJail;
    @FXML private Button toGeneralStoreFromJail;
    @FXML private Button toTrainStationFromJail;
    @FXML private Button toJailFromTrainStation;
    @FXML private Button toGeneralStoreFromTrainStation;
    @FXML private Button toofficeFromTrainStation;
    @FXML private Button toTrainStationFromoffice;
    @FXML private Button toRanchFromoffice;
    @FXML private Button toSecretHideoutFromoffice;

    //Player pieces
    @FXML private ImageView player1;
    @FXML private ImageView player2;
    @FXML private ImageView player3;
    @FXML private ImageView player4;
    @FXML private ImageView player5;
    @FXML private ImageView player6;
    @FXML private ImageView player7;
    @FXML private ImageView player8;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boardImage.setImage(Board.getInstance().getBoardImage());
        boardImage.setVisible(false);
        deck.setVisible(false);
        makeButtonVisible(false,false,false,false);
    }

    //want this function to set the number of players and continue to the game
    //right now all it does is print the string the user inputs then display board image
    public void submitPlayers(ActionEvent event) {
        int numberPlayers = 0;
        String val = userInput.getText();
        try {
            numberPlayers = Integer.parseInt(val);
        } catch (NumberFormatException e) {
        }

        if (numberPlayers >= 2 && numberPlayers <= 8) {

            submitButton.setVisible(false); //may also nee to disable all of these
            displayText.setVisible(false);
            userInput.setVisible(false);

            init(Integer.parseInt(val));
            run();
        }
    }

    public void actButtonAction(ActionEvent event) {
        makeButtonVisible(false,false,false, true);
    }
    
    public void rehearseButtonAction(ActionEvent event) {
        OnTurn.getInstance().rehearse(currentP);
        makeButtonVisible(false, false,false,false);
    }
    public void upgradeButtonAction(ActionEvent event) {
        makeButtonVisible(false,false,false, false);
    }

    public void rollDieAction(ActionEvent event) {
        rollDieButton.setVisible(false);
        if( OnTurn.getInstance().act(currentP)) {
            cardsFinished++;
        }
    }

    public void makeButtonVisible(boolean act, boolean rehearse, boolean upgrade, boolean roll) {
        actButton.setVisible(act);
        rehearseButton.setVisible(rehearse);
        upgradeButton.setVisible(upgrade);
        rollDieButton.setVisible(roll);
    }

    public void printLabel(String str) {
        actPrintLabel.setText(str);
    }

    public void onMove(ActionEvent event) {
        System.out.println(((Node) event.getSource()).getId());
        OnTurn.getInstance().movePlayer(currentP);
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
            int num = i + 1;
            switch (num) {
                case 1 -> player1.setImage(players[i].getPlayerImage());
                case 2 -> player2.setImage(players[i].getPlayerImage());
                case 3 -> player3.setImage(players[i].getPlayerImage());
                case 4 -> player4.setImage(players[i].getPlayerImage());
                case 5 -> player5.setImage(players[i].getPlayerImage());
                case 6 -> player6.setImage(players[i].getPlayerImage());
                case 7 -> player7.setImage(players[i].getPlayerImage());
                default -> player8.setImage(players[i].getPlayerImage());
            }
        }
    }

    public void addButtons(Set set, String name) {
        switch (name) {
            case "Main Street":
                set.getOut().add(totrailersFromMainStreet);
                set.getOut().add(toSaloonFromMainStreet);
                set.getOut().add(toJailFromMainStreet);
                break;
            case "trailers":
                set.getOut().add(toMainStreetFromtrailers);
                set.getOut().add(toSaloonFromtrailers);
                set.getOut().add(toHotelFromtrailers);
                break;
            case "Hotel":
                set.getOut().add(toChurchFromHotel);
                set.getOut().add(totrailersFromHotel);
                break;
            case "Church":
                set.getOut().add(toBankFromChurch);
                set.getOut().add(toSecretHideoutFromChurch);
                set.getOut().add(toHotelFromChurch);
                break;
            case "Bank":
                set.getOut().add(toSaloonFromBank);
                set.getOut().add(toRanchFromBank);
                set.getOut().add(toChurchFromBank);
                set.getOut().add(toHotelFromBank);
                break;
            case "Saloon":
                set.getOut().add(totrailersFromSaloon);
                set.getOut().add(toBankFromSaloon);
                set.getOut().add(toGeneralStoreFromSaloon);
                set.getOut().add(toMainStreetFromSaloon);
                break;
            case "Secret Hideout":
                set.getOut().add(toChurchFromSecretHideout);
                set.getOut().add(toRanchFromSecretHideout);
                set.getOut().add(toofficeFromSecretHideout);
                break;
            case "Ranch":
                set.getOut().add(toBankFromRanch);
                set.getOut().add(toGeneralStoreFromRanch);
                set.getOut().add(toofficeFromRanch);
                set.getOut().add(toSecretHideoutFromRanch);
                break;
            case "General Store":
                set.getOut().add(toRanchFromGeneralStore);
                set.getOut().add(toSaloonFromGeneralStore);
                set.getOut().add(toJailFromGeneralStore);
                set.getOut().add(toTrainStationFromGeneralStore);
                break;
            case "Jail":
                set.getOut().add(toMainStreetFromJail);
                set.getOut().add(toGeneralStoreFromJail);
                set.getOut().add(toTrainStationFromJail);
                break;
            case "Train Station":
                set.getOut().add(toJailFromTrainStation);
                set.getOut().add(toGeneralStoreFromTrainStation);
                set.getOut().add(toofficeFromTrainStation);
                break;
            default:
                set.getOut().add(toTrainStationFromoffice);
                set.getOut().add(toRanchFromoffice);
                set.getOut().add(toSecretHideoutFromoffice);
                break;
        }
    }

    // Set buttons visible for when moving
    @FXML
    public void showButton(ArrayList<Button> buttons) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setVisible(true);
        }
    }

    // Turn manager initializes all players
    public void init(int numPlayer) {
        this.numPlayer = numPlayer;

        // Init num players array
        players = new Player[numPlayer];

        // Array of dice colors
        String[] playerDie = new String[]{"b", "c", "g", "o", "p", "r", "v", "y"};

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
        for (int i = 0; i < days; i++) {
            cardsFinished = 0;

            resetAll(list, i + 1);

//            do {
                currentP = list[player];
                // If card has finished increment cards finished
                turn.turn(list[player]);

//                player++; // Next player turn
//
//                // Reset back to player 1
//                if (player == list.length) {
//                    player = 0;
//                }
//
//            } while (cardsFinished < 9); /* !9/10 cards */
        }
        // Calculate end score
        endFunction();
    }
}
