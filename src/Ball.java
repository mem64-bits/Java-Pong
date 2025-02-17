// Library for handling colour change, and more
import java.awt.*;
import java.util.Random;

/*Class for creating Pong ball and controls 
its properties and behaviours*/

public class Ball {
    // Member variables for controlling ball attributes
    
    /*dy,dx = the rate the ball is moving across 
    the screen, each frame in x,y = (dx,dy)
    Remember calculus = rate of change */

    int x, y, dx, dy, size, speed;
    private Color color;
    // Maximium speed ball can increase to
    static final int MAX_SPEED = 12;

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
            //randomBallColour();
            reverseY();
        }
        // Checks if y is at the top of screen
        else if(y < top){
            //randomBallColour();
            reverseY();
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

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getY(){
        return y;
    }

    public int getSize(){
        return size;
    }
    
    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int speed){
        this.speed = speed; 
    }

    public void setDx(int dx){
        this.dx = dx;
    }

    public void setDy(int dy){
        this.dy = dy;
    }

    public void increaseSpeed(){
        if(speed < MAX_SPEED){
            //increase the speed by one
            speed ++;
            //update cy and cx with the new speed
            dx = (dx / Math.abs(dx)*speed);
            dy = (dy / Math.abs(dy)*speed);
        }
    }

    public void randomBallColour(){
        Random rand = new Random();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);

        /*A formula gotten,that calculates 
        the brightness of a colour*/
        double brightness = 0.299 * r + 0.587 * g + 0.114 * b;
    // Rerandomises r,g,b if random rgb isn't bright enough
        if(brightness < 50){
            r = rand.nextInt(256);
            g = rand.nextInt(256);
            b = rand.nextInt(256);
        }
        
        this.color = new Color(r,g,b);
    }

    public void setBallColour(Color color){
        this.color = color;
    }
}