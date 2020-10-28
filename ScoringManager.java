//Tai
import java.util.Random; 

public class ScoringManager {

    //Adds together user dollar and credits
    public static int addDollarCredits(int dollar, int credit) {
        return dollar + credit;
    }

    //Calculates points by player rank and adds dollar and credit total
    public static int getRankPoints(int rank, int dollarCredit) {
        int tot = rank * 5;
        return tot + dollarCredit;
    }

    //Gets player final score
    public static int finalScore(int rank, int dollar, int credit) {
        int firstTotal = addDollarCredits(dollar, credit);
        return getRankPoints(rank, firstTotal);
    }

    public static boolean onCard() {
        return true;
    }

    //Calculates payout value and will return the int values in an
    //array
    public static int[] calculatePayout(int budget) {
        OnTurn turn = new OnTurn();
        
        int highestRank = 0;
        int middleRank = 0;
        int lowestRank = 0;
        int[] total = new int[3];

        //Rolls the amount of die equal to the budget.
        //Will add to each level how much should earn
        for (int i = 1; i <= budget; i++) {
            int temp = turn.roll();
            switch (i) {
                case 1:
                    highestRank += temp;
                    break;
                case 2:
                    middleRank += temp;
                    break;
                case 3: 
                    lowestRank += temp;
                    break;
                case 4: 
                    highestRank += temp;
                    break;
                case 5: 
                    middleRank += temp;
                    break;
                default:
                    lowestRank += temp;
                    break;
            }
        }

        //Populates array with payout values
        total[0] = highestRank;
        total[1] = middleRank;
        total[2] = lowestRank;

        return total;
    }

    //Returns the payout depending on their role on the card
    public static int bonusOnCard(int priorityOnRole, int budget) {
        int[] total = calculatePayout(budget);

        if (priorityOnRole == 1) {
            return total[0];
        } else if (priorityOnRole == 2) {
            return total[1];
        } else {
            return total[2];
        }
    }

    //Gives off card player money to the rank of their role 
    public static int bonusOffCard(int roleRank) {
        return roleRank;
    }

    public static void main(String[] args) {

    }
}