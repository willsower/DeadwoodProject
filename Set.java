public class Set{

    private String setName;
    private int numberOfNeighbors;
    private String[] neighbor= new String[numberOfNeighbors];
    private int[] setArea = new int[4];
    private int numberOfTakes;
    private Take[] take = new Take[numberOfTakes];
    private int numberOfParts;
    private Part[] part = new Part[numberOfParts];

    class Take {
        int takeNumber;
        int[] takeArea;
    }

    class Part {
        String partName;
        int level;
        int[] partArea;
        String line;
    }

    //constructor
    public Set(String setName){
        this.setName = setName;
    }

    //getters
    public String getSetName(){
        return setName;
    }

    public int getNumberOfNeighbors(){
        return numberOfNeighbors;
    }

    public String[] getNeighbor(){
        return neighbor;
    }

    public int[] getSetArea(){
        return setArea;
    }

    public int getNumberOfTakes(){
        return numberOfTakes;
    }

    public Take[] getTake(){
        return take;
    }

    public int getNumberOfParts(){
        return numberOfParts;
    }

    public Part[] getParts(){
        return part;
    }

    //setters
    public void setNumberOfNeighbors(int num){
       numberOfNeighbors = num;
    }

    public void setNeighbors(String neighbor1, String neighbor2, String neighbor3 ){
        neighbor[0] = neighbor1;
        neighbor[1] = neighbor2;
        neighbor[2] = neighbor3;
    }

    public void setNeighbors(String neighbor1, String neighbor2, String neighbor3, String neighbor4){
        neighbor[0] = neighbor1;
        neighbor[1] = neighbor2;
        neighbor[2] = neighbor3;
        neighbor[3] = neighbor4;
    }

    public void setSetArea(int x, int y, int h, int w){
        setArea[0] = x;
        setArea[1] = y;
        setArea[2] = h;
        setArea[3] = w;
    }

    public void setNumberOfTakes(int num){
        numberOfTakes = num;
    }

    public void setTake(int num, int x, int y, int h, int w){
        take[num-1].takeNumber = num;
        take[num-1].takeArea[0] = x;
        take[num-1].takeArea[1] = y;
        take[num-1].takeArea[2] = h;
        take[num-1].takeArea[3] = w;
    }

    public void setNumberOfParts(int numParts) {
        numberOfParts = numParts;
    }

    public void setPart(int counter, String name, int level, int x, int y, int h, int w, String line) {
        part[counter].partName = name;
        part[counter].level = level;
        part[counter].partArea[0] = x;
        part[counter].partArea[1] = y;
        part[counter].partArea[2] = h;
        part[counter].partArea[3] = 2;
        part[counter].line = line;
    }

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
            System.out.println("  " + myTakes[i].takeArea[0]);
            System.out.println("  " + myTakes[i].takeArea[1]);
            System.out.println("  " + myTakes[i].takeArea[2]);
            System.out.println("  " + myTakes[i].takeArea[3]);
        }

        System.out.println("Parts: ");
        Part[] myPart = getParts();
        for (int i = 0; i < myPart.length; i++) {
            System.out.println("  " + myPart[i].partName);
            System.out.println("  " + part[i].level);
            System.out.println("  " + part[i].partArea[0]);
            System.out.println("  " + part[i].partArea[1]);
            System.out.println("  " + part[i].partArea[2]);
            System.out.println("  " + part[i].partArea[3]);
            System.out.println("  " + part[i].line);
        }
    }

}