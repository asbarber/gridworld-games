package game.path;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.grid.UnboundedGrid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Aaron
 */
public class PathFinder {
    protected Node target;
    protected Node start;
    protected ArrayList<Object> walkables;
    protected ArrayList<Node> openList;
    protected ArrayList<Node> closedList;
    

    protected final int MAX_PATH_CHECK = 200;      //Limits unbounded grids
    protected int counter = MAX_PATH_CHECK + 1;    //For limiting unbounded grids
    
    public PathFinder(Grid g, Location begin, Location end){
        start = new Node(g, begin);
        target = new Node(g, end);
        
        openList = new ArrayList<Node>();
        closedList = new ArrayList<Node>();
        
        //Limits unbounded grids
        if (g instanceof UnboundedGrid)
        {
            counter = 0;
        }

        walkables = new ArrayList<Object>();
    }
    public PathFinder(Grid g, Location begin, Location end, ArrayList<Object> canWalkOn){
        start = new Node(g, begin);
        target = new Node(g, end);
        
        openList = new ArrayList<Node>();
        closedList = new ArrayList<Node>();
        
        //Limits unbounded grids
        if (g instanceof UnboundedGrid)
        {
            counter = 0;
        }
        
        walkables = canWalkOn;
    }   
    
    //Starting point
    public Node getPath(){   
        //Begins process from start node
        Node lowest = start;
        
        //When target isn't on the closed list and the open list isn't empty
        do
        {
            addOpenAdjacentNodes(lowest);
            openList.remove(lowest);
            closedList.add(lowest);
            lowest = getLeastCost();
            
            counter++;
        }while( !isContainedClosed(target) && !openList.isEmpty() && counter != MAX_PATH_CHECK);
        
        
        //No path to target
        if (openList.isEmpty())
        {
            return start;
        }
        else
        {
            //Last on closed list was target
            return closedList.get(closedList.size() - 1);
        }
    }
    
    public void addOpenAdjacentNodes(Node main){
        //Neighbor nodes
        for(Node i: main.getAdjacentNodes())
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
    
    public boolean isTarget(Node source){
        //Is target node
        if (source.getID().equals(target.getID()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean isOpen(Node source){
        //Walkable or Target
        if ( source.getOccupant() == null || isWalkable(source) || isTarget(source) )
        {
            return true;
        }
        else
        {
            return false;
        }
            
    }
    public boolean isWalkable(Node source){

        
        //All Walkable objects
        for (Object o: walkables)
        {
            //If source class is walkable class type
            if ( source.getOccupant().getClass() == o.getClass() )
            {
                return true;
            }
        }
        
        //Unwalkable
        return false;
    }
    public boolean isContainedOpen(Node source){
        for (Node i: openList){
            //Is Contained (Location Based)
            if ( i.getID().equals(source.getID()) )
            {
                return true;
            }
        }
        
        return false;
    }   
    public boolean isContainedClosed(Node source){
        for (Node i: closedList){
            //Is Contained (Location Based)
            if ( i.getID().equals(source.getID()) )
            {
                return true;
            }
        }
        
        return false;
    }  
    public int indexContainedOpen(Node source){
        for (int i = 0; i < openList.size(); i++)
        {
            //On Open List
            if ( openList.get(i).getID().equals(source.getID()) )
            {
                //Index
                return i;
            }
        }
        
        //Not on open list
        return -1;
    }
    
    /** Cost Analysis */
    public Node getLeastCost(){
        //No Open Squares
        if (openList == null || openList.isEmpty())
        {
            return start;
        }
        
        //Sort by F cost
        Collections.sort(openList, new compareCost());
        
        //Least Cost
        return openList.get(0);
    }
    public class compareCost implements Comparator<Node>{
        public int compare(Node t, Node t1) {
            return getF(t) - getF(t1);
        }
    };
    
    public int getF(Node source){
        //Cost
        return getG(source) + getH(source);
    }
    public int getH(Node source){
        //Distance To Target (In Horizontal or Vertical Movements)
        int dx = Math.abs( target.getX() - source.getX() );
        int dy = Math.abs( target.getY() - source.getY() );
        
        //Cost
        return 10 * (dx + dy);
    }
    public int getG(Node source){
        //Direction towards parent
        int dir = source.getLocation().getDirectionToward(source.parent.getLocation());
        
        //Horizontal or Vertical
        if (dir % 90 == 0)
        {
            //Cost (1)
            return 10;
        }
        //Diagonal
        else
        {
            //Cost (root 2)
            return 14;
        }
    }

    public void printOpenList(){
        System.out.println("Open List: ");
        
        for (Node i: openList)
        {
                System.out.print( "Location: " + i.getLocation().toString() + ", Cost: " + getF(i));
            
            if (i.parent != null)
            {
                System.out.println( ", Parent: " + i.parent.getLocation().toString() );
            }
            else
            {
                System.out.println();
            }
        }
        
        System.out.println();
    }   
    public void printClosedList(){
        System.out.println("Closed List: ");
        
        for (Node i: closedList)
        {
                System.out.print( "Location: " + i.getLocation().toString());
            
            if (i.parent != null)
            {
                System.out.println( ", Parent: " + i.parent.getLocation().toString() );
            }
            else
            {
                System.out.println();
            }
        }
        
        System.out.println();
    }
        
}
