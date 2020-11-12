/*
    Deadwood Class
    Purpose: Initiates the game, asks how many players, then calls
             system manager to setup everything.
*/

public class Deadwood {
    public static void main(String[] args) {
        try {
            int numPlayers = UserInterface.getInstance().getNumPlayers();
            systemManager(numPlayers);

        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }

    // Create system for players then run functionality
    public static void systemManager(int numPlayers) {
        SystemManager systemManager = new SystemManager(numPlayers);
        systemManager.run();
    }
}
