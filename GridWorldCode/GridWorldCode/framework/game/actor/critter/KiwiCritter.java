package game.actor.critter;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Aaron
 */
public class KiwiCritter extends Critter{
    public Actor mainBug;
    public Color storedColor = Color.green;
    public boolean mating = false;
    public boolean fighting = false;
    public boolean negated = false;

    public KiwiCritter(){
        this.setColor(storedColor);
    }
    public boolean hasMainBug(){
        //Does Not Exist
        if (mainBug == null)
        {
            return false;
        }
        else
        {
            //Has been removed from Grid
            if (mainBug.getGrid() == null)
            {
                return false;
            }
            //Has mainBug
            else
            {
                return true;
            }
        }
    }
    public void processSelf(){
        this.setColor(storedColor);
        
        //Updates Removed mainBug
        if (mainBug != null)
        {
            if (mainBug.getGrid() == null)
            {
                mainBug = null;
            }
        }
    }
    
    @Override
    public void act(){
        //Not Skipping act
        if (!negated)
        {  
            if (getGrid() == null)
                return;
            processSelf();
            killFlowers();
            
            ArrayList<Actor> actors = getActors();
            processActors(actors);
            ArrayList<Location> moveLocs = getMoveLocations();
            Location loc = selectMoveLocation(moveLocs);
            
            //Fail safe
            if (getGrid().isValid(loc))
            {
                makeMove(loc);
            }
        }
        //Skipped act this step
        else
        {
            negated = false;
        }
    }

    public void killFlowers(){
        for (Actor a: this.getGrid().getNeighbors(getLocation()))
        {
            if (a instanceof Flower)
            {
                a.removeSelfFromGrid();
            }
        }
    }
    
    /**
     * If there are adjacent KiwiCritter then:
     *      returns those KiwiCritter
     * If there are no adjacent KiwiCritters and this KiwiCritter is chasing a bug then:
     *      returns the chased bug (mainBug)
     * If there are no adjacent KiwiCritters and this KiwiCritter is not chasing a bug then:
     *      returns the closest Bug(s)
     */
    @Override
    public ArrayList<Actor> getActors(){
        //Actors
        ArrayList<Actor> actors = new ArrayList<Actor>();

            //Neighbor Kiwis
            actors = getKiwiChain(actors, this);

            //No Kiwi Neighbors and No Bug being Chased
            if (actors.isEmpty() && !hasMainBug())
            {
                //Gets Closest Bug(s)
                actors = getClosestBugs();
            }
            //No Kiwi Neighbors but Chasing A Bug
            else if (actors.isEmpty() && hasMainBug())
            {
                actors.add(mainBug);
            }


        return actors;
    }

