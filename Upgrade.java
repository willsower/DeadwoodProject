//Singleton
//Tai
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

    // Upgrade player, it will check to see if player can upgrade then will prompt
    // user
    // on what to upgrade with.
    public void upgradePlayer(int currentLevel, String location, int dollar, int credit) {
        if (canUpgrade(currentLevel, location, dollar, credit)) {
            if (playerHasCredit(currentLevel, credit) && playerHasDollar(currentLevel, dollar)) {
                System.out.println("Upgrade with credit or dollar? (C/D)");
            } else if (playerHasDollar(currentLevel, dollar)) {
                System.out.println("Upgrade with dollar? (Y/N)");
            } else if (playerHasCredit(currentLevel, credit)) {
                System.out.println("Upgrade with credit? (Y/N)");
            }
        }
    }

    // Check if player can upgrade to next level
    public boolean canUpgrade(int currentLevel, String location, int dollar, int credit) {
        if (location.equals("Casting Office")) {
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