
//Tai
import java.util.Random;
import java.util.ArrayList;

public class OnTurn {
    // Return true if number is numeric
    // false if not
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void onMove(Player player) {
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
                // Upgrade
            } else {

                ArrayList<String> partsOnCardAval = Deck.getInstance()
                        .getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum())
                        .availablePartsOnCard();
                ArrayList<String> partsOffCardAval = Board.getInstance().getSet(player.getPlayerLocation())
                        .availablePartsOffCard();

                String playerChoice = UserInterface.getInstance().roleChoice(partsOnCardAval, partsOffCardAval);

                if (isNumeric(playerChoice)) {
                    int roleNumber = Integer.parseInt(playerChoice);
                    if (roleNumber <= partsOnCardAval.size()) {
                        takeOnCardRole(player, roleNumber);
                    } else {
                        takeOffCardRole(player, roleNumber, partsOnCardAval.size());
                    }
                }
            }
        }
    }

    public static void takeOnCardRole(Player player, int roleNumber) {
        int level = Deck.getInstance().getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum())
                .getPartLevel(roleNumber - 1);

        player.setOnCardRole(true);
        player.setRoleLevel(level);
    }

    public static void takeOffCardRole(Player player, int roleNumber, int size) {
        int level = Board.getInstance().getSet(player.getPlayerLocation()).getPartLevel(roleNumber - size - 1);

        player.setOffCardRole(true);
        player.setRoleLevel(level);
    }

    // Function to rehearse
    // Player is not able to reherase if they already have 5 practice chips
    public void canRehearse(Player player) {
        if (player.getRoleLevel() + player.getPracticeChip() < 6) {
            player.setPracticeChip(player.getPracticeChip() + 1);
        }
    }

    //Act function for players
    public boolean act(Player player) {
        int cardBudget = Deck.getInstance().getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum())
                .getCardBudget();
        int diceRole = roll();

        // if success
        if (diceRole + player.getPracticeChip() >= cardBudget) {
            // if oncard
            int counter = Board.getInstance().getSet(player.getPlayerLocation()).getShotCounter();
            Board.getInstance().getSet(player.getPlayerLocation()).setShotCounter(counter--);

            if (player.getOnCardRole() == true) {
                player.setCredit(player.getCredit() + 2);
            } else {
                player.setCredit(player.getCredit() + 1);
                player.setDollar(player.getDollar() + 1);
            }

            // end of card
            if (counter == 0) {
                return true;
            }

        } else { // else fail
            if (player.getOffCardRole() == true) {
                player.setDollar(player.getDollar() + 1);
            }
        }
        return false;
    }

    // Random number generater between 1 and 6, like a die roll
    public static int roll() {
        Random ran = new Random();

        // Die rolls between 1 and 6
        int lowerBound = 1;
        int upperBound = 6;

        return ran.nextInt(upperBound - lowerBound + 1) + lowerBound;
    }

    // return 1 - one card has finished
    // return 0 - move or rehearse
    public void turn(Player player) {

        // If player has not taken a role
        if (player.getOffCardRole() == false || player.getOnCardRole() == false) {
            // move
        }
    }
}
