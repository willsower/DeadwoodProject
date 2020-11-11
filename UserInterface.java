//Singleton class, will be where we get user input/output

import java.util.Scanner;
import java.util.ArrayList;

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
    public String moveOption(Player player, String[] neighbors) {
        Scanner ob = new Scanner(System.in);
        String val;

        System.out.println("Would you like to move? (Y/N)");
        val = ob.nextLine();

        while (!val.equals("Y") || !val.equals("y") || !val.equals("Yes") || !val.equals("yes") || !val.equals("q") || !val.equals("Q") || !val.equals("N") || !val.equals("n")) {
            System.out.println("Would you like to move? (Y/N)");
            System.out.println("[Press q to quit]");
            val = ob.nextLine();
        }
        String returnType = "q";

        if (val.equals("Y") || val.equals("y") || val.equals("Yes") || val.equals("yes")) {
            for (int i = 0; i < neighbors.length; i++) {
                System.out.println("Type " + (i + 1) + " to move to '" + neighbors[i] + "'");
            }
            System.out.println("[Press q to quit]");
            returnType = ob.nextLine();

            int num = 0;
            try {
                Integer.parseInt(returnType);
                num = Integer.parseInt(returnType);
            } catch (NumberFormatException e) {
            }

            if (!returnType.equals("q") || !returnType.equals("Q") || (num > 0 && num <= neighbors.length)) {
                for (int i = 0; i < neighbors.length; i++) {
                    System.out.println("Type " + (i + 1) + " to move to '" + neighbors[i] + "'");
                }
                System.out.println("[Press q to quit]");
                returnType = ob.nextLine();
    
                try {
                    Integer.parseInt(returnType);
                    num = Integer.parseInt(returnType);
                } catch (NumberFormatException e) {
                }
            }
        }
        return returnType; //returns to onMove() in OnTurn.java
    }
 
    // Give user option to get a role
    public String roleChoice(ArrayList<String> onCard, ArrayList<String> offCard, int card, String setName) {
        System.out.println("Would you like to take a role? (Y/N)");
        Scanner ob = new Scanner(System.in);
        String val = ob.nextLine();
        String returnType = "q";

        if (val.equals("Y") || val.equals("y") || val.equals("Yes") || val.equals("yes")) {
            for (int i = 0; i < onCard.size(); i++){
                int level = Deck.getInstance().getCard(card).getPartLevel(onCard.get(i));
                System.out.println("Type " + (i + 1) + " to choose [on card] role of " + onCard.get(i) + " level "+ level);
            }
            int k = onCard.size();
            for (int i = 0; i< offCard.size(); i++){
                int level = Board.getInstance().getSet(setName).getPartLevel(offCard.get(i));
                System.out.println("Type " + (i + k + 1) + " to choose [off card] role of " + offCard.get(i) + " level "+ level);
            }
            System.out.println("[Press q to quit]");
            returnType = ob.nextLine();
        }
        
        return returnType; //returns to onMove() in OnTurn.java
    }

    // Give user option to act
    public boolean act() {
        System.out.println("Would you like to act? (Y/N)");
        System.out.println("[Type 'q' for quit]");
        Scanner ob = new Scanner(System.in);
        String val = ob.nextLine();

        if (val.equals("Y") || val.equals("y") || val.equals("Yes") || val.equals("yes")) {
            return true;
        }
        return false; //returns to act() in OnTurn.java
    }

    //Give user option to act or rehearse
    public int actOrRehearse() {
        System.out.println("Would you like to act? (Type 'a')");
        System.out.println("Would you like to rehearse? (Type 'r')");
        System.out.println("[Type 'q' for quit]");
        Scanner ob = new Scanner(System.in);
        String val = ob.nextLine();

        if (val.equals("a") || val.equals("A")) {
            return 1;
        } else if (val.equals('r') || val.equals('R')) {
            return 2;
        }
        return 3;
        // possible if wrong input 
    }

    // Upgrade player, it will check to see if player can upgrade then will prompt
    // user
    // on what to upgrade with.
    public void upgradePlayer(Player player, int currentLevel, String location, int dollar, int credit) {
        Scanner ob = new Scanner(System.in);
        String val;
        if (Upgrade.getInstance().canUpgrade(currentLevel, location, dollar, credit)) {
            if (Upgrade.getInstance().playerHasCredit(currentLevel, credit) && Upgrade.getInstance().playerHasDollar(currentLevel, dollar)) {
                System.out.println("Upgrade with credit or dollar? (C/D)");
                val = ob.nextLine();

                if (val.equals("C") || val.equals("c")) {
                    player.setCredit(player.getCredit() - Upgrade.getInstance().getLevel(currentLevel++).credit);
                    player.setLevel(currentLevel);
                } else if (val.equals("D") || val.equals("d")) {
                    player.setDollar(player.getDollar() - Upgrade.getInstance().getLevel(currentLevel++).dollar);
                    player.setLevel(currentLevel);
                }

            } else if (Upgrade.getInstance().playerHasDollar(currentLevel, dollar)) {
                System.out.println("Upgrade with dollar? (Y/N)");
                val = ob.nextLine();
                if (val.equals("Y") || val.equals("y") || val.equals("Yes") || val.equals("yes")) {
                    player.setDollar(player.getDollar() - Upgrade.getInstance().getLevel(currentLevel++).dollar);
                    player.setLevel(currentLevel);
                }
            } else if (Upgrade.getInstance().playerHasCredit(currentLevel, credit)) {
                System.out.println("Upgrade with credit? (Y/N)");
                val = ob.nextLine();
                if (val.equals("Y") || val.equals("y") || val.equals("Yes") || val.equals("yes")) {
                    player.setCredit(player.getCredit() - Upgrade.getInstance().getLevel(currentLevel++).credit);
                    player.setLevel(currentLevel);
                }
            }
        }
    }
}
