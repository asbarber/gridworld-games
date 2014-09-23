package game.path;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.ArrayList;

/**
 *
 * @author Aaron
 */
public class Node {
    public Node parent;
    protected String id;
    protected Location loc;
    protected Grid grid;
    protected Object occupant;

    public Node(){
        grid = null;
        loc = null;
        id = null;
        occupant = null;
        parent = null;
    }
    public Node(Grid g, Location l){
        grid = g;
        loc = l;
        id = loc.toString();
        occupant = g.get(l);
        parent = null;
    }
    public Node(Grid g, Location l, Node p){
        grid = g;
        loc = l;  
        id = loc.toString();
        occupant = g.get(l);
        parent = p;
    }
    
    public Grid getGrid(){
        return grid;
    }
    public String getID(){
        return id;
    }
    public Location getLocation(){
        return loc;
    } 
    public int getY(){
        return loc.getRow();
    }    
    public int getX(){
        return loc.getCol();
    }
    public Object getOccupant(){
        return occupant;
    }
    
    /** Ancestry */
    public Node getParent(){
        return parent;
    } 
    public Node getFirstDescendant(){
        Node ancestor = this;
        
        //This Node is Null
        if (ancestor == null)
        {
            return null;
        }
        //Parent is Null
        if (ancestor.getParent() == null)
        {
            return this;
        }
        
        //Keep Ascending Tree
        while (ancestor.getParent().getParent() != null)
        {
            ancestor = ancestor.getParent();
        }
        
        return ancestor;
    }
    public Node getAncestor(){
        Node ancestor = this;
        
        while (ancestor.getParent() != null)
        {
            ancestor = ancestor.getParent();
        }
        
        return ancestor;
    }
    public Node getAncestor(int i){
        Node ancestor = this;
        int count = 0;
        
        while (count < i)
        {
            //Ascends Tree of Descendants
            ancestor = ancestor.getParent();
            count++;
            
            //Can't Reach indicated Ancestor level
            if (ancestor == null)
            {
                return null;
            }
        }
        
        return ancestor;
    }
    public int  getFamilySize(){
        int size = 1;
        Node ancestor = this;
        
        //Keep Ascending Tree until no parent
        while (ancestor.getParent() != null)
        {
            ancestor = ancestor.getParent();
            size++;
        }
        
        return size;
    }
    public void setParent(Node p){
        parent = p;
    }   
    

    public ArrayList<Node> getAdjacentNodes(){
        ArrayList<Node> adjNodes = new ArrayList<Node>();
        ArrayList<Location> adjLocs = grid.getValidAdjacentLocations(loc);
        
        for (Location i: adjLocs)
        {
            adjNodes.add(new Node(grid, i, this));
        }
        
        return adjNodes;        
    }
    public ArrayList<Node> getAdjacentXYNodes(){
        ArrayList<Node> adjNodes = new ArrayList<Node>();

        //Locations are only North, East, South, West
        ArrayList<Location> adjLocs = getAdjacentXYLocations();

        //Converts to Node
        for (Location i: adjLocs)
        {
            adjNodes.add(new Node(getGrid(), i, this));
        }

        return adjNodes;
    }
    public ArrayList<Location> getAdjacentXYLocations(){
        ArrayList<Location> adjLocs = new ArrayList<Location>();

        Location l;
        for (int i = 0; i < 360; i+=90)
        {
            l = getLocation().getAdjacentLocation(i);

            if ( getGrid().isValid(l) )
            {
                adjLocs.add(l);
            }
        }

        return adjLocs;
    }


    public void printNode(){
        System.out.println("Node: Location: " + loc.toString() + ", Parent: " + parent.getLocation().toString());
    }
}
