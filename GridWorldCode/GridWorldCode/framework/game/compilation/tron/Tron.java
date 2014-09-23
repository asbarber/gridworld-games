package game.compilation.tron;

import game.actor.Character;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author ostudent
 */
public class Tron extends Character {
    public String id;
    public String[] keySet;

    public final static String KEYS_WASD[] = {"W", "S", "A", "D"};
    public final static String KEYS_IJKL[] = {"I", "K", "J", "L"};
    public final static String KEYS_1235[] = {"NUMPAD5", "NUMPAD2", "NUMPAD1", "NUMPAD3"};
    public final static String KEYS_ARROWS[] = {"UP", "DOWN", "LEFT", "RIGHT"};
    
    /**
     * Creates a Tron actor
     * @param name
     * @param color
     * @param keys Size 4: 1 = up, 2 = down, 3 = left, 4 = right
     */
    public Tron(String name, Color color, String[] keys)
    {
        id = name;
        setColor(color);
        keySet = keys;
    }

    @Override
    public int getDirectionAdjust(){
        return -90;
    }

    @Override
    public void act(){
        Location next = getLocation().getAdjacentLocation(getDirection());
        move(next);
    }
    @Override
    public void act(String e){
        int direction, dirTest;

            if (e.equals(keySet[0]))
            {
                direction = 0;
            }
            else if (e.equals(keySet[1]))
            {
                direction = 180;
            }
            else if (e.equals(keySet[2]))
            {
                direction = 270;
            }
            else if (e.equals(keySet[3]))
            {
                direction = 90;
            }
            else
            {
                //Not A Key For This Character
                return;
            }

                    //Prevents Turning on own path
                    if (getDirection() + 180 >= 360)
                    {
                        dirTest = getDirection() - 180;
                    }
                    else
                    {
                        dirTest = getDirection() + 180;
                    }

                    if (dirTest == direction)
                    {
                            return;
                    }

        setDirection(direction);
    }


    public void move(Location next) {
        //Location Before Move
        Location tmp = getLocation();

        //Moves
        if (canMove(next))
        {
            moveTo(next);

            //Drops The Rock
            dropRock(tmp);
        }
        else
        {
            endGame();
        }
    }
    private void dropRock(Location dropZone) {
        //Drops rock of this character's color
        Rock stream = new Rock();
        stream.setColor(getColor());

        //Puts in grid
        stream.putSelfInGrid(getGrid(), dropZone);
    }
    public boolean canMove(Location next) {
        //If does not exist or is occupied
        if (!this.getGrid().isValid(next) || this.getGrid().get(next) != null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private void endGame() {
        this.removeSelfFromGrid();
    }


}
