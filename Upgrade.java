/*
    Upgrade Class
    Purpose: Deals with all upgrading in game. Will check if user has enough to upgrade
             Will call from UserInterface to help display and get user input on upgrade
    Singleton = true
*/
public class Upgrade {
    private static Upgrade instance = null;

    // Create instance
    public static Upgrade getInstance() {
        if (instance == null) {
            instance = new Upgrade();
        }
        return instance;
    }

    // Creating class to hold Level Data
    class Level {
        int level, credit, dollar;

        public Level(int level, int dollar, int credit) {
            this.level = level;
            this.credit = credit;
            this.dollar = dollar;
        }
    }

    // Creating level objects
    Level levelTwo = new Level(2, 4, 5);
    Level levelThree = new Level(3, 10, 10);
    Level levelFour = new Level(4, 18, 15);
    Level levelFive = new Level(5, 28, 20);
    Level levelSix = new Level(6, 40, 25);

    // Check if player can upgrade to next level
    public boolean canUpgrade(int currentLevel, String location, int dollar, int credit) {
        if (location.equals("office")) {
            if (currentLevel < 6) {
                if (playerHasCredit(currentLevel, credit) || playerHasDollar(currentLevel, dollar)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Check if player has enough dollars for next level
    public boolean playerHasDollar(int level, int dollar) {
        int nextRank = level + 1;
        Level upgradeLevel = getLevel(nextRank);

        if (upgradeLevel.dollar > dollar) {
            return false;
        }

        return true;
    }

    // Check if player has enough credits for next level
    public boolean playerHasCredit(int level, int credit) {
        int nextRank = level + 1;
        Level upgradeLevel = getLevel(nextRank);

        if (upgradeLevel.credit > credit) {
            return false;
        }

        return true;
    }

    // Upgrade player using dollars
    public void upgradeDollar(Player player) {
        int currentLevel = player.getLevel();
        int upgrade = currentLevel + 1;

        Level next = getLevel(upgrade);

        player.setLevel(upgrade);
        player.setDollar(player.getDollar() - next.dollar);
    }

    // Upgrade player using credits
    public void upgradeCredit(Player player) {
        int currentLevel = player.getLevel();
        int upgrade = currentLevel + 1;

        Level next = getLevel(upgrade);

        player.setLevel(upgrade);
        player.setCredit(player.getCredit() - next.credit);
    }

    // Getting level values
    public Level getLevel(int level) {
        switch (level) {
            case 2:
                return levelTwo;
            case 3:
                return levelThree;
            case 4:
                return levelFour;
            case 5:
                return levelFive;
            default:
                return levelSix;
        }
    }
}