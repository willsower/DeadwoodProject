//Tai
//make singleton
import org.w3c.dom.Document;
import java.util.Arrays;
import java.util.Random;

public class Deck {
    private Card[] deck;
    private Integer[] cardShuffle;

    //Deck constructor. Will call parser on cards.xml file
    //Create deck of card objects
    public Deck() {
        Document doc = null;
        ParseXML parsing = new ParseXML();
        try {
            doc = parsing.getDocFromFile("cards.xml");
            deck = parsing.readCardData(doc);

            cardShuffle = shuffle();
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }

    //Get function
    public Card[] getDeck() {
        return deck;
    }

    //Shuffle is going to create an array a random
    //generated list of numbers. This will ensure
    //randomness at every game
    public static Integer[] shuffle() {
        Integer[] list = new Integer[40];

        //Populate list
        for (int i = 0; i < list.length; i++) {
            list[i] = i + 1;
        }

        //Shuffle
        Random random = new Random();
		for (int i = 0; i < list.length; i++) {
			int index = random.nextInt(list.length);
			int temp = list[index];
			list[index] = list[i];
			list[i] = temp;
        }
        
		System.out.println(Arrays.toString(list));
        return list;
    }

    public static void cardsToBoard() {

    }

    public static void main(String[] args) {

    }
}
