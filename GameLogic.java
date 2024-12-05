import java.util.*;

/**
 * The src.src.GameLogic class is responsible for managing the game's logic such as checking for legal moves,
 * 
 * @author Niranjan
 * @version 9.9999999x10^9999999
 */
public class GameLogic
{
    int size;               // Int for the size of the board
    int enemy;              // int to determine which colour is the "enemy" colour
    int notenemy;           // int to determine which colour is not the enemy. (your colour)
    int atleastone = 0;     // int to determine whether atleast one button is a legal move
    Reversi revers;         // Passing along an instance of the reversi board so the src.src.GameLogic can use it's methods.
    /**
     * Constructor for objects of class src.src.GameLogic
     */
    public GameLogic(Reversi reversi)
    {
        revers = reversi;
        size = revers.getSize();
        sortEnemy();
    }
    
    /**
     * Method to set which colour is the enemy for that turn, before determining legal moves
     */
    public void sortEnemy() 
    {
        if (revers.getWhiteTurn() == false) {
            enemy = 2;
            notenemy = 1;
        }
        else if (revers.getWhiteTurn() == true) {
            enemy = 1;
            notenemy = 2;
        }
    }
    
    /**
     * Method which marks all potential legal moves for the person who's turn it is.
     */
    public int markLegalMoves()
    {
        for (int i = 0; i < size*size; i++) {
            if (((GameButton)(revers.getButton(i))).getOccupation() == 0) {
                GameButton butt = revers.getButton(i);
                checkSurroundings(butt);
            }  
        }
        return atleastone;
    }
    
    /**
     * Method which checks the 8 surrounding buttons of the button in question to check if the button in question is a legal move.
     */
    public void checkSurroundings(GameButton butt) 
    {
        LegalMove legal = new LegalMove(butt.getx(), butt.gety(), new ArrayList<>(), revers);
        
        checkUp(butt, new ArrayList<>(), legal);
        checkDown(butt, new ArrayList<>(), legal);
        checkLeft(butt, new ArrayList<>(), legal);
        checkRight(butt, new ArrayList<>(), legal);
        checkUpLeft(butt, new ArrayList<>(), legal);
        checkUpRight(butt, new ArrayList<>(), legal);
        checkDownLeft(butt, new ArrayList<>(), legal);
        checkDownRight(butt, new ArrayList<>(), legal);
    }
    
    /**
     * Checks in a straight line up until it meets either the edge of the table, a blank, a legalmove (so a blank), or an enemy button.
     * If there has been at least one enemy button in between itself and the friendly one, with no gaps, it marks the button in question
     * as a legal move.
     */
    public void checkUp(GameButton butt, ArrayList list, LegalMove legal)
    {
        GameButton next = butt;
        try {
            next = butt.getNextUp(revers);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            
        }
        try {
            if (next.getOccupation() == enemy) {
                list.add(next);
                checkUp(next, list, legal);
            }
            else if (next.getOccupation() == 0) {
            
            }
            else if (next.getOccupation() == notenemy) {
                if (list.size() > 0) {
                    legal.addToArrayList(list);
                    revers.getButton(revers.toInt(legal.getx(), legal.gety())).setLegalMoves(legal);
                    revers.setButtonImage(revers.toInt(legal.getx(), legal.gety()), revers.valid);
                    atleastone = 1;
                }
            }
            else if (next.getOccupation() == 3) {
                 
            }
        }
        catch (java.lang.NullPointerException xd) {}
    }
    
    /**
     * Checks in a straight line down until it meets either the edge of the table, a blank, a legalmove (so a blank), or an enemy button.
     * If there has been at least one enemy button in between itself and the friendly one, with no gaps, it marks the button in question
     * as a legal move.
     */
    public void checkDown(GameButton butt, ArrayList list, LegalMove legal)
    {
        GameButton next = butt;
        try {
            next = butt.getNextDown(revers);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            
        }
        try {
            if (next.getOccupation() == enemy) {
                list.add(next);
                checkDown(next, list, legal);
            }
            else if (next.getOccupation() == 0) {
                
            }
            else if (next.getOccupation() == notenemy) {
                if (list.size() > 0) {
                    legal.addToArrayList(list);
                    revers.getButton(revers.toInt(legal.getx(), legal.gety())).setLegalMoves(legal);
                    revers.setButtonImage(revers.toInt(legal.getx(), legal.gety()), revers.valid);
                    atleastone = 1;
                }
            }
            else if (next.getOccupation() == 3) {
                 
            }
        }
        catch (java.lang.NullPointerException xd){}
    }
    
    /**
     * Checks in a straight line Left until it meets either the edge of the table, a blank, a legalmove (so a blank), or an enemy button.
     * If there has been at least one enemy button in between itself and the friendly one, with no gaps, it marks the button in question
     * as a legal move.
     */
    public void checkLeft(GameButton butt, ArrayList list, LegalMove legal)
    {
        GameButton next = butt;
        try {
            next = butt.getNextLeft(revers);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            
        }
        try {
            if (next.getOccupation() == enemy) {
                list.add(next);
                checkLeft(next, list, legal);
            }
            else if (next.getOccupation() == 0) {
                
            }
            else if (next.getOccupation() == notenemy) {
                if (list.size() > 0) {
                    legal.addToArrayList(list);
                    revers.getButton(revers.toInt(legal.getx(), legal.gety())).setLegalMoves(legal);
                    revers.setButtonImage(revers.toInt(legal.getx(), legal.gety()), revers.valid);
                    atleastone = 1;
                }
            }
            else if (next.getOccupation() == 3) {
                
            }
        }
        catch (java.lang.NullPointerException xd) {}
    }
    