            public ArrayList<Actor> getKiwiChain(ArrayList<Actor> kiwiChain, Actor kiwiBase){
                //Neighbors Of Checking Kiwi
                ArrayList<Actor> neighbors = kiwiBase.getGrid().getNeighbors(kiwiBase.getLocation());

                for (int i = 0; i < neighbors.size(); i++)
                {
                    Actor check = neighbors.get(i);

                    //Checked is a kiwi, not already included, and not this kiwi
                    if (check instanceof KiwiCritter && !kiwiChain.contains(check) && check != this)
                    {
                        //Now Included
                        kiwiChain.add(check);

                        //Continues chain
                        getKiwiChain(kiwiChain, check);
                    }
                }

                return kiwiChain;
            }
            public ArrayList<Actor> getClosestBugs(){
                //Closest Bugs
                ArrayList<Actor> closestBugs = new ArrayList<Actor>();
                
                //All Actors
                ArrayList<Location> all = this.getGrid().getOccupiedLocations();
                
                //All Actors Traversal
                for (int i = 0; i < all.size(); i++)
                {
                    //Traversed Element
                    Location tmpLoc = all.get(i);
                    Actor tmpAct = getGrid().get(tmpLoc);
                    
                    //Is A Bug
                    if (tmpAct instanceof Bug)
                    {
                        closestBugs.add(tmpAct);
                    }
                } 
                
                //If Bugs
                if (!closestBugs.isEmpty())
                {
                        //Sorts by Distance
                        Collections.sort(closestBugs, new DistanceComparator());

                        //Weeds Out Far Bugs
                        int minDist = getDistanceSquared(this, closestBugs.get(0));
                        
                        //Bugs of min distance
                        ArrayList<Actor> tmp = new ArrayList<Actor>();
                        tmp.add(closestBugs.get(0));
                        
                        //Checks if multiple minDist bugs
                        for (int j = 1; j < closestBugs.size(); j++)
                        {
                            //If Equal minDistance
                            if ( getDistanceSquared(this, closestBugs.get(j)) == minDist )
                            {
                                tmp.add(closestBugs.get(j));
                            }
                            //Not minDistance
                            else
                            {
                                //Since Sorted by Distance, Exits For
                                break;
                            }
                        }
                        
                        //Bugs only of minDist
                        closestBugs = tmp;
                }
                //No Bugs
                else
                {
                    
                }
                
                return closestBugs;
            }
                public class DistanceComparator implements Comparator<Actor>{
                    public int compare(Actor a, Actor b)
                    {
                        //Distance
                        return getDistanceSquared(a, b);
                    }
                };
                public int getDistanceSquared(Actor a, Actor b){
                        //x and y relations
                        int xDist = a.getLocation().getCol() - b.getLocation().getCol();
                        int yDist = a.getLocation().getRow() - b.getLocation().getRow();

                        //Distance
                        return (xDist*xDist + yDist*yDist);                
                }
            
            
            
    @Override
    public void processActors(ArrayList<Actor> actors){
        //Bugs or Adj Kiwis
        if (!actors.isEmpty())
        {
            //Adj Kiwis
            if (actors.get(0) instanceof KiwiCritter)
            {
                processKiwis(actors);
                mainBug = null;         //Stops Chase
            }
            //Bugs
            else if (actors.get(0) instanceof Bug)
            {
                processBugs(actors);
                mating = false;
                fighting = false;
            }
        }
        //No Bugs or Adj Kiwis
        else
        {
            
        }
    }
    
