//Tai

public class turnManager {
    LinkedList<int> playerTurn = new LinkedList<int>();
    private int currentPlayer;
    private int nextPlayer;

    public static turnManager(int numPlayer) {

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