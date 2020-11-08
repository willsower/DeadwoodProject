

public class Board{

    private Board instance = null;




    //create instance
    public Board getInstance(){
        if (instance == null){
            instance = new Board();
        }
        return instance;
    }
    
    




}