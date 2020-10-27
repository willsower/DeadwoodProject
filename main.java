import org.w3c.dom.Document;
import java.util.Scanner;

//together!
public class main {
    public static void main(String[] args) {
        // Document doc = null;
        // ParseXML parsing = new ParseXML();
        try {
            // doc = parsing.getDocFromFile("board.xml");
            // parsing.readBoardData(doc);

            //need some sort of while loop or loop till game ends
            Scanner myOb = new Scanner(System.in);
            //setup board here
            int numPlayers = getNumPlayers(myOb);
            // turnManage(numPlayers);
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }

    //Prompts user how many players will be playing
    //Will throw player in loop until they enter 2-8 players
    public static int getNumPlayers(Scanner myOb) {
        String val;
        int numberPlayers = 0;

        while (!(numberPlayers >= 2 && numberPlayers <= 8)) {
            System.out.println("How many players? (2 - 8) ");
            val = myOb.nextLine();
            try {
                numberPlayers = Integer.parseInt(val);
            } catch (NumberFormatException e) {}
        }

        return numberPlayers;
    }

    public void turnManage(int numPlayers) {

    }

    public void dayManage() {
    
    }
}