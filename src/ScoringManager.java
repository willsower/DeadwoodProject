/*
    ScoringManager Class
    Purpose: This class will do anything scoring related. It will calculate the score
             payouts at the end of the card. Also will calculate the final scores
             at the end of the game. Will also distribute those payout to players
    Singleton = true
*/

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class ScoringManager {

    private static ScoringManager instance = null;

    // Create instance
    public static ScoringManager getInstance() {
        if (instance == null) {
            instance = new ScoringManager();
        }
        return instance;
    }

    // Adds together user dollar and credits
    public static int addDollarCredits(int dollar, int credit) {
        return dollar + credit;
    }

    // Calculates points by player rank and adds dollar and credit total
    public static int getRankPoints(int rank, int dollarCredit) {
        int tot = rank * 5;
        return tot + dollarCredit;
    }

    // Gets player final score
    public static int finalScore(int rank, int dollar, int credit) {
        int firstTotal = addDollarCredits(dollar, credit);
        return getRankPoints(rank, firstTotal);
    }

    /* do we use this function? */
    public static boolean onCard() {
        return true;
    }

    // Calculates payout value and will return the int values in an
    // array
    public static int[] calculatePayout(int budget, int totalRoles) { // add perameter on howmany roles on card
        OnTurn turn = new OnTurn();

        int[] total = new int[totalRoles];
        int[] budgetHolder = new int[budget];
        int index = 0;
        Arrays.fill(total, 0);

        // Adding roll to array
        for (int i = 0; i < budget; i++) {
            int temp = turn.roll();
            budgetHolder[i] = temp;
        }

        Arrays.sort(budgetHolder);

        // Adding the rolls in the previous array to the payout
        // array
        for (int i = budget - 1; i >= 0; i--) {
            total[index] += budgetHolder[i];
            index++;

            if (index == totalRoles) {
                index = 0;
            }
        }
        return total;
    }

    // Gives off card player money to the rank of their role
    public static int bonusOffCard(int roleRank) {
        return roleRank;
    }

    // Function to distribute end of card payouts to on card players and off card
    // players
    public void endOfCard(Player player, int cardBudget, ArrayList<Player> playersOnCard,
            ArrayList<Player> playersOffCard, int cardSlots) {
        int[] payout = calculatePayout(cardBudget, cardSlots);
        System.out.println("Card Budget: " + cardBudget + " Card Slots: " + cardSlots);
        System.out.println("\nEnd of Card: Bonuses distributed");
        System.out.println("Players on card ");
        // Give payout to on card players
        for (Player p : playersOnCard) { 
            System.out.println("  Player " + p.getRolePriority());
            
            System.out.println();
            // If player has highest role rank (highest priority)
            if (p.getRolePriority() == 1) {
                p.setDollar(p.getDollar() + payout[0]);
            // If player has median role rank (middle priority)
            } else if (p.getRolePriority() == 2) {
                p.setDollar(p.getDollar() + payout[1]);
            // If player has low role rank (low priority)
            } else {
                p.setDollar(p.getDollar() + payout[2]);
            }
            p.resetPlayers(false); // parameter is for isNotEndOfCard
/**/            UserInterfaceDisplay.getInstance().displayPlayerInfo(p);
        }
        System.out.println("Players off card: ");
        // Give payout to off card players
        for (Player p : playersOffCard) {
            System.out.println("  Player " + p.getPlayerPriority());
            System.out.println();
            p.setDollar(p.getDollar() + bonusOffCard(p.getRoleLevel())); // role rank
            p.resetPlayers(false); // parameter is for isNotEndOfCard
/**/            UserInterfaceDisplay.getInstance().displayPlayerInfo(p);
        }
    }

    // Function to reset players if there were no onCard players
    public void endCardNoCardWorkers(Player player, ArrayList<Player> playersOffCard) {
        System.out.println("\nEnd of Card: No Bonuses Given [No on card workers]");
        System.out.println("Players off card: ");
        for (Player p : playersOffCard) {
            System.out.println("  Player " + p.getPlayerPriority());
            p.resetPlayers(false);
/**/            UserInterfaceDisplay.getInstance().displayPlayerInfo(p);
        }
    }
}
