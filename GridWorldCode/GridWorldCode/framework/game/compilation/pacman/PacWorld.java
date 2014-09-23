package game.compilation.pacman;

import game.main.PacmanGame;
import game.interactive.InteractiveWorld;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Aaron
 */
public class PacWorld extends InteractiveWorld {
    public ArrayList<Dot> dotList;
    public PacmanGame thisGame;
    public boolean gameOver = false;;
    
    public PacWorld(Grid<Actor> grid) {
        super(grid);
    }
    
    
    @Override
    public void step(){        
        //Normal Step
            super.step();
        
        
        //Force Dot Action
            for (Dot dd: dotList)
            {
                dd.actForced();
            }    
            if ( isLevelOver() )
            {
                levelOver();
            }
    }  
    /**
     * Steps actors that are Character classes or subclasses
     * @param e Key Pressed
     */
    @Override
    public void step(String e)
    {               
        //Only Acts By Key if Running World
        if ( this.isRunning() )
        {   
            super.step(e);
        }
    }    

    
    public void setDots(ArrayList<Dot> aGroupOfDots) {
        dotList = aGroupOfDots;
    }
    public boolean isLevelOver(){
        //If any dots visible on grid
        for (Location ll: getGrid().getOccupiedLocations())
        {
            //Still Dots
            if (getGrid().get(ll) instanceof Dot)
            {
                return false;
            }
        }
        
        //No Visible Dots but dot has been displaced
        for (Dot dd: dotList)
        {
            if (dd.displaced == true)
            {
                return false;
            }
        }
        
        //No Dots
        return true;
    }
    public void levelOver(){
        if (!gameOver)
        {
            //Congrats
            JOptionPane.showMessageDialog(null, "Congrats!\n Good Luck with the next level!");
            thisGame.nextLevel();

            gameOver = true;
        }
    }
    
    
    
}
