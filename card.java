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
    private Part part1 = new Part();
    private Part part2 = new Part();
    private Part part3 = new Part(); 

    public Card(String name, int budget, int cardID) {

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
    public String setSceneDescription(String scene) {
        sceneDescription = scene;
    }

    public static void main(String[] args) {

    }
}