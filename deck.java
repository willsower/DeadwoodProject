//Tai
//make singleton
import org.w3c.dom.Document;
import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck;

    public Deck() {
        Document doc = null;
        ParseXML parsing = new ParseXML();

        doc = parsing.getDocFromFile("cards.xml");
        deck = parsing.readCardData(doc);
    }

    public static void removeCardFromDeck(int cardNum) {

    }

    public static void addCardToDeck(int cardNum) {

    }

    public static void shuffle() {

    }

    public static void cardsToBard() {

    }

    public static void main(String[] args) {

    }
}