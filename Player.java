//Daria

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
    private String roleLocation;
    private String roleName;

    // Constructor
    public Player(int playerPriority, int level, int dollar, int credit, String playerLocation) {
        this.playerPriority = playerPriority;
        this.level = level;
        this.dollar = dollar;
        this.credit = credit;
        this.playerLocation = playerLocation;

        // printPlayerInfo();
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

    public String getRoleName(){
        return roleName;
    }

    // Setter functions
    public void setPlayerPriority(int priority) {
        playerPriority = priority;
    }

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
    
    public void resetPlayers(){
        this.playerLocation = "Trailers";
        this.practiceChip = 0;
        this.onCardRole = false;
        this.OffCardRole = false;
        this.roleLevel = 0;
        this.rolePriority = 0;
        this.roleLocation = "";
        this.roleName = "";
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
    }
}
