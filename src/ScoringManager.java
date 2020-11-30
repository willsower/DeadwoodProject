/*
    ScoringManager Class
    Purpose: This class will do anything scoring related. It will calculate the score
             payouts at the end of the card. Also will calculate the final scores
             at the end of the game. Will also distribute those payout to players
    Singleton = true
*/

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.image.Image;

public class ScoringManager {

    private static ScoringManager instance = null;
    private String bonusDistrubted;
    ArrayList<Image> payout = new ArrayList<Image>();

    // Create instance
    public static ScoringManager getInstance() {
        if (instance == null) {
            instance = new ScoringManager();
        }
        return instance;
    }

    // Getter Function

    public String getBonusDistributed() { return bonusDistrubted; }

    public ArrayList<Image> getPayoutImages() { return payout; }

    // Setter Function

    public void setBonusDistributed(String val) { bonusDistrubted = val; }

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

    // Calculates payout value and will return the int values in an
    // array
    public int[] calculatePayout(int budget, int totalRoles) { // add perameter on howmany roles on card
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
        payout.clear();

        // Adding the rolls in the previous array to the payout
        // array
        for (int i = budget - 1; i >= 0; i--) {
            total[index] += budgetHolder[i];
            index++;
            Image temp = new Image("./images/dice/w" + budgetHolder[i] + ".png");
            payout.add(temp);
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
//        System.out.println("\nEnd of Card: Bonuses distributed");
        setBonusDistributed("Bonuses Distributed");
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
///**/            UserInterfaceDisplay.getInstance().displayPlayerInfo(p);
        }
        System.out.println("Players off card: ");
        // Give payout to off card players
        for (Player p : playersOffCard) {
            p.setDollar(p.getDollar() + bonusOffCard(p.getRoleLevel())); // role rank
            p.resetPlayers(false);
        }
    }

    // Function to reset players if there were no onCard players
    public void endCardNoCardWorkers(Player player, ArrayList<Player> playersOffCard) {
        setBonusDistributed("No Bonuses Distributed");
        for (Player p : playersOffCard) {
            p.resetPlayers(false);
        }
    }
}
