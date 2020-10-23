//Tai
import java.util.Random; 

public class scoringManager {
    onTurn turn = new onTurn();

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
        int roll1, roll2, roll3, roll4, roll5, roll6;

        for (int i = 0; i < budget; i++) {
            roll[i] = turn.roll();
        }
    }

    public static void bonusOnCard() {

    }

    public static void bonusOffCard() {

    }
}