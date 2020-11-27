/*
    Upgrade Class
    Purpose: Deals with all upgrading in game. Will check if user has enough to upgrade
             Will call from UserInterface to help display and get user input on upgrade
    Singleton = true
*/

import java.util.ArrayList;

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
    // Return 0 if can't upgrade
    // Return 1 if can upgrade with both dollar and credit
    // Return 2 if can upgrade with dollar
    // Return 3 if can upgrade with credit
    public int canUpgrade(int level, String location, int dollar, int credit) {
        if (location.equals("office")) {
            if (level < 6) {
                if (playerHasCredit(level, credit) && playerHasDollar(level, dollar)) {
                    return 1;
                } else if (playerHasCredit(level, credit)) {
                    return 3;
                } else if (playerHasDollar(level, dollar)) {
                    return 2;
                }
            }
        }
        return 0;
    }

    // Check if player has enough dollars for next level
    public boolean playerHasDollar(int level, int dollar) {
        Level upgradeLevel = getLevel(level);

        if (upgradeLevel.dollar >= dollar) {
            return false;
        }
        //SystemManager.getInstance().makePayButtonsVisible(true, false);
        return true;
    }

    // Check if player has enough credits for next level
    public boolean playerHasCredit(int level, int credit) {
        Level upgradeLevel = getLevel(level);

        if (upgradeLevel.credit >= credit) {
            return false;
        }
        //SystemManager.getInstance().makePayButtonsVisible(false, true);
        return true;
    }

    // Upgrade player using dollars
    public void upgradeDollar(Player player, int level) {
        int currentLevel = player.getLevel();

        Level next = getLevel(level);

        player.setLevel(level);
        player.setDollar(player.getDollar() - next.dollar);
    }

    // Upgrade player using credits
    public void upgradeCredit(Player player, int level) {
        int currentLevel = player.getLevel();

        Level next = getLevel(level);

        player.setLevel(level);
        player.setCredit(player.getCredit() - next.credit);
    }

    // Function to get how many levels can upgrade
    public ArrayList<Integer> levelsCanUpgrade(Player player) {
        int currentLevel = player.getLevel();
        ArrayList<Integer> canUpgrade = new ArrayList<Integer>();
        int credit = player.getCredit();
        int dollar = player.getDollar();

        for (int i = currentLevel + 1; i <= 6; i++) {
            if (getLevel(i).credit <= credit || getLevel(i).dollar <= dollar) {
                canUpgrade.add(i);
                //SystemManager.getInstance().addUpgradeOptions(i);
            }
        }

        return canUpgrade;
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