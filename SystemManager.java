//Tai
import java.util.Arrays;

public class SystemManager {
    private Player[] players;
    private int numPlayer;

    //Turn manager initializes all players
    public SystemManager(int numPlayer) {
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
                    players[i] = new Player(i + 1, 1, 0, 0, "Trailers");
                    break;
            }
        }
    }

    //Get function
    public int getNumPlayer() {
        return numPlayer;
    }

    public Player[] getPlayerList() {
        return players;
    }

    //Calculate days of play
    private int calculateDaysPlayed() {
        if (getNumPlayer() == 2 || getNumPlayer() == 3) {
            return 3;
        } 
        return 4;
    }

    //Set zero array to 0
    public Integer[] zero(Integer[] curr) {
        for (int i = 0; i < curr.length; i++) {
            curr[i] = 0;
        }
        return curr;
    }

    //Get the final scores
    public void endFunction() {
        Player[] player = getPlayerList();
        Integer[] whoWon = new Integer[getNumPlayer()];
        int index = 0;

        //Set everything to 0
        whoWon = zero(whoWon);

        //Set final score for players
        for (int i = 0; i < getNumPlayer(); i++) {
            player[i].setFinalScore(ScoringManager.getInstance().finalScore(player[i].getLevel(), player[i].getDollar(), player[i].getCredit()));

            //First player goes in
            if (whoWon[0] == 0) {
                whoWon[0] = 1;

            //If player score is higher than current
            } else if (player[i].getFinalScore() != 0 && player[i].getFinalScore() > player[whoWon[index] - 1].getFinalScore()) {
                //If there are no ties
                if (whoWon[index] == 0) {
                    whoWon[index] = i + 1;
                //Else there is a tie
                } else {
                    index = 0;
                    whoWon = zero(whoWon);
                    whoWon[index] = i + 1;
                }
            //Else if player has a tie with another player, put them in list
            } else if (player[i].getFinalScore() == player[whoWon[index] - 1].getFinalScore()) {
                index++;
                whoWon[index] = i + 1;
            }
        }

        UserInterface.getInstance().displayWinner(whoWon);
    }

    public void run() {
        //Initialize onTurn
        OnTurn turn = new OnTurn();
        Player[] list = getPlayerList();

        int days = calculateDaysPlayed();

        //Run for each day
        for (int i = 0; i < days; i++) {
            //do while (iterate through each player's turn, stop at end day)
                //call onTurn

            //figure out if 9/10 cards are done, to end day
        }

        //Calculate end score
        endFunction();
    }
}