    /**
     * Checks in a straight line right until it meets either the edge of the table, a blank, a legalmove (so a blank), or an enemy button.
     * If there has been at least one enemy button in between itself and the friendly one, with no gaps, it marks the button in question
     * as a legal move.
     */
    public void checkRight(GameButton butt, ArrayList list, LegalMove legal)
    {
        GameButton next = butt;
        try {
            next = butt.getNextRight(revers);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            
        }
        try {
            if (next.getOccupation() == enemy) {
                list.add(next);
                checkRight(next, list, legal);
            }
            else if (next.getOccupation() == 0) {
                
            }
            else if (next.getOccupation() == notenemy) {
                if (list.size() > 0) {
                    legal.addToArrayList(list);
                    revers.getButton(revers.toInt(legal.getx(), legal.gety())).setLegalMoves(legal);
                    revers.setButtonImage(revers.toInt(legal.getx(), legal.gety()), revers.valid);
                    atleastone = 1;
                }
            }
            else if (next.getOccupation() == 3) {
                 
            }
        }
        catch (java.lang.NullPointerException xd) {}
    }
    
    /**
     * Checks in a straight line diagonally up left until it meets either the edge of the table, a blank, a legalmove (so a blank), or an enemy button.
     * If there has been at least one enemy button in between itself and the friendly one, with no gaps, it marks the button in question
     * as a legal move.
     */
    public void checkUpLeft(GameButton butt, ArrayList list, LegalMove legal)
    {
        GameButton next = butt;
        try {
            next = butt.getNextUpLeft(revers);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            
        }
        try {
            if (next.getOccupation() == enemy) {
                list.add(next);
                checkUpLeft(next, list, legal);
            }
            else if (next.getOccupation() == 0) {
                
            }
            else if (next.getOccupation() == notenemy) {
                if (list.size() > 0) {
                    legal.addToArrayList(list);
                    revers.getButton(revers.toInt(legal.getx(), legal.gety())).setLegalMoves(legal);
                    revers.setButtonImage(revers.toInt(legal.getx(), legal.gety()), revers.valid);
                    atleastone = 1;
                }
            }
            else if (next.getOccupation() == 3) {
                     
            }
        }
        catch (java.lang.NullPointerException xd) {}
    }
    
    /**
     * Checks in a straight line diagonally up right until it meets either the edge of the table, a blank, a legalmove (so a blank), or an enemy button.
     * If there has been at least one enemy button in between itself and the friendly one, with no gaps, it marks the button in question
     * as a legal move.
     */
    public void checkUpRight(GameButton butt, ArrayList list, LegalMove legal)
    {
        GameButton next = butt;
        try {
            next = butt.getNextUpRight(revers);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            
        }
        try {
            if (next.getOccupation() == enemy) {
                list.add(next);
                checkUpRight(next, list, legal);
            }
            else if (next.getOccupation() == 0) {
                
            }
            else if (next.getOccupation() == notenemy) {
                if (list.size() > 0) {
                    legal.addToArrayList(list);
                    revers.getButton(revers.toInt(legal.getx(), legal.gety())).setLegalMoves(legal);
                    revers.setButtonImage(revers.toInt(legal.getx(), legal.gety()), revers.valid);
                    atleastone = 1;
                }
            }
            else if (next.getOccupation() == 3) {
                     
            }
        }
        catch (java.lang.NullPointerException xd) {}
    }
    
    /**
     * Checks in a straight line diagonally down left until it meets either the edge of the table, a blank, a legalmove (so a blank), or an enemy button.
     * If there has been at least one enemy button in between itself and the friendly one, with no gaps, it marks the button in question
     * as a legal move.
     */
    public void checkDownLeft(GameButton butt, ArrayList list, LegalMove legal)
    {
        GameButton next = butt;
        try {
            next = butt.getNextDownLeft(revers);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            
        }
        try {
            if (next.getOccupation() == enemy) {
                list.add(next);
                checkDownLeft(next, list, legal);
            }
            else if (next.getOccupation() == 0) {
                
            }
            else if (next.getOccupation() == notenemy) {
                if (list.size() > 0) {
                    legal.addToArrayList(list);
                    revers.getButton(revers.toInt(legal.getx(), legal.gety())).setLegalMoves(legal);
                    revers.setButtonImage(revers.toInt(legal.getx(), legal.gety()), revers.valid);
                    atleastone = 1;
                }
            }
            else if (next.getOccupation() == 3) {
                     
            }
        }
        catch (java.lang.NullPointerException xd) {}
    }
    
    /**
     * Checks in a straight line diagonally down right until it meets either the edge of the table, a blank, a legalmove (so a blank), or an enemy button.
     * If there has been at least one enemy button in between itself and the friendly one, with no gaps, it marks the button in question
     * as a legal move.
     */
    public void checkDownRight(GameButton butt, ArrayList list, LegalMove legal)
    {
        GameButton next = butt;
        try {
            next = butt.getNextDownRight(revers);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            
        }
        try {
            if (next.getOccupation() == enemy) {
                list.add(next);
                checkDownRight(next, list, legal);
            }
            else if (next.getOccupation() == 0) {
                
            }
            else if (next.getOccupation() == notenemy) {
                if (list.size() > 0) {
                    legal.addToArrayList(list);
                    revers.getButton(revers.toInt(legal.getx(), legal.gety())).setLegalMoves(legal);
                    revers.setButtonImage(revers.toInt(legal.getx(), legal.gety()), revers.valid);
                    atleastone = 1;
                }
            }
            else if (next.getOccupation() == 3) {
                     
            }
        }
        catch (java.lang.NullPointerException xd) {}
    }
}
