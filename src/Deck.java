/*
    Deck Class
    Purpose: Creates a Deck of Card objects (40). Will create an int array of card order
             for the game
    Singleton = true
*/

import org.w3c.dom.Document;
import java.util.Random;
import javafx.scene.image.Image;

public class Deck {
    private Card[] deck;
    private Integer[] cardShuffle;
    private static Deck instance = null;
    private Image backOfCard;
    private Image backOfCardSmall;

    // create instance
    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }

    // Deck constructor. Will call parser on cards.xml file
    // Create deck of card objects
    public Deck() {
        Document doc = null;
        ParseXML parsing = new ParseXML();
        try {
            doc = parsing.getDocFromFile("xml/cards.xml");
            deck = parsing.readCardData(doc);

            backOfCard = new Image("./images/CardBack.jpg");
            backOfCardSmall = new Image("./images/CardBack-small.jpg");
            cardShuffle = shuffle();
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }

    // Get function
    public Card[] getDeck() {
        return deck;
    }

    public Integer[] getCardShuffle() {
        return cardShuffle;
    }

    public Card getCard(int cardNum) {
        Card[] temp = getDeck();
        return temp[cardNum - 1];
    }

    // Shuffle is going to create an array of a random
    // generated list of numbers (1->40). This will ensure
    // randomness at every game
    public static Integer[] shuffle() {
        Integer[] list = new Integer[40];

        // Populate list
        for (int i = 0; i < list.length; i++) {
            list[i] = i + 1;
        }

        // Shuffle
        Random random = new Random();
        for (int i = 0; i < list.length; i++) {
            int index = random.nextInt(list.length);
            int temp = list[index];
            list[index] = list[i];
            list[i] = temp;
        }

        return list;
    }
}
