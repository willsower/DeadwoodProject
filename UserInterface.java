//Singleton class, will be where we get user input/output

import java.util.Scanner;

public class UserInterface {
    private static UserInterface instance = null;

    //Create instance
    public static UserInterface getInstance() {
        if (instance == null) {
            instance = new UserInterface(); 
        }
        return instance; 
    }

    //Prompts user how many players will be playing
    //Will throw player in loop until they enter 2-8 players
    public static int getNumPlayers() {
        Scanner myOb = new Scanner(System.in);
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
}
