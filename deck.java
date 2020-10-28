//Tai
//make singleton
import org.w3c.dom.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck {
    private Card[] deck;

    public Deck() {
        Document doc = null;
        ParseXML parsing = new ParseXML();
        try {
            doc = parsing.getDocFromFile("cards.xml");
            deck = parsing.readCardData(doc);
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }

    public static void removeCardFromDeck(int cardNum) {

    }

    public static void addCardToDeck(int cardNum) {

    }

    //Shuffle is going to create an array a random
    //generated list of numbers. This will ensure
    //randomness at every game
    public static Integer[] shuffle() {
        Integer[] list = new Integer[40];
        List<Integer> myList = Arrays.asList(list);
		Collections.shuffle(myList);
        myList.toArray(list);
        
        System.out.println(Arrays.toString(list));
        return list;
    }

    public static void cardsToBard() {

    }

    public static void main(String[] args) {
        Deck myDeck = new Deck();
    }
}