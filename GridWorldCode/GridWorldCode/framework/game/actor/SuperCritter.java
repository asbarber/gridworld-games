package game.actor;

import game.path.Node;
import game.path.PathFinder;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import java.util.ArrayList;

/**
 * This class is the same as Critter but with added methods.
 * These methods cover:
 * (1) Re-orienting an image
 * (2) Basic Location Determination (Adjacency, Up/Down/Left/Right Locations)
 * (3) Pathfinding (Simplistic Approach, A* Algorithm Approach)
 * (4) Movement (Constrained Moving, Turning Towards Move)
 * @author Aaron Barber
 */
public class SuperCritter extends Critter{
    /**
     * The rotational adjustment for the image.
     */
    private int DIRECTION_ADJUST = 0;

    /**
     * Use this to reorient the image for this critter.
     * Uses bearing (0 degrees is North, 90 degrees is East).
     * @param rot The number of degrees image should be rotated.
     */
    public void setDirectionAdjust(int rot){
        DIRECTION_ADJUST = rot;
    }
    /**
     * Gets the re-orientation on the image.
     * @return The number of degrees rotated.
     * Uses bearing (0 degrees is North, 90 degrees is East).
     */
    public int getDirectionAdjust(){
        return DIRECTION_ADJUST;
    }    
    
