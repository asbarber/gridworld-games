package game.compilation.pacman;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;

/**
 *
 * @author Aaron
 */
public class Dot extends Actor{
    public Grid storedGrid;
    public Location storedLoc;
    
    public boolean displaced = false;
    
    public Dot(){
        setColor(Color.yellow);
    }

    public void act(){      
    }
    public void actForced(){
        //If displaced last step then replace this step
        if (displaced)
        {
            replace();
        }
    }
    
    public void displace(){
        store();
        this.removeSelfFromGrid();
    }  
    public void replace(){
        Object toPlaceAt = storedGrid.get(storedLoc);
                
        //Will replace if not occupied
        if (toPlaceAt == null)
        {
            this.putSelfInGrid(storedGrid, storedLoc);
            forget();           
        }
        //Occupied by Pacman
        else if (toPlaceAt instanceof Pacman)
        {
            forget();
        }
        else
        {
            //Attempt to replace next step
        }
    }
    
    public void store(){
        store(this.getGrid(), this.getLocation());             
    }
    public void store(Grid gr, Location loc){
        storedGrid = gr;
        storedLoc = loc;
        displaced = true;
    }
    public void forget(){
        storedGrid = null;
        storedLoc = null;
        displaced = false;
    }
}
