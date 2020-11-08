//together!
import org.w3c.dom.Document;

public class Main {
    public static void main(String[] args) {
        Document doc = null;
        ParseXML parsing = new ParseXML();
        try {
            doc = parsing.getDocFromFile("boards.xml");
            parsing.readBoardData(doc);
            //int numPlayers = UserInterface.getInstance().getNumPlayers();
            //systemManager(numPlayers);

        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }

    //Create system for players then run functionality
    public static void systemManager(int numPlayers) {
        SystemManager turnManager = new SystemManager(numPlayers);
        turnManager.run();
    }
}
