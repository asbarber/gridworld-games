package game.main;

import game.assets.AssetManager;
import game.compilation.pacman.Dot;
import game.compilation.pacman.Ghost;
import game.compilation.pacman.PacWorld;
import game.compilation.pacman.Pacman;
import game.maze.MazeBuilder;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Aaron
 */
public class PacmanGame {
    private PacWorld world;
    private MazeBuilder build;
    private int NUM_GHOSTS;
    
    public PacmanGame(){   
        setup();
    }
    public PacmanGame(int numGhosts){ 
        setup(numGhosts);
    }
    
    public final void setup(int n){
        //Ghost Count
        NUM_GHOSTS = n;
        
        //Builds the grid from file
        build = new MazeBuilder(AssetManager.getFile("PacmanMaze.txt"));
        build.readFromFile(); 
        
        //Initializes and Shows World
        world = new PacWorld(build.getMaze());      //Creates world from grid       
        initActors();
        world.show();      
        world.setNoGridLines();
        world.setFullScreen();
        
        world.thisGame = this;        
    }
    public final void setup(){
        setup(2);
    }   
    public final void initActors(){      
        //Character Spawn point
        world.add(build.getSpawnPoints().get(0), new Pacman());
        
        //Enemy spawn points (Make that many ghosts as long as that many spawn points)
        Ghost g;
        for (int i = 1; i <= NUM_GHOSTS && i < build.getSpawnPoints().size(); i++)
        {
            g = new Ghost();
            g.setRandomColor();
            
            world.add(build.getSpawnPoints().get(i), g);
        }       
        
        initDots();
    }   
    public void initDots(){
        ArrayList<Dot> setOfDots = new ArrayList<Dot>();
        Grid gr = world.getGrid();
        Object oo;
        Dot dd;
        Location ll;
        
        //DEBUG MODE
        //        dd = new Dot();
        //        dd.putSelfInGrid(gr, world.getRandomEmptyLocation());
        //        setOfDots.add(dd);
        
        
        //All Grid Locations
        for (int r = 0; r < gr.getNumRows(); r++){
            for (int c = 0; c < gr.getNumCols(); c++){
                //New Index Objects
                ll = new Location(r, c);
                oo = gr.get( ll );
                dd = new Dot();
                
                if ( oo == null )
                {
                    //Places In Grid, Add to Dot Set
                    dd.putSelfInGrid(gr, ll);
                    setOfDots.add(dd);
                }
                else if ( oo instanceof Ghost )
                {
                    dd.store(gr, ll);
                    setOfDots.add(dd);
                }
        }}
        
        world.setDots( setOfDots );
    }
    
    public PacWorld getWorld(){
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
        setup(NUM_GHOSTS);
    }      
    public void nextLevel(){
        world.close();
        setup(NUM_GHOSTS + 1);
    }
  
    public void displayControls(){
        String message = "Click 'run' to start the game of Pacman.\n";
        message += "While running, you can click stop to pause the game.\n";
        message += "Adjust the speed slider to change the difficulty.\n\n";
        message += "Use WSAD to move Pacman.\n\n";
        message += "You cannot move unless the game is running.\n";
        message += "Pressing step will move the Ghosts while you are locked in place.\n";
        JOptionPane.showMessageDialog(null, message);
    }     
}

