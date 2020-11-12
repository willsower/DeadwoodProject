/*
    Deadwood Class
    Purpose: Initiates the game, asks how many players, then calls
             system manager to setup everything.
*/
import java.util.*;

public class Deadwood {
    public static void main(String[] args) {
        Scanner myOb = new Scanner(System.in);
        String val = args[0];
        int numberPlayers = 0;
	    try {
            numberPlayers = Integer.parseInt(val);
        } catch (NumberFormatException nfe) {
        }

        while (!(numberPlayers >= 2 && numberPlayers <= 8)) {
            System.out.println("How many players? (2 - 8) ");
            val = myOb.nextLine();
            try {
                numberPlayers = Integer.parseInt(val);
            } catch (NumberFormatException e) {
            }
        }
        systemManager(numberPlayers);

    }

    // Create system for players then run functionality
    public static void systemManager(int numPlayers) {
        SystemManager systemManager = new SystemManager(numPlayers);
        systemManager.run();
    }
}
