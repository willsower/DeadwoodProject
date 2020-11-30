/*
    Player Class
    Purpose: Store all player information, also has function that will reset
             player variables at the end of card or end of day
*/

import javafx.scene.image.Image;

public class Player {
    private int playerPriority;
    private int level;
    private int dollar;
    private int credit;
    private int practiceChip = 0;
    private boolean onCardRole = false;
    private boolean offCardRole = false;
    private String playerLocation;
    private int finalScore = 0;
    private int roleLevel = 0;
    private int rolePriority = 0;
    private int xCoord;
    private int yCoord;
    private String roleLocation;
    private String roleName; // part
    private String colorName;
    private Image playerImage;

    // Constructor
    public Player(int playerPriority, int level, int dollar, int credit, String playerLocation, String color, int x, int y) {
        this.playerPriority = playerPriority;
        this.level = level;
        this.dollar = dollar;
        this.credit = credit;
        this.playerLocation = playerLocation;
        setColorName(color);
        setPlayerImage();
        xCoord = x;
        yCoord = y;
    }

    // Get functions
    public int getPlayerPriority() {
        return playerPriority;
    }

    public int getLevel() {
        return level;
    }

    public int getDollar() {
        return dollar;
    }

    public int getCredit() {
        return credit;
    }

    public int getPracticeChip() {
        return practiceChip;
    }

    public boolean getOnCardRole() {
        return onCardRole;
    }

    public boolean getOffCardRole() {
        return offCardRole;
    }

    public String getPlayerLocation() {
        return playerLocation;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public int getRoleLevel() {
        return roleLevel;
    }

    public String getRoleLocation() {
        return roleLocation;
    }

    public int getRolePriority() {
        return rolePriority;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getColorName() { return colorName; }

    public Image getPlayerImage() { return playerImage; }

    public int getXCoord() { return xCoord; }

    public int getYCoord() { return yCoord; }

    // Setter functions

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDollar(int dollar) {
        this.dollar = dollar;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setPracticeChip(int practiceChip) {
        this.practiceChip = practiceChip;
    }

    public void setOnCardRole(boolean roleStatus) {
        onCardRole = roleStatus;
    }

    public void setOffCardRole(boolean roleStatus) {
        offCardRole = roleStatus;
    }

    public void setPlayerLocation(String location) {
        playerLocation = location;
    }

    public void setFinalScore(int score) {
        finalScore = score;
    }

    public void setRoleLevel(int level) {
        roleLevel = level;
    }

    public void setRoleLocation(String local) {
        roleLocation = local;
    }

    public void setRolePriority(int pri) {
        rolePriority = pri;
    }

    public void setRoleName(String name) {
        roleName = name;
    }

    // Set current player's image
    public void setPlayerImage() {
        String col = getColorName().toLowerCase();
        playerImage = new Image("./images/dice/" + col.charAt(0) + getLevel() + ".png");
    }

    // Set color name for player
    public void setColorName(String name) {
        switch (name) {
            case "b" -> colorName = "Blue";
            case "c" -> colorName = "Cadet Blue";
            case "g" -> colorName = "Green";
            case "o" -> colorName = "Orange";
            case "p" -> colorName = "Pink";
            case "r" -> colorName = "Red";
            case "v" -> colorName = "Violet";
            default -> colorName = "Yellow";
        }
    }

    // Function to reset players at the end of day or card
    // depending on the boolean value
    public void resetPlayers(boolean isNotAfterCard) {
        if (isNotAfterCard) { // true
//            playerLocation = "trailer";
            //playerLocation = "office"; /* TEST UPGRADE */
            //System.out.println("TEST 1");
        }
        practiceChip = 0;
        onCardRole = false;
        offCardRole = false;
        roleLevel = 0;
        rolePriority = 0;
        roleLocation = "";
        roleName = "";
    }

    // Print player information
    public void printPlayerInfo() {
        System.out.println("Player Priority: " + getPlayerPriority());
        System.out.println("  Player Level: " + getLevel());
        System.out.println("  Player Credits: " + getCredit() + " Player Dollar: " + getDollar());
        System.out.println("  Player Location: " + getPlayerLocation());
        System.out.println("  Player Has On Card Role? " + getOnCardRole());
        System.out.println("  Player Has Off Card Role? " + getOffCardRole());
        System.out.println("  Player Practice Chip: " + getPracticeChip());
        System.out.println("  Player Location: " + getPlayerLocation());
    }
}
