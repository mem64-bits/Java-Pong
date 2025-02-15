// Library for making windows
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main {
    public static void main(String[] args){
        // Sets up java swing window object
        JFrame window  = new JFrame("Java Pong");
        
        //Changes window icon to custom icon of choice
        ImageIcon icon = new ImageIcon("icons/ping-pong.png");
        
        window.setIconImage(icon.getImage());
        // Sets up window exiting
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
        /*The size of the game will be 480x640, the size of the JFrame needs to be slightly larger   
        In computer graphics x is across, y is down*/
        window.setSize(650,495);

        // Instantiates PongGame object
        PongGame game = new PongGame();
        // adds the extended PongGame class to JFrame
        window.add(game);

		// shows the window
        window.setVisible(true);

        // Sets up timer to repeat at around 60 fps (60fps is 16.67 mils a second)
        Timer timer = new Timer (16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){  
                //updates game logic and physics
                game.gameLogic();

                // updates the screen 30 times a second
                game.repaint();
            }
        });

        // starts the timer
        timer.start();
    }
}
