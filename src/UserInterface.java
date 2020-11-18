/*
    UserInterface class
    Purpose: Display user prompts and get user input throughout
             the game
    Singleton = true
*/

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
//
////====    // Display winners of game
//    public void displayWinner(Integer[] finals) {
//        System.out.println("The winner(s) are: ");
//        for (int i = 0; i < finals.length; i++) {
//            if (finals[i] != 0) {
//                System.out.println("  Player " + finals[i]);
//            }
//        }
//    }

    // Move option
    public String moveOption(Player player, String[] neighbors, Boolean[] isActive) {
        Scanner ob = new Scanner(System.in);
        String val;
        String returnType = "q";
        System.out.println();

        // Prompts user to move
        do {
            System.out.println("Would you like to move? (Y/N)");
            System.out.println("[Press q to forfeit turn]");
            val = ob.nextLine();
        } while (!val.equals("Y") && !val.equals("y") && !val.equals("Yes") && !val.equals("yes") && !val.equals("q")
                && !val.equals("Q") && !val.equals("N") && !val.equals("n"));

        // If yes, display available moving options, along with whether that set is
        // active or not
        if (val.equals("Y") || val.equals("y") || val.equals("Yes") || val.equals("yes")) {
            int num = 0;
            System.out.println();
            do {
                for (int i = 0; i < neighbors.length; i++) {
                    if (neighbors[i].equals("trailer") || neighbors[i].equals("office")) {
                        System.out.println("Type " + (i + 1) + " to move to '" + neighbors[i] + "'");
                    } else {
                        System.out.println("Type " + (i + 1) + " to move to '" + neighbors[i] + "'" + " Active Set: "
                                + isActive[i]);
                    }
                }
                System.out.println("[Press q to forfeit turn]");
                returnType = ob.nextLine();

                try {
                    Integer.parseInt(returnType);
                    num = Integer.parseInt(returnType);
                } catch (NumberFormatException e) {
                }
            } while (!(num > 0 && num <= neighbors.length) && !returnType.equals("Q") && !(returnType.equals("q")));
        }
        return returnType; // returns to onMove() in OnTurn.java
    }

    // Give user option to get a role -> Output is user's input (this value will
    // help us calculate)
    // if user took a role or not
    public String roleChoice(ArrayList<String> onCard, ArrayList<String> offCard, int card, String setName) {
        Scanner ob = new Scanner(System.in);
        String val;
        System.out.println();

        // Prompts user to take a role
        do {
            System.out.println("Would you like to take a role? (Y/N)");
            val = ob.nextLine();
        } while (!val.equals("Y") && !val.equals("y") && !val.equals("Yes") && !val.equals("yes") && !val.equals("N")
                && !val.equals("n"));

        String returnType = "q";

        // If user wants to take a role
        if (val.equals("Y") || val.equals("y") || val.equals("Yes") || val.equals("yes")) {
            int num = 0;
            int k = onCard.size();
            System.out.println();

            do {
                // Print on card roles (if applicable)
                if (!onCard.isEmpty()) {
                    for (int i = 0; i < onCard.size(); i++) {
                        int level = Deck.getInstance().getCard(card).getPartLevel(onCard.get(i));
                        System.out.println("Type " + (i + 1) + " to choose [on card] role of " + onCard.get(i)
                                + " level " + level);
                        ;
                    }
                }
                // Print off card roels (if applicable)
                if (!offCard.isEmpty()) {
                    for (int i = 0; i < offCard.size(); i++) {
                        int level = Board.getInstance().getSet(setName).getPartLevel(offCard.get(i));
                        System.out.println("Type " + (i + k + 1) + " to choose [off card] role of " + offCard.get(i)
                                + " level " + level);
                    }
                }
                System.out.println("[Press q to quit]");
                returnType = ob.nextLine();

                try {
                    Integer.parseInt(returnType);
                    num = Integer.parseInt(returnType);
                } catch (NumberFormatException e) {
                }

            } while (!(num > 0 && num <= (k + offCard.size())) && !returnType.equals("Q") && !(returnType.equals("q")));
        }

        return returnType; // returns to onMove() in OnTurn.java
    }

    // Give user option to act
    public boolean act() {
        Scanner ob = new Scanner(System.in);
        String val;
        System.out.println();
        do {
            System.out.println("Would you like to act? (Y/N)");
            val = ob.nextLine();
        } while (!val.equals("Y") && !val.equals("y") && !val.equals("Yes") && !val.equals("yes") && !val.equals("N")
                && !val.equals("n"));

        if (val.equals("Y") || val.equals("y") || val.equals("Yes") || val.equals("yes")) {
            return true;
        }
        return false; // returns to act() in OnTurn.java
    }

    // Give user option to act or rehearse
    public int actOrRehearse() {
        Scanner ob = new Scanner(System.in);
        String val;
        System.out.println();
        do {
            System.out.println("Would you like to act? (Type 'a')");
            System.out.println("Would you like to rehearse? (Type 'r')");
            System.out.println("[Type 'q' for quit]");

            val = ob.nextLine();
        } while (!val.equals("a") && !val.equals("r") && !val.equals("q") && !val.equals("A") && !val.equals("R")
                && !val.equals("Q"));

        // Returns appropriate variable depending on user input
        if (val.equals("a") || val.equals("A")) {
            return 1;
        } else if (val.equals("r") || val.equals("R")) {
            return 2;
        }
        return 3;
    }

    // Upgrade player, it will check to see if player can upgrade then will prompt
    // user
    // on what to upgrade with.
    public int upgradePlayer(Player player, int currentLevel, String location, int dollar, int credit) {
        UserInterfaceDisplay.getInstance().displayCastingOffice(player);
        Scanner ob = new Scanner(System.in);
        String returnType = "";
        int num = 0;
        ArrayList<Integer> lev = new ArrayList<Integer>();
       
        // Checks if user can upgrade
        if (Upgrade.getInstance().canUpgrade(currentLevel, location, dollar, credit)) {

            lev = Upgrade.getInstance().levelsCanUpgrade(player);
            System.out.println();
            do {
                for (int i = 0; i < lev.size(); i++) {
                    System.out.println("Press " + (i + 1) + " to upgrade to Level " + lev.get(i));
                }

                System.out.println("[Press q to quit]");
                returnType = ob.nextLine();

                try {
                    Integer.parseInt(returnType);
                    num = Integer.parseInt(returnType);

                } catch (NumberFormatException e) {
                }
            } while (!(num > 0 && num <= lev.size()) && !returnType.equals("Q") && !(returnType.equals("q")));
        } else {
            return 0;
        }
        if (returnType.equals("Q") || returnType.equals("q")) {
            return 0;
        }
        return lev.get(num - 1);
    }
