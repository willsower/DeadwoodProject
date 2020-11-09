import org.w3c.dom.Document;
import java.util.*;

public class Board {

    private static Board instance = null;
    private Hashtable<String, Set> board;

    // create instance
    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public Board() {
        Document doc = null;
        ParseXML parsing = new ParseXML();
        try {
            doc = parsing.getDocFromFile("boards.xml");
            board = parsing.readBoardData(doc);
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }

    // Get function
    public Hashtable<String, Set> getBoard() {
        return board;
    }

    public Set getSet(String location) {
        return getBoard().get(location);
    }
}