//together!

public class Main {
    public static void main(String[] args) {
        try {
            ParseXML test = new ParseXML();
            test.getDocFromFile("boards.xml");
            // int numPlayers = UserInterface.getInstance().getNumPlayers();
            // systemManager(numPlayers);

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
