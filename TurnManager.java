//Tai
import java.util.Arrays;

public class TurnManager {
    private Player[] players;
    private int currentPlayer;
    private int nextPlayer;

    //Turn manager initializes all players
    public TurnManager(int numPlayer) {
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

    //Get functions
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getNextPlayer() {
        return nextPlayer;
    }

    //Set functions
    public void setCurrentPlayer(int player) {
        currentPlayer = player;
    }

    public void setNextPlayer(int player) {
        nextPlayer = player;
    }
    
    public static void main(String[] args) {

    }
}
