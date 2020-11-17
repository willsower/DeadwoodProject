/*
    Board class
    Purpose: This class will hold all sets in a hashtable. Decided to use this data
             structure to increase look up times by names of sets. Has a function to
             assign card to each set at start of day
    Singleton = true
*/

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
                doc = parsing.getDocFromFile("xml/boards.xml");
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

    // Assigns cards to the set each day
    public static void assignCardToSet(Integer[] deckOrder, int day) {
        int index = (day * 10) - 10;
        Enumeration<Set> values = getBoard().elements();

        // iterate through values
        while (values.hasMoreElements()) {
            Set set = values.nextElement();

            // Doesn't set cards to trailer or casting office
            if (!set.getSetName().equals("trailer") && !set.getSetName().equals("office")) {
                set.setCardNum(deckOrder[index]);
                set.setHasCard(true);
                set.setIsActive(true);
            
                index++;
            }
        }
    }
}