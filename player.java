//Daria

public class player {
    private int playerPriority;
    private int level;
    private int dollar;
    private int credit;
    private int practiceChip = 0;
    private boolean onCardRole = false;
    private boolean offCardRole = false;
    private String playerLocation;
    private int finalScore = 0;

    //Constructor
    public player(int playerPriority, int level, int dollar, int credit, String playerLocation) {
        playerPriority = this.playerPriority;
        level = this.level;
        dollar = this.dollar;
        credit = this.credit;
        playerLocation = this.playerLocation;
    }

    //Get functions
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

    //Setter functions 
    public void setPlayerPriority(int priority) {
        playerPriority = priority;
    }

    public void setLevel(int level) {
        level = this.level;
    }

    public void setDollar(int dollar) {
        dollar = this.dollar;
    }

    public void setCredit(int credit) {
        credit = this.credit;
    }

    public void setPracticeChip(int practiceChip) {
        practiceChip = this.practiceChip;
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
}