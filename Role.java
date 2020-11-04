//Daria
//Have it inside of a clas? Or sepearte -> Look at Upgrade.java and Level class for example
//compiles

import java.util.*;
import java.io.*;

public class Role{

    String name; 
    int level; //rank?
    String line;
    boolean onCard; //if someone is already on the card
    boolean mainPlayer;
    String role;

    public Role(int level, boolean mainPlayer, String name, String line ){
	    this.level = level;
        this.mainPlayer = mainPlayer; 
        this.name = name;
        this.line = line;
        this.onCard = false;
        return;
    }

    public String getName(){
	    return this.name;
    }

    public int getLevel(){
        return this.level;
    }

    public String getRole(){
        return role;
    }

    public String getLine(){
        return this.line;
    }

    public boolean isMainPlayer(){
        return this.mainPlayer;
    }

    public boolean isOnCard(){
        return this.onCard;
    }

    
    public void setOnCard(){
        this.onCard = true;
        //comment
    }

    public void leaveRole(){
        this.onCard = false;
    }
    
    
    public static void main(String[] args){ //dont use

    }

}