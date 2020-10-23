//Tai
import java.util.Random; 

public class scoringManager {

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

    public static void calculatePayout(int budget) {
        onTurn turn = new onTurn();
        
        int highestRank;
        int middleRank;
        int lowestRank;

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
    }

    public static void bonusOnCard() {
    
    }

    public static void bonusOffCard() {

    }
}