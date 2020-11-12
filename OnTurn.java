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
                UserInterface.getInstance().upgradePlayer(player, player.getLevel(), player.getPlayerLocation(),
                        player.getDollar(), player.getCredit());
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
            UserInterface.getInstance().upgradePlayer(player, player.getLevel(), player.getPlayerLocation(),
                    player.getDollar(), player.getCredit());
            moveTakeRoleOption(player);

            // Allow player to move then take a role
        } else if (player.getPlayerLocation().equals("trailer")) {
            moveTakeRoleOption(player);

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
                moveTakeRoleOption(player);
            }
        }
    }

    // Function to rehearse
    // Player is not able to reherase if they already have 5 practice chips
    public void rehearse(Player player) {
        if (player.getRoleLevel() + player.getPracticeChip() < 6) {
            player.setPracticeChip(player.getPracticeChip() + 1);
            System.out.println("  You now have " + player.getPracticeChip() + " practice chips");
            System.out.println("  You are on role level " + player.getRoleLevel());
        }
    }

    // Act function for players
    public boolean act(Player player) {
        int cardBudget = Deck.getInstance().getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum())
                .getCardBudget();
        int counter = Board.getInstance().getSet(player.getPlayerLocation()).getShotCounter();
        int diceRoll = roll();

        System.out.println("  Card Budget: " + cardBudget);
        System.out.println("  Dice Rolled: " + diceRoll);
        System.out.println("  Player Practice Chips: " + player.getPracticeChip());
        System.out.println("  Card Shot Counter " + counter);

        // if success
        if (diceRoll + player.getPracticeChip() >= cardBudget) {
            // if oncar
            counter -= 1;
            Board.getInstance().getSet(player.getPlayerLocation()).setShotCounter(counter);

            System.out.println("  SUCCESS IN ACTING");
            System.out.println("  Current Shot Counter: " + counter);
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

                if ( Deck.getInstance().getCard(cardNum).getPlayersInRoomOnCard().isEmpty() == false) {
                    ScoringManager.getInstance().endOfCard(player, cardBudget,
                            Deck.getInstance().getCard(cardNum).getPlayersInRoomOnCard(),
                            Board.getInstance().getSet(player.getPlayerLocation()).getPlayersInRoomOffCard());
                } else {
                    ScoringManager.getInstance().endCardNoCardWorkers(player, Board.getInstance().getSet(player.getPlayerLocation()).getPlayersInRoomOffCard());
                }

                Board.getInstance().getSet(player.getPlayerLocation()).setIsActive(false);
                return true; // returns to turn() in onTurn.java
            }

        } else { // else fail
            System.out.println("  FAILED IN ACTING");
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
        System.out.println("\n==========");
        System.out.println("Player " + player.getPlayerPriority() + " turn!");
        boolean endOfCard = false;
        // If player has not taken a role, let them move
        if (player.getOffCardRole() == false && player.getOnCardRole() == false) {
            moveManager(player);
        } else {
            System.out.println("  You are in room " + player.getPlayerLocation());
            System.out.println("  You are on card " + Deck.getInstance()
                    .getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum()).getCardName());
            System.out.println("  Your role is " + player.getRoleName());
            System.out.println("  Card budget is " +   Deck.getInstance().getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum()).getCardBudget());

            // If player can rehearse or act, give them options
            if (player.getRoleLevel() + player.getPracticeChip() < 6) {
                int decide = UserInterface.getInstance().actOrRehearse();
                System.out.println(decide);
                if (decide == 1) {
                    endOfCard = act(player);
                } else if (decide == 2) {
                    rehearse(player);
                }
                // If they can't rehearse anymore give them only act option
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
 * TODO:
 * 
 * Bugs: 1. Fix ParseXML failure bug at beginning 2. Fix NullPtr exception at
 * this command Type 1 to move to 'Train Station' Type 2 to move to 'Ranch' Type
 * 3 to move to 'Secret Hideout' [Press q to quit] 1 Would you like to take a
 * role? (Y/N) yes Type 1 to choose [on card] role of Curious girl level 3 Type
 * 2 to choose [on card] role of Ghost of Plato level 4 Error =
 * java.lang.NullPointerException
 * 
 * Functions: 1. Implement the function that will be called at start of day to
 * set everything up (This is the same function that will be called to reset if
 * it is end day but at start) 1.a TODO this, go in each major class (Player,
 * Set, etc) and create a resetPlayer or resetSet function That we can call to
 * reset all players/sets/other classes. For example, we'd have to reset all
 * player.Location back to Trailers, and reset player.PracticeChip to 0, same
 * with set. We need to go to setParts and reset the isTaken to false and other
 * stuff. (Don't need to do Card, since the Card won't ever be played again). 2.
 * Have a better user input (use while loops and have a
 * "Are you sure you want to move" option) 3. Have better prints when player
 * enters room 4. Comment functions 5. Clean up functions 6. Need to adjust
 * shotcounters 7. Player can only take rank or lower 8. Reset practice chips of
 * end of card
 * 
 */

/*
 * where do we include the lines? no make sure player cant leave role yes does
 * end of card mean end of day? or wrap scene go to bonuses -the counter in act
 * is for the shots the when its at 0 it would go to bonus,remove card, ect.. +
 * for rolechoice from userinterface - should check for valid number? -and
 * possibly for moveoption too when are the cards choisen from the deck? -is it
 * just shuffled and then one is provided when current card is done? + print
 * messages about success or fail for user need to update setup for different
 * group sizes for upgrade has the required amount they need set and/or
 * implimented need to create reset board for end of day
 */

/*
 * REMINDER TO ME
 * 
 * shotCounter => 0 --> end of scene -> bonus endOfDay => 9/10 cards --> reset
 * deck and all players
 * 
 */