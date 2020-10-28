//Tai

public class Card {    
    //Part object which will display roles on card
    class Part {
        public String partName;
        public String line;
        public int level;
        public String xVal;
        public String yVal;
        public String hVal;
        public String wVal;
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

    //Set functions
    public void setSceneDescription(String scene) {
        sceneDescription = scene;
    }

    public static void main(String[] args) {

    }
}