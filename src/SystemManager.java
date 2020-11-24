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
    private boolean dollarVisible;
    private boolean creditVisible;
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

    @FXML private ImageView boardImage, deck;

    // Card Image Views
    @FXML private ImageView trainStationCard, jailCard, mainStreetCard, generalStoreCard, saloonCard, ranchCard, bankCard, secretHideoutCard, churchCard, hotelCard;

    // Player's display information
    @FXML private Label currentPlayer, playerDollar, playerCredit, playerPracticeChip;

    // Text Display
    @FXML private Label dayDisplay, displayText; // Display current day
    @FXML private TextField userInput;
    @FXML private Button submitButton;

    //Action Buttons
    @FXML private Button actButton, rehearseButton, upgradeButton, rollDieButton;

    //upgrade buttons
    @FXML private Button upgradeRankButton, payWDollarButton, payWCreditButton;

    ObservableList<Integer> list = FXCollections.observableArrayList();
    @FXML private ChoiceBox<Integer> upgradeOptions;

    @FXML private Label actPrintLabel; //print to user success, fail, etc..
    @FXML private Button nextPlayer;

    // Pane values
    @FXML private Pane trailer, office, mainStreet, saloon, hotel, ranch, generalStore, trainStation, secretHideout, jail, church, bank;

    //Player pieces
    @FXML private ImageView player1, player2, player3, player4, player5, player6, player7, player8;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boardImage.setImage(Board.getInstance().getBoardImage());
        boardImage.setVisible(false);
        deck.setVisible(false);
        makeButtonVisible(false, false, false, false); /* DONT THINK ROLL IS NEEDED */
        nextPlayer.setVisible(false);

        rollDieButton.setVisible(false);

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

            players = OnTurn.getInstance().init(Integer.parseInt(val));
            numPlayer = Integer.parseInt(val);
            currentP = players[0];

            boardImage.setVisible(true);
            setUpBoard(day);

            turn(players[0]);
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

    /* WON'T NEED - TAKE OUT - maybe */
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

    }

    public void upgradeRankAction(ActionEvent event){ /* NEED TO FIX THE UPGRADE BUTTON TO HIDE WHEN LEFT ROOM */
        rankChoice = upgradeOptions.getValue();  // may need to add hide() and setDisable() etc.....
        if (rankChoice > 1 && rankChoice < 7 ) {
            upgradeOptions.setVisible(false);
            upgradeRankButton.setVisible(false);

            payWDollarButton.setVisible(dollarVisible);
            payWDollarButton.toFront(); //may not need
            payWCreditButton.setVisible(creditVisible);
            payWCreditButton.toFront(); //may not need

        } else {
            actPrintLabel.setText("Can't upgrade to that rank");
            upgradeOptions.setVisible(false);
            upgradeRankButton.setVisible(false);

            /* NEXT TURN set in pay with buttons */ /////////////////////////////////////////////////////
        }
    }

    public int getUpgradeRankChoice() {
        return rankChoice;
    }

    public void loadData(){
        //list.removeAll(list);
        System.out.println(list);
        list.clear();
        System.out.println(list);
        for (int l : list){
           list.removeAll(l);
           System.out.println(list);
        }
        list.add(0);

        int currentLevel = currentP.getLevel();
        int credit = currentP.getCredit();
        int dollar = currentP.getDollar();

        for (int i = currentLevel + 1; i <= 6; i++) {
            if (Upgrade.getInstance().getLevel(i).credit <= credit || Upgrade.getInstance().getLevel(i).dollar <= dollar) {
                list.add(i);
            }
        }
        upgradeOptions.getItems().addAll(list);
    }

    public void payWDollarAction(ActionEvent event) {
        Upgrade.getInstance().upgradeDollar(currentP, rankChoice);
        payWDollarButton.setVisible(false);
        payWCreditButton.setVisible(false);
        nextPlayer.setVisible(true);
        //list.removeAll(list);   /* */
        System.out.println(list);
        //list.clear();
        upgradeOptions.getItems().clear();

        currentP.setPlayerImage();
        playerPerson(currentP.getPlayerPriority()).setImage(currentP.getPlayerImage());

        playerDollar.setText("Dollars: " + currentP.getDollar());
    }

    public void payWCreditAction(ActionEvent event) {
        Upgrade.getInstance().upgradeCredit(currentP, rankChoice);
        payWDollarButton.setVisible(false);
        payWCreditButton.setVisible(false);
        nextPlayer.setVisible(true);
        //list.removeAll(list);   /* */
        System.out.println(list);
        //list.clear();
        upgradeOptions.getItems().clear();

        currentP.setPlayerImage();
        playerPerson(currentP.getPlayerPriority()).setImage(currentP.getPlayerImage());

        playerCredit.setText("Credits: " + currentP.getCredit());
    }

    public void setPayButtonsVisible(boolean dollar, boolean credit) {
        dollarVisible = dollar;
        creditVisible = credit;
    }

    public void makeButtonVisible(boolean act, boolean rehearse, boolean upgrade) {
        actButton.toFront(); //may not need
        actButton.setVisible(act);
        rehearseButton.toFront(); //may not need
        rehearseButton.setVisible(rehearse);
        upgradeButton.setVisible(upgrade);
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
        String name = ((Node) event.getSource()).getId();
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

        /* TAKE ROLE OPTION */   //////////////////////////////////////////////////////////

        letUpgrade();
        nextPlayer.setVisible(true);
    }

    public void showOffCardRoleOptions() {
        ArrayList<String> offCard = OnTurn.getInstance().getPartsAvailOffCard(currentP);
    }

    public void takeOffCardRole(ActionEvent event) {
        System.out.println("PUSH");
    }

    public void letUpgrade() {
        if (currentP.getPlayerLocation().equals("office")) {
            System.out.println("TEST ");
            nextPlayer.setVisible(true);
            //visible upgrade button
            //makeButtonVisible(false, false, true);
            //Upgrade.getInstance().levelsCanUpgrade(currentP); //populate choice box
            int answer = Upgrade.getInstance().canUpgrade(currentP.getLevel(), currentP.getPlayerLocation(), currentP.getDollar(), currentP.getCredit());
            if (answer != 0) {
                makeButtonVisible(false, false, true);
                switch (answer) {
                    case 1 -> setPayButtonsVisible(true, true);
                    case 2 -> setPayButtonsVisible(true, false);
                    default -> setPayButtonsVisible(false, true);
                }
            }

        } else if (!currentP.getPlayerLocation().equals("trailer")){
            makeButtonVisible(false,false, false);

            /* TAKE ROLE OPTION */ //////////////////////////////////////////////////////////
        }
    }

    public void nextPlayerPush(ActionEvent event) {
        nextPlayer.setVisible(false);
        player++; // Next player turn

        if (player == players.length) {
            player = 0;
        }

        currentP = players[player];

        currentPlayer.setText("Player " + currentP.getPlayerPriority() + ": " +currentP.getColorName());
        playerDollar.setText("Dollars: " + currentP.getDollar());
        playerCredit.setText("Credits: " + currentP.getCredit());
        playerPracticeChip.setText("Practice Chips: " + currentP.getPracticeChip());

        // If card has finished increment cards finished
        turn(currentP);

        if (cardsFinished == 9) {
            cardsFinished = 0;
            day++;
            if (OnTurn.getInstance().calculateDaysPlayed(numPlayer) + 1 == day) {
                OnTurn.getInstance().endFunction(players, numPlayer);
            } else {
                resetAll(players, day, numPlayer);
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

        currentPlayer.setText("Player " + currentP.getPlayerPriority() + ": "+ currentP.getColorName());
        playerDollar.setText("Dollars: " + currentP.getDollar());
        playerCredit.setText("Credits: " + currentP.getCredit());
        playerPracticeChip.setText("Practice Chips: "+ 0);
    }

    // Resetall function will be called at the start of each game
    // It will reset the variables in other classes, put players back into trailers
    // Put cards on the appropriate sets
    public void resetAll(Player[] list, int day, int numPlayer) {
        OnTurn.getInstance().resetHelper(list, day, numPlayer);
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
            nextPlayer.setVisible(true); //skip turn

            /* NEED TAKE ROLE OPTION */ //////////////////////////////////////////////////////////

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
