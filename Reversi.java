import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.border.*;
import javax.imageio.*;
import java.util.*;

/**
 * src.src.Reversi is a board game for two players.
 *
 * @author Niranjan
 * @version 3.4
 */
public class Reversi
{
    // Defining fields of src.src.Reversi to be used by various methods.
    private JFrame frame;               // The frame containing the game.
    private JLabel bottomlabel = new JLabel("");         // The label for the bottom text.
    private JButton newgamebutton;      // The hidden button for "New Game" on the side panel.
    private JButton startbutton;        // The button for starting the game on the side panel.
    private int size;                   // The playing board's size
    private int player1scoreint = 0;    // Score counter of black disks.
    private int player2scoreint = 0;    // Score counter of white disks.
    private JLabel player1score;        // The lavel for Black's score, displayed below the Name Box
    private JLabel player2score;        // The lavel for White's score, displayed below the Name Box
    public boolean whiteturn = false;   // Whether it is White's turn, or Black's turn.
    public Image black;                 // Image for the black disk
    public Image white;                 // Image for the white disk
    public Image valid;                 // Image for the valid disk placeholder
    public Image empty;                 // Image for the empty slots on the buttons
    private GameLogic previousmoves;    // Object to handle the game's disk logic. Methods are called from it often.
    public int whiteskipped = 0;        // Int to check whether white's turn has been skipped
    public int blackskipped = 0;        // Int to check whether black's turn has been skipped
    private int gamestarted = 0;        // Int to track whether the game has began yet or not.
    
    // Constructor methods for the board 
    /** 
     * Constructor to create a src.src.Reversi with a default size of 8x8
     */
    public Reversi()
    {
        size = 8;
        previousmoves = new GameLogic(this);
        loadIcons();
        makeFrame(); 
    }
    /**
     * Constructor to create a src.src.Reversi with a default size specified by the user or a method.
     */
    public Reversi(int boardsize)
    {
        size = boardsize;
        previousmoves = new GameLogic(this);
        if ((boardsize % 2 == 0) && (boardsize >= 4 && size <= 20)) {            
            size = boardsize;
        }
        else {
            System.out.println("number must be an even number between 4 and 20. Setting default size.");
            size = 8;
        }
        
        GameLogic previousmoves = new GameLogic(this);
        loadIcons();
        makeFrame();
             
    }
    /**
     * Constructor to Load a src.src.Reversi game from a Save File.
     */
    public Reversi(int boardsize, ArrayList list, boolean iswhiteturn)
    {
        size = boardsize;
        
        loadIcons();
        whiteturn = !iswhiteturn;
        nextTurn();
        makeFrame();
        startbutton.setVisible(false);
        for (int i = 0; i < list.size(); i++) {
            String line = (String)list.get(i);
            int occupationtemp = Integer.parseInt(line);      
            if (occupationtemp == 0) {
                setButtonImage(i, empty);
            }
            else if (occupationtemp == 1) {
                setButtonImage(i, black);
            }
            else if (occupationtemp == 2) {
                setButtonImage(i, white);
            }
        }
        addActionListeners();
        gamestarted = 1;
        GameLogic previousmoves = new GameLogic(this);
        previousmoves.markLegalMoves();
        updateDiscCount();
    }
    