//
////====    // Output player information
//    public void playerUpgrade(Player player) {
//        System.out.println("\nJust upgraded!");
//        System.out.println("  Player rank: " + player.getLevel());
//        System.out.println("  Player dollars: " + player.getDollar());
//        System.out.println("  Player credits: " + player.getCredit());
//    }
//
////====    // Display Casting Office Level upgrades
//    public void displayCastingOffice(Player player) {
//        System.out.println("Welcome to the Casting Office!");
//        System.out.println("  Level 2 | Dollar: " + Upgrade.getInstance().levelTwo.dollar + " Credit: "
//                + Upgrade.getInstance().levelTwo.credit);
//        System.out.println("  Level 3 | Dollar: " + Upgrade.getInstance().levelThree.dollar + " Credit: "
//                + Upgrade.getInstance().levelThree.credit);
//        System.out.println("  Level 4 | Dollar: " + Upgrade.getInstance().levelFour.dollar + " Credit: "
//                + Upgrade.getInstance().levelFour.credit);
//        System.out.println("  Level 5 | Dollar: " + Upgrade.getInstance().levelFive.dollar + " Credit: "
//                + Upgrade.getInstance().levelFive.credit);
//        System.out.println("  Level 6 | Dollar: " + Upgrade.getInstance().levelSix.dollar + " Credit: "
//                + Upgrade.getInstance().levelSix.credit);
//
//        displayPlayerInfo(player);
//    }
//
////====    // Display player info
//    public void displayPlayerInfo(Player player) {
//        System.out.println("  Player " + player.getPlayerPriority());
//        System.out.println("    Player level: " + player.getLevel());
//        System.out.println("    Player dollars: " + player.getDollar());
//        System.out.println("    Player credits: " + player.getCredit());
//        System.out.println("    Player practice chips " + player.getPracticeChip());
//    }
}