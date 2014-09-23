package game.compilation.pacman;

import game.actor.SuperCritter;
import game.compilation.pacman.Pacman;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Aaron
 */
public class Ghost extends SuperCritter{
    @Override
    public ArrayList<Actor> getActors(){
        ArrayList<Actor> actors = new ArrayList<Actor>();
        ArrayList<Location> locs = getGrid().getOccupiedLocations();
        
        //Adds All Occupied Locations that are Pacman Actors
        for(Location l : locs)
        {
            Actor a = getGrid().get(l);
            
            if (a instanceof Pacman)
            {
                actors.add(a);
            }
        }

        return actors;
    } 
    @Override
    public void processActors(ArrayList<Actor> actors){
        for (Actor a: actors)
        {
            //Kills Pacman
            if (isAdjacent(a))
            {
                a.removeSelfFromGrid();
            }

        }
    } 
    @Override
    public Location selectMoveLocation(ArrayList<Location> moves){
        //Random Actor To Chase
            ArrayList<Actor> tmp = getActors();

            if (tmp.isEmpty() || tmp == null)
            {
                turn();
                return getLocation();
            }

            int r = (int)(Math.random() * tmp.size());
            Actor main = tmp.get(r);
        
        //Location To Move
            ArrayList<Object> walkables = new ArrayList<Object>();
            walkables.add(new Dot());
            return getPathLocationTo(main, walkables);
    }
    @Override
    public void makeMove(Location loc){
        //Invalid
        if (loc == null) 
        {
            removeSelfFromGrid();
        }
        //Valid by Dot
        else if (getGrid().get(loc) instanceof Dot)
        {
            //Displaces the Dot and Moves to that location
            Dot dd = (Dot) getGrid().get(loc);
            dd.displace();
            moveTo(loc);
        }
        //Valid
        else 
        {
            moveTo(loc);
        }        
    }
    
    
    public void setRandomColor(){
        Color r;
        int n = (int)(Math.random() * 5);

        switch (n)
        {
            case 0: r = Color.CYAN;     break;
            case 1: r = Color.RED;      break;
            case 2: r = Color.GREEN;    break;
            case 3: r = Color.BLUE;     break;
            case 4: r = Color.MAGENTA;  break;
            default: r = Color.BLACK;
        }

        this.setColor(r);
    }
}