    // Method to load in the pieces's designs
    /**
     * Loads in the icons for each of the players
     * NOTE: SET TO FALSE FOR THE NORMAL GAME!!! (I promise you'll have fun)
     */
    public void loadIcons()
    {
        try {
            if (true) { /** SET THIS TO FALSE FOR THE NORMAL GAME!!!!! (black and white pieces was too boring)*/
                black = ImageIO.read(getClass().getResource("easter1.png"));
                white = ImageIO.read(getClass().getResource("easter2.png"));
                valid = ImageIO.read(getClass().getResource("valid.png"));
                empty = ImageIO.read(getClass().getResource("blank.png"));
            }
            else { 
                black = ImageIO.read(getClass().getResource("blackcircle.png"));
                white = ImageIO.read(getClass().getResource("whitecircle.png"));
                valid = ImageIO.read(getClass().getResource("valid.png"));
                empty = ImageIO.read(getClass().getResource("blank.png"));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    // Methods creating the UI
    /**
     * Creates the frame containing the entire game, and all the Swing elements.
     */
    public void makeFrame() 
    {
        frame = new JFrame("src.src.Reversi");
        JPanel framepanel = new JPanel(new BorderLayout());
        framepanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        framepanel.setLayout(new BorderLayout(5, 5));
        
        makeMenuBar(frame);
        
        // Create the board
        Board temp = new Board(size);
        JPanel board = temp.createBoard(size, this);
        framepanel.add(board, BorderLayout.CENTER);
        
        // Create the panel on the right  for the score etc 
        GridLayout sidebargrid = new GridLayout(24, 0);
        sidebargrid.setVgap(5);
        sidebargrid.setHgap(5);
        JPanel sidebar = new JPanel(sidebargrid);
        sidebar.setBorder(new EtchedBorder());
        
        // creating player one (white)
        JLabel playerblackname = new JLabel("Player 1 (Black)");
        sidebar.add(playerblackname);
        
        JTextArea player1 = new JTextArea();    
        player1.setSize(new Dimension(100, 23));
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        player1.setBorder(border);
        sidebar.add(player1);
        
        player1score = new JLabel("Player 1 has " + player1scoreint + " Black pieces");
        sidebar.add(player1score);
        
        // adding one gap
        sidebar.add(new JPanel());
        
        // creating player two (black)
        JLabel playerwhitename = new JLabel("Player 2 (White)");
        sidebar.add(playerwhitename);
        
        JTextArea player2 = new JTextArea();
        player2.setSize(new Dimension(100, 23));
        player2.setBorder(border);
        sidebar.add(player2);
        
        player2score = new JLabel("Player 2 has " + player2scoreint + " Black pieces");
        sidebar.add(player2score);
        
        // adding some gaps
        sidebar.add(new JPanel());
        sidebar.add(new JPanel());
        
        // adding the start button
        startbutton = new JButton("Start");
        startbutton.addActionListener(ee -> setUp(previousmoves));
        sidebar.add(startbutton);
        
        // adding some gaps
        sidebar.add(new JPanel());
        sidebar.add(new JPanel());
        
        // adding the new game button
        newgamebutton = new JButton("New Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newgamebutton.addActionListener(e -> newGame(size));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newgamebutton.setVisible(false);
        sidebar.add(newgamebutton);
        
        // assigning the sidebar
        framepanel.add(sidebar, BorderLayout.EAST);
        
        // creating the bottom label
        bottomlabel.setText("Please enter your names in the boxes on the right hand side.");
        bottomlabel.setBorder(new EtchedBorder());
        framepanel.add(bottomlabel, BorderLayout.SOUTH);
        
        // finishing up
        frame.add(framepanel);
        frame.setSize(1000, 809);
        frame.setVisible(true); 
        
    }
    /**
     * Creates the Menu bar at the top. Called by makeFrame();
     */
    public void makeMenuBar(JFrame frame)
    {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        JMenu menu = new JMenu("Menu");
        menubar.add(menu);
        
        JMenuItem item = new JMenuItem("New Game");
        item.addActionListener(e -> new Reversi());
        menu.add(item);
        
        item = new JMenuItem("Change Size");
        item.addActionListener(e -> newGameDifferentSize());
        menu.add(item);
        
        item = new JMenuItem("Save Game");
        item.addActionListener(e -> saveGame());
        menu.add(item);
        
        item = new JMenuItem("Resumme Game");
        item.addActionListener(e -> resumeGame());
        menu.add(item);
    }
    /**
     * Adds all the action listeners to each button.
     */
    public void addActionListeners()
    {
        for (int i = 0; i < size*size; i++) {
            GameButton butt = getButton(i);
            butt.addActionListener(e -> buttonPressed(butt));
        }
    }
    
    // Methods handling creating and setting up new games.
    /**
     * Sets up the board and begins the game.
     */
    public void setUp(GameLogic gamelog)
    {
        addActionListeners();
        gamestarted = 1;
        bottomlabel.setText("");
        
        int number = toInt((size/2), (size/2));
        setButtonImage(number, white);
        
        number = toInt((size/2)+1, (size/2));
        setButtonImage(number, black);
        
        number = toInt((size/2), (size/2)+1);
        setButtonImage(number, black);
        
        number = toInt((size/2)+1, (size/2)+1);
        setButtonImage(number, white);
        
        gamelog.markLegalMoves();
        startbutton.setVisible(false);
        updateDiscCount();
    }
    /**
     * Starts a new game with the given game size.
     */
    public void newGame(int gamesize)
    {
        Reversi reversi = new Reversi(gamesize);
        frame.dispose();
    }
    /**
     * Sets the "New Game" button to visible
     */
    public void gameOver()
    {
        newgamebutton.setVisible(69 == 69);
    }
    /**
     * Creates a new game with a different size, determined by user input.
     */
    public void newGameDifferentSize()
    {
        int paneinput1 = 0;
        if (gamestarted == 1) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Proceeding will end your current game","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION){
                String paneinput = JOptionPane.showInputDialog("Please input new game size (Even number between 4 and 20 inclusive)");
                paneinput1 = Integer.parseInt(paneinput);
            }
        }
        else if (gamestarted == 0) {
                String paneinput = JOptionPane.showInputDialog("Please input new game size (Even number between 4 and 20 inclusive)");
                paneinput1 = Integer.parseInt(paneinput);
        }
        Reversi reversi = new Reversi(paneinput1);
        frame.dispose();
    }
    /**
     * Resumes a game from a save file.
     */
    public void resumeGame()
    {
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\Niranjan\\AppData\\Roaming\\Hidden Desktop\\OneDrive\\Cross Device\\Work\\CO520\\BlueJ Projects\\A4 src.src.Reversi\\src.src.Reversi");
        int returnVal = fileChooser.showOpenDialog(frame);
        int firstpart = 0;
        ArrayList list = new ArrayList();
        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File selectedfile = fileChooser.getSelectedFile();
        try {
            Scanner scan = new Scanner(selectedfile);
            int sizetemp = 0;
            boolean whiteturntemp = false;
            while (scan.hasNextLine()) {
                String line = scan.nextLine(); 
                if (firstpart == 0) {
                    String[] parts = line.split(",");
                    
                    try {
                        sizetemp = Integer.parseInt(parts[0]);
                        if (Integer.parseInt(parts[1]) == 1) {
                            whiteturntemp = false;
                        }
                        else if (Integer.parseInt(parts[1]) == 2) {
                            whiteturntemp = true;
                        }
                    }
                    catch (NumberFormatException e) {
                            
                    }
                }
                else {
                    list.add(line);
                }
                firstpart = 1;
            }
            Reversi reversi = new Reversi(sizetemp, list, whiteturntemp);
            frame.dispose();
        }
        catch (FileNotFoundException eeeee) {
            
        }
    }
    /**
     * Saves a game to a new save file, dated using the current Epoch.
     */
    public void saveGame()
    {
        try {
            FileWriter writer = new FileWriter("savefiles/src.src.Reversi Save "+ System.currentTimeMillis()+".txt", true);
            int turnofwhite = 0;
            if (whiteturn == true) {
                turnofwhite = 2;
            }
            else if (whiteturn == false) {
                turnofwhite = 1;
            }
            writer.write(size+","+turnofwhite);
            writer.write("\r\n");
            for (int i = 0; i < size*size; i++) {
                GameButton butt = getButton(i);
                int occupation = butt.getOccupation();
                if (butt.getOccupation() == 0) {
                    writer.write("0");
                }
                else if (butt.getOccupation() == 1) {
                    writer.write("1");
                }
                else if (butt.getOccupation() == 2) {
                    writer.write("2");
                }
                else if (butt.getOccupation() == 3) {
                    writer.write("0");
                }
                writer.write("\r\n");
            }
            writer.close();
        }
        catch (IOException e) {
            
        }
    }
    
    // Methods hanndling the Buttons and Turns of the board.
    /**
     * Returns the given number's button. (i.e 0 gives the 1st button on the board etc.)
     */
    public GameButton getButton(int number)
    {
        Component components1 = frame.getContentPane().getComponent(0);
        Component component2 = ((Container)components1).getComponent(0);
        Component component3 = ((Container)component2).getComponent(number);
        return ((GameButton)component3);
    }
    /**
     * Method called by the ActionListener on each button.
     * Checks if button is legal, and if not, sets a message.
     * If legal, it executes the button's changes, and sets up for the next turn.
     */
    public void buttonPressed(GameButton button)
    {
        if (button.getOccupation() == 0 || button.getOccupation() == 1 || button.getOccupation() == 2) {
            bottomlabel.setText("Illegal Move! Press one of the highlighted spaces");
        }
        if (button.getOccupation() == 3) {
            int marked = 0;
            int idk = 0;
            button.getLegalMoves().execute();
            removeLegals();
            placeDisc(button.getLocationField());
            previousmoves = new GameLogic(this);
            marked = previousmoves.markLegalMoves();
            String topopup = "";
            if (whiteturn) {
                topopup = "Black's turn again!";
            }
            else if (!whiteturn) {
                topopup = "White's turn again!";
            }
            if (marked == 0) {
                nextTurn();
                previousmoves = new GameLogic(this);
                marked = previousmoves.markLegalMoves();
                idk = 1;
                if (marked == 0) {
                    gameOver();
                    JOptionPane.showMessageDialog(null, "Game Over! To play again press 'New Game'\n on the right, or use the Menu.");
                    idk = 2;
                }
                
            }
            if (idk == 1) {
                    JOptionPane.showMessageDialog(null, "You can't make any moves! "+topopup );
                    
                }
            updateDiscCount();
        }
    }
    /**
     * Removes the placeholder icons between turns which mark which moves are legal.
     */
    public void removeLegals()
    {
        for (int i = 0; i < size*size; i++) {
            GameButton butt = getButton(i);
            if (butt.getOccupation() == 3) {
                butt.setLegalMoves(null);
                setButtonImage(i, empty);
            }
        }
    }
    /**
     * Updates the disk count on the right panel below the Name Boxes.
     */
    public void updateDiscCount()
    {
        GameButton butt;
        int blackdisks = 0;
        int whitedisks = 0;
        int totaldisks = 0;
        for (int i = 0; i < size*size; i++) {
            butt = getButton(i);
            if (butt.getOccupation() == 1) {
                blackdisks++;
                totaldisks++;
            }
            else if (butt.getOccupation() == 2) {
                whitedisks++;
                totaldisks++;
            }
        }
        player1scoreint = blackdisks;
        player2scoreint = whitedisks;
        player1score.setText("Player 1 has " + player1scoreint + " Black pieces");
        player2score.setText("Player 2 has " + player2scoreint + " White pieces");
    }
    /**
     * Sets the appropiate image on the button, and sets its occupation field appropiately.
     */
    public void setButtonImage(int number, Image colour)
    {
        GameButton butt = getButton(number);
        butt.setIcon(new StretchIcon(colour));
        if (colour == black) {
            butt.setOccupation(1);
        }
        else if (colour == white) {
            butt.setOccupation(2);
        }
        else if (colour == valid) {
            butt.setOccupation(3);
        }
        else if (colour == empty) {
            butt.setOccupation(0);
        }
    }
    /**
     * Places a disk, changes the turn to the next, and updates the text in the bottom label.
     */
    public void placeDisc(int number)
    {
        if (whiteturn) {
            setButtonImage(number, white);
            whiteturn = false;
            bottomlabel.setText("Black's turn!");
        }
        else if (!whiteturn) {
            setButtonImage(number, black);
            whiteturn = true;
            bottomlabel.setText("White's turn!");
        }
        updateDiscCount();
    }
    /**
     * Changes turn to the next, and updates the bottom label.
     */
    public void nextTurn()
    {
        if (whiteturn) {
            whiteturn = false;
            bottomlabel.setText("Black's turn!");
        }
        else if (!whiteturn) {
            whiteturn = true;
            bottomlabel.setText("White's turn!");
        }
    }
    
    // Utility Methods.
    /**
     * Takes grid coordinates and converts it to an absolute position
     */
    public int toInt(int x, int y)
    {
        return (((size)*y)+x)-(size+1);
    }
    /**
     * Accessor method for board's size.
     */
    public int getSize()
    {
        return size;
    }
    /**
     * Accessor method for the current turn
     */
    public boolean getWhiteTurn()
    {
        return whiteturn;
    }
    /**
     * Sets the current turn
     */
    public void setWhiteTurn(boolean truefalse)
    {
        whiteturn = truefalse;
    }
}
