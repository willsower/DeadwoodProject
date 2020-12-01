/*
    SystemManager Class
    Purpose: This class is the power of this game. It will create the player objects, manage
             how many days will be played, and will trigger the onTurn functions to help managing
             the system. Will also calculate end of game metrics by calling ScoringManager and
             will reset each day by calling helper functions. This Controller class is the main
             Controller that interacts with the View Board.fxml
*/

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.*;

public class SystemManager implements Initializable {
    private Player[] players;
    private int numPlayer;
    private int cardsFinished = 0;
    private Player currentP;
    private int rankChoice;
    private int player = 0;
    private int day = 1;

    @FXML // Card Panes
    private Pane trainStationCardHolder, jailCardHolder, mainStreetCardHolder, generalStoreCardHolder, saloonCardHolder,
            ranchCardHolder, bankCardHolder, secretHideoutCardHolder, churchCardHolder, hotelCardHolder, trailer, office,
            mainStreet, saloon, hotel, ranch, generalStore, trainStation, secretHideout, jail, church, bank, endOfCardPrint, bonusDistributed;
    @FXML //ImageViews
    private ImageView trainStationCard, jailCard, mainStreetCard, generalStoreCard, saloonCard, ranchCard, bankCard,
            secretHideoutCard, churchCard, hotelCard, boardImage, player1, player2, player3, player4, player5, player6, player7,
            player8, actRollDisplay, bonusOne, bonusTwo, bonusThree, bonusFour, bonusFive, bonusSix;
    @FXML // Text display
    private Label currentPlayer, playerDollar, playerCredit, playerPracticeChip, dayDisplay, displayText, actPrintLabel,
            actPrintLabelTwo, diceRollLabel, finalScoreLabel, winnerIsLabel, winnerLabel, finalScoreOne, finalScoreTwo,
            finalScoreThree, finalScoreFour, finalScoreFive, finalScoreSix, finalScoreSeven, finalScoreEight, bonus, bonusLabel;
    @FXML // Action Buttons
    private Button actButton, rehearseButton, upgradeButton, rollDieButton, payWDollarButton, payWCreditButton, nextPlayer;
    @FXML private TextField userInput;
    @FXML private VBox numPlayerBox, upgradeBox, paymentOption;
    ObservableList<Integer> list = FXCollections.observableArrayList();
    @FXML private ChoiceBox<Integer> upgradeOptions;
    @FXML private GridPane  woodBoard;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boardImage.setImage(Board.getInstance().getBoardImage());
    }

    // Getter functions

    // Get pane name of each location
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

    // Get card pane in each location
    public Pane getCardPane(String location) {
        Pane obj = switch (location) {
            case "Main Street" -> mainStreetCardHolder;
            case "Secret Hideout" -> secretHideoutCardHolder;
            case "Train Station" -> trainStationCardHolder;
            case "Ranch" -> ranchCardHolder;
            case "Jail" -> jailCardHolder;
            case "Hotel" -> hotelCardHolder;
            case "Bank" -> bankCardHolder;
            case "Saloon" -> saloonCardHolder;
            case "General Store" -> generalStoreCardHolder;
            default -> churchCardHolder;
        };
        return obj;
    }

    // Get card imageView of each location
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

    // Get player imageView
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

    // Get player label for finalScore
    public Label playerScore(int val) {
        return switch (val) {
            case 1 -> finalScoreOne;
            case 2 -> finalScoreTwo;
            case 3 -> finalScoreThree;
            case 4 -> finalScoreFour;
            case 5 -> finalScoreFive;
            case 6 -> finalScoreSix;
            case 7 -> finalScoreSeven;
            default -> finalScoreEight;
        };
    }

    // Get bonus imageviews
    public ImageView bonusViews(int val) {
        return switch (val) {
            case 1 -> bonusOne;
            case 2 -> bonusTwo;
            case 3 -> bonusThree;
            case 4 -> bonusFour;
            case 5 -> bonusFive;
            default -> bonusSix;
        };
    }

    // Get number of players for game to start. Sets up board afterwords to start game
    public void submitPlayers(ActionEvent event) {
        int numberPlayers = 0;
        String val = userInput.getText();
        try {
            numberPlayers = Integer.parseInt(val);
        } catch (NumberFormatException e) {
        }

        if (numberPlayers >= 2 && numberPlayers <= 8) {
            numPlayerBox.setVisible(false);
            numPlayerBox.setDisable(true);

            // Initialize players
            players = OnTurn.getInstance().init(Integer.parseInt(val));
            numPlayer = Integer.parseInt(val);
            currentP = players[0];

            boardImage.setVisible(true);
            setUpBoard(day);

            // Add players into trailers
            for (int i = 0; i < numPlayer; i++) {
                playerPerson(i + 1).setImage(players[i].getPlayerImage());
                changeCoords(players[i].getXCoord(), players[i].getYCoord(), playerPerson(i + 1));
            }
            turn(players[0]);
        }
    }

    // Sets board up at each day
    public void setUpBoard(int day) {
        boardImage.setVisible(true);
        dayDisplay.setText("Day " + day);

        // Reset players to trailers
        if (day > 1) {
            for (int i = 0; i < numPlayer; i++) {
                showOffCardRoleOptions(false, players[i].getPlayerLocation(), players[i]);
                resetToTrailers(playerPerson(i + 1), i);
            }
        }

        Enumeration<Set> values = Board.getInstance().getBoard().elements();
        // iterate through values, set card images,  delete card panes, reset shot counter
        while (values.hasMoreElements()) {
            Set set = values.nextElement();
            if (!set.getSetName().equals("trailer") && !set.getSetName().equals("office")) {
                getCard(set.getSetName()).setImage(Deck.getInstance().getBackOfCardSmall());
                getCard(set.getSetName()).setVisible(true);
                if (day > 1) {
                    deleteCardHelperInfo(set.getSetName());
                    resetShotCounter(set.getSetName());
                }
                createCardHelper(set);
            }
        }
        setPlayerInformation(0);
    }

    // Reset helper, will reset board and everything at end of day
    public void resetAll(Player[] list, int day, int numPlayer) {
        OnTurn.getInstance().resetHelper(list, day, numPlayer);
        setUpBoard(day);
    }

    // Reset players to trailers helper
    public void resetToTrailers(ImageView player, int num) {
        movePlayerHelper(getButtonLocation(players[num].getPlayerLocation()), trailer, player);
        players[num].setPlayerLocation("trailer");
        changeCoords(players[num].getXCoord(), players[num].getYCoord(), player);
        nextPlayer.setVisible(true);
    }

    // Function will help faciliate players turns, what options to give player
    public boolean turn(Player player) {
        boolean endOfCard = false;

        // If player has not taken a role, let them move
        if (player.getOffCardRole() == false && player.getOnCardRole() == false) {
            letUpgrade();
            showRoleMoveNext(true, true, true, currentP.getPlayerLocation());

        } else { /* goes into this else correctly */
            showRoles(false, currentP.getPlayerLocation());
            int cardBudget = Deck.getInstance()
                    .getCard(Board.getInstance().getSet(currentP.getPlayerLocation()).getCardNum()).getCardBudget();
            // If player can rehearse or act, give them options
            if (player.getPracticeChip() < (cardBudget - 1)) {
                makeButtonVisible(true, true, false);
            } else {
                makeButtonVisible(true, false, false); // get rid of roll
            }
        }
        return endOfCard;
    }

    // Set buttons visible for when moving
    public void showButton(String location, boolean val) {
        Pane obj = getButtonLocation(location);
        if (val) obj.toFront();

        for (int i = 0; i < obj.getChildren().size(); i++) {
            if (obj.getChildren().get(i).getAccessibleRole().compareTo(AccessibleRole.BUTTON) == 0) {
                obj.getChildren().get(i).setVisible(val);
            }
        }
    }

    // Function will end the current players turn and set player label information for next player
    public void nextPlayerPush(ActionEvent event) {
        showRoleMoveNext(false, false, false, currentP.getPlayerLocation());
        actRollDisplay.setVisible(false);
        endOfCardPrint.setVisible(false);
        bonusDistributed.setVisible(false);
        player++;
        if (player == players.length) {
            player = 0;
        }
        currentP = players[player];
        // Hide print label
        actPrintLabel.setText("");
        actPrintLabelTwo.setText("");
        diceRollLabel.setText("");

        if (cardsFinished < 9) {
            showRoleMoveNext(false, false, false, currentP.getPlayerLocation());
            // Set label with player information
            setPlayerInformation(currentP.getPracticeChip());
            upgradeBox.setVisible(false);

            turn(currentP);
        } else if (cardsFinished == 9) {
            showRoles(false, currentP.getPlayerLocation());
            cardsFinished = 0;
            day++;
            if (OnTurn.getInstance().calculateDaysPlayed(numPlayer) + 1 == day) {
                nextPlayer.setVisible(false);
                woodBoard.toFront();
                finalScoreLabel.setVisible(true);

                displayWinner(OnTurn.getInstance().endFunction(players, numPlayer));
                for (int i = 0; i < players.length; i++) {
                    playerScore(i +1).setText("\t\t\t\t\t\t\tPlayer "+ (i+1) + ": "+ players[i].getFinalScore());
                    playerScore(i +1).setVisible(true);
                }
            } else {
                resetAll(players, day, numPlayer);
                turn(currentP);
            }
        }
    }

    // Will set upgrade/act/rehearse buttons
    public void makeButtonVisible(boolean act, boolean rehearse, boolean upgrade) {
        actButton.setVisible(act);
        rehearseButton.setVisible(rehearse);
        upgradeButton.setVisible(upgrade);
    }

    // Function that will display role or move options
    public void showRoleMoveNext(boolean move, boolean role, boolean next, String location) {
        showButton(currentP.getPlayerLocation(), move);
        showRoles(role, location);
        nextPlayer.setVisible(next);
    }

    // Make role button visible once act button has been pushed
    public void actButtonAction(ActionEvent event) {
        showRoles(false, currentP.getPlayerLocation());
        rollDieButton.setVisible(true);
        makeButtonVisible(false, false, false);
    }

    // If player hits roll die action button, will roll the die and distribute bonuses if applicable
    // Wil lalso tell if card has finished
    public void rollDieAction(ActionEvent event) {
        rollDieButton.setVisible(false);

        int actResponse = OnTurn.getInstance().act(currentP);
        if (actResponse == 1) {
            cardFinished();
        }

        if (actResponse == 2 || actResponse == 1) {
            Pane paneCurrent = getButtonLocation(currentP.getPlayerLocation());
            for (int i = 0; i< paneCurrent.getChildren().size(); i++) {
                if (paneCurrent.getChildren().get(i).getAccessibleRole().compareTo(AccessibleRole.IMAGE_VIEW) == 0) {
                    if (!paneCurrent.getChildren().get(i).isVisible()) {
                        paneCurrent.getChildren().get(i).setVisible(true);
                        break;
                    }
                }
            }
        }

        diceRollLabel.setText(OnTurn.getInstance().getPrintMessageRoll());
        actPrintLabel.setText(OnTurn.getInstance().getPrintMessage());
        actPrintLabelTwo.setText(OnTurn.getInstance().getPrintMessageTwo());
        setPlayerInformation(currentP.getPracticeChip());
        actRollDisplay.setVisible(true);
        actRollDisplay.setImage(OnTurn.getInstance().getDieImage());
        nextPlayer.setVisible(true);
    }

    // Rehearses button, when pushed will increase player's rehearse chip
    public void rehearseButtonAction(ActionEvent event) {
        OnTurn.getInstance().rehearse(currentP, Deck.getInstance()
                .getCard(Board.getInstance().getSet(currentP.getPlayerLocation()).getCardNum()).getCardBudget());
        playerPracticeChip.setText("Practice Chips: " + currentP.getPracticeChip());
        makeButtonVisible(false, false, false);
        nextPlayer.setVisible(true);
    }

    // Player clicks upgrade to upgrade themselves, will populate upgradeOptions with possible levels to upgrade
    public void upgradeButtonAction(ActionEvent event) {
        makeButtonVisible(false, false, false);
        upgradeOptions.getItems().clear();
        upgradeOptions.setValue(0);
        upgradeOptions.getItems().addAll(Upgrade.getInstance().loadData(list, currentP));
        upgradeBox.setVisible(true);
    }

    // Player chooses whwat option to upgrade to, and does payment with credit/dollar if applicable
    public void upgradeRankAction(ActionEvent event) {
        rankChoice = upgradeOptions.getValue();
        if (rankChoice > 1 && rankChoice < 7) {
            actPrintLabel.setText("");
            actPrintLabelTwo.setText("");
            diceRollLabel.setText("");
            upgradeBox.setVisible(false);
            paymentOption.setVisible(true);
            payWDollarButton.setVisible(Upgrade.getInstance().playerHasDollar(rankChoice, currentP.getDollar()));
            payWCreditButton.setVisible(Upgrade.getInstance().playerHasCredit(rankChoice, currentP.getCredit()));
        }
    }

    // Cards Finished helepr function, will be called from rollDieAction
    public void cardFinished() {
        cardsFinished++;
        displayPayouts();
        deleteCardHelperInfo(currentP.getPlayerLocation());
        int cardNum = Deck.getInstance().getCard(Board.getInstance().getSet(currentP.getPlayerLocation()).getCardNum()).getCardID();

        // Moves players on card role / off card role back to room
        for (Player p : Deck.getInstance().getCard(cardNum).getPlayersInRoomOnCard()) {
            movePlayerHelper(((Pane) ((Node) playerPerson(p.getPlayerPriority()).getParent())), getButtonLocation(currentP.getPlayerLocation()), playerPerson(p.getPlayerPriority()));
            changeCoords(p.getXCoord(), p.getYCoord(), playerPerson(p.getPlayerPriority()));
        }
        for (Player p : Board.getInstance().getSet(currentP.getPlayerLocation()).getPlayersInRoomOffCard()) {
            movePlayerHelper((Pane) playerPerson(p.getPlayerPriority()).getParent(), getButtonLocation(currentP.getPlayerLocation()), playerPerson(p.getPlayerPriority()));
            changeCoords(p.getXCoord(), p.getYCoord(), playerPerson(p.getPlayerPriority()));
        }

        Board.getInstance().getSet(currentP.getPlayerLocation()).removePlayersFormRoomOffCard(currentP);
        Board.getInstance().getSet(currentP.getPlayerLocation()).setIsActive(false);
        getCard(currentP.getPlayerLocation()).setVisible(false);
        showRoles(false, currentP.getPlayerLocation());
    }

    // Set player's new picture and set dollar/credit
    public void paymentHelperUpgrade() {
        paymentOption.setVisible(false);
        nextPlayer.setVisible(true);

        currentP.setPlayerImage();
        playerPerson(currentP.getPlayerPriority()).setImage(currentP.getPlayerImage());

        playerDollar.setText("Dollars: " + currentP.getDollar());
        playerCredit.setText("Credits: " + currentP.getCredit());
    }

    // Player upgrades with dollar
    public void payWDollarAction(ActionEvent event) {
        Upgrade.getInstance().upgradeDollar(currentP, rankChoice);
        paymentHelperUpgrade();
    }

    // Player upgrades with credit
    public void payWCreditAction(ActionEvent event) {
        Upgrade.getInstance().upgradeCredit(currentP, rankChoice);
        paymentHelperUpgrade();
    }

    public void changeCoords(int x, int y, ImageView player) {
        player.setTranslateX(x);
        player.setTranslateY(y);
    }

    // When player clicks one of the arrows to move
    public void onMove(ActionEvent event) {
        String name = ((Node) event.getSource()).getId();
        showRoleMoveNext(false, false, false, currentP.getPlayerLocation());
        String previous = currentP.getPlayerLocation();

        // Check if card is flipped, if not flip
        boolean cardFlip = OnTurn.getInstance().movePlayer(currentP, name);
        if (!cardFlip) {
            getCard(currentP.getPlayerLocation()).setImage(Deck.getInstance()
                    .getCard(Board.getInstance().getSet(currentP.getPlayerLocation()).getCardNum()).getCardImage());
        }
        movePlayerHelper(getButtonLocation(previous), getButtonLocation(currentP.getPlayerLocation()), playerPerson(currentP.getPlayerPriority()));
        // Let player do next turn, show role options, let player upgrade if applicable
        showRoleMoveNext(false, true, true, currentP.getPlayerLocation());
        letUpgrade();
    }

    // Helper function to add player into pane when they take role
    public void takeRoleHelper(ActionEvent event) {
        changeCoords(0, 0, playerPerson(currentP.getPlayerPriority()));
        movePlayerHelper(getButtonLocation(currentP.getPlayerLocation()), ((Pane) ((Button) event.getSource()).getParent()), playerPerson(currentP.getPlayerPriority()));
        showRoleMoveNext(false, false, true, currentP.getPlayerLocation());
    }

    // When clicked off card role, will set player to that role
    public void offCardRole(ActionEvent event) {
        String name = ((Node) event.getSource()).getId();
        String set = OnTurn.getInstance().parseForSet(((Node) event.getSource()).getParent().getParent().getId());

        OnTurn.getInstance().takeOffCardRole(currentP, name, set);
        takeRoleHelper(event);
    }

    // Function will call createCardRoles function depending on how many roles are on a card. If there are three roles,
    // will create three different panes/buttons for that card, etc
    public void createCardHelper(Set name) {
        Pane obj = getCardPane(name.getSetName());
        if (Deck.getInstance().getCard(name.getCardNum()).getPart().size() == 1) {
            createCardRoles(obj, 50, 28, Deck.getInstance().getCard(Board.getInstance().getSet(name.getSetName()).getCardNum()).getPart().get(0).partName);
        } else if (Deck.getInstance().getCard(name.getCardNum()).getPart().size() == 2) {
            createCardRoles(obj, 30, 28, Deck.getInstance().getCard(Board.getInstance().getSet(name.getSetName()).getCardNum()).getPart().get(0).partName);
            createCardRoles(obj, 70, 28, Deck.getInstance().getCard(Board.getInstance().getSet(name.getSetName()).getCardNum()).getPart().get(1).partName);
        } else {
            createCardRoles(obj, 11, 28, Deck.getInstance().getCard(Board.getInstance().getSet(name.getSetName()).getCardNum()).getPart().get(0).partName);
            createCardRoles(obj, 50, 28, Deck.getInstance().getCard(Board.getInstance().getSet(name.getSetName()).getCardNum()).getPart().get(1).partName);
            createCardRoles(obj, 90, 28, Deck.getInstance().getCard(Board.getInstance().getSet(name.getSetName()).getCardNum()).getPart().get(2).partName);
        }
    }

    // Function that creates on card role pane, buttons. This is so at each day on card, user can take on card roles
    // this is reset at the end of each day and then redone at the beginning of the day for new card
    public void createCardRoles(Pane parentCard, int x, int y, String id) {
        Pane newPane = new Pane();
        newPane.setPrefWidth(30);
        newPane.setPrefHeight(30);
        newPane.setLayoutX(x);
        newPane.setLayoutY(y);
        newPane.setId(id.replace(" ", "_"));
        parentCard.getChildren().add(newPane);
        Button test = new Button();
        newPane.getChildren().add(test);
        test.setPrefWidth(30);
        test.setPrefHeight(30);
        test.setId(id.replace(" ", "_"));
        test.setVisible(false);
        test.setOpacity(0.5);
        test.setOnAction(new EventHandler<ActionEvent>() {
            @Override //When player clicks on on card role, will set them to that role
            public void handle(ActionEvent event) {
                String name = ((Node) event.getSource()).getId();
                String set = OnTurn.getInstance()
                        .parseForSet(((Node) event.getSource()).getParent().getParent().getParent().getId());

                OnTurn.getInstance().takeOnCardRole(currentP, name, set);
                takeRoleHelper(event);
            }
        });
    }

    // Show on card roles or off card roles depending
    public void showRoles(boolean val, String location) {
        if (!location.equals("trailer") && !location.equals("office")) {
            if (!Board.getInstance().getSet(location).getIsActive()) {
                showOffCardRoleOptions(false, location, currentP);
                showOnCardRoleOptions(false);
            } else {
                showOffCardRoleOptions(val, location, currentP);
                showOnCardRoleOptions(val);
            }
        }
    }

    // Helps showOffCardRoleOptions and onCardRoleOptions. Will display the roles necessary or disable
    public void showRoleOptionHelper(ArrayList<String> card, boolean val, Pane obj) {
        int i = 0;
        while (i < card.size()) {
            for (int j = 0; j < obj.getChildren().size(); j++) {
                String name = obj.getChildren().get(j).getId().replace("_", " ");
                if ((obj.getChildren().get(j).getAccessibleRole().compareTo(AccessibleRole.PARENT) == 0)) {
                    if (name.equals(card.get(i).replace(",", "")) || val == false) {
                        for (int x = 0; x < ((Pane) obj.getChildren().get(j)).getChildren().size(); x++) {
                            if (((Pane) obj.getChildren().get(j)).getChildren().get(x).getAccessibleRole()
                                    .compareTo(AccessibleRole.BUTTON) == 0) {
                                Button myButton = ((Button) ((Pane) obj.getChildren().get(j)).getChildren().get(x));
                                myButton.setVisible(val);
                                myButton.toFront();
                                break;
                            }
                        }
                    }
                }
            }
            i++;
        }
    }

    // Function that will show off card role options if they are availble, or disable
    public void showOffCardRoleOptions(boolean val, String location, Player player) {
        ArrayList<String> offCard = OnTurn.getInstance().getPartsAvailOffCard(player);
        showRoleOptionHelper(offCard, val, getButtonLocation(location));
    }

    // Function that will show on card role options if they are availble, or disable
    public void showOnCardRoleOptions(boolean val) {
        ArrayList<String> onCard = OnTurn.getInstance().getPartsAvailOnCard(currentP);
        showRoleOptionHelper(onCard, val, getCardPane(currentP.getPlayerLocation()));
    }

    // Deletes pane objects for card roles at start of each day
    public void deleteCardHelperInfo(String name) {
        Pane obj = getCardPane(name);

        for (int i = 0; i < obj.getChildren().size(); i++) {
            if (obj.getChildren().get(i).getAccessibleRole().compareTo(AccessibleRole.PARENT) == 0) {
                obj.getChildren().remove(obj.getChildren().get(i));
                i--;
            }
        }
    }

    // resets image view of shot counters
    public void resetShotCounter(String name) {
        Pane object = getButtonLocation(name);
        for (int i = 0; i < object.getChildren().size(); i++) {
            if (object.getChildren().get(i).getAccessibleRole().compareTo(AccessibleRole.IMAGE_VIEW) == 0) {
                String id = object.getChildren().get(i).getId();
                if (id.equals("shotOne") || id.equals("shotTwo") || id.equals("shotThree")) {
                    object.getChildren().get(i).setVisible(false);
                }
            }
        }
    }

    // Check to see if player can upgrade, if they can show the option
    public void letUpgrade() {
        if (currentP.getPlayerLocation().equals("office")) {
            nextPlayer.setVisible(true);
            if (Upgrade.getInstance().canUpgrade(currentP.getLevel() + 1, currentP.getPlayerLocation(),
                    currentP.getDollar(), currentP.getCredit())) {
                makeButtonVisible(false, false, true);
            }
        }
    }

    // Add images to payout
    public void addPayoutImages(ArrayList<Image> payouts) {
        for (int i = 0; i < 6; i++) {
            if (i < payouts.size()) {
                bonusViews(i + 1).setImage(payouts.get(i));
            } else {
                bonusViews(i + 1).setImage(null);
            }
        }
    }

    // Display end of card payouts
    public void displayPayouts() {
        endOfCardPrint.setVisible(true);
        bonus.setText(ScoringManager.getInstance().getBonusDistributed());
        if (ScoringManager.getInstance().bonusAccepted()) {
            bonusDistributed.setVisible(true);
            addPayoutImages(ScoringManager.getInstance().getPayoutImages());
            bonusLabel.setText(ScoringManager.getInstance().getBonusInformation());
        }
    }

    // Display winners of game
    public void displayWinner(Integer[] finals) {
        //winnerIsLabel.setText("The winner(s) are: ");
        winnerIsLabel.setVisible(true);
        for (int i = 0; i < finals.length; i++) {
            if (finals[i] != 0) {
                winnerLabel.setText("  Player " + finals[i]);
                winnerLabel.setVisible(true);
            }
        }
    }

    // Prints player information
    public void setPlayerInformation(int practiceChip) {
        currentPlayer.setText("Player " + currentP.getPlayerPriority() + ": " + currentP.getColorName());
        playerDollar.setText("Dollars: " + currentP.getDollar());
        playerCredit.setText("Credits: " + currentP.getCredit());
        playerPracticeChip.setText("Practice Chips: " + practiceChip);
    }

    // Function that will help move players from previous panes to new panes
    public void movePlayerHelper(Pane previousPane, Pane newPane, ImageView player) {
        previousPane.getChildren().remove(player);
        newPane.getChildren().add(player);
    }
}