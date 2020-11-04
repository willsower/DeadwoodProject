//Daria

/*
MAY NOT NEED??
*/

//complies
import java.io.*;
import java.util.*;

public class Stage {
    
    String setName;
    int totSetCount;
    int currSetCount;

    public Stage(String setName, int totSetCount, int CurrSetCount){
        this.setName = setName;
        this.totSetCount = totSetCount;
        this.currSetCount = currSetCount;
        return;
    }

    //getters
    public int getTotSetCount(){
        //Comment
	    return this.totSetCount;
    }

    public int getCurrSetCount(){
	    return this.currSetCount;
    }

    //setter
    public void setTotSetCount(int totSetCount){
        this.totSetCount = totSetCount;
	    
    }

    public void setCurrSetCount(int currSetCount){
        this.currSetCount = currSetCount;
    }

    public void rmFinalScene(){ //might be in board or the equivalent of my class scene

    }

    public static void main(String[] args){ //no

    }

}