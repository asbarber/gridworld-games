/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package game.actor.jumper;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

/**
 *
 * @author Aaron Barber
 */
public class Jumper extends Bug{
    
    @Override
    public void act(){
        if (canJump()){
            jump();
        }
        else if (canMove()){
            move();
        }
        else{
            turn();
        }

    }

    public void jump(){
        //Grid
        Grid<Actor> gr = getGrid();
            //Not empty grid
            if (gr == null){
                return;
            }

        //Location
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        Location jump = next.getAdjacentLocation(getDirection());
            //Valid move
            if (gr.isValid(jump)){
                moveTo(jump);
            }
            else{
                removeSelfFromGrid();
            }
    }

    public boolean canJump(){
        //Grid
        Grid<Actor> gr = getGrid();
            //Empty Grid
            if (gr == null){
                return false;
            }

        //Location in Grid
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        Location jump = next.getAdjacentLocation(getDirection());
            //Two spaces ahead is in grid
            if (!gr.isValid(jump)){
                return false;
            }

        //Actor at Location
        Actor neighbor = gr.get(jump);
            //Can jump to this space
            if (neighbor == null || neighbor instanceof Bug){
                return true;
            }
            else{   //is a rock
                return false;
            }
    }
}
