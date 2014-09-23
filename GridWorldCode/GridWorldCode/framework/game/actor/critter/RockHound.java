package game.actor.critter;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;
import java.util.ArrayList;

/**
 *
 * @author Aaron and Dan
 */
public class RockHound extends Critter{

    /**
     * Eats all rocks in actor list
     * @param actors Actor list
     */
    @Override
    public void processActors(ArrayList<Actor> actors){
        for (Actor a : actors)
        {
            if (a instanceof Rock && !(a instanceof Critter) )
                a.removeSelfFromGrid();
        }
    }

}
