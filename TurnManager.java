//Tai
import java.util.Arrays;

public class TurnManager {
    private Player[] players;
    private int numPlayer;

    //Turn manager initializes all players
    public TurnManager(int numPlayer) {
        this.numPlayer = numPlayer;

        //Init num players array
        players = new Player[numPlayer];

        //Populate players
        for (int i = 0; i < numPlayer; i++) {
            switch (numPlayer) {
                case 5:
                    players[i] = new Player(i + 1, 1, 0, 2, "Trailers");
                    break;
                case 6:
                    players[i] = new Player(i + 1, 1, 0, 4, "Trailers");
                    break;
                case 7:
                    players[i] = new Player(i + 1, 2, 0, 0, "Trailers");
                    break;
                case 8:
                    players[i] = new Player(i + 1, 2, 0, 0, "Trailers");
                    break;
                default:
                    players[i] = new Player(i + 1, 0, 0, 0, "Trailers");
                    break;
            }
        }
    }

    //Get function
    public int getNumPlayer() {
        return numPlayer;
    }

    //Calculate days of play
    private int calculateDaysPlayed() {
        if (getNumPlayer() == 2 || getNumPlayer() == 3) {
            return 3;
        } 
        return 4;
    }

    public void run() {
        int days = calculateDaysPlayed();

        for (int i = 0; i < days; i++) {

        }

        //Would calculate scoring here
    }
}
