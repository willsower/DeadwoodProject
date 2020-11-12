import org.w3c.dom.Document;
import java.util.*;

public class Board {

    private static Board instance = null;
    private static Hashtable<String, Set> board;
    
    // create instance
    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();

            Document doc = null;
            ParseXML parsing = new ParseXML();
            try {
                doc = parsing.getDocFromFile("boards.xml");
                board = parsing.readBoardData(doc);
            } catch (Exception e) {
                System.out.println("Error = " + e);
            }
        }
        return instance;
    }

    // Get function
    public static Hashtable<String, Set> getBoard() {
        return board;
    }

    public Set getSet(String location) {
        return getBoard().get(location);
    }

    // Assigns cards to the set each time
    public static void assignCardToSet(Integer[] deckOrder, int day) {
        int index = (day * 10) - 10;
        Enumeration<Set> values = getBoard().elements();

        // iterate through values
        while (values.hasMoreElements()) {
            Set set = values.nextElement();

            if (!set.getSetName().equals("trailer") && !set.getSetName().equals("office")) {
                set.setCardNum(deckOrder[index]);
                set.setHasCard(true);
                set.setIsActive(true);
            
                index++;
            }
        }
    }
}