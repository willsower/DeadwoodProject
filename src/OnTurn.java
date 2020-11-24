/*
    OnTurn Class
    Purpose: This class has everything to do with player choices in game
             Whether they decide to upgrade (which will call upgrade class),
             Act, Rehearse, Move, or take a role. All these interactions
             and calculations will be done in this class
*/

import java.util.Enumeration;
import java.util.Hashtable;
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
    public static void takeOffCardRole(Player player, String roleName, String setName) {
        Set set = Board.getInstance().getSet(setName);

        player.setOffCardRole(true);
        player.setRoleLevel(set.getPartLevel(setName));
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
        } else {
            // key of card name
            int cardNum = Deck.getInstance()
                    .getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum()).getCardID();
            System.out.println("  Card Name: " + Deck.getInstance().getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum()).getCardName());
//            String playerChoice = UserInterface.getInstance().roleChoice(partsOnCardAval, partsOffCardAval, cardNum,
//                    player.getPlayerLocation());
            String playerChoice = "5";

            if (isNumeric(playerChoice)) { // choice for which role to take
                int roleNumber = Integer.parseInt(playerChoice);
                if (roleNumber <= partsOnCardAval.size()) {
                    takeOnCardRole(player, roleNumber, cardNum, partsOnCardAval.get(roleNumber - 1));
                } else {
//                    takeOffCardRole(player, roleNumber, partsOnCardAval.size(), player.getPlayerLocation(),
//                            partsOffCardAval.get(roleNumber - partsOnCardAval.size() - 1));
                }
                return true;
            }
        }
        return false;
    }

    // Function to get array of parts available  off the card
    public ArrayList<String> getPartsAvailOffCard(Player player) {
        ArrayList<String> partsOffCardAval = Board.getInstance().getSet(player.getPlayerLocation())
                .availablePartsOffCard(player.getLevel());

        for (int i = 0; i < partsOffCardAval.size(); i++) {
            partsOffCardAval.get(i).replace(",", "");
        }
        return partsOffCardAval;
    }

    // Function to get array of parts available on the card
    public ArrayList<String> getPartsAvailOnCard(Player player) {
        ArrayList<String> partsOnCardAval = Deck.getInstance()
                .getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum())
                .availablePartsOnCard(player.getLevel());

        for (int i = 0; i < partsOnCardAval.size(); i++) {
            partsOnCardAval.get(i).replace(",", "");
        }

        return partsOnCardAval;
    }

    public String parseForSet(String location) {
        String name = "";
        for (int i = 0; i < location.length(); i++) {
            if (i == 0) {
                name += Character.toUpperCase(location.charAt(i));
            } else if (Character.isUpperCase(location.charAt(i))) {
                name += " ";
                name += location.charAt(i);
            } else {
                name += location.charAt(i);
            }
        }
        return name;
    }

    public String parseMoveTo(String location) {
        String name = "";
        int countCaps = 0;
        boolean flag = false;
        for (int i = 2; i < location.length(); i++) {
            if (location.charAt(i) == 'F') {
                break;
            } else if (Character.isUpperCase(location.charAt(i))) {
                countCaps++;

                if (countCaps == 2) {
                    flag = true;
                }
            }

            if (flag) {
                name += " ";
                flag = false;
            }

            name += location.charAt(i);
        }
        return name;
    }

    // Function to check if card has been flipped or not already
    // Also this function will move the player location, and set it
    public boolean movePlayer(Player player, String location) {
        String newLocation = parseMoveTo(location);
        player.setPlayerLocation(newLocation);

        if (newLocation.equals("office") || newLocation.equals("trailer")) {
            return true;
        } else {
            return Board.getInstance().getSet(newLocation).getIsCardFlipped();
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

        SystemManager.getInstance().printLabel(Integer.toString(diceRoll));

        // if success
        if (diceRoll + player.getPracticeChip() >= cardBudget) {
            // if oncar
            counter -= 1;
            Board.getInstance().getSet(player.getPlayerLocation()).setShotCounter(counter);

            SystemManager.getInstance().printLabel("SUCCESS IN ACTING");
            System.out.println("  Current Shot Counter: " + counter);
            if (player.getOnCardRole() == true) { // on card
                player.setCredit(player.getCredit() + 2);
            } else {// off card
                player.setCredit(player.getCredit() + 1);
                player.setDollar(player.getDollar() + 1);
            }
            UserInterfaceDisplay.getInstance().displayPlayerInfo(player);

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

    // Calculate days of play
    public int calculateDaysPlayed(int numPlayer) {
        if (numPlayer == 2 || numPlayer == 3) {
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
    public void endFunction(Player[] player, int numPlayer) {
        Integer[] whoWon = new Integer[numPlayer];
        int index = 0;

        System.out.println("\n=========");
        System.out.println("Calculating end score");
        for (int i = 0; i < player.length; i++) { //probably dont need
            /**/            UserInterfaceDisplay.getInstance().displayPlayerInfo(player[i]);
        }
        System.out.println("\n\n");

        // Set everything to 0
        whoWon = zero(whoWon);

        // Set final score for players
        for (int i = 0; i < numPlayer; i++) {
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

        /**/        UserInterfaceDisplay.getInstance().displayWinner(whoWon); //dont need
    }

    // Resetall function will be called at the start of each game
    // It will reset the variables in other classes, put players back into trailers
    // Put cards on the appropriate sets
    public void resetHelper(Player[] list, int day, int numPlayer) {
        // Reset player info
        for (int i = 0; i < numPlayer; i++) {
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
    }

    // Turn manager initializes all players
    public Player[] init(int numPlayer) {

        // Init num players array
        Player[] players = new Player[numPlayer];

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
                    //players[i] = new Player(i + 1, 1, 0, 0, "trailer", playerDie[i]);
                    players[i] = new Player(i + 1, 1, 10, 10, "office", playerDie[i]);
                    break;
            }
        }
        return players;
    }
}