    /**
     * Checks whether another actor is touching this actor.
     * @param a The other actor.
     * @return True if adjacent (within one grid location), false if not.
     */
    public boolean isAdjacent(Actor a){
        //1 if horizontal, 1.4 if diagonal
        if ( getDistanceSquared(this, a) <= 2 )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Gets the distance squared (saves computations) between this actor and another.
     * Uses Pythagorean theorem as distance calculation.
     * *Note* Therefore diagonal movements are more farther distances.
     * @param a Other actor to compare.
     * @return The distance.
     */
    public int getDistanceSquared(Actor a){
        return getDistanceSquared(this, a);
    }
    /**
     * Gets the distance squared (saves computations) between two actors.
     * Uses Pythagorean theorem as distance calculation.
     * *Note* Therefore diagonal movements are more farther distances.
     * @param a An actor.
     * @param b Another actor.
     * @return The distance.
     */
    public int getDistanceSquared(Actor a, Actor b){
        //x and y relations
        int xDist = a.getLocation().getCol() - b.getLocation().getCol();
        int yDist = a.getLocation().getRow() - b.getLocation().getRow();

        //Distance
        return (xDist*xDist + yDist*yDist);                
    }    

    /**
     * Very rudimentary and basic pathfinding by getting the location
     * in the direction of another actor.
     * If that location is occupied it will return the current location.
     * @param a An actor to get the location to.
     * @return The location in the direction of the actor or the current location
     * if that location is occupied.
     */
    public Location getLocationTowards(Actor a){
        //Direction Towards Actor
        int dirTowards = getLocation().getDirectionToward(a.getLocation());
        
        //Location Towards Actor
        Location primeLoc = getLocation().getAdjacentLocation(dirTowards);
        
        //If Occupied Location
        if (getGrid().get(primeLoc) != null)
        {
            return getLocation();
        }
        //Unoccupied Location
        else
        {
            return primeLoc;
        }
        
    } 
    
    /**
     * Pathfinding using the A* algorithm to get the most efficient path to an actor.
     * The actual final node is returned.
     * Assumes all objects are "unwalkable".
     * This is very effective when implemented towards AI.
     * Note: (1) Heavy amounts of calls to this method will slow the program down significantly.
     *       (2) This method alone gives no way to implement reasonable difficulty.
     *           Think of this path as the heat seeking missile that is unavoidable.
     * @param a Actor to get path towards.
     * @return The node containing actor A and ancestor containing this critter.
     */
    public Node getPathTo(Actor a){
        PathFinder pathway = new PathFinder( getGrid(), getLocation(), a.getLocation() );
        return pathway.getPath();
    }
    /**
     * Pathfinding using the A* algorithm to get the most efficient path to an actor.
     * The actual final node is returned.
     * Enables a list of "walkable" object types to be listed.
     * Note: (1) This method does not deal with the "walkable" objects itself,
     *           that should be dealt within the movement functioning of individual actors.
     * This is very effective when implemented towards AI.
     * Note: (2) Heavy amounts of calls to this method will slow the program down significantly.
     *       (3) This method alone gives no way to implement reasonable difficulty.
     *           Think of this path as the heat seeking missile that is unavoidable.
     * @param a Actor to get path towards.
     * @return The node containing actor A and ancestor containing this critter.
     */    
    public Node getPathTo(Actor a, ArrayList<Object> walkables){
        PathFinder pathway = new PathFinder( getGrid(), getLocation(), a.getLocation(), walkables );
        return pathway.getPath();
    }    
    /**
     * Pathfinding using the A* algorithm to get the most efficient path to an actor.
     * The location next along this path is returned. If no path then returns current location.
     * Assumes all objects are "unwalkable".
     * This is very effective when implemented towards AI.
     * Note: (1) Heavy amounts of calls to this method will slow the program down significantly.
     *       (2) This method alone gives no way to implement reasonable difficulty.
     *           Think of this path as the heat seeking missile that is unavoidable.
     * @param a Actor to get path towards.
     * @return The location that is the first step on the path.
     */
    public Location getPathLocationTo(Actor a){
        //Ending Place
        Node endNode = getPathTo(a);
        
        //Gets the first step towards the ending place
        Location nextMove = endNode.getFirstDescendant().getLocation();
        
        //If no path to the end
        if (nextMove == null)
        {
            return this.getLocation();
        }
        
        return nextMove;
    }
    /**
     * Pathfinding using the A* algorithm to get the most efficient path to an actor.
     * The location next along this path is returned. If no path then returns current location.
     * Enables a list of "walkable" object types to be listed.
     * Note: (1) This method does not deal with the "walkable" objects itself,
     *           that should be dealt within the movement functioning of individual actors.
     * This is very effective when implemented towards AI.
     * Note: (2) Heavy amounts of calls to this method will slow the program down significantly.
     *       (3) This method alone gives no way to implement reasonable difficulty.
     *           Think of this path as the heat seeking missile that is unavoidable.
     * @param a Actor to get path towards.
     * @return The location that is the first step on the path.
     */      
    public Location getPathLocationTo(Actor a, ArrayList<Object> walkables){
        //Ending Place
        Node endNode = getPathTo(a, walkables);
        
        //Gets the first step towards the ending place
        Location nextMove = endNode.getFirstDescendant().getLocation();
        
        //If no path to the end
        if (nextMove == null)
        {
            return this.getLocation();
        }
        
        return nextMove;
    }    
    
    /**
     * Simply rotates the direction the actor is facing by 45 degrees clockwise.
     */
    public void turn(){
        setDirection(this.getDirection() + Location.HALF_RIGHT);
    } 
    /**
     * A specific type of turn that will set the actor to face in the direction
     * of the location. Note, this location does not have to be valid.
     * It is generally ideal to implement this method before moveTo() or moveConstrained().
     * @param newLocation Location to turn towards.
     */
    public void turnTowardsMove(Location newLocation){
        int dir = getLocation().getDirectionToward(newLocation); 
        setDirection(dir + getDirectionAdjust());
    } 
    /**
     * A specific type of move that only moves to the location if it is valid and empty.
     * This method eliminates the preconditions (valid/non-empty) from moveTo()
     * by not moving if these conditions are not met.
     * It is generally ideal to implement this method after turnTowardsMove().
     * @param newLocation The location to move to.
     */
    public void moveConstrained(Location newLocation){
        //Not Valid or Occupied Location
        if (!this.getGrid().isValid(newLocation) || this.getGrid().get(newLocation) != null)
        {
            //Don't Move
        }
        else
        {
            moveTo(newLocation);
        }
    }
    

    /**
     * Gets the grid location below this critter.
     * @return The location one row down.
     */
    public Location getDown(){
        return new Location(getLocation().getRow() + 1, getLocation().getCol());        
    }   
    /**
     * Gets the grid location above this critter.
     * @return The location one row up
     */
    public Location getUp(){
        return new Location(getLocation().getRow() - 1, getLocation().getCol());        
    }
    /**
     * Gets the grid location left of this critter.
     * @return The location one col left.
     */
    public Location getLeft(){
        return new Location(getLocation().getRow(), getLocation().getCol() - 1);        
    }
    /**
     * Gets the grid location right of this critter.
     * @return The location one col right.
     */
    public Location getRight(){
        return new Location(getLocation().getRow(), getLocation().getCol() + 1);        
    }
}
