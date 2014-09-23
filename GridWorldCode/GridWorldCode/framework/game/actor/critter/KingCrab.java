package game.actor.critter;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.ArrayList;

/**
 *
 * @author Aaron and Dan
 */
public class KingCrab extends CrabCritter{

    @Override
    public void processActors(ArrayList<Actor> actors){
        for (Actor a : actors)
        {
            if (!(a instanceof Rock) && !(a instanceof KingCrab))
            {
                turnActor(a);

                if (canMoveActor(a)){
                    moveActor(a);
                }
                else{
                    a.removeSelfFromGrid();
                }
            }
        }
    }

    
    //Turns Away From King
    public void turnActor(Actor a){
        //Face Away From King
        a.setDirection(getDirection());
    }

    //Checks if Actor Can Move
    public boolean canMoveActor(Actor a){
        if (getMoveLocations(a).isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
    
    //Moves the Actor
    public void moveActor(Actor a){
        ArrayList<Location> locs = getMoveLocations(a);

        //Random location out of available ones
        int n = locs.size();
        int r = (int) (Math.random() * n);

        //Moves Actor
        a.moveTo(locs.get(r));
    }

    
    //Possible Moves for ACTOR
    public ArrayList<Location> getMoveLocations(Actor a){
        ArrayList<Location> locs = new ArrayList<Location>();

        //Directions
        int[] dirs = { Location.AHEAD, Location.HALF_LEFT, Location.HALF_RIGHT };

        //Gets Possible Locations For Actor
        for (Location loc : getLocationsInDirections(dirs, a))
            if (getGrid().get(loc) == null)
                locs.add(loc);

        return locs;
    }

    //Locations in Directions For an Actor
    public ArrayList<Location> getLocationsInDirections(int[] directions, Actor a)
    {
        //Loc, Grid, Array For Actor
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid gr = a.getGrid();
        Location loc = a.getLocation();

        //All Directions
        for (int d : directions)
        {
            //Get Location
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);

            //If valid
            if (gr.isValid(neighborLoc))
                locs.add(neighborLoc);
        }
        return locs;
    }
}
