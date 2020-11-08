import org.w3c.dom.Document;
public class Board{

    private Board instance = null;
    private Set[] set;

    //create instance
    public Board getInstance(){
        if (instance == null){
            instance = new Board();
        }
        return instance;
    }
    
    public Board() {
        Document doc = null;
        ParseXML parsing = new ParseXML();
        try {
            doc = parsing.getDocFromFile("boards.xml");
            set = parsing.readBoardData(doc);
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }

    //Get function
    public Set[] getSet() {
        return set;
    }
    
}