/*
    OnTurn Class
    Purpose: This class has everything to do with player choices in game
             Whether they decide to upgrade (which will call upgrade class),
             Act, Rehearse, Move, or take a role. All these interactions
             and calculations will be done in this class
*/

import java.util.Random;
import java.util.ArrayList;

public class OnTurn {
    private static OnTurn instance = null;


    // Create instance
    public static OnTurn getInstance() {
        if (instance == null) {
            instance = new OnTurn();
        }
        return instance;
    }


    // Return true if number is numeric
    // false if not
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Function to show that player has taken on card role
    // Updates Player and Card Attributes
    public static void takeOnCardRole(Player player, int roleNumber, int cardNum, String roleName) {
        int level = Deck.getInstance().getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum()).getPartLevel(roleNumber - 1);

        int rolePriority = Deck.getInstance()
                .getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum())
                .getPartPriority(roleNumber - 1);

        player.setOnCardRole(true);
        player.setRoleLevel(level);
        player.setRoleLocation(player.getPlayerLocation());
        player.setRolePriority(rolePriority);
        player.setRoleName(roleName);
        Deck.getInstance().getCard(cardNum).addPlayerToRoomOnCard(player);
        Deck.getInstance().getCard(cardNum).setPartTaken(roleName, true);
    }

    // Function to show that player has taken off card role
    // Updates Player and Set Attributes
    public static void takeOffCardRole(Player player, int roleNumber, int size, String setName, String roleName) {
        int level = Board.getInstance().getSet(player.getPlayerLocation()).getPartLevel(roleNumber - size - 1);

        player.setOffCardRole(true);
        player.setRoleLevel(level);
        player.setRoleLocation(player.getPlayerLocation());
        player.setRoleName(roleName);
        Board.getInstance().getSet(setName).addPlayerToRoomOffCard(player);
        Board.getInstance().getSet(setName).setPartTaken(roleName, true);
    }

    // Function for taking a role
    // Will call helper methods if user decides to take role
    public boolean takeRole(Player player) {
        ArrayList<String> partsOnCardAval = Deck.getInstance()
                .getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum())
                .availablePartsOnCard(player.getLevel());
        ArrayList<String> partsOffCardAval = Board.getInstance().getSet(player.getPlayerLocation())
                .availablePartsOffCard(player.getLevel());
        if (partsOnCardAval.isEmpty() && partsOffCardAval.isEmpty()) {
            System.out.println("  NO PARTS AVAILABLE FOR YOUR CURRENTLY");
        } else {
            // key of card name
            int cardNum = Deck.getInstance()
                    .getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum()).getCardID();
            System.out.println("  Card Name: " + Deck.getInstance().getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum()).getCardName());
            String playerChoice = UserInterface.getInstance().roleChoice(partsOnCardAval, partsOffCardAval, cardNum,
                    player.getPlayerLocation());

            if (isNumeric(playerChoice)) { // choice for which role to take
                int roleNumber = Integer.parseInt(playerChoice);
                if (roleNumber <= partsOnCardAval.size()) {
                    takeOnCardRole(player, roleNumber, cardNum, partsOnCardAval.get(roleNumber - 1));
                } else {
                    takeOffCardRole(player, roleNumber, partsOnCardAval.size(), player.getPlayerLocation(),
                            partsOffCardAval.get(roleNumber - partsOnCardAval.size() - 1));
                }
                return true;
            }
        }
        return false;
    }

    public void movePlayer(Player player, String location) {
        // Display move button options

    }

    // Ultimately the move option. Once moved, will allow players to take a role
    // Will also allow user to upgrade if they moved into casting office
    public void moveTakeRoleOption(Player player) {
        // Gets neighbors of room player currently is in
        String[] neighbors = Board.getInstance().getSet(player.getPlayerLocation()).getNeighbor();
        Boolean[] isActiveList = Board.getInstance().getSet(player.getPlayerLocation()).getIsActiveList();

        // Get user input if player wants to move
        String move = UserInterface.getInstance().moveOption(player, neighbors, isActiveList);
        int numNeighbors = neighbors.length;

        // If player enters number, move to that area
        if (isNumeric(move)) {
            player.setPlayerLocation(neighbors[Integer.parseInt(move) - 1]);
            System.out.println("  You are in room " + player.getPlayerLocation());

            if (player.getPlayerLocation().equals("trailer")) {
                // Do nothing
            } else if (player.getPlayerLocation().equals("office")) {
                int up = UserInterface.getInstance().upgradePlayer(player, player.getLevel(), player.getPlayerLocation(),
                        player.getDollar(), player.getCredit());
                if (up != 0) {
                    if (player.getCredit() >= Upgrade.getInstance().getLevel(up).credit) {
                        Upgrade.getInstance().upgradeCredit(player, up);
                    } else {
                        Upgrade.getInstance().upgradeDollar(player, up);
                    }
/**/                    UserInterfaceDisplay.getInstance().playerUpgrade(player);
                }
            } else {
                if (Board.getInstance().getSet(player.getPlayerLocation()).getIsActive() == true) {
                    takeRole(player);
                } else {
                    System.out.println("  SCENE IS FINISHED");
                }
            }
        }
    }

    // Overall the move manager will allow users to move, upgrade or take role
    // depending on their locations
    public void moveManager(Player player) {
        // Allow player to upgrade then move
        if (player.getPlayerLocation().equals("office")) {
            int up = UserInterface.getInstance().upgradePlayer(player, player.getLevel(), player.getPlayerLocation(),
                    player.getDollar(), player.getCredit());
            moveTakeRoleOption(player);

            if (up != 0) {
                if (player.getCredit() >= Upgrade.getInstance().getLevel(up).credit) {
                    Upgrade.getInstance().upgradeCredit(player, up);
                } else {
                    Upgrade.getInstance().upgradeDollar(player, up);
                }
/**/                UserInterfaceDisplay.getInstance().playerUpgrade(player);
            }

            // Allow player to move then take a role
        } else if (player.getPlayerLocation().equals("trailer")) {
//            moveTakeRoleOption(player);
//            movePlayer(player);

            // First allow player to take a role on the board
            // If they choose not to take role, let them move to another location
            // Then give user option to take role there
        } else {
            boolean choice = false;
            if (Board.getInstance().getSet(player.getPlayerLocation()).getIsActive() == true) {
                choice = takeRole(player);
            } else {
                System.out.println("  SCENE IS FINISHED");
            }

            if (choice == false) {
//                moveTakeRoleOption(player);
//                movePlayer(player);
            }
        }
    }

    // Function to rehearse
    // Player is not able to reherase if they already have 5 practice chips
    public void rehearse(Player player) {
        if (player.getRoleLevel() + player.getPracticeChip() < 6) {
            player.setPracticeChip(player.getPracticeChip() + 1);
/**/            UserInterfaceDisplay.getInstance().displayPlayerInfo(player);
        }
    }

    // Act function for players
    public boolean act(Player player) {
        int cardBudget = Deck.getInstance().getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum())
                .getCardBudget();
        int counter = Board.getInstance().getSet(player.getPlayerLocation()).getShotCounter();
        int diceRoll = roll();

        //String strDiceRoll = Integer.toString(diceRoll);

        SystemManager.getInstance().printLabel(Integer.toString(diceRoll));

        System.out.println("  Card Budget: " + cardBudget);
        System.out.println("  Dice Rolled: " + diceRoll);
        System.out.println("  Shot Counter [Before Act] " + counter);

        // if success
        if (diceRoll + player.getPracticeChip() >= cardBudget) {
            // if oncar
            counter -= 1;
            Board.getInstance().getSet(player.getPlayerLocation()).setShotCounter(counter);

            //System.out.println("\n  SUCCESS IN ACTING");
            SystemManager.getInstance().printLabel("SUCCESS IN ACTING");
            System.out.println("  Current Shot Counter: " + counter);
            if (player.getOnCardRole() == true) { // on card
                player.setCredit(player.getCredit() + 2);
            } else {// off card
                player.setCredit(player.getCredit() + 1);
                player.setDollar(player.getDollar() + 1);
            }

/**/            UserInterfaceDisplay.getInstance().displayPlayerInfo(player);

            // end of card, calculate payout will be called and reset card and player information
            if (counter == 0) {
                int cardNum = Deck.getInstance()
                        .getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum()).getCardID();

                if ( Deck.getInstance().getCard(cardNum).getPlayersInRoomOnCard().isEmpty() == false) {
                    ScoringManager.getInstance().endOfCard(player, cardBudget,
                            Deck.getInstance().getCard(cardNum).getPlayersInRoomOnCard(),
                            Board.getInstance().getSet(player.getPlayerLocation()).getPlayersInRoomOffCard(), Deck.getInstance().getCard(cardNum).getPart().size());
                } else {
                    ScoringManager.getInstance().endCardNoCardWorkers(player, Board.getInstance().getSet(player.getPlayerLocation()).getPlayersInRoomOffCard());
                }

                Board.getInstance().getSet(player.getPlayerLocation()).resetSetAtCard();
                Board.getInstance().getSet(player.getPlayerLocation()).setIsActive(false);
                return true; // returns to turn() in onTurn.java
            }

        } else { // else fail
            //System.out.println("\n  FAILED IN ACTING");
            SystemManager.getInstance().printLabel("FAILED IN ACTING");
            if (player.getOffCardRole() == true) {
                player.setDollar(player.getDollar() + 1);
            }
/**/            UserInterfaceDisplay.getInstance().displayPlayerInfo(player);
        }
        return false; // returns to turn() in onTurn.java
    }

    // Random number generater between 1 and 6, like a die roll
    public static int roll() {
        Random ran = new Random();

        // Die rolls between 1 and 6
        int lowerBound = 1;
        int upperBound = 6;

        return ran.nextInt(upperBound - lowerBound + 1) + lowerBound;
    }

    // Function turn will give player options at start of turn
    // Will return true if card has finished
    // will return false if not
    public boolean turn(Player player) {
        boolean endOfCard = false;

        // If player has not taken a role, let them move
        if (player.getOffCardRole() == false && player.getOnCardRole() == false) {
            moveManager(player);
        } else {

            // If player can rehearse or act, give them options
            if (player.getRoleLevel() + player.getPracticeChip() < 6) {
                SystemManager.getInstance().makeButtonVisible(true,true,false, false);
            // If they can't rehearse anymore give them only act option
            } else {
                SystemManager.getInstance().makeButtonVisible(true,false,false,false);
            }
        }
        return endOfCard; // return to SystemManager.java
    }
}