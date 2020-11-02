import java.util.Scanner;

//together!
public class Main {
    public static void main(String[] args) {
        try {
            Scanner myOb = new Scanner(System.in);

            int numPlayers = getNumPlayers(myOb);
            systemManager(numPlayers);

        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }

    //Prompts user how many players will be playing
    //Will throw player in loop until they enter 2-8 players
    public static int getNumPlayers(Scanner myOb) {
        String val;
        int numberPlayers = 0;

        while (!(numberPlayers >= 2 && numberPlayers <= 8)) {
            System.out.println("How many players? (2 - 8) ");
            val = myOb.nextLine();
            try {
                numberPlayers = Integer.parseInt(val);
            } catch (NumberFormatException e) {}
        }

        return numberPlayers;
    }

    //Create system for players then run functionality
    public static void systemManager(int numPlayers) {
        SystemManager turnManager = new SystemManager(numPlayers);
        turnManager.run();
    }
}
