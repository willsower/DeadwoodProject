//Tai

public class card {    
    class Part {
        String partName;
        String line;
        int level;

        public Part(String name, String line, int level) {
            partName = name;
            line = this.line;
            level = this.level;
        }
    }

    private String cardName;
    private int cardBudget;
    private String sceneDescription;
    private int cardID;
    private Part part1;
    private Part part2;
    private Part part3; 

    public card(String name, int budget, int cardID) {

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

    public static void main(String[] args) {

    }
}