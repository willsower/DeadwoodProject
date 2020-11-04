//Daria
//complies!!!!
import java.io.*;
import java.util.*;

public class Board{

    private ArrayList<String> roomList;
    private int day;
    private int numPlayers;
    private int numRooms = 12;
    private boolean endOfDay = false;
    private int currNumRooms = numRooms-2;
   // private static room[] rooms = new room[numRooms];

    //private player play; //I think this needs to be capital

   /* public board(int numRooms, int numPlayers, int day ){
        this.numRooms = numRooms;
        this.numPlayers = numPlayers;
	    return;
    }*/

    //public void createBoard(){ // same as above function
	//    return;
    //}

    public int getNumPlayers(){  //may also need current player
	    return this.numPlayers;
    }

    public void setAdjacentRooms(){  //may not need here
    
    }

    public int getCurrNumRooms(){
        return currNumRooms;
    }

    public void updateNumRooms(int numRooms){
	    currNumRooms--;
        if (currNumRooms == 1){
            endOfDay = true;
        }
    }
    
    public boolean isEndDay(){
	    return endOfDay;
    }
    
    public void resetBoard(){
        //this.endOfDay = false;
        currNumRooms = numRooms-2;
        endOfDay = false;
        //for(int i=0; i<numRooms-2; i++){
            //
        //}
	    //return;
    }

    public static void main(String[] args){ //need to replace with static

    }

}