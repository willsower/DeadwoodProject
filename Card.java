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
        public int priority;
    }

    private String cardName;
    private int cardBudget;
    private String sceneDescription;
    private int cardID;
    private ArrayList<Part> part = new ArrayList<Part>();
    private ArrayList<Player> playersInRoomOnCard = new ArrayList<Player>();

    public Card(String name, int budget, int cardID) {
        cardName = name;
        cardBudget = budget;
        this.cardID = cardID;
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

    public ArrayList<Part> getPart() {
        return part;
    }

    public int getPartLevel(String partName) {
        ArrayList<Part> myPart = getPart();
        for (int i = 0; i < myPart.size(); i++) {
            if (myPart.get(i).partName.equals(partName)) {
                return myPart.get(i).level;
            }
        }
        return 0;
    }

    // Get certain part level
    public int getPartLevel(int num) {
        ArrayList<Part> myParts = getPart();
        return myParts.get(num).level;
    }

    public int getPartPriority(String partName) {
        ArrayList<Part> myPart = getPart();
        for (int i = 0; i < myPart.size(); i++) {
            if (myPart.get(i).partName.equals(partName)) {
                return myPart.get(i).priority;
            }
        }
        return 0;
    }

    public int getPartPriority(int num) {
        ArrayList<Part> myParts = getPart();
        return myParts.get(num).priority;
    }

    public ArrayList<Player> getPlayersInRoomOnCard() {
        return playersInRoomOnCard;
    }

    // Set functions

    // Set scene description
    public void setSceneDescription(String scene) {
        sceneDescription = scene;
    }

    // Set part name and level
    public void setPartNameLevel(int partNum, int partLevel, String partName, int pri) {
        Part obj = new Part();

        obj.partName = partName;
        obj.level = partLevel;
        obj.priority = pri;

        part.add(obj);
    }

    // Set part coordinates
    public void setPartCoords(int partNum, int x, int y, int h, int w) {

        part.get(partNum).xVal = x;
        part.get(partNum).yVal = y;
        part.get(partNum).hVal = h;
        part.get(partNum).wVal = w;

    }

    // Set part line
    public void setPartLine(int partNum, String line) {
        part.get(partNum).line = line;
    }

    public void setPartTaken(String partName, boolean taken) {
        ArrayList<Part> partTaken = getPart();
        for (int i = 0; i < getPart().size(); i++) {
            if (partName.equals(partTaken.get(i).partName)) {
                partTaken.get(i).isTaken = taken;
            }
        }
    }

    // Available parts in card
    public ArrayList<String> availablePartsOnCard(int playerRank) {
        ArrayList<String> available = new ArrayList<String>();
        ArrayList<Part> myParts = getPart();

        for (int i = 0; i < myParts.size(); i++) {
            if (myParts.get(i).isTaken == false) {
                if(myParts.get(i).level <= playerRank) {
                    available.add(myParts.get(i).partName);
                }
            }
        }
        return available;
    }

    public void addPlayerToRoomOnCard(Player player) {
        playersInRoomOnCard.add(player);
    }

    public void removePlayersFormRoomOnCard(Player player) {
        playersInRoomOnCard.clear();
    }

    // Helper function for the sake of printing out each card's values
    // Helps with debugging
    public void helperPrintCard() {
        System.out.println("Printing information for card " + getCardID());
        System.out.println("Card = " + getCardName());
        System.out.println("Budget = " + getCardBudget());
        System.out.println("Scene = " + getSceneDescription());

        // Part[] test = getPart();

        // for (int i = 0; i < 3; i++) {
        //     System.out.println();
        //     System.out.println(test[i].partName);
        //     System.out.println("Part Level = " + test[i].level);
        //     System.out.println("  xVal = " + test[i].xVal);
        //     System.out.println("  yVal = " + test[i].yVal);
        //     System.out.println("  hVal = " + test[i].hVal);
        //     System.out.println("  wVal = " + test[i].wVal);
        //     System.out.println("  line = " + test[i].line);
        // }
    }
}
