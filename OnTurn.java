import java.util.Random;
import java.util.ArrayList;

public class OnTurn {
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
        int level = Deck.getInstance().getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum())
                .getPartLevel(roleNumber - 1);

        int rolePriority = Deck.getInstance()
                .getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum())
                .getPartPriority(roleNumber - 1);

        player.setOnCardRole(true);
        player.setRoleLevel(level);
        player.setRoleLocation(player.getPlayerLocation());
        player.setRolePriority(rolePriority);
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
        Board.getInstance().getSet(setName).addPlayerToRoomOffCard(player);
        Board.getInstance().getSet(setName).setPartTaken(roleName, true);
    }

    // Function for taking a role
    // Will call helper methods if user decides to take role
    public void takeRole(Player player) {
        ArrayList<String> partsOnCardAval = Deck.getInstance()
                .getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum())
                .availablePartsOnCard();
        ArrayList<String> partsOffCardAval = Board.getInstance().getSet(player.getPlayerLocation())
                .availablePartsOffCard();

        // key of card name
        int cardNum = Deck.getInstance()
                .getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum()).getCardID();

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
        }
    }
    

    public void onMove(Player player) {
        // Allow player to upgrade
        if (player.getPlayerLocation().equals("Casting Office")) {
            UserInterface.getInstance().upgradePlayer(player, player.getLevel(), player.getPlayerLocation(), player.getDollar(), player.getCredit());
        }

        // Gets neighbors of room player currently is in
        String[] neighbors = Board.getInstance().getSet(player.getPlayerLocation()).getNeighbor();

        // Get user input if player wants to move
        String move = UserInterface.getInstance().moveOption(player, neighbors);
        int numNeighbors = neighbors.length;

        // If player enters number, move to that area
        if (isNumeric(move)) {
            player.setPlayerLocation(neighbors[Integer.parseInt(move) - 1]);

            if (player.getPlayerLocation().equals("Trailers")) {
                // Do nothing
            } else if (player.getPlayerLocation().equals("Casting Office")) {
                UserInterface.getInstance().upgradePlayer(player, player.getLevel(), player.getPlayerLocation(), player.getDollar(), player.getCredit());
            } else {
                takeRole(player);
            }
        }
    }

    // Function to rehearse
    // Player is not able to reherase if they already have 5 practice chips
    public void rehearse(Player player) {
        if (player.getRoleLevel() + player.getPracticeChip() < 6) {
            player.setPracticeChip(player.getPracticeChip() + 1);
        }
    }

    // Act function for players
    public boolean act(Player player) {
        int cardBudget = Deck.getInstance().getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum())
                .getCardBudget();
        int diceRoll = roll();

        // if success
        if (diceRoll + player.getPracticeChip() >= cardBudget) {
            // if oncard
            int counter = Board.getInstance().getSet(player.getPlayerLocation()).getShotCounter();
            Board.getInstance().getSet(player.getPlayerLocation()).setShotCounter(counter--);

            System.out.println("Success in performing role");
            if (player.getOnCardRole() == true) { // on card
                player.setCredit(player.getCredit() + 2);
            } else {// off card
                player.setCredit(player.getCredit() + 1);
                player.setDollar(player.getDollar() + 1);
            }

            // end of card
            if (counter == 0) {
                int cardNum = Deck.getInstance()
                        .getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum()).getCardID();
                if (!(Deck.getInstance().getCard(cardNum).getPlayersInRoomOnCard()).isEmpty()) {
                    /* mostly done */ ScoringManager.getInstance().endOfCard(player, cardBudget,
                            Deck.getInstance().getCard(cardNum).getPlayersInRoomOnCard(),
                            Board.getInstance().getSet(player.getPlayerLocation()).getPlayersInRoomOffCard());
                }

                return true; // returns to turn() in onTurn.java
            }

        } else { // else fail
            System.out.println("Failed in performing role");
            if (player.getOffCardRole() == true) {
                player.setDollar(player.getDollar() + 1);
            }
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
        System.out.println("\n\nPlayer " + player.getPlayerPriority() + " turn!");
        boolean endOfCard = false;
        // If player has not taken a role, let them move
        if (player.getOffCardRole() == false && player.getOnCardRole() == false) {
            onMove(player);
        } else {
            // If player can rehearse or act, give them options
            // If they can't rehearse anymore give them only act option
            if (player.getRoleLevel() + player.getPracticeChip() < 6) {
                int decide = UserInterface.getInstance().actOrRehearse();
                if (decide == 1) {
                    endOfCard = act(player);
                } else if (decide == 2) {
                    rehearse(player);
                }
            } else {
                if (UserInterface.getInstance().act()) {
                    endOfCard = act(player);
                }
            }
        }
        return endOfCard; // return to SystemManager.java
    }

}

/*
 * where do we include the lines? no make sure player cant leave role yes does
 * end of card mean end of day? or wrap scene go to bonuses -the counter in act
 * is for the shots the when its at 0 it would go to bonus,remove card, ect.. -
 * maybe only need + for rolechoice from userinterface - should check for valid
 * number? -and possibly for moveoption too when are the cards choisen from the
 * deck? -is it just shuffled and then one is provided when current card is
 * done? + print messages about success or fail for user need to update setup
 * for different group sizes for upgrade has the required amount they need been
 * set and/or implimented need to create reset board for end of day
 */

/*
 * for scoremanager - we can create an array or list of players in card.java to
 * keep track of players in oncard in location - same for set.java to keep track
 * of players offcard in location - ArrayList<> playersInRoom - possibly include
 * the part name (is hashmap type thing) - then in scoremanager we would call
 * those lists - would also need addPlayer function for when a player takes a
 * role
 * 
 * - might want to incude a getCurrentCard function in card.java - we would also
 * need a removePlayer and both would be called in onMove
 * 
 * --- check if at least one person is on card --- remove card from deck
 * 
 * - create a reset deck in deck.java
 * 
 */

/*
 * REMINDER TO ME
 * 
 * shotCounter => 0 --> end of scene -> bonus endOfDay => 9/10 cards --> reset
 * deck and all players
 * 
 */