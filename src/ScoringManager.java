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
    private String bonusInformation;
    ArrayList<Image> payout = new ArrayList<Image>();
    boolean bonus = false;

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

    public String getBonusInformation() { return bonusInformation; }

    public boolean bonusAccepted() { return bonus; }

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
        bonus = true;

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
        setBonusDistributed("Bonuses Distributed");

        bonusInformation = "";
        bonusInformation += "Players On Card:\n";
        // Give payout to on card players
        for (Player p : playersOnCard) {
            // If player has highest role rank (highest priority)
            if (p.getRolePriority() == 1) {
                p.setDollar(p.getDollar() + payout[0]);
                bonusInformation += "Player " + p.getPlayerPriority() + ": +" + payout[0] + " Dollar\n";
            // If player has median role rank (middle priority)
            } else if (p.getRolePriority() == 2) {
                p.setDollar(p.getDollar() + payout[1]);
                bonusInformation += "Player " + p.getPlayerPriority() + ": +" + payout[1] + " Dollar\n";
            // If player has low role rank (low priority)
            } else {
                p.setDollar(p.getDollar() + payout[2]);
                bonusInformation += "Player " + p.getPlayerPriority() + ": +" + payout[2] + " Dollar\n";
            }
            p.resetPlayers(false); // parameter is for isNotEndOfCard
        }

        bonusInformation += "\nPlayers Off Card:\n";
        // Give payout to off card players
        for (Player p : playersOffCard) {
            p.setDollar(p.getDollar() + bonusOffCard(p.getRoleLevel())); // role rank
            p.resetPlayers(false);
            System.out.println(bonusOffCard(p.getRoleLevel()));
            bonusInformation += "Player " + p.getPlayerPriority() + ": +" + bonusOffCard(p.getRoleLevel()) + " Dollar\n";
        }
    }

    // Function to reset players if there were no onCard players
    public void endCardNoCardWorkers(Player player, ArrayList<Player> playersOffCard) {
        setBonusDistributed("No Bonuses Distributed");
        bonus = false;
        for (Player p : playersOffCard) {
            p.resetPlayers(false);
        }
    }
}
