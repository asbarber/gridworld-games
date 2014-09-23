package game.actor.critter;

/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Chris Nevison
 * @author Barbara Cloud Wells
 * @author Cay Horstmann
 */

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Location;
import java.awt.Color;

import java.util.ArrayList;

/**
 * A <code>ChameleonCritter</code> takes on the tmp of neighboring actors as
 * it moves through the grid. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class ChameleonCritter extends Critter
{
    /**
     * Randomly selects a neighbor and changes this critter's tmp to be the
     * same as that neighbor's. If there are no neighbors, no action is taken.
     */
    public void processActors(ArrayList<Actor> actors)
    {
        int n = actors.size();
        if (n == 0){
            darken();
            return;
        }
        int r = (int) (Math.random() * n);

        Actor other = actors.get(r);
        setColor(other.getColor());
    }

    /**
     * Turns towards the new location as it moves.
     */
    public void makeMove(Location loc)
    {
        Location tmp = getLocation();           //Added In for flower drop

        setDirection(getLocation().getDirectionToward(loc));
        super.makeMove(loc);

        Flower flower = new Flower(getColor()); //Added In for flower drop
        flower.putSelfInGrid(getGrid(),tmp);    //Added In for flower drop
    }


    /**
     * Changes the critters color to a darker version of it's current color.
     */
    public void darken(){
        double DARKENING_FACTOR = 0.05;
        Color c = getColor();
        
        int red = (int) (c.getRed() * (1 - DARKENING_FACTOR));
        int green = (int) (c.getGreen() * (1 - DARKENING_FACTOR));
        int blue = (int) (c.getBlue() * (1 - DARKENING_FACTOR));

        setColor(new Color(red, green, blue));
    }
}
