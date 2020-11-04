//Could be inside of OnTurn class

//Daria
// compiles
import java.util.*;
import java.lang.*;


public class Room{
  
    private Card currScene; //used to be scene
    private Role[] roles;  //might not need
    private ArrayList<Player> currPlayer = new ArrayList<Player>();
    private Room[] adjRoom;
    private int shots;
    private int currShots;
    private String name; //name of scene
    private Board board;
    private boolean haveEnd = false;
  

    //constructor
    public Room(Role[] roles, Card scene, String name, Board board, int shots){
	    /*
	    roles = this.roles; //maybe
	    scene = this.currScene ;
	    name = this.name;
	    boards = this.board;
        shots = this.shots;
        */

        this.roles = roles; //maybe
	    this.currScene = scene ;
	    //this.name = name;
	    this.board = board;
        this.shots = shots;

	return;
    }


    //getters
    public Card getScene(){  //I think it should be card? maybe? 
	    return currScene;
    }

    public Role[] getRole(){  
	    return roles;
    }

    public ArrayList<Player> getPlayers(){ 
	    return this.currPlayer;
    }

    public Room[] getAdjRooms(){  
	    return adjRooms;
    }

    public String getName(){ //title
	    return this.name;
    }


    //setters
    public void setAdjRooms(Room[] rooms){
	    this.adjRooms = rooms;
    }


    //methods
    public void addPlayer(Player player){
	    this.currPlayer.add(player);
        return; //might not need since void
    }

    public void removePlayers(){
	    this.currPlayer.clear(); //clear all?
    }

    public void endRoom(){
        boolean bonus = false;

        //is actor in room
	    haveEnd = true;
        role[] temp = this.currScene.getRole();

        for(int i = 0; i<temp.lenght; i++){ //set bonus
            if (temp[i].isOnCard()){ //role is taken
                bonus = true;
            }
        }


        /* Is bonas going to be given out in this class? */

        /* Reset Rehearsal */

        //this.removePlayers;

        //this.haveEnd = true; //false?
        //this.board.updateNumRooms();
        //this.currScene.endScene;

        return;
    }

    public void resetRoom(){
        this.removePlayers();
        this.currentShots = this.shots;
        this.haveEnd = false;
        this.currentScene = scene;
        for (role[] role : this.roles){
                role.leaveRole();
        }
	    return;
    }

    public static void main(String[] args){  //no

    }

}