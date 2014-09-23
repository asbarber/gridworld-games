package game.actor.critter;

import info.gridworld.grid.Location;
import java.util.ArrayList;

/**
 *
 * @author Aaron and dan
 */
public class QuickCrab extends CrabCritter{

    @Override
    public ArrayList<Location> getMoveLocations() {
        ArrayList<Location> locs = new ArrayList<Location>();

        int[] dirs =  { Location.LEFT, Location.RIGHT };

        //All neighbor locations in dirs[] direction
        for (int i = 0; i < dirs.length; i++)
        {
                //Immediate Neighbor
                Location neighbor = getLocation().getAdjacentLocation(getDirection() + dirs[i]);

                    //If neighbor location is empty
                    if (getGrid().isValid(neighbor) && getGrid().get(neighbor) == null)
                    {
                        //Neighbor of neighbor
                        Location neighborTwo = neighbor.getAdjacentLocation(getDirection() + dirs[i]);

                            //Can Move Two Locs
                            if (  getGrid().isValid(neighborTwo) && getGrid().get(neighborTwo) == null ){
                                //Adds Neighbors Neighbor
                                locs.add(neighborTwo);
                            }
                            //Can only Move One Loc
                            else{
                                //Adds Immediate Neighbor
                                locs.add(neighbor);
                            }
                    }
        }

        return locs;
    }

}
