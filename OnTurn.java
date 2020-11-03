//Tai
import java.util.Random; 

public class OnTurn {
    public static void onMove(Player player) {

    }

    public static void takeOnCardRole()  {

    }

    public static void takeOffCardRole() {

    }

    //Function to see if player is allowed to rehearse.
    //Player is not able to reherase if they already have 5 practice chips
    public static boolean canRehearse(int practiceChip) {
        if (practiceChip == 5) {
            return false;
        }
        return true;
        //setter function in player will increase practice chip
    }

    public static boolean act() {
        return true;
    }

    //Random number generater between 1 and 6, like a die roll
    public static int roll() {
        Random ran = new Random();

        //Die rolls between 1 and 6
        int lowerBound = 1;
        int upperBound = 6;

        return ran.nextInt(upperBound - lowerBound + 1) + lowerBound;
    }

    public static void turn(Player player) {

        //If player has not taken a role
        if (player.getOffCardRole() == false || player.getOnCardRole() == false) {
            //If player is not in the casting office
            if ((player.getPlayerLocation().equals("Casting Office"))) {
                System.out.println()
            }
        }
    }
}
