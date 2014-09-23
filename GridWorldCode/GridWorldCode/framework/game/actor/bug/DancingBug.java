package game.actor.bug;

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
 * @author Cay Horstmann
 * @author Chris Nevison
 * @author Barbara Cloud Wells
 */

import info.gridworld.actor.Bug;

/**
 * A <code>BoxBug</code> traces out a square "box" of a given size. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class DancingBug extends Bug
{
    private int[] turnVariation;
    private int lineSegment;

    public DancingBug(){
        int[] dance = new int[(int)(Math.random() * 100)];
        for (int i = 0; i < dance.length; i++){
            dance[i] = (int)(Math.random() * 10);
        }

        turnVariation = dance;
        lineSegment = 0;
    }

    public DancingBug(int[] dance)
    {
        turnVariation = dance;
        lineSegment = 0;
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
            //Moves if can
            if (canMove())
            {
                move();
            }

            //Sets the new direction
            int newDirection = getDirection() + 45 * turnVariation[lineSegment];
            setDirection(newDirection);

            lineSegment++; //Next element of array

            //Restart loop
            if (lineSegment >= turnVariation.length){
                lineSegment = 0;
            }
        
    }
}
