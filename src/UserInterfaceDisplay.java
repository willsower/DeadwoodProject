/*
    UserInterface class
    Purpose: Display user prompts
    Singleton = true
*/


public class UserInterfaceDisplay /*implements Initializable*/ {

    private static UserInterfaceDisplay instance = null;
    // Create instance
    public static UserInterfaceDisplay getInstance() {
        if (instance == null) {
            instance = new UserInterfaceDisplay();
        }
        return instance;
    }



    // Display player info
    public void displayPlayerInfo(Player player) {
        System.out.println("  Player " + player.getPlayerPriority());
        System.out.println("    Player level: " + player.getLevel());
        System.out.println("    Player dollars: " + player.getDollar());
        System.out.println("    Player credits: " + player.getCredit());
        System.out.println("    Player practice chips " + player.getPracticeChip());
    }
}
