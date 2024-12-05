import javax.swing.*;
import java.awt.*;

/**
 * The src.src.GameButton is a customised JButton which adds fields and methods allowing for easier handling of the game's Logic.
 *
 * @author Niranjan
 * @version 1.4
 */
public class GameButton extends JButton
{
    private int location;       // The location of the button
    private int occupation;     // the occupation of the button (where 0 is empty, 1 is black, 2 is white, and 3 is a legal move)
    private int x;              // The x coordinate of the button
    private int y;              // the y coordinate of the button
    private LegalMove legals;   //the set of legal moves for that button (so that when it is pressed it can execute the changes to the board necessary)
    
    /**
     * Constructor method for a src.src.GameButton
     */
    public GameButton(int locaction, int ex, int why) {
        location = locaction;
        x = ex;
        y = why;
    }
    
    // Methods handling the field values in the src.src.GameButton
    /**
     * Sets the occupation field to whatever the button's Icon represents
     */
    public void setOccupation(int value) 
    {
        occupation = value;
    }
    /**
     * Accessor method for the Occupation Field
     */
    public int getOccupation()
    {
        return occupation;
    }
    /**
     * Accessor method for the location Field
     */
    public int getLocationField()
    {
        return location;
    }
    /**
     * Accessor method for the x Field
     */
    public int getx()
    {
        return x;
    }
    /**
     * Accessor method for the y Field
     */
    public int gety()
    {
        return y;
    }
    /**
     * Accessor method for the LegalMoves associated with this button
     */
    public LegalMove getLegalMoves()
    {
        return legals;
    }
    /**
     * Setter (mutator) method for assignign the set of legal moves associated with the button.
     */
    public void setLegalMoves(LegalMove legalmoves)
    {
        legals = legalmoves;
    }
    /**
     * Sets the button image and assigns the corresponding occupation
     */
    public void setButtonImage(Image colour, Reversi revers)
    {
        setIcon(new StretchIcon(colour));
        if (colour == revers.black) {
            setOccupation(1);
        }
        else if (colour == revers.white) {
            setOccupation(2);
        }
        else if (colour == revers.valid) {
            setOccupation(3);
        }
        else if (colour == revers.empty) {
            setOccupation(0);
        }
    }
    
    /**
     * Returns the next button which is placed up from this one
     */
    public GameButton getNextUp(Reversi revers)
    {
        GameButton butt = null;
        if (revers.toInt(x, y-1) < revers.getSize()*revers.getSize() && revers.toInt(x, y-1) >= 0) {
            if (!(y==1)) {
                butt = revers.getButton(revers.toInt(x, y-1));
            }
        }
        return butt;
    }
    /**
     * Returns the next button which is placed down from this one
     */
    public GameButton getNextDown(Reversi revers)
    {
        GameButton butt = null;
        if (revers.toInt(x, y+1) < revers.getSize()*revers.getSize() && revers.toInt(x, y+1) >= 0) {
            if (!(y==revers.getSize())) {
                butt = revers.getButton(revers.toInt(x, y+1));
            }
        }
        return butt;
    }
    /**
     * Returns the next button which is placed left from this one
     */
    public GameButton getNextLeft(Reversi revers)
    {
        GameButton butt = null;
        if (revers.toInt(x-1, y) < revers.getSize()*revers.getSize() && revers.toInt(x-1, y) >= 0) {
            if (!(x==1)) {
                butt = revers.getButton(revers.toInt(x-1, y));
            }
        }
        return butt;
    }
    /**
     * Returns the next button which is placed right from this one
     */
    public GameButton getNextRight(Reversi revers)
    {
        GameButton butt = null;
        if (revers.toInt(x+1, y) < revers.getSize()*revers.getSize() && revers.toInt(x+1, y) >= 0) {
            if (!(x==revers.getSize())) {
                butt = revers.getButton(revers.toInt(x+1, y));
            }
        }
        return butt;
    }
    /**
     * Returns the next button which is placed up left from this one
     */
    public GameButton getNextUpLeft(Reversi revers)
    {
        GameButton butt = null;
        if (revers.toInt(x-1, y-1) < revers.getSize()*revers.getSize() && revers.toInt(x-1, y-1) >= 0) {
            if ((!(y==1)) && (!(x==1))) {
                butt = revers.getButton(revers.toInt(x-1, y-1));
            }
        }
        return butt;
    }
    /**
     * Returns the next button which is placed up right from this one
     */
    public GameButton getNextUpRight(Reversi revers)
    {
        GameButton butt = null;
        if (revers.toInt(x+1, y-1) < revers.getSize()*revers.getSize() && revers.toInt(x+1, y-1) >= 0) {
            if ((!(y==1)) && (!(x==revers.getSize()))) {
                butt = revers.getButton(revers.toInt(x+1, y-1));
            }
        }
        return butt;
    }
    /**
     * Returns the next button which is placed down left from this one
     */
    public GameButton getNextDownLeft(Reversi revers)
    {
        GameButton butt = null;
        if (revers.toInt(x-1, y+1) < revers.getSize()*revers.getSize() && revers.toInt(x-1, y+1) >= 0) {
            if ((!(y==revers.getSize())) && (!(x==1))) {
                butt = revers.getButton(revers.toInt(x-1, y+1));
            }
        }
        return butt;
    }
    /**
     * Returns the next button which is placed down right from this one
     */
    public GameButton getNextDownRight(Reversi revers)
    {
        GameButton butt = null;
        if (revers.toInt(x+1, y+1) < revers.getSize()*revers.getSize() && revers.toInt(x+1, y+1) >= 0) {
            if ((!(y==revers.getSize())) && (!(x==revers.getSize()))) {
                butt = revers.getButton(revers.toInt(x+1, y+1));
            }
        }
        return butt;
    }
}
