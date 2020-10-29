//Tai
import java.util.Arrays;

public class TurnManager {
    private Player[] players;
    private int currentPlayer;
    private int nextPlayer;

    public TurnManager(int numPlayer) {

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
