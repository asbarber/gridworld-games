package game.compilation.tron;

import game.path.MostOpenPath;
import game.path.Node;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Aaron
 */
public class TronAI extends Tron{
    private int step = 0;

    public TronAI(String name, Color color, String[] keys){
        super(name, color, keys);
    }    
    @Override
    public void act(){
        ai();
        
        //AIrotate();
        //step++;
        
        Location next = getLocation().getAdjacentLocation(getDirection());
        move(next);        
    }
    @Override
    public void act(String key){
        //Non-character
    }




    public void AIrotate() {  
        ArrayList<Integer> options = new ArrayList<Integer>();
        ArrayList<Integer> turn = new ArrayList<Integer>();
        
        //A Place To Turn
        turn.add(0);
        turn.add(90);
        turn.add(270);
          
        //All Options Can Move To
        for (int i = 0; i < turn.size(); i++)
        {
            if ( canMove( getNextLoc(turn.get(i)) ) )
            {
                options.add(turn.get(i));
            }
        }       
        
        //No Option
        if (options.isEmpty())
        {
            return;
        }
        
        double p = getGrid().getNumRows() / 2;
        //Determines Whether to Change Direction
        if ( (step + Math.random() * p) > p )
        {
            //Random Option
            int r = (int)(Math.random() * options.size());
            setDirection(getDirection() + options.get(r));
            step = 0;
        }
        else
        {
            //First Available Option (Current Direction Unless Blocked)
            setDirection(getDirection() + options.get(0));
        }

    }
    public Location getNextLoc(int degrees){
        return getLocation().getAdjacentLocation(this.getDirection() + degrees);
    }

    public void ai(){
        MostOpenPath pathFinder = new MostOpenPath(getGrid(), getLocation());
        Node path = pathFinder.getPath(5).getFirstDescendant();
        int dir = getLocation().getDirectionToward( path.getLocation() );
        
        setDirection(dir);
    }
}
