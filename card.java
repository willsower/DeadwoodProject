//Tai

public class Card {    
    //Part object which will display roles on card
    class Part {
        public String partName;
        public String line;
        public int level;
        public int xVal;
        public int yVal;
        public int hVal;
        public int wVal;
    }

    private String cardName;
    private int cardBudget;
    private String sceneDescription;
    private int cardID;
    private Part[] part = new Part[3];

    public Card(String name, int budget, int cardID) {
        cardName = name;
        cardBudget = budget;
        cardID = this.cardID;

        part = initPart(part);
    }

    //Initialize the parts
    public Part[] initPart(Part[] parts) {
        for (int i = 0; i < 3; i++) {
            parts[i] = new Part();
        }

        return parts;
    }

    //Get functions
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

    //Set functions

    //Set scene description
    public void setSceneDescription(String scene) {
        sceneDescription = scene;
    }

    //Set part name and level
    public void setPartNameLevel(int partNum, int partLevel, String partName) {
        Part[] part = getPart();

        part[partNum].partName = partName;
        part[partNum].level = partLevel;
    }

    //Set part coordinates
    public void setPartCoords(int partNum, int x, int y, int h, int w) {
        Part[] part = getPart();

        part[partNum].xVal = x;
        part[partNum].yVal = y;
        part[partNum].hVal = h;
        part[partNum].wVal = w;

    }

    //Set part line
    public void setPartLine(int partNum, String line) {
        Part[] part = getPart();

        part[partNum].line = line;
    }

    public void helperPrintCard() {
        System.out.println("Printing information for card " + getCardID());
        System.out.println("Card = " + getCardName());
        System.out.println("Budget = " + getCardBudget());
        System.out.println("Scene = " + getSceneDescription());

        Part[] test = getPart();

        for (int i = 0; i < 3; i++) {
            System.out.println();
            System.out.println(test[i].partName);
            System.out.println(test[i].level);
            System.out.println(test[i].xVal);
            System.out.println(test[i].yVal);
            System.out.println(test[i].hVal);
            System.out.println(test[i].wVal);
            System.out.println(test[i].line);
        }
    }

    public static void main(String[] args) {

    }
}