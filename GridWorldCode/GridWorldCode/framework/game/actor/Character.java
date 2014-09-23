package game.actor;

import java.awt.Color;

/**
 * The class is for an actor that responds to key presses.
 * This actor can still respond to regular steps as well.
 * @author Aaron and Dan
 */
public class Character extends SuperCritter{ 
    
    /**
     * The action based on steps rather than Key Presses.
     * In general, most characters will not act based on step.
     */
    @Override
    public void act(){
        //Nothing
    }
    /**
     * Characters overload the act method:
     * There is a specific action in response to the KeyPressed.
     * Good implementation is to use a decision structure to switch the
     * keys to respond to and call a specific action that handles this response.
     * (ex: call actionSpace() or actionShoot() for a space bar press).
     * @param e The KeyPressed
     */
    public void act(String e) {
        if (e.equals("A"))
        {
            action();
        }
    }
    
    /**
     * Action based on key press 'A'.
     * Sets to a random color.
     */
    public void action(){
        Color r;
        int n = (int)(Math.random() * 4);

        switch (n)
        {
            case 0: r = Color.BLACK;    break;
            case 1: r = Color.RED;      break;
            case 2: r = Color.BLUE;     break;
            case 3: r = Color.YELLOW;   break;
            case 4: r = Color.ORANGE;   break;
            default: r = Color.GREEN;
        }

        this.setColor(r);
    }
}
