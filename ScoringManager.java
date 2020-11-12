import java.util.Random;
import java.util.ArrayList;

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

/*do we use this function?*/    
    public static boolean onCard() {
        return true;
    }

    // Calculates payout value and will return the int values in an
    // array
    public static int[] calculatePayout(int budget) { //add perameter on howmany roles on card
        OnTurn turn = new OnTurn();
        //int array total[how many]
        
        int[] total = new int[3];
        int index = 0;
        // Rolls the amount of die equal to the budget.
        // Will add to each level how much should earn
        for (int i = 1; i <= budget; i++) {///////////////////////////////////////////////
            int temp = turn.roll();
            //tot[index] =temp
            //index++
            //if index > spots -> set back to zero
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
        ArrayList<Player> playersOffCard) {
        int[] payout = calculatePayout(cardBudget);
        
        System.out.println("End of Card: Bonuses distributed");

        // Give payout to on card players
        for (Player p : playersOnCard) { /////////////////////////////////////

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
            System.out.println("  Player "+ p.getPlayerPriority() + " has "+ p.getDollar() + " dollars and " + p.getCredit() + " credits\nrPlayer " + p.getPlayerPriority() + " is rank " + p.getLevel());
            
            p.resetPlayers(false); //parameter is for isNotEndOfCard
        }

        // Give payout to off card players
        for (Player p : playersOffCard) {
            p.setDollar(p.getDollar() + bonusOffCard(p.getRoleLevel())); // role rank
            System.out.println("  Player "+ p + " has "+ p.getDollar() + " dollars and " + p.getCredit() + " credits\nrPlayer " + p + " is rank " + p.getLevel());
            p.resetPlayers(false); //parameter is for isNotEndOfCard
        }
    }

    public void endCardNoCardWorkers(Player player, ArrayList<Player> playersOffCard) {
        System.out.println("End of Card: No Bonuses Given [No on card workers]");

        for (Player p : playersOffCard) {
            p.resetPlayers(false);
        }
    }
}
