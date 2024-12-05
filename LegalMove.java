import java.util.*;
import java.awt.*;

/**
 * Write a description of class src.src.LegalMove here.
 *
 * @author Niranjan
 * @version 190000000000
 */
public class LegalMove
{
    private int x;              // x location of the button containing the legal move (for convenience)
    private int y;              // x location of the button containing the legal move (for convenience)
    private ArrayList tochange; // ArrayList containing all the enemy buttons to be changed to friendlies
    Reversi revers;             // Instance of the reversi board so this class can access its methods
    
    /**
     * Constructor for objects of class src.src.LegalMove
     */
    public LegalMove(int xx, int yy, ArrayList legals, Reversi reversi)
    {
        x = xx;
        y = yy;
        tochange = legals;
        revers = reversi;
    }
    
    /**
     * Adds an ArrayList of buttons to be changed to the src.src.LegalMove's internal Arraylist
     * This is so that multiple buttons can be changed at different directions
     */
    public void addToArrayList(ArrayList incoming)
    {
        tochange.addAll(incoming);
    }
    
    /**
     * Accessor method to get the x location of the button containing the src.src.LegalMove (for convenience)
     */
    public int getx()
    {
        return x;
    }
    
    /**
     * Accessor method to get the y location of the button containing the src.src.LegalMove (for convenience)
     */
    public int gety()
    {
        return y;
    }
    
    /**
     * Method which "executes" the src.src.LegalMove. This sets all the buttons to the friendly button.
     */
    public void execute()
    {
        Image toset = null;
        if (((GameButton)tochange.get(0)).getOccupation() == 1) {
            toset = revers.white;
        }
        else if (((GameButton)tochange.get(0)).getOccupation() == 2) {
            toset = revers.black;
        }
        
        for (int i = 0; i < tochange.size(); i++) {
            GameButton current = ((GameButton)tochange.get(i));
            current.setButtonImage(toset, revers);
        }
    }
}
