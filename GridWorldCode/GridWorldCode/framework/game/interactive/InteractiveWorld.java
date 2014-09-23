/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package game.interactive;

import game.actor.Character;
import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.ArrayList;

/**
 *
 * @author Aaron
 */
public class InteractiveWorld extends ActorWorld {

    public InteractiveWorld(Grid<Actor> grid) {
        super(grid);
    }
    
    /**
     * Steps actors that are Character classes or subclasses
     * @param e Key Pressed
     */
    public void step(String e)
    {
        //Only Acts By Key if Running World
        if ( this.isRunning() )
        {
                Grid<Actor> gr = getGrid();
                ArrayList<Actor> actors = new ArrayList<Actor>();
                for (Location loc : gr.getOccupiedLocations())
                    actors.add(gr.get(loc));

                for (Actor a : actors)
                {
                    // only act if another actor hasn't removed a
                    if (a.getGrid() == gr && a instanceof Character)
                        ( (Character) a ).act(e);
                }            
        }
    }

    @Override
    public boolean keyPressed(String description, Location loc){        
        //Events Based on
        step(description);
  
        //Updates
        repaint();

        //Key Consumption (see World.java)
        return false;
    }
  
    public void clear(){
        for (int r = 0; r < getGrid().getNumRows(); r++)
        {
            for (int c = 0; c < getGrid().getNumCols(); c++)
            {
                Object oo = getGrid().get(new Location(r, c));
                        
                if (oo instanceof Actor)
                {
                    ((Actor)oo).removeSelfFromGrid();
                }
            }
        }
    }
}
