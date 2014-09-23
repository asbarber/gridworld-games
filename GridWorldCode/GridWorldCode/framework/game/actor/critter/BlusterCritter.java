package game.actor.critter;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Aaron and Dan
 */
public class BlusterCritter extends Critter {
    private int courage;
    
    public BlusterCritter(int c){
        courage = c;
    }

    /**
     * All neighbors within two squares of the BlusterCritter
     * @return Array of actors within two squares
     */
    @Override
    public ArrayList<Actor> getActors(){
        //Array for all neighbors within two spaces
        ArrayList<Actor> overallSet = new ArrayList<Actor>();

        //Immediate Neighbors
        ArrayList<Actor> innerNeighbor = new ArrayList<Actor>();
        innerNeighbor = getGrid().getNeighbors(getLocation());

        //Adds Immediate Neighbors to overall set
        overallSet = innerNeighbor;

            //All Immediate Neighbors Traversed
            for (int i = 0; i < innerNeighbor.size(); i++){

                //Get all neighbors of immediate neighbors
                ArrayList<Actor> outerNeighbor = innerNeighbor.get(i).getGrid().getNeighbors(innerNeighbor.get(i).getLocation());

                //Traverses all outwards neighbors
                for (int j = 0; j < outerNeighbor.size(); j++){

                    //If not duplicate and not the BlusterCritter's location
                    if (!overallSet.contains(outerNeighbor.get(j)) && (getLocation() != outerNeighbor.get(j).getLocation())){

                        //Adds to overall set
                        overallSet.add(outerNeighbor.get(j));
                    }
                }
            }

        //Returns all neighbors within 2 spaces of BlusterCritter
        return overallSet;
    }

    @Override
    public void processActors(ArrayList<Actor> actors){
        int n = actors.size();

        if (n < courage){
            brighten();
            return;
        }
        else{
            darken();
            return;
        }
    }


    /**
     * Changes the critters color to innerNeighbor darker version of it's current color.
     */
    public void darken(){
        double DARKENING_FACTOR = 0.05;
        Color c = getColor();

        int red = (int) (c.getRed() * (1 - DARKENING_FACTOR));
        int green = (int) (c.getGreen() * (1 - DARKENING_FACTOR));
        int blue = (int) (c.getBlue() * (1 - DARKENING_FACTOR));

        setColor(new Color(red, green, blue));
    }

    /**
     * Changes the critters color to innerNeighbor brighter version of it's current color.
     */
    public void brighten(){
        double b = 0.05;
        Color c = getColor();

        int red = (int) (c.getRed()*(1 + b) + 1);
        int green = (int) (c.getGreen()*(1 + b) + 1);
        int blue = (int) (c.getBlue()*(1 + b) + 1);

        if (red > 254)
            red = 255;
        if (green > 254)
            green = 255;
        if (blue > 254)
            blue = 255;
        setColor(new Color(red, green, blue));
    }
}
