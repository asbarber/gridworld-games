package game.actor.bug;

/*
 * @author Aaron Barber
 */

import info.gridworld.actor.Bug;
import info.gridworld.grid.Location;

/**
 * A <code>ASBBug</code> traces out the letters ASB of a given height. <br />
 */
public class ASBBug extends Bug
{
    private int steps;
    private int sideLength;
    private int sides;
    private int letters;

    private int sizeA, sizeS, sizeB;

    /**
     * Constructs an ASB Bug
     * @param length the side length
     */
    public ASBBug(int length)
    {
        steps = 0;
        
        sideLength = makeOdd(length);

        sides = 0;
        letters = 0;

        //Default Sizes
        sizeA = sideLength;
        sizeB = sideLength;
        sizeS = sideLength;
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
        switch (letters){
            case -1:
                    break;
            case 0: createA();
                    break;
            case 1: createS();
                    break;
            case 2: createB();
                    break;
        }
    }


    public void createA(){        
        int tmpSide0 = sizeA - 1;
        int tmpSide1 = sizeA / 2;
        int tmpSide2 = tmpSide0;
        int tmpSide3 = tmpSide1;

        int tmpColJump = sizeA / 2;
        switch (sides){
            case 0:
                    setDirection(0);
                    doSide(tmpSide0, 90);
                    break;

            case 1: doSide(tmpSide1, 90);
                    break;

            case 2: 
                    Location startLocSide3 = new Location(getLocation().getRow() - tmpSide1 - 1, getLocation().getCol() - tmpSide1);
                    doSide(tmpSide2, -90, startLocSide3);
                    break;
            case 3: 
                    Location startLocNextLetter = new Location(getLocation().getRow() + tmpColJump, getLocation().getCol() + 1);
                    doSide(tmpSide3, -360, startLocNextLetter);
                    break;
            case 4:
                    steps = 0;
                    sides = 0;
                    letters++;
                    break;
        }
    }
    public void createS(){
        int sLength = sizeS/2;

        switch (sides){
            case 0:
                    setDirection(90);
                    doSide(sLength, -90);
                    break;
            case 1: doSide(sizeS / 2, -90);
                    break;
            case 2: doSide(sLength, 90);
                    break;
            case 3: doSide(sLength, 90);
                    break;
            case 4:
                    Location nextLetter = new Location(getLocation().getRow() + sizeS - 1, getLocation().getCol() + 1);
                    doSide(sLength, 0, nextLetter);
                    break;
            case 5:
                    steps = 0;
                    sides = 0;
                    letters++;
                    break;
        }
    }
    public void createB(){
        switch (sides){
            case 0:
                    setDirection(0);
                    doSide(sizeB - 1, 90);
                    break;
            case 1:
                    doSide(sizeB / 2, 90);
                    break;
            case 2:
                    doSide(1, 45);
                    break;
            case 3:
                    doSide(sizeB / 2 - 1, -90);
                    break;
            case 4:
                    doSide(sizeB / 2 - 1, 45);
                    break;
            case 5:
                    doSide(1, 90);
                    break;
            case 6:
                    doSide(sizeB / 2, 90);
                    break;
        }
    }


    //Modifier
            //Assumes c is A, S, or B
            public void setSizeLetter(char c, int size){
                size = makeOdd(size);

                if (c == 'A' || c == 'a')
                    sizeA = size;
                if (c == 'S' || c == 's')
                    sizeS = size;
                if (c == 'B' || c == 'b')
                    sizeB = size;
            }
            public void makeMonogram(){
                setSizeLetter('a', sideLength);
                setSizeLetter('s', (int)(1.5*sideLength));
                setSizeLetter('b', sideLength);
            }

    //Helper
            //Creates Side
            private void doSide(int thisSide, int turnDegrees){
                if (steps < thisSide && canMove()){
                    move();
                    steps++;
                }
                else if (steps >= thisSide){
                    setDirection(getDirection() + turnDegrees);
                    steps = 0;
                    sides++;
                }
            }
            private void doSide(int thisSide, int turnDegrees, Location newLoc){
                if (steps <= thisSide && canMove()){
                    move();
                    steps++;
                }
                else if (steps > thisSide){
                    setDirection(getDirection() + turnDegrees);
                    moveTo(newLoc);
                    steps = 0;
                    sides++;
                }
            }

            //Makes side Length odd
            private int makeOdd(int i){
                if (i % 2 > 0){
                    return i;
                }
                else{
                    return i + 1;
                }
            }
}
