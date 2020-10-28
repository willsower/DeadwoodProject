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

        sceneDescription = "";

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
    public void setSceneDescription(String scene) {
        sceneDescription = scene;
    }

    public void setPartNameLevel(int partNum, int partLevel, String partName) {
        Part[] part = getPart();

        part[partNum].partName = partName;
        part[partNum].level = partLevel;
    }

    public void setPartCoords(int partNum, int x, int y, int h, int w) {
        Part[] part = getPart();

        part[partNum].xVal = x;
        part[partNum].yVal = y;
        part[partNum].hVal = h;
        part[partNum].wVal = w;

    }

    public void setPartLine(int partNum, String line) {
        Part[] part = getPart();

        part[partNum].line = line;
    }

    public static void main(String[] args) {

    }
}