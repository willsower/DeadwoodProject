//Tai

public class card {    
    class Part {
        String partName;
        String line;
        int level;

        public Part(String name, String line, int level) {
            partName = this.name;
            line = this.line;
            level = this.level;
        }
    }

    private String cardName;
    private int cardBudget;
    private int cardID;
    private Part part1;
    private Part part2;
    private Part part3; 

    public static card(String name, int budget, int cardID) {

    }

    //Get functions
    public static String getCardName() {
        return cardName;
    }

    public static getCardBudget() {
        return cardBudget;
    }

    public static getCardID() {
        return cardID;
    }   

    public static void main(String[] args) {

    }
}