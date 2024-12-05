import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

/**
 * The board class creates a JPanel and adds the customised GameButtons in a grid array. 
 *
 * @author Niranjan
 * @version 1.1
 */
public class Board
{
    private JPanel board;       //the JPanel containing the gamebuttons
    private int boardsize;      // int for the size of the board

    /**
     * Constructor to create the 8x8 board
     */
    public Board(int size)
    {
        board = new JPanel(new GridLayout(size, size));
    }
    
    /**
     * Method which creates all the buttons to go on the board, and sets their colour and outline.
     */
    public JPanel createBoard(int size, Reversi revers)
    {
        int x = 1;
        int y = 1;
        boardsize = size;
        
        for (int i = 0; i < size*size; i++, x++) {
            GameButton button = new GameButton(i, x, y);
            button.setBackground(new Color(46, 134, 53));
            button.setBorder(BorderFactory.createLineBorder(Color.black));
            button.setIcon(new StretchIcon(revers.empty));
            button.setOccupation(0);
            board.add(button);
            
            if (x == size) {
                x = 0;
                y++;
            }
        }
        
        board.setBorder(new EtchedBorder());
        return board;
    }
}
