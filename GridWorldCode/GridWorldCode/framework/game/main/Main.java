package game.main;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Aaron
 */

public class Main {
    public static void main ( String[] args )
    {        
        boolean askAgain;
        
        //Input loop
        do{
            askAgain = false;
            
            String input = JOptionPane.showInputDialog(new JLabel("Game: (Pacman, Tron, Kiwi)")).toLowerCase();

            //Game to play
            if (input.contains("p")){
                PacmanGame game = new PacmanGame();
                game.displayControls();
            }
            else if (input.contains("t")){
                TronGame game = new TronGame();
                game.displayControls();
            }
            else if (input.contains("k")){
                KiwiSimulation game = new KiwiSimulation();
                game.displayControls();
            }
            //Invalid input
            else{
                askAgain = true;
            }            
        }while(askAgain);
    }
}
