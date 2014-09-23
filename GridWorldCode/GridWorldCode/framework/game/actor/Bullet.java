package game.actor;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import java.awt.Color;

/**
 *
 * @author Aaron
 */
public class Bullet extends Actor{
    private int direction;
    private final int DIRECTION_ADJUSTMENT = 90;
    private int speed;   //In this case, speed represents how many steps must be taken for movement
    private int counter;
    
    public Bullet(int dir, int wait){
        direction = dir;
        speed = wait;
        setDirection(direction + DIRECTION_ADJUSTMENT);
        setColor(Color.BLACK);
    }
    public Bullet(Actor a){
        direction = a.getDirection();
        speed = 1;
        setDirection(direction + DIRECTION_ADJUSTMENT);
        setColor(Color.BLACK);   
        
        Location spawnPoint = a.getLocation().getAdjacentLocation(direction);
        
        this.putSelfInGrid(a.getGrid(), spawnPoint);
    }
    public Bullet(Actor a, int dir){
        direction = dir;
        speed = 1;
        setDirection(direction + DIRECTION_ADJUSTMENT);
        setColor(Color.BLACK);

        Location spawnPoint = a.getLocation().getAdjacentLocation(direction);

        this.putSelfInGrid(a.getGrid(), spawnPoint);
    }
    
    @Override
    public void act(){
        //Waits
        if (counter < speed)
        {
            counter++;
        }
        //Moves in Direction
        else
        {
            //in map
            if ( getGrid().isValid( next() ) )
            {
                moveTo( next() );
            }
            //Out of map
            else
            {
                this.removeSelfFromGrid();
            }
        }
    }
    
    public Location next(){
        return this.getLocation().getAdjacentLocation(direction);
    }
}
