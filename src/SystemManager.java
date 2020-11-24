/*
    SystemManager Class
    Purpose: This class is the power of this game. It will create the player objects, manage
             how many days will be played, and will trigger the onTurn functions for each 
             player when it's their turn. Will also calculate end of game metrics by calling
             ScoringManager and will reset each day by calling helper functions
*/

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.*;

public class SystemManager implements Initializable {
    private Player[] players;
    private int numPlayer;
    int cardsFinished = 0;
    private Player currentP;
    int rankChoice;
    boolean dollarVisible;
    boolean creditVisible;
    private static SystemManager instance = null;
    private int player = 0;
    private int day = 1;

    // create instance
    public static SystemManager getInstance() {
        if (instance == null) {
            instance = new SystemManager();
        }
        return instance;
    }

    @FXML private ImageView boardImage;
    @FXML private ImageView deck;

    @FXML private GridPane backOfCardGrid;
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

    //upgrade buttons
    @FXML private Button upgradeRankButton;
    @FXML private Button payWDollarButton;
    @FXML private Button payWCreditButton;

    ObservableList<Integer> list = FXCollections.observableArrayList();
    @FXML private ChoiceBox<Integer> upgradeOptions;


    @FXML private Label actPrintLabel; //print to user success, fail, etc..
    @FXML private Button nextPlayer;

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

    // Pane values
    @FXML private Pane trailer;
    @FXML private Pane office;
    @FXML private Pane mainStreet;
    @FXML private Pane saloon;
    @FXML private Pane hotel;
    @FXML private Pane ranch;
    @FXML private Pane generalStore;
    @FXML private Pane trainStation;
    @FXML private Pane secretHideout;
    @FXML private Pane jail;
    @FXML private Pane church;
    @FXML private Pane bank;

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
        makeButtonVisible(false, false, false, false); /* DONT THINK ROLL IS NEEDED */
        nextPlayer.setVisible(false);


        rollDieButton.setVisible(false);

