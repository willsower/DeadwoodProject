
//Tai
import java.util.Random;
import java.util.ArrayList;

public class OnTurn {
    //Return true if number is numeric
    //false if not
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
            player.setPlayerLocation(neighbors[Integer.parseInt(move)-1]);

            if (player.getPlayerLocation().equals("Trailers")) {
                //Do nothing
            } else if (player.getPlayerLocation().equals("Casting Office")) {
                //Call upgrade
            } else {

                ArrayList<String> partsOnCardAval = Deck.getInstance().getCard(Board.getInstance().getSet(player.getPlayerLocation()).getCardNum()).availablePartsOnCard();
                ArrayList<String> partsOffCardAval = Board.getInstance().getSet(player.getPlayerLocation()).availablePartsOffCard();

                String playerChoice =UserInterface.getInstance().roleChoice( partsOnCardAval, partsOffCardAval);

                if(isNumeric(playerChoice)){
                    int roleNumber = Integer.parseInt(playerChoice);
                    //if (roleNumber <= )
                }

            }
        } else {
            //Exit onMove
        }

        // call user interface
        // Interact with board?

        // if player moved to casting office
        // allow upgrade possibility

        // if player moved to card area and card still has roles
        // give option to choose roll
    }

    public static void takeOnCardRole(Player player) {
        player.setOnCardRole(true);
    }

    public static void takeOffCardRole(Player player) {
        player.setOffCardRole(true);
    }

    // Function to see if player is allowed to rehearse.
    // Player is not able to reherase if they already have 5 practice chips
    public static boolean canRehearse(int practiceChip) {
        if (practiceChip == 5) {
            return false;
        }
        return true;
        // setter function in player will increase practice chip
    }

    public static boolean act() {
        return true;
    }

    // Random number generater between 1 and 6, like a die roll
    public static int roll() {
        Random ran = new Random();

        // Die rolls between 1 and 6
        int lowerBound = 1;
        int upperBound = 6;

        return ran.nextInt(upperBound - lowerBound + 1) + lowerBound;
    }

    public void turn(Player player) {

        // If player has not taken a role
        if (player.getOffCardRole() == false || player.getOnCardRole() == false) {

        }
    }
}
