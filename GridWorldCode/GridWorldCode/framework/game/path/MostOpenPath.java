package game.path;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Aaron
 */
public class MostOpenPath{
    protected Node home;
    protected ArrayList<Node> openList;
    protected ArrayList<Object> walkables;
    protected Comparator comparePaths;
    protected int arrayCount;
    
    public MostOpenPath(Grid g, Location begin){
        home = new Node(g, begin);
        walkables = new ArrayList<Object>();
        openList = new ArrayList<Node>();
        comparePaths = new compareSize();
        arrayCount = 0; //Only add neighbors to Nodes on openList beyond this
    }
    
    /**
     * Finds the path with the least number of obstructions
     * @param n Number of Layers to extend
     * @return 
     */
    public Node getPath(int n){
        int step = 1;
        openList.add(home);
        
        //Continually Adds Adjacent Nodes
        while (step <= n)
        {
            //Size Before openList expands
            int fixedSize = openList.size();
            
            for (int i = 0; arrayCount < fixedSize; arrayCount++)
            {
                addAdjacentNodes( openList.get(arrayCount) );
            }
            
            step++;
        }
        
        return getMostOpenNode();
    }
    
    public void addAdjacentNodes(Node main){
        //Neighbor nodes
        for(Node i: main.getAdjacentXYNodes())
        {
            //Unoccupied Neighbor
            if (isOpen(i))
            {          
                //Not On Open List, Add it
                if (!isContainedOpen(i))
                {
                    openList.add(i);
                }
                //On Open List, Check if better path
                else
                {
                    //Path Already in list
                    Node otherPath = openList.get( indexContainedOpen(i) );
                    
                    //Better using this new path (i)
                    if ( comparePaths.compare(i, otherPath) < 0 )
                    {
                        otherPath.setParent(i);
                    }
                }
            }
        }
    }
    
    public boolean isOpen(Node source){
        //Walkable
        if ( source.getOccupant() == null || isWalkable(source) )
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
       
    public Node getMostOpenNode(){
        Collections.sort(openList, comparePaths);
        

        int min = openList.size() - 1;
        int tmp = 0;
        int max = min;        
        int maxFamSize = openList.get( min ).getFamilySize();
        
        //Gets the Lowest Index on openList that has an equivalent maximum open path
        min--;
        while ( min > -1)
        {
            //If equivalent number of open spaces
            if ( openList.get(min).getFamilySize() == openList.get(max).getFamilySize() )
            {
                tmp = min;
                min--;
            }
            else
            {
                min = -1;
            }
        }
        min = tmp;
        
        //Random Index
        int r = min + (int)( Math.random() * (max - min) );
        
        return openList.get( r );
    }
    public class compareSize implements Comparator<Node>{   
        public int compare(Node t, Node t1) {
            //Bigger Family Size Comes First
            return t.getFamilySize() - t1.getFamilySize();
        }  
    };
}
