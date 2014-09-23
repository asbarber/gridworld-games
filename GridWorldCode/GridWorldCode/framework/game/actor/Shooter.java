/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.actor;

import game.interactive.InteractiveWorld;
import info.gridworld.grid.Location;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Aaron
 */
public class Shooter extends Character{
    private ArrayList<Bullet> bullets;
    
    public Shooter(){
        bullets = new ArrayList<Bullet>();
    }
    @Override
    public void act(){
        //Does not act without keyPress
    }
    
    @Override
    public void act(String e){
        if (e.equals("W"))
        {
            actionW();
        }
        else if (e.equals("S"))
        {
            actionS();
        }
        else if (e.equals("A"))
        {
            actionA();
        }
        else if (e.equals("D"))
        {
            actionD();
        }
        else if (e.equals("P"))
        {
            actionP();
        }
    }
    
        public void actionW(){
            customMove(getUp());
        }
        public void actionS(){
            customMove(getDown());
        }
        public void actionA(){
            customMove(getLeft());
        }
        public void actionD(){
            customMove(getRight());
        }    

        public void customMove(Location direction){
            turnTowardsMove(direction);
            moveConstrained(direction);
        }    
        
        public void actionP(){
            bullets.add(new Bullet(this));
        }

}
