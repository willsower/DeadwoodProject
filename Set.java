import java.util.ArrayList;

public class Set {

    private String setName;
    private int numberOfNeighbors;
    private String[] neighbor = new String[5];
    private int[] setArea = new int[4];
    private int numberOfTakes;
    private Take[] take = new Take[5];
    private int numberOfParts;
    private Part[] part = new Part[9];
    private boolean hasCard;
    private int cardNum;

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

        initPart(getParts());
        initTake(getTake());
    }

    // Initialize the parts
    public Part[] initPart(Part[] parts) {
        for (int i = 0; i < 9; i++) {
            parts[i] = new Part();
        }

        return parts;
    }

    // Initialize the takes
    public Take[] initTake(Take[] take) {
        for (int i = 0; i < 5; i++) {
            take[i] = new Take();
        }

        return take;
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

    public Take[] getTake() {
        return take;
    }

    public int getNumberOfParts() {
        return numberOfParts;
    }

    public Part[] getParts() {
        return part;
    }

    public boolean getHasCard() {
        return hasCard;
    }

    public int getCardNum() {
        return cardNum;
    }

    // setters
    public void setNumberOfNeighbors(int num) {
        numberOfNeighbors = num;
    }

    public void setNeighbors(String neigh, int index) {
        neighbor[index] = neigh;
    }

    public void setSetArea(int x, int y, int h, int w) {
        setArea[0] = x;
        setArea[1] = y;
        setArea[2] = h;
        setArea[3] = w;
    }

    public void setNumberOfTakes(int num) {
        numberOfTakes = num;
    }

    public void setTake(int num, int x, int y, int h, int w) {
        take[num - 1].takeNumber = num;
        take[num - 1].xVal = x;
        take[num - 1].yVal = y;
        take[num - 1].hVal = h;
        take[num - 1].wVal = w;

    }

    public void setNumberOfParts(int numParts) {
        numberOfParts = numParts;
    }

    public void setPartNameLevel(int counter, String name, int level) {
        part[counter].partName = name;
        part[counter].level = level;
    }

    public void setPartLine(int counter, String line) {
        part[counter].line = line;
    }

    public void setPartArea(int counter, int x, int y, int h, int w) {
        part[counter].xVal = x;
        part[counter].yVal = y;
        part[counter].hVal = h;
        part[counter].wVal = w;
    }

    public void setPartTaken(String partName, boolean taken) {
        Part[] partTaken = getParts();
        for (int i = 0; i < getParts().length; i++) {
            if (partName.equals(partTaken[i].partName)) {
                partTaken[i].isTaken = taken;
            }
        }
    }

    public ArrayList<String> availablePartsOffCard(){
        ArrayList<String> available = new ArrayList<String>();
        Part[] partsAvailable = getParts();

        for (int i = 0; i < partsAvailable.length; i++) {
            if (partsAvailable[i].isTaken == false) {
                available.add(partsAvailable[i].partName);
            }
        }
        return available;
    }



    public void setHasCard(boolean val) {
        hasCard = val;
    }

    public void setCardNum(int num) {
        cardNum = num;
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

        System.out.println("Takes: ");
        Take[] myTakes = getTake();
        for (int i = 0; i < myTakes.length; i++) {
            System.out.println("  " + myTakes[i].takeNumber);
            System.out.println("  " + myTakes[i].xVal);
            System.out.println("  " + myTakes[i].yVal);
            System.out.println("  " + myTakes[i].hVal);
            System.out.println("  " + myTakes[i].wVal);
        }

        System.out.println("Parts: ");
        Part[] myPart = getParts();
        for (int i = 0; i < myPart.length; i++) {
            System.out.println("  " + myPart[i].partName);
            System.out.println("  " + part[i].level);
            System.out.println("  " + part[i].xVal);
            System.out.println("  " + part[i].yVal);
            System.out.println("  " + part[i].hVal);
            System.out.println("  " + part[i].wVal);
            System.out.println("  " + part[i].line);
        }
    }

}