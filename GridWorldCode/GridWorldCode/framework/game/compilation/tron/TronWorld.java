/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.compilation.tron;

import game.interactive.InteractiveWorld;
import game.main.TronGame;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Aaron
 */
public class TronWorld extends InteractiveWorld{
    private TronGame myGame;
    private ArrayList<Tron> tronsLeft = new ArrayList<Tron>();
    
    //Constructor
    public TronWorld(Grid<Actor> g, TronGame aGame){
        super(g); 
        myGame = aGame;
    }
    
    //Turns
    public void step(){
        super.step();
        
        if ( isGameOver() )
        {
            gameOver();
        }
    }
    public void step(String e){
        super.step(e);
        
        if ( isGameOver() )
        {
            gameOver();
        }
    }
    
    //Game Over
    private boolean isGameOver(){
        tronsLeft.clear();
        
        //Any Trons
        for (Location ll: getGrid().getOccupiedLocations())
        {
            //Still Trons
            if (getGrid().get(ll) instanceof Tron)
            {
                tronsLeft.add( (Tron)getGrid().get(ll) );
            }
        }
        
       //One winner or tie 
       if (tronsLeft.size() <= 1)
       {
           return true;
       }
       //Multiple trons left
       else
       {
           return false;
       }
    }
    private void gameOver() {
        //Updates
        repaint();
        
        //Tie!
        if (tronsLeft.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "There was a tie O_o");
            this.stop();
            return;
        }
        
        JOptionPane.showMessageDialog(null, tronsLeft.get(0).id + " is the winner!");
        
        this.stop();
    }

    //KeyPress
    public boolean keyPressed(String description, Location loc){ 
        super.keyPressed(description, loc);
        
        //Reset
        if (description.equals("R"))
        {
            myGame.reset();
        }
        
        return false;
    }       

}