            public void processKiwis(ArrayList<Actor> actors){
                ArrayList<KiwiCritter> kiwis = new ArrayList<KiwiCritter>();

                //Converts From Actor to KiwiCritter, Negates All KiwiCritters
                for (int i = 0; i < actors.size(); i++)
                {
                    KiwiCritter k = (KiwiCritter) actors.get(i);
                    k.negated = true;
                    kiwis.add( k );
                }
                
                //Mating
                if (kiwis.size() == 1)
                {
                    mating = true;
                    
                    //Stores Color
                    storedColor = this.getColor();
                    this.setColor(Color.gray);
                    
                    //Happy Kiwis
                    for (KiwiCritter k: kiwis)
                    {
                        k.setColor(Color.gray);
                    }
                    
                    //Baby Kiwi
                    spawnKiwi(kiwis.get(0));
                }
                //Fighting
                else
                {
                    fighting = true;
                    
                    //Stores Color
                    storedColor = this.getColor();
                    this.setColor(Color.red);
                    
                    //Angry Kiwis
                    for (KiwiCritter k: kiwis)
                    {
                        k.setColor(Color.red);
                    }
                    
                    //Kills a kiwi
                    killOffKiwis(kiwis);
                }
            }
                public void spawnKiwi(Actor kiwi){
                    //New Baby kiwi
                    KiwiCritter babyKiwi = new KiwiCritter();
                    babyKiwi.negated = true;
                    babyKiwi.setColor(Color.gray);
                    babyKiwi.storedColor = Color.GREEN;
                    
                    //Empty Neighboring Locations
                    ArrayList<Location> emptyLoc = getGrid().getEmptyAdjacentLocations(getLocation());
                    ArrayList<Location> mateEmptyNeighbor = getGrid().getEmptyAdjacentLocations(kiwi.getLocation());

                    //Exists Empty Neighbor Location
                    if (!emptyLoc.isEmpty())
                    {
                        //Random Location
                        int r = (int)(Math.random() * (emptyLoc.size() - 1));
                        Location spawnLoc = emptyLoc.get(r);

                        //Spawns Kiwi
                        babyKiwi.putSelfInGrid(this.getGrid(), spawnLoc);
                    }
                    //Empty Space Next To Mate
                    else if (!mateEmptyNeighbor.isEmpty())
                    {
                        //Random Location
                        int r = (int)(Math.random() * (mateEmptyNeighbor.size() - 1));
                        Location spawnLoc = mateEmptyNeighbor.get(r);

                        //Spawns Kiwi
                        babyKiwi.putSelfInGrid(this.getGrid(), spawnLoc);
                    }
                    //No Empty Space Around Either Kiwi
                    else
                    {

                    }
                }  
                public void killOffKiwis(ArrayList<KiwiCritter> kiwis){
                    //Kills Off Random Kiwi
                    int r = (int)(Math.random() * (kiwis.size() - 1));
                    kiwis.get(r).removeSelfFromGrid();
                }
            public void processBugs(ArrayList<Actor> actors){
                //No Bug Being Chased
                if ( mainBug == null )
                {
                    //Random Closest Bug
                    int r = (int)(Math.random() * (actors.size() - 1));
                    mainBug = actors.get(r);  
                    
                }
                //Bug Being Chased
                else
                {
                    //Adjacent  (Diagonal = 2, Horizontal/Vertical = 1)
                    if (getDistanceSquared(this, mainBug) <= 2)
                    {
                        //Kills Bug
                        mainBug.removeSelfFromGrid();
                        mainBug = null;
                    }
                    //Not Adjacent
                    else
                    {
                        /*
                        //Random Direction Away From Kiwi
                        int rand45rotation = (int)(Math.random() * 3);
                        
                        switch (rand45rotation)
                        {
                            case 0: rand45rotation = -45;   break;
                            case 1: rand45rotation = 0;     break;
                            case 2: rand45rotation = 45;    break;
                        }
                        
                        int newDir = this.getDirection() + rand45rotation;
                        
                        mainBug.setDirection(newDir);
                        */
                    }
                }
            }

            
    @Override
    public Location selectMoveLocation(ArrayList<Location> locs){
        //If dead Bug, or mate/fought this step
        if (mainBug == null || mating || fighting)
        {
            //Move Randomly
            int n = locs.size();
            if (n == 0){
                return getLocation();
            }
            int r = (int) (Math.random() * n);
            return locs.get(r);
        }
        //Move Towards mainBug
        else
        {
            return getMoveTowardsBug(locs);
        }
    }

                public Location getMoveTowardsBug(ArrayList<Location> locs)
                {
                    //Step
                    int n = 0;

                    //Direction (in degrees) towards Bug
                    int dirTowards = getLocation().getDirectionToward(mainBug.getLocation());

                    //Prime Location Towards Bug
                    Location primeLoc;

                    //Checks for Prime Locations out of valid moves
                    do
                    {
                        primeLoc = getLocation().getAdjacentLocation(dirTowards + 45*n);
                        n++;
                    }while(!locs.contains(primeLoc) && n < locs.size());

                    //If all of getLocations() are blocked
                    if (n == locs.size() - 1)
                    {
                        //Teleports on top of bug
                        hocusPocus();
                        return this.getLocation();
                    }
                    //Location closest to bug
                    else
                    {
                        return primeLoc;
                    }

                }
                /**
                 * Teleports on top of Bug
                 */
                public void hocusPocus()
                {
                    Location tmp = mainBug.getLocation();

                    //Removes Bug
                    mainBug.removeSelfFromGrid();
                    mainBug = null;

                    //Moves Kiwi To Bugs Location
                    this.moveTo(tmp);
                }
}
