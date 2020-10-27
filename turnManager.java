//Tai
import java.util.*; 

public class turnManager {
    LinkedList<Integer> playerTurn = new LinkedList<Integer>();
    private int currentPlayer;
    private int nextPlayer;

    public turnManager(int numPlayer) {

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