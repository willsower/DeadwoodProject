//Could be inside of OnTurn class

//Daria
// compiles
import java.util.*;
import java.lang.*;


public class room{
  /*
    private Scene currScene;
    private Role[] roles;  //might not need
    private ArrayList<player> currPlayer = new Array<Player>();
    private Room[] adjRooms;
    private int shots;
    private int currShots;
    private String name;
    private Board board;
    //end boolean
  */

    //constructor
    public room(/*Role[] roles, Scene scene, String name, Board board, int shots*/){
	/*
	this.roles = roles; //maybe
	this.currScene = scene;
	this.name = name;
	this.board = board;
	*/
	return;
    }


    //getters
    public int getScene(){ //not int
	//return this.currScene;
	return 0;
    }

    public int getRole(){  // not int
	//return this.role;
	return 0;
    }

    public int getPlayers(){ //array
	//return this.currPlayer;
	return 0;
    }

    public int getAdjRooms(){  //not int
	//return this.adjRooms;
	return 0;
    }

    public String getName(){
	//return this.name;
	return "";
    }


    //setters
    public void setAdjRooms(/*Room[] rooms*/){
	//this.adjRooms = rooms;
	return;
    }


    //methods
    public void addPlayer(/*Player player*/){
	return;
    }

    public void removePlayer(){
	return;
    }

    public void endRoom(){
	return;
    }

    public void resetRoom(){
	return;
    }

    public static void main(String[] args){  //no

    }

}
