import java.util.ArrayList;

public class Set {

    private String setName;
    private int numberOfNeighbors;
    private String[] neighbor;
    private int[] setArea = new int[4];
    private int numberOfTakes;
    private ArrayList<Take> take = new ArrayList<Take>();
    private int numberOfParts;
    private ArrayList<Part> part = new ArrayList<Part>();
    private boolean hasCard;
    private int cardNum;
    private int shotCounter;
    private ArrayList<Player> playersInRoomOffCard = new ArrayList<Player>();
    private boolean isActive = true;

    class Take {
        int takeNumber;
        int xVal;
        int yVal;
        int wVal;
        int hVal;

        public void setTakeNumber(int num) {
            takeNumber = num;
        }
    }

    class Part {
        String partName;
        int level;
        int xVal;
        int yVal;
        int wVal;
        int hVal;
        String line;
        boolean isTaken = false;
    }

    // constructor
    public Set(String setName) {
        this.setName = setName;
    }

    // Initialize the neighbors
    public String[] initNeigh(String[] neigh, int num) {
        neigh = new String[num];
        return neigh;
    }

    // getters
    public String getSetName() {
        return setName;
    }

    public int getNumberOfNeighbors() {
        return numberOfNeighbors;
    }

    public String[] getNeighbor() {
        return neighbor;
    }

    public int[] getSetArea() {
        return setArea;
    }

    public int getNumberOfTakes() {
        return numberOfTakes;
    }

    public ArrayList<Take> getTake() {
        return take;
    }

    public int getNumberOfParts() {
        return numberOfParts;
    }

    public ArrayList<Part> getParts() {
        return part;
    }

    public boolean getHasCard() {
        return hasCard;
    }

    public int getCardNum() {
        return cardNum;
    }

    public int getShotCounter(){
        return shotCounter;
    }

    public boolean getIsActive() {
        return isActive;
    }

    // setters
    public void setNumberOfNeighbors(int num) {
        numberOfNeighbors = num;
    }

    public void setNeighbors(ArrayList<String> neigh, int index) {
        neighbor = new String[index];
        // System.out.println(getSetName());

        for (int i = 0; i < index; i++) {
            neighbor[i] = neigh.get(i);
            // System.out.println("  " + neighbor[i]);
        }
    }

    public void setSetArea(int x, int y, int h, int w) {
        setArea[0] = x;
        setArea[1] = y;
        setArea[2] = h;
        setArea[3] = w;
    }

    public void setNumberOfTakes(int num) {
        numberOfTakes = num;

        setShotCounter(num);
    }

    public void setTake(int num, int x, int y, int h, int w) {
        Take obj = new Take();

        obj.takeNumber = num;
        obj.xVal = x;
        obj.yVal = y;
        obj.hVal = h;
        obj.wVal = w;

        take.add(obj);
    }

    public void setNumberOfParts(int numParts) {
        numberOfParts = numParts;
    }

    public void setPartNameLevel(int counter, String name, int level) {
        Part obj = new Part();

        obj.partName = name;
        obj.level = level;

        part.add(obj);
    } 

    public void setPartLine(String line) {
        part.get(0).line = line;
    }

    public void setPartArea(int x, int y, int h, int w) {
        part.get(0).xVal = x;
        part.get(0).yVal = y;
        part.get(0).hVal = h;
        part.get(0).wVal = w;
    }

    public void setPartTaken(String partName, boolean taken) {
        ArrayList<Part> partTaken = getParts();
        for (int i = 0; i < getParts().size(); i++) {
            if (partName.equals(partTaken.get(i).partName)) {
                partTaken.get(i).isTaken = taken;
            }
        }
    }

    public void setShotCounter(int counter){
        shotCounter = counter;
    }

    // Function to get list of all active sets
    public Boolean[] getIsActiveList() {
        Boolean[] list = new Boolean[neighbor.length];
        for (int i = 0; i < neighbor.length; i++) {
            list[i] = Board.getInstance().getSet(neighbor[i]).isActive;
        }
        return list;
    }

    // Getting available cards
    public ArrayList<String> availablePartsOffCard(int playerRank){
        ArrayList<String> available = new ArrayList<String>();
        ArrayList<Part> partsAvailable = getParts();

        System.out.println("TEST 1 " + available.isEmpty());

        for (int i = 0; i < partsAvailable.size(); i++) {
            if (partsAvailable.get(i).isTaken == false) {
                if (partsAvailable.get(i).level <= playerRank) {
                    available.add(partsAvailable.get(i).partName);
                }
            }
        }
        System.out.println("TEST 2 " + available.isEmpty());
        return available; //returns to onMove() in OnTurn.java
    }

    // Get certain part level 
    public int getPartLevel(int num) {
        ArrayList<Part> myParts = getParts();
        return myParts.get(num).level;
    }

    public int getPartLevel(String partName){
        ArrayList<Part> myPart = getParts();
        for (int i = 0; i < myPart.size(); i++){
            if (myPart.get(i).partName.equals(partName)){
                return myPart.get(i).level;
            }
        }
        return 0;
    }

    public ArrayList<Player> getPlayersInRoomOffCard() {
        return playersInRoomOffCard;
    }

    //setters
    public void setHasCard(boolean val) {
        hasCard = val;
    }

    public void setCardNum(int num) {
        cardNum = num;
    }

    public void setIsActive(boolean val) {
        isActive = val;
    }

    public void addPlayerToRoomOffCard(Player player) {
        playersInRoomOffCard.add(player);
    }

    public void removePlayersFormRoomOffCard(Player player) {
        playersInRoomOffCard.clear();
    }

    // Reset Set at end of day
    public void resetSetDay() {
        hasCard = true;
        isActive = true;

        ArrayList<Part> partTaken = getParts();
        for (int i = 0; i < getParts().size(); i++) {
            partTaken.get(i).isTaken = false;
        }

        playersInRoomOffCard.clear();
        
        shotCounter = numberOfTakes;
    }

    // Print helper funciton
    public void printHelper() {
        System.out.println("Set Name: " + getSetName());
        System.out.println("Neighors: ");

        String[] prac = getNeighbor();
        for (int i = 0; i < getNeighbor().length; i++) {
            System.out.println("  " + prac[i]);
        }

        System.out.println("Set Area: ");
        int[] area = getSetArea();
        for (int i = 0; i < getSetArea().length; i++) {
            System.out.println("  " + area[i]);
        }

        // System.out.println("Takes: ");
        // Take[] myTakes = getTake();
        // for (int i = 0; i < myTakes.length; i++) {
        //     System.out.println("  " + myTakes[i].takeNumber);
        //     System.out.println("  " + myTakes[i].xVal);
        //     System.out.println("  " + myTakes[i].yVal);
        //     System.out.println("  " + myTakes[i].hVal);
        //     System.out.println("  " + myTakes[i].wVal);
        // }

        // System.out.println("Parts: ");
        // Part[] myPart = getParts();
        // for (int i = 0; i < myPart.length; i++) {
        //     System.out.println("  " + myPart[i].partName);
        //     System.out.println("  " + part[i].level);
        //     System.out.println("  " + part[i].xVal);
        //     System.out.println("  " + part[i].yVal);
        //     System.out.println("  " + part[i].hVal);
        //     System.out.println("  " + part[i].wVal);
        //     System.out.println("  " + part[i].line);
        // }
    }

}