/*
    Set Class
    Purpose: Will hold all data from board.xml. Will use the getters/setters
             to grab and give the information. Will keep a list of players
             on the offCard roles, and has functions to reset all the variables
             if it's the next day
*/

import java.util.ArrayList;
import javafx.scene.control.Button;

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
    private ArrayList<Button> out = new ArrayList<>();
    private boolean isCardFlipped = false;

    // Shot counter values
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

    // Part values
    class Part {
        String partName;
        int level;
        int xVal;
        int yVal;
        int wVal;
        int hVal;
        String line;
        boolean isTaken;
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

    // getters functions
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

    public int getShotCounter() {
        return shotCounter;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public boolean getIsCardFlipped() { return isCardFlipped; }

    public ArrayList<Button> getOut() {
//        for (int i = 0; i < out.size(); i++) {
//            System.out.println(out.get(i));
//        }
        return out;
    }

    // Function to get list of all active sets
    public Boolean[] getIsActiveList() {
        Boolean[] list = new Boolean[neighbor.length];
        for (int i = 0; i < neighbor.length; i++) {
            list[i] = Board.getInstance().getSet(neighbor[i]).isActive;
        }
        return list;
    }

    // Get certain part level with number
    public int getPartLevel(int num) {
        ArrayList<Part> myParts = getParts();
        return myParts.get(num).level;
    }

    // Get part level given the part name
    public int getPartLevel(String partName) {
        ArrayList<Part> myPart = getParts();
        for (int i = 0; i < myPart.size(); i++) {
            if (myPart.get(i).partName.equals(partName)) {
                return myPart.get(i).level;
            }
        }
        return 0;
    }

    // Return the playersInRoom offCard role list
    public ArrayList<Player> getPlayersInRoomOffCard() {
        return playersInRoomOffCard;
    }

    // setters
    public void setNumberOfNeighbors(int num) {
        numberOfNeighbors = num;
    }

    public void setNeighbors(ArrayList<String> neigh, int index) {
        neighbor = new String[index];

        for (int i = 0; i < index; i++) {
            neighbor[i] = neigh.get(i);
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

    // Set part taken to whatever value is
    public void setPartTaken(String partName, boolean taken) {
        ArrayList<Part> partTaken = getParts();
        for (int i = 0; i < getParts().size(); i++) {
            System.out.println("PART NAME: " + partName);
            if (partName.equals(partTaken.get(i).partName)) {
                partTaken.get(i).isTaken = taken;
            }
        }
    }

    public void addToButtonList(Button name) {
        out.add(name);
    }

    public void setShotCounter(int counter) {
        shotCounter = counter;
    }

    public void setHasCard(boolean val) {
        hasCard = val;
    }

    public void setCardNum(int num) {
        cardNum = num;
    }

    public void setIsActive(boolean val) {
        isActive = val;
    }

    public void setIsCardFlipped(boolean val) { isCardFlipped = val; }

    // Getting available cards
    public ArrayList<String> availablePartsOffCard(int playerRank) {
        ArrayList<String> available = new ArrayList<String>();
        ArrayList<Part> partsAvailable = getParts();
        System.out.println(setName);
        for (int i = 0; i < partsAvailable.size(); i++) {
            if (partsAvailable.get(i).isTaken == false) {
                System.out.println("  " + partsAvailable.get(i).partName);
                if (partsAvailable.get(i).level <= playerRank) {
                    available.add(partsAvailable.get(i).partName);
                }
            }
        }
        return available; // returns to onMove() in OnTurn.java
    }

    // Add players to arrayList of offCard players
    public void addPlayerToRoomOffCard(Player player) {
        playersInRoomOffCard.add(player);
    }

    // Remove players from arrayList of offCard players
    public void removePlayersFormRoomOffCard(Player player) {
        playersInRoomOffCard.clear();
    }

    // Reset Set at end of day
    public void resetSetDay() {
        hasCard = true;
        isActive = true;

        for (int i = 0; i < part.size(); i++) {
            part.get(i).isTaken = false;
        }
        playersInRoomOffCard.clear(); /*** NEED TO CALL THIS AFTER THE MOVE PANE IN SYSTEMMANAGER */
        shotCounter = numberOfTakes;
        isCardFlipped = false;
    }

    // Reset set at end of card
    public void resetSetAtCard() {
        for (int i = 0; i < part.size(); i++) {
            part.get(i).isTaken = false;
        }
        //playersInRoomOffCard.clear();/*** NEED TO CALL THIS AFTER THE MOVE PANE IN SYSTEMMANAGER */
        shotCounter = numberOfTakes;
        isCardFlipped = false;
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
        // System.out.println(" " + myTakes[i].takeNumber);
        // System.out.println(" " + myTakes[i].xVal);
        // System.out.println(" " + myTakes[i].yVal);
        // System.out.println(" " + myTakes[i].hVal);
        // System.out.println(" " + myTakes[i].wVal);
        // }

        // System.out.println("Parts: ");
        // Part[] myPart = getParts();
        // for (int i = 0; i < myPart.length; i++) {
        // System.out.println(" " + myPart[i].partName);
        // System.out.println(" " + part[i].level);
        // System.out.println(" " + part[i].xVal);
        // System.out.println(" " + part[i].yVal);
        // System.out.println(" " + part[i].hVal);
        // System.out.println(" " + part[i].wVal);
        // System.out.println(" " + part[i].line);
        // }
    }

}