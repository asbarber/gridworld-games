package game.main;

import game.actor.bug.DancingBug;
import game.compilation.tron.Tron;
import game.compilation.tron.TronAI;
import game.compilation.tron.TronWorld;
import info.gridworld.actor.Critter;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author Aaron
 */
public class TronGame{
    private TronWorld world;
    
    private int humanPlayers, aiPlayers;
    private boolean largeGrid;
    private boolean obstructions;
    private BoundedGrid grid;

    //Constructor
    public TronGame(){
        setup();
    }
      
    //Setup and Initialization
    private void setup(){
        askPlayers();
        askSettings();
        initSettings();
        
        world = new TronWorld(grid, this);
        initActors();
        world.show();
        world.setNoGridLines();
        world.setFullScreen();
    }
    private void initActors() {
        //Locations
            int rMax = world.getGrid().getNumRows() - 1;
            int cMax = world.getGrid().getNumCols() - 1;
            int rMid = rMax / 2;
            int cMid = cMax / 2;
        
        //Default Spawn, Direction, Color, Keys
            //Center Bottom, Center Top, Center Left, Center Right
            Location spawnPoints[] = {new Location(rMax - 1, cMid), new Location(1, cMid),
                                      new Location(rMid, 1),    new Location(rMid, cMax - 1)};
            int direction[] = {0, 180, 90, 270};
            Color colors[] = {Color.ORANGE, Color.BLUE, Color.RED, Color.GREEN};
            String keys[][] = {Tron.KEYS_WASD, Tron.KEYS_IJKL, Tron.KEYS_1235, Tron.KEYS_ARROWS};
        
        //Actor Insertion
        Tron t;
            //Human
            for (int hu = 0; hu < humanPlayers; hu++)
            {
                t = new Tron("Player " + (hu + 1), colors[hu], keys[hu]);
                t.setDirection(direction[hu]);
                t.putSelfInGrid(world.getGrid(), spawnPoints[hu]);
            }
            //AI
            for (int ai = 0; ai < aiPlayers; ai++)
            {
                t = new TronAI("Computer " + (ai + 1), colors[ai + humanPlayers], keys[0]);
                t.setDirection(direction[ai + humanPlayers]);
                t.putSelfInGrid(world.getGrid(), spawnPoints[ai + humanPlayers]);
            }        

        //Settings (Additional Critters)
            if (obstructions)
            {
                critters();
            }
    }
    private void initSettings(){
        if (largeGrid)
        {
            grid = new BoundedGrid(41, 91);
        }
        else
        {
            grid = new BoundedGrid(21, 21);
        }
    }
    private void critters(){
        Critter a = new Critter();
        Critter b = new Critter();
        Critter c = new Critter();
        Critter d = new Critter();
        Critter e = new Critter();
        Critter f = new Critter();
        Critter g = new Critter();
        DancingBug h = new DancingBug();
        DancingBug i = new DancingBug();

        a.setColor(Color.gray);
        b.setColor(Color.gray);
        c.setColor(Color.gray);
        d.setColor(Color.gray);
        e.setColor(Color.gray);
        f.setColor(Color.gray);
        g.setColor(Color.gray);
        h.setColor(Color.gray);
        i.setColor(Color.gray);
        
        world.add(world.getRandomEmptyLocation(), a);
        world.add(world.getRandomEmptyLocation(), b);
        world.add(world.getRandomEmptyLocation(), c);
        world.add(world.getRandomEmptyLocation(), d);
        world.add(world.getRandomEmptyLocation(), e);
        world.add(world.getRandomEmptyLocation(), f);
        world.add(world.getRandomEmptyLocation(), g);
        world.add(world.getRandomEmptyLocation(), h);
        world.add(world.getRandomEmptyLocation(), i);

        world.repaint();
    }

    //World Functions
    public TronWorld getWorld(){
        return world;
    }
    public boolean isRunning(){
        return world.isRunning();
    }   
    public void start(){
        world.run();
    }
    public void stop(){
        world.stop();
    }
    public void close(){
        world.close();
    }    
    public void reset(){
        world.close();
        setup();
    }
    
    //User-Interface
    public void displayControls(){
        String message = "Click 'run' to start the game of Tron.\n";
        message += "While running, you can click stop to pause the game.\n";
        message += "Adjust the speed slider to change the speed of the gameplay.\n\n";
        message += "Use WSAD to move Player 1 (Bottom: Orange).\n";
        message += "Use IJKL to move Player 2 (Top: Blue).\n";
        message += "Use 5213 on the numpad to move Player 3 (Left: Red).\n";
        message += "Use the arroy keys to move Player 4 (Right: Green). \n\n";
        message += "You cannot change direction unless the game is running.\n";
        message += "Pressing step will move the one turn at a time.\n";
        JOptionPane.showMessageDialog(null, message);
    } 
    private void askPlayers(){
        String message;
        String input;
        int tmp;
        
        //Total Players
        message = "How many total players (AI and Human)? (2-4)\n"; 
        do
        {
           input = JOptionPane.showInputDialog(null, message);         
        }while ( !isValid("Total", input, -1) );
        tmp = Integer.parseInt(input);
        
        //Human Players
        message = "How many human players? (1-4)\n";
        do
        {
            input = JOptionPane.showInputDialog(null, message);
        }while ( !isValid("Human", input, tmp) );
        
        //Num of Players
        humanPlayers = Integer.parseInt(input);
        aiPlayers = tmp - humanPlayers;
    }
    private void askSettings(){
        String message;
        String input;
        int tmp;

        //Grid Size
        message = "Do you want a big grid or small grid? (Big/Small)\n";
        do
        {
           input = JOptionPane.showInputDialog(null, message);
        }while ( !isValid("Size", input, -1) );

            if (input.toUpperCase().trim().contains("B"))
            {
                largeGrid = true;
            }
            else
            {
                largeGrid = false;
            }

        //**NOTE: this is initalized under init actors
        //Critters
        message = "Do you want additional obstructions? (Yes/No)\n";
        do
        {
            input = JOptionPane.showInputDialog(null, message);
        }while ( !isValid("Obstructions", input, -1) );

            if (input.toUpperCase().trim().contains("Y"))
            {
                obstructions = true;
            }
            else
            {
                obstructions = false;
            }
    }
    private boolean isValid(String type, String input, int total) {
        if (type.equals("Total"))
        {
            int in = Integer.parseInt(input);
            if ( in <= 4 && in >= 2)
            {
                return true;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Enter Valid Input!");
                return false;
            }
        }
        else if (type.equals("Human"))
        {
            int in = Integer.parseInt(input);
            if ( in <= 4 && in >= 1 && in <= total)
            {
                return true;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Enter Valid Input!");                
                return false;
            }
        }
        else if (type.equals("Size"))
        {
            //Format
            input = input.toUpperCase().trim();

            if ( input.contains("B") || input.contains("S") )
            {
                return true;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please enter 'Big' or 'Small'!");
                return false;
            }
        }
        else if (type.equals("Obstructions"))
        {
            input = input.toUpperCase().trim();

            if ( input.contains("Y") || input.contains("N"))
            {
                return true;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please enter 'Yes' or 'No'!");
                return false;
            }
        }
        //Failsafe
        JOptionPane.showMessageDialog(null, "Woah!");        
        return false;
    }
}
