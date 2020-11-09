//Singleton class, will be where we get user input/output

import java.util.Scanner;

public class UserInterface {
    private static UserInterface instance = null;

    // Create instance
    public static UserInterface getInstance() {
        if (instance == null) {
            instance = new UserInterface();
        }
        return instance;
    }

    // Prompts user how many players will be playing
    // Will throw player in loop until they enter 2-8 players
    public static int getNumPlayers() {
        Scanner myOb = new Scanner(System.in);
        String val;
        int numberPlayers = 0;

        while (!(numberPlayers >= 2 && numberPlayers <= 8)) {
            System.out.println("How many players? (2 - 8) ");
            val = myOb.nextLine();
            try {
                numberPlayers = Integer.parseInt(val);
            } catch (NumberFormatException e) {
            }
        }

        return numberPlayers;
    }

    // Display winners of game
    public void displayWinner(Integer[] finals) {
        System.out.println("The winner(s) are: ");
        for (int i = 0; i < finals.length; i++) {
            if (finals[i] != 0) {
                System.out.println("  Player " + finals[i]);
            }
        }
    }

    // Move option
    public String moveTakeRole(Player player) {
        System.out.println("Would you like to move? (Y/N) [press any key to quit]");
        Scanner ob = new Scanner(System.in);
        String val = ob.nextLine();
        String returnType = "q";

        if (val.equals("Y") || val.equals("y") || val.equals("Yes") || val.equals("yes")) {
            String[] neighbors = Board.getInstance().getSet(player.getPlayerLocation()).getNeighbor();

            for (int i = 0; i < neighbors.length; i++) {
                System.out.println("Type " + (i + 1) + "to move to '" + neighbors[i] + "'");
            }
            returnType = ob.nextLine();
        }
        return returnType;
    }
}
