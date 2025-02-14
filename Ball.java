// Library for handling colour change, and more
import java.awt.*;

/*Class for creating Pong ball and controls 
its properties and behaviours*/

public class Ball {

    // Member variables for controlling ball attributes
    
    /*dy,dx = the rate the ball is moving across 
    the screen, each frame in x,y = (dx,dy)
    Remember calculus = rate of change */

    int x, y, dx, dy, size, speed;
    private Color color;

    // Ball Constructor, for initilizing instance of Ball object
    public Ball(int x, int y, int dx, int dy, int speed, Color color, int size){
        this.x = x;
        this.y = y;

        this.dx = dx;
        this.dy = dy;

        this.speed = speed;
        this.color = color;
        this.size = size;
    }

    public void paint(Graphics g){
        // set brush to ball colour defined in the constructor
        g.setColor(color);

        // Paints ball at specifed (x,y) with a width and height of ball size
        g.fillOval(x, y, size, size);
    }

    // Method for moving ball
    public void moveBall(){
        x += dx;
        y += dy;
    }

    //Method for boucing ball of edges so it doesn't leave the screen
    public void bounceOffEdges(int top, int bottom){ 
        // Checks if  Y is at the bottom of screen
        if(y > bottom - size){
            reverseY();
        }
        // Checks if y is at the top of screen
        else if(y < top){
            reverseY();
        }

        if(x < 0){
            reverseX();
        }
        else if(x > 640 - size){
            reverseX();
        }
    }

    // Methods for reversing (x,y) of ball 

    public void reverseX(){
        dx *= -1;
    }

    public void reverseY(){
        dy *= -1;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
    
}
