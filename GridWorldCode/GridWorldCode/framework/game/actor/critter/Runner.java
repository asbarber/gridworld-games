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

import game.interactive.InteractiveWorld;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains chameleon critters. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class Runner
{
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        
        KiwiCritter k = new KiwiCritter();
        k.negated = true;
        
        world.add(new Location(2, 2), new Bug());
        world.add(new Location(3, 4), k);
        world.add(new Location(4, 4), new KiwiCritter());
        //world.add(new Location(5, 8), new QuickCrab());
        world.show();
    }
}