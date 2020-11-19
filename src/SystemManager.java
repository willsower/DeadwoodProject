/*
    SystemManager Class
    Purpose: This class is the power of this game. It will create the player objects, manage
             how many days will be played, and will trigger the onTurn functions for each 
             player when it's their turn. Will also calculate end of game metrics by calling
             ScoringManager and will reset each day by calling helper functions
*/

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.*;


public class SystemManager implements Initializable {
    private Player[] players;
    private int numPlayer;
    private static SystemManager instance = null;

    // create instance
    public static SystemManager getInstance(int num) {
        if (instance == null) {
            instance = new SystemManager(num);
        }
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // Turn manager initializes all players
    public SystemManager(int numPlayer) {
        this.numPlayer = numPlayer;

        // Init num players array
        players = new Player[numPlayer];

        // Array of dice colors
        String[] playerDie = new String[]{"b", "c", "g", "o", "p", "r", "v", "w", "y"};

        // Populate players
        for (int i = 0; i < numPlayer; i++) {
            switch (numPlayer) {
                case 5:
                    players[i] = new Player(i + 1, 1, 0, 2, "trailer", playerDie[i]);
                    break;
                case 6:
                    players[i] = new Player(i + 1, 1, 0, 4, "trailer", playerDie[i]);
                    break;
                case 7:
                    players[i] = new Player(i + 1, 2, 0, 0, "trailer", playerDie[i]);
                    break;
                case 8:
                    players[i] = new Player(i + 1, 2, 0, 0, "trailer", playerDie[i]);
                    break;
                default:
                    players[i] = new Player(i + 1, 1, 0, 0, "trailer", playerDie[i]);
                    break;
            }
        }
    }

    // Get function
    public int getNumPlayer() {
        return numPlayer;
    }

    public Player[] getPlayerList() {
        return players;
    }

    // Calculate days of play
    private int calculateDaysPlayed() {
        if (getNumPlayer() == 2 || getNumPlayer() == 3) {
            return 3;
        }
        return 4;
    }

    // Set zero array to 0
    public Integer[] zero(Integer[] curr) {
        for (int i = 0; i < curr.length; i++) {
            curr[i] = 0;
        }
        return curr;
    }

    // Get the final scores
    public void endFunction() {
        Player[] player = getPlayerList();
        Integer[] whoWon = new Integer[getNumPlayer()];
        int index = 0;

        System.out.println("\n=========");
        System.out.println("Calculating end score");
        for (int i = 0; i < player.length; i++) {
/**/            UserInterfaceDisplay.getInstance().displayPlayerInfo(player[i]);
        }
        System.out.println("\n\n");

        // Set everything to 0
        whoWon = zero(whoWon);

        // Set final score for players
        for (int i = 0; i < getNumPlayer(); i++) {
            player[i].setFinalScore(ScoringManager.getInstance().finalScore(player[i].getLevel(), player[i].getDollar(),
                    player[i].getCredit()));

            // First player goes in
            if (whoWon[0] == 0) {
                whoWon[0] = 1;

                // If player score is higher than current
            } else if (player[i].getFinalScore() != 0
                    && player[i].getFinalScore() > player[whoWon[index] - 1].getFinalScore()) {
                // If there are no ties
                if (whoWon[1] == 0) {
                    whoWon[index] = i + 1;
                    // Else there is a tie
                } else {
                    index = 0;
                    whoWon = zero(whoWon);
                    whoWon[index] = i + 1;
                }
                // Else if player has a tie with another player, put them in list
            } else if (player[i].getFinalScore() == player[whoWon[index] - 1].getFinalScore()) {
                index++;
                whoWon[index] = i + 1;
            }
        }

/**/        UserInterfaceDisplay.getInstance().displayWinner(whoWon);
    }

    // Resetall function will be called at the start of each game
    // It will reset the variables in other classes, put players back into trailers
    // Put cards on the appropriate sets
    public void resetAll(Player[] list, int day) {
        // Reset player info
        for (int i = 0; i < getNumPlayer(); i++) {
            list[i].resetPlayers(true); // parameter is for notEndOfCard
        }

        Hashtable<String, Set> board = Board.getInstance().getBoard();

        Enumeration<Set> values = board.elements();
        int ind = 0;
        // iterate through values
        while (values.hasMoreElements()) {
            Set set = values.nextElement();
            if (ind < 10) {
                set.resetSetDay();
            }
            ind++;
        }
        Board.getInstance().assignCardToSet(Deck.getInstance().getCardShuffle(), day);
        UserInterfaceDisplay.getInstance().setUpBoard(day, 2);
    }

    // This is the run function, will play for x amount of days
    // and iterate through a do-while loop until the amount of cards
    // have finished for that day.
    public void run() {
        OnTurn turn = new OnTurn();
        Player[] list = getPlayerList();
        int player = 0;

        int days = calculateDaysPlayed();

        // Run for each day
        for (int i = 0; i < days; i++) {
            int cardsFinished = 0;

            resetAll(list, i + 1);

//            do {
//                // If card has finished increment cards finished
//                if (turn.turn(list[player])) {
//                    cardsFinished++;
//                }
//
//                player++; // Next player turn
//
//                // Reset back to player 1
//                if (player == list.length) {
//                    player = 0;
//                }
//
//            } while (cardsFinished < 9); /* !9/10 cards */
        }
        // Calculate end score
        endFunction();
    }
}
