package game.compilation.pacman;

import game.actor.Character;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Aaron and Dan
 */
public class Pacman extends Character{
    //Customized
    public Pacman(){
        setColor(Color.YELLOW);
    }
    @Override
    public int getDirectionAdjust(){
        return -90;
    }
    
    //Actions
    @Override
    public void act(){
        //Does not act without keyPress
    }
    @Override
    public void act(String e){
        if (e.equals("W"))
        {
            actionW();
        }
        else if (e.equals("S"))
        {
            actionS();
        }
        else if (e.equals("A"))
        {
            actionA();
        }
        else if (e.equals("D"))
        {
            actionD();
        }
    }
    
        //Key Based
        public void actionW(){
            customMove(getUp());
        }
        public void actionS(){
            customMove(getDown());
        }
        public void actionA(){
            customMove(getLeft());
        }
        public void actionD(){
            customMove(getRight());
        }    

        //Movement
        public void customMove(Location direction){
            turnTowardsMove(direction);
            
            //If the location moved to is outside and thus warp zone
            if ( isWarpZone(direction) )
            {
                teleport();
            }
            else
            {
                moveConstrained(direction);                
            }
        }     
        @Override
        public void moveConstrained(Location newLocation){            
            //If Occupied by Dot: Destroy and move
            if (this.getGrid().get(newLocation) instanceof Dot)
            {
                //Eats the dot and Moves
                ( (Dot) getGrid().get(newLocation) ).forget();
                moveTo(newLocation);
            }
            //Not Valid or Occupied Location
            else if (!this.getGrid().isValid(newLocation) || this.getGrid().get(newLocation) != null)
            {
                //Dont Move
            }
            else
            {
                moveTo(newLocation);
            }
        }     
        public boolean isWarpZone(Location loc){
            //This location is not valid so warp!
            if ( !getGrid().isValid(loc) )
            {
                return true;
            }
            //Is valid so no warp!
            else
            {
                return false;
            }
        }
        public void teleport() {
            int newRow = this.getLocation().getRow();
            int newCol;
            
            //Warp to the right
            if (this.getLocation().getCol() == 0)
            {
                newCol = getGrid().getNumCols() - 1;
            }
            //Warp to the left
            else
            {
                newCol = 0;
            }            
            
            this.moveTo( new Location( newRow, newCol ) );
        }
        
}
