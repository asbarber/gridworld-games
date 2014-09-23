package game.path;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.ArrayList;

/**
 *
 * @author Dan and Aaron
 */
public class XYPathFinder extends PathFinder{
    public XYPathFinder(Grid g, Location begin, Location end){
        super(g, begin, end);
    }
    public XYPathFinder(Grid g, Location begin, Location end, ArrayList<Object> canWalkOn){
        super(g, begin, end, canWalkOn);
    }


    @Override
    public void addOpenAdjacentNodes(Node main){
        
        //Neighbor nodes
        for(Node i: main.getAdjacentXYNodes())
        {
            //Unoccupied Neighbor and Not on Closed List
            if (isOpen(i) && !isContainedClosed(i))
            {
                //Node not on open list
                if (!isContainedOpen(i))
                {
                    openList.add(i);
                }
                //Node on open list
                else
                {
                    //Index on which 'i' is contained on the openList
                    int index = indexContainedOpen(i);

                    //Better Path using 'i' than from openList's version of node 'i'
                    if( (getG(i) + getG(main)) < getG(openList.get(index)) )
                    {
                        Node previous = openList.get(index);
                        previous.setParent(i);

                        openList.set(index, previous);
                    }
                }
            }

        }
    }
}
