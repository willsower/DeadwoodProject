//Tai
import java.util.ArrayList;

public class Card {
    // Part object which will display roles on card
    class Part {
        public String partName;
        public String line;
        public int level;
        public int xVal;
        public int yVal;
        public int hVal;
        public int wVal;
        public boolean isTaken = false;
    }

    private String cardName;
    private int cardBudget;
    private String sceneDescription;
    private int cardID;
    private Part[] part = new Part[3];
    private ArrayList<Integer> playersInRoom = new ArrayList<Integer>();

    public Card(String name, int budget, int cardID) {
        cardName = name;
        cardBudget = budget;
        this.cardID = cardID;

        part = initPart(part);
    }

    // Initialize the parts
    public Part[] initPart(Part[] parts) {
        for (int i = 0; i < 3; i++) {
            parts[i] = new Part();
        }

        return parts;
    }

    // Get functions
    public String getCardName() {
        return cardName;
    }

    public int getCardBudget() {
        return cardBudget;
    }

    public int getCardID() {
        return cardID;
    }

    public String getSceneDescription() {
        return sceneDescription;
    }

    public Part[] getPart() {
        return part;
    }

    public int getPartLevel(String partName){
        Part[] myPart = getPart();
        for (int i = 0; i < myPart.length; i++){
            if (myPart[i].partName.equals(partName)){
                return myPart[i].level;
            }
        }
        return 0;
    }

    public ArrayList<Player> getPlayersInRoom() {
        return playersInRoom;
    }

 

    // Set functions

    // Set scene description
    public void setSceneDescription(String scene) {
        sceneDescription = scene;
    }

    // Set part name and level
    public void setPartNameLevel(int partNum, int partLevel, String partName) {
        Part[] part = getPart();

        part[partNum].partName = partName;
        part[partNum].level = partLevel;
    }

    // Set part coordinates
    public void setPartCoords(int partNum, int x, int y, int h, int w) {
        Part[] part = getPart();

        part[partNum].xVal = x;
        part[partNum].yVal = y;
        part[partNum].hVal = h;
        part[partNum].wVal = w;

    }

    // Set part line
    public void setPartLine(int partNum, String line) {
        Part[] part = getPart();

        part[partNum].line = line;
    }

    public void setPartTaken(String partName, boolean taken) {
        Part[] partTaken = getPart();
        for (int i = 0; i < getPart().length; i++) {
            if (partName.equals(partTaken[i].partName)) {
                partTaken[i].isTaken = taken;
            }
        }
    }

    // Available parts in card 
    public ArrayList<String> availablePartsOnCard() {
        ArrayList<String> available = new ArrayList<String>();
        Part[] myParts = getPart();

        for (int i = 0; i < 3; i++) {
            if (myParts[i].isTaken == false) {
                available.add(myParts[i].partName);
            }
        }
        return available;
    }

    // Get certain part level 
    public int getPartLevel(int num) {
        Part[] myParts = getPart();
        return myParts[num].level;
    }


    public void addPlayerToRoom(Player player) {
        playersInRoom.add(player);
    }

    public void removePlayersFormRoom(Player player) {
        playersInRoom.clear();
    }


    // Helper function for the sake of printing out each card's values
    // Helps with debugging
    public void helperPrintCard() {
        System.out.println("Printing information for card " + getCardID());
        System.out.println("Card = " + getCardName());
        System.out.println("Budget = " + getCardBudget());
        System.out.println("Scene = " + getSceneDescription());

        Part[] test = getPart();

        for (int i = 0; i < 3; i++) {
            System.out.println();
            System.out.println(test[i].partName);
            System.out.println("Part Level = " + test[i].level);
            System.out.println("  xVal = " + test[i].xVal);
            System.out.println("  yVal = " + test[i].yVal);
            System.out.println("  hVal = " + test[i].hVal);
            System.out.println("  wVal = " + test[i].wVal);
            System.out.println("  line = " + test[i].line);
        }
    }
}
