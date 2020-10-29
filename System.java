//Tai
import java.util.Arrays;

public class System {
    private Player[] players;
    private int numPlayer;

    //Turn manager initializes all players
    public System(int numPlayer) {
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
        for (int i = 0; i < curr.length(); i++) {
            curr[i] = 0;
        }
        return curr;
    }

    //Display winners
    public void displayWinner(Integer[] finals) {
        System.out.println("The winner(s) are: ");
        for (int i = 0; i < finals.length(); i++) {
            if (finals[i] != 0) {
                System.out.println("  Player " + finals[i]);
            }
        }
    }
    
    //Get the final scores
    public void endFunction(ScoringManager scoreManager) {
        Player[] player = getPlayerList();
        Integer[] whoWon = new Integer[getNumPlayer()];
        int index = 0;

        //Set everything to 0
        whoWon = zero(whoWon);

        //Set final score for players
        for (int i = 0; i < getNumPlayer(); i++) {
            player[i].setFinalScore(finalScore(player[i].getLevel(), player[i].getDollar(), player[i].getCredit()));

            //If player score is higher than current
            if (player[i].getFinalScore() != 0 && player[i].getFinalScore() > whoWon[player[index].getFinalScore()]) {

                //If there are no ties
                if (index == 0) {
                    whoWon[index] = i + 1;
                //Else there is a tie
                } else {
                    index = 0;
                    whoWon = zero(whoWon);
                    whoWon[index] = i + 1;
                }
            //Else if player has a tie with another player, put them in list
            } else if (player[i].getFinalScore() == whoWon[index]) {
                whoWon[index++] = i + 1;
            }
        }

        displayWinner(whoWon);
    }

    public void run() {
        //Initialize scoring manager
        ScoringManager scoreManager = new ScoringManager();

        int days = calculateDaysPlayed();

        //Run for each day
        for (int i = 0; i < days; i++) {

        }

        //Calculate end score
        endFunction(scoreManager);
    }
}
