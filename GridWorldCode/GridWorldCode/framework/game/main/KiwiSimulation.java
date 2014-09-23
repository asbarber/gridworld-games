package game.main;

import game.actor.critter.BlusterCritter;
import game.actor.critter.CrabCritter;
import game.actor.critter.RockHound;
import game.actor.critter.KiwiCritter;
import game.actor.critter.ChameleonCritter;
import game.actor.critter.QuickCrab;
import game.actor.bug.ASBBug;
import game.actor.bug.DancingBug;
import game.actor.bug.SpiralBug;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * 
 * @author Aaron
 */
public class KiwiSimulation
{
    private ActorWorld world;
    private Timer clock;
    private boolean running;
    
    public KiwiSimulation(){
        setup();
    }
    
    public final void setup(){        
        world = new ActorWorld(new BoundedGrid(20, 20));    
        initActors();
        askSimType();
        world.setNoGridLines();
        world.show();
        
        clock = new Timer(2000, new Tick());
        clock.stop();
        running = false;        
    }    
    public void initActors(){        
        world.add(new Location(12, 10), new KiwiCritter());
        world.add(new Location(4, 17), new KiwiCritter());         
        
        world.add(new Location(5, 5), new Bug());
        world.add(new Location(9, 0), new Bug());        
    }
    public void initAdditionalActors(){
        ASBBug a = new ASBBug(5);
        DancingBug b = new DancingBug();
        SpiralBug c = new SpiralBug(3);
        BlusterCritter d = new BlusterCritter(4);
        ChameleonCritter e = new ChameleonCritter();
        CrabCritter f = new CrabCritter();
        QuickCrab g = new QuickCrab();
        RockHound h = new RockHound();
        
        a.setColor(Color.yellow);
        b.setColor(Color.blue);
        c.setColor(Color.gray);
        d.setColor(Color.blue);
        e.setColor(Color.MAGENTA);
        f.setColor(Color.orange);
        g.setColor(Color.orange);
        h.setColor(Color.yellow);
        
        world.add(world.getRandomEmptyLocation(), a);
        world.add(world.getRandomEmptyLocation(), b);
        world.add(world.getRandomEmptyLocation(), c);
        world.add(world.getRandomEmptyLocation(), d);
        world.add(world.getRandomEmptyLocation(), e);
        world.add(world.getRandomEmptyLocation(), f);
        world.add(world.getRandomEmptyLocation(), g);
        world.add(world.getRandomEmptyLocation(), h);
        world.add(world.getRandomEmptyLocation(), new Rock());
        world.add(world.getRandomEmptyLocation(), new Rock());
        world.add(world.getRandomEmptyLocation(), new Rock());
        
        world.repaint();
    }
    
    public boolean isRunning(){
        return running;
    }
    public void start(){
        world.run();
        running = true;
        clock.start();
    }
    public void stop(){
        world.stop();
        running = false;
        clock.stop();
    }
    public void close(){
        world.close();
    }
    public void reset(){
        world.close();
        setup();
    } 
    
    public void displayControls(){
        String message = "Use the step button to take the simulation one 'step' at a time!\n";
        message += "Use the run button while adjusting the speed slider to speed through the simulation!\n\n";
        message += "While the simulation is not running, you may click on a location and add a creature or edit an existing one.\n";
        message += "Click the world button at the top of the display to set the grid type to an unbounded grid--a grid without limits!\n";
        
        JOptionPane.showMessageDialog(null, message);
    } 
    private void askSimType(){
        String message = "Do you want to include other critters and bugs? <Y/N> \n "
                        +"(As opposed to the classy Kiwi-only simulation)\n";


        int input = JOptionPane.showConfirmDialog(null, message, "Extra Creatures?", JOptionPane.YES_NO_OPTION);

        //Yes
        if (input == 0)
        {
            initAdditionalActors();
        }
    }
    
    private class Tick implements ActionListener{

        /** Randomly spawns a new Bug */
        public void actionPerformed(ActionEvent ae) {
                //Random direction
                Bug spawned = new Bug();
                spawned.setDirection( 45 * ((int)(Math.random() * 9)) );

                //Place in World
                spawned.putSelfInGrid( world.getGrid(), world.getRandomEmptyLocation() );
                world.repaint();                
        }
        
    };
}