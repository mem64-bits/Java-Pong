//Libraries needed for window drawing
import javax.swing.*;
import java.awt.*;

// 
public class PongGame extends JPanel {
    // Sets up window dimension constants
    static final int WINDOW_WIDTH = 640, WINDOW_HEIGHT = 480;
    
    // Sets up a Ball object to be created
    private Ball gameBall;
    
    // Sets up player and CPU paddle
    private Paddle playerPaddle, cpuPaddle;
    
    /*Method to update and draw all graphics to screen*/
    public void paintComponent(Graphics g){ 
        //Sets drawing colour to black
        g.setColor(Color.BLACK);

        /*draws black rectangle from (0,0) to (800,600),
        covering whole screen */ 
        g.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);

        //Draws a coloured ball 
        gameBall.paint(g);
         //draw the paddles
        playerPaddle.paint(g);
        cpuPaddle.paint(g);
    }

    /*Method handling games physics , called each frame */

    public void gameLogic(){
        gameBall.moveBall();

        // check for edges to bounce ball off
        gameBall.bounceOffEdges(0, WINDOW_HEIGHT);

        // Moves player and cpu paddles
        playerPaddle.moveTowards(0);
        cpuPaddle.moveTowards(600);
    }
    // Creates constructor for PongGame
    public PongGame(){
        // Instantiates instances of needed game objects
        gameBall = new Ball(300, 200, 3, 3, 3, Color.WHITE, 10);
        playerPaddle = new Paddle(10,200,75,3,Color.BLUE);
        cpuPaddle = new Paddle(610,200,75,3,Color.RED);
    }
}
