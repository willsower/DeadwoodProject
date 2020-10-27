//Tai

public class turnManager {
    LinkedList<int> playerTurn = new LinkedList<int>();
    private int currentPlayer;
    private int nextPlayer;

    public static turnManager(int numPlayer) {

    }

    //Get functions
    public static getCurrentPlayer() {
        return currentPlayer;
    }

    public static getNextPlayer() {
        return nextPlayer;
    }

    //Set functions
    public static setCurrentPlayer(int player) {
        currentPlayer = player;
    }

    public static setNextPlayer(int player) {
        nextPlayer = player;
    }
    
    public static void main(String[] args) {

    }
}