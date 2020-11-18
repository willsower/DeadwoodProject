/*
    UserInterface class
    Purpose: Display user prompts
    Singleton = true
*/

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class UserInterfaceDisplay implements Initializable {

    private static UserInterfaceDisplay instance = null;

    // Create instance
    public static UserInterfaceDisplay getInstance() {
        if (instance == null) {
            instance = new UserInterfaceDisplay();
        }
        return instance;
    }

    @FXML private ImageView boardImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // Display winners of game
    public void displayWinner(Integer[] finals) {
        System.out.println("The winner(s) are: ");
        for (int i = 0; i < finals.length; i++) {
            if (finals[i] != 0) {
                System.out.println("  Player " + finals[i]);
            }
        }
    }

    public void playerUpgrade(Player player) {
        System.out.println("\nJust upgraded!");
        System.out.println("  Player rank: " + player.getLevel());
        System.out.println("  Player dollars: " + player.getDollar());
        System.out.println("  Player credits: " + player.getCredit());
    }

    // Display Casting Office Level upgrades
    public void displayCastingOffice(Player player) {
        System.out.println("Welcome to the Casting Office!");
        System.out.println("  Level 2 | Dollar: " + Upgrade.getInstance().levelTwo.dollar + " Credit: "
                + Upgrade.getInstance().levelTwo.credit);
        System.out.println("  Level 3 | Dollar: " + Upgrade.getInstance().levelThree.dollar + " Credit: "
                + Upgrade.getInstance().levelThree.credit);
        System.out.println("  Level 4 | Dollar: " + Upgrade.getInstance().levelFour.dollar + " Credit: "
                + Upgrade.getInstance().levelFour.credit);
        System.out.println("  Level 5 | Dollar: " + Upgrade.getInstance().levelFive.dollar + " Credit: "
                + Upgrade.getInstance().levelFive.credit);
        System.out.println("  Level 6 | Dollar: " + Upgrade.getInstance().levelSix.dollar + " Credit: "
                + Upgrade.getInstance().levelSix.credit);

        displayPlayerInfo(player);
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