        //upgradeOptions.setValue(0);
        upgradeOptions.setVisible(false); // may also need to disable
        upgradeRankButton.setVisible(false);
        payWDollarButton.setVisible(false);
        payWCreditButton.setVisible(false);
    }

    // Set buttons visible for when moving
    @FXML
    public void showButton(String location, boolean val) {
        Pane obj = getButtonLocation(location);
        if (val) {
            obj.toFront();
        }
        for (int i = 0; i < obj.getChildren().size(); i++) {
            if (obj.getChildren().get(i).getAccessibleRole().compareTo(AccessibleRole.BUTTON) == 0) {
                obj.getChildren().get(i).setVisible(val);
            }
        }
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
            currentP = getPlayerList()[0];

            boardImage.setVisible(true);
            System.out.print("test 1");
            setUpBoard(day);


            turn(getPlayerList()[0]);
            System.out.print("test 2");
        }
    }

    public void actButtonAction(ActionEvent event) {
        makeButtonVisible(false,false,false, true);
        makeButtonVisible(false,false,false);
        rollDieButton.setVisible(true);

    }

    public void rollDieAction(ActionEvent event) {
        rollDieButton.setVisible(false);
        if( OnTurn.getInstance().act(currentP)) {
            cardsFinished++;
        }

    }

    public void rehearseButtonAction(ActionEvent event) {
        OnTurn.getInstance().rehearse(currentP);
        makeButtonVisible(false, false,false);
    }

    /* WON'T NEED - TAKE OUT */
    public void makeButtonVisible(boolean act, boolean rehearse, boolean upgrade, boolean roll) {
        makeButtonVisible(false,false,false);
    }

    public void upgradeButtonAction(ActionEvent event) {
        System.out.println("test34566");
        makeButtonVisible(false,false,false);
        //Upgrade.getInstance().levelsCanUpgrade(currentP); //set add upgrade options
        upgradeOptions.setValue(0);
        loadData();
        upgradeOptions.setVisible(true);     ///// I think I need to all do show() and setDisable() etc.....//////
        upgradeRankButton.setVisible(true);

        //show buttons
        //call upgrade class
    }

    public void upgradeRankAction(ActionEvent event){ /* NEED TO FIX THE UPGRADE BUTTON TO HIDE WHEN LEFT ROOM */

        rankChoice = upgradeOptions.getValue();  // may need to add hide() and setDisable() etc.....
        if (rankChoice >1 && rankChoice < 7 ) {
            upgradeOptions.setVisible(false);
            upgradeRankButton.setVisible(false);
            //button for credit & button for dollar
            //makePayButtonsVisible(dollarVisible,creditVisible);

            System.out.println(dollarVisible);
            System.out.println(creditVisible);
            payWDollarButton.setVisible(dollarVisible);
            payWDollarButton.toFront();
            payWCreditButton.setVisible(creditVisible);
            payWCreditButton.toFront();

        } else {
            actPrintLabel.setText("Can't upgrade to that rank");
            upgradeOptions.setVisible(false);
            upgradeRankButton.setVisible(false);

            /* NEXT TURN ???? */ //maybe /////////////////////////////////////////////////////////////////////


        }
//        upgradeOptions.setVisible(false);
//        upgradeRankButton.setVisible(false);
//        //button for credit & button for dollar
//        makePayButtonsVisible(dollarVisible,creditVisible);
    }

    public int getUpgradeRankChoice() {
        return rankChoice;
    }

    public void addUpgradeOptions(int num) {
        //upgradeOptions.getItems().add(num);
        list.add(num);
    }

    public void loadData(){
        list.removeAll(list);   /* NOT WORKING THE WAY IT SHOULD */
       // list.clear();

        list.add(0); //may not need but currently using to check if they enter zero then do nothing
       //

        int currentLevel = currentP.getLevel();
        //ArrayList<Integer> canUpgrade = new ArrayList<Integer>();
        int credit = currentP.getCredit();
        int dollar = currentP.getDollar();

        for (int i = currentLevel + 1; i <= 6; i++) {
            if (Upgrade.getInstance().getLevel(i).credit <= credit || Upgrade.getInstance().getLevel(i).dollar <= dollar) {
                //canUpgrade.add(i);
                //SystemManager.getInstance().addUpgradeOptions(i);
                list.add(i);
            }
        }

        upgradeOptions.getItems().addAll(list);

    }


    public void payWDollarAction(ActionEvent event) {
        Upgrade.getInstance().upgradeDollar(currentP, rankChoice);
        payWDollarButton.setVisible(false);
    }

    public void payWCreditAction(ActionEvent event) {
        Upgrade.getInstance().upgradeCredit(currentP, rankChoice);
        payWCreditButton.setVisible(false);
    }

    public void setPayButtonsVisible(boolean dollar, boolean credit) {
        System.out.println("HHHHHHHHHHHHHHHHHHHHH");
        System.out.println(dollar);
        System.out.println(credit);
        dollarVisible = dollar;
        creditVisible = credit;
    }
//    public void makePayButtonsVisible(boolean dollar, boolean credit) {
//
//        payWDollarButton.setVisible(dollar);
//        payWCreditButton.setVisible(credit);
//    }

    public void makeButtonVisible(boolean act, boolean rehearse, boolean upgrade) {
        actButton.toFront();
        actButton.setVisible(act);
        rehearseButton.toFront();
        rehearseButton.setVisible(rehearse);
        //System.out.print("test3");

        upgradeButton.setVisible(upgrade);
        //System.out.print("test5");
    }

    public void printLabel(String str) { //print to user success, fail, etc..
        actPrintLabel.setText(str);
    }

    public Pane getButtonLocation(String location) {
        Pane obj = switch (location) {
            case "Main Street" -> mainStreet;
            case "trailer" -> trailer;
            case "office" -> office;
            case "Secret Hideout" -> secretHideout;
            case "Train Station" -> trainStation;
            case "Ranch" -> ranch;
            case "Jail" -> jail;
            case "Hotel" -> hotel;
            case "Bank" -> bank;
            case "Saloon" -> saloon;
            case "General Store" -> generalStore;
            default -> church;
        };
        return obj;
    }

    public ImageView getCard(String location) {
        ImageView obj = switch (location) {
            case "Main Street" -> mainStreetCard;
            case "Secret Hideout" -> secretHideoutCard;
            case "Train Station" -> trainStationCard;
            case "Ranch" -> ranchCard;
            case "Jail" -> jailCard;
            case "Hotel" -> hotelCard;
            case "Bank" -> bankCard;
            case "Saloon" -> saloonCard;
            case "General Store" -> generalStoreCard;
            default -> churchCard;
        };
        return obj;
    }

    public ImageView playerPerson(int val) {
        return switch (val) {
            case 1 -> player1;
            case 2 -> player2;
            case 3 -> player3;
            case 4 -> player4;
            case 5 -> player5;
            case 6 -> player6;
            case 7 -> player7;
            default -> player8;
        };
    }

    public void onMove(ActionEvent event) {
        String name = ((Node) event.getSource()).getId().toString();
        // Don't display button for move
        showButton(currentP.getPlayerLocation(), false);
        // Put player in new set area
        Pane previousArea = getButtonLocation(currentP.getPlayerLocation());
        boolean cardFlip = OnTurn.getInstance().movePlayer(currentP, name);
        Pane newArea = getButtonLocation(currentP.getPlayerLocation());

        ImageView thisPlayer = playerPerson(currentP.getPlayerPriority());
        previousArea.getChildren().remove(thisPlayer);
        newArea.getChildren().add(thisPlayer);

        // Check if card is flipped, if not flip
        if (!cardFlip) {
            getCard(currentP.getPlayerLocation()).setImage(Deck.getInstance().getCard(Board.getInstance().getSet(currentP.getPlayerLocation()).getCardNum()).getCardImage());
        }

        letUpgrade();

        nextPlayer.setVisible(true);
    }

    public void letUpgrade() {
        if (currentP.getPlayerLocation().equals("office")) {
            System.out.println("TEST ");
            //visible upgrade button
            //makeButtonVisible(false, false, true);
            //Upgrade.getInstance().levelsCanUpgrade(currentP); //populate choice box
            if (Upgrade.getInstance().canUpgrade(currentP.getLevel(), currentP.getPlayerLocation(), currentP.getDollar(), currentP.getCredit())) {
                makeButtonVisible(false, false, true);
                System.out.println("TEST 7 ");
            }
            //call onturn function
            upgradeButton.toFront(); ///////////////////////////////////////////////////
            System.out.println("TEST 5 ");
        } else {
            makeButtonVisible(false, false, false);

        }
    }

    public void nextPlayerPush(ActionEvent event) {
        nextPlayer.setVisible(false);

        player++; // Next player turn

        if (player == getPlayerList().length) {
            player = 0;
        }

        currentP = getPlayerList()[player];
        // If card has finished increment cards finished
        turn(currentP);

        if (cardsFinished == 9) {
            cardsFinished = 0;
            day++;
            if (calculateDaysPlayed() + 1 == day) {
                endFunction();
            } else {
                resetAll(getPlayerList(), day);
            }
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
                    //players[i] = new Player(i + 1, 1, 0, 2, "trailer", playerDie[i]);
                    //players[i] = new Player(i + 1, 1, 10, 10, "trailer", playerDie[i]); /* TEST UPGRADE */
                    //System.out.println("TEST 3");
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
                    //players[i] = new Player(i + 1, 1, 0, 0, "trailer", playerDie[i]);
                    players[i] = new Player(i + 1, 1, 10, 10, "trailer", playerDie[i]);

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

    // Function turn will give player options at start of turn
    // Will return true if card has finished
    // will return false if not
    public boolean turn(Player player) {
        boolean endOfCard = false;

        // If player has not taken a role, let them move
        if (player.getOffCardRole() == false && player.getOnCardRole() == false) {
//            moveManager(player);
            letUpgrade();
            showButton(player.getPlayerLocation(), true); //location buttons


            /* NEED TAKE ROLE OPTION */

        } else {
            // If player can rehearse or act, give them options
            if (player.getRoleLevel() + player.getPracticeChip() < 6) {
                makeButtonVisible(true,true,false, false);

            // If they can't rehearse anymore give them only act option
            } else {
                makeButtonVisible(true,false,false,false); //get rid of roll
            }
        }
        return endOfCard; // return to SystemManager.java
    }
}
