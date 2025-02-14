// For colour
import java.awt.*;
public class Paddle {
    private int height, x, y, speed;
    private Color color;

    // Constant
    static final int PADDLE_WIDTH = 12;

    // Constructor for Paddle Class

    public Paddle(int x, int y, int height,int speed, Color color){
        this.x = x;
        this.y = y;
        this.height = height;
        this.speed = speed;
        this.color = color;
    }

    // Method for drwaing paddles to game window
    void paint(Graphics g){
        // Draw in coloured rectangle
        g.setColor(color);
        g.fillRect(x,y, PADDLE_WIDTH,height);
    }

    // Method for moving paddles
    public void moveTowards(int moveToY){
        // Finds location of the center of the paddle
        int centerY = y + height / 2;
        
        //determine if we need to move more than the speed away from where we are now
        if(Math.abs(centerY - moveToY) > speed){
           // if the center of the paddle is too far down
           if(centerY > moveToY){
            // move the paddle up by the speed
            y -= speed;
            }
            
            // if the center of the paddle is too far up
            if(centerY < moveToY){
                //move the paddle down by speed
                y += speed;
            }

        }
    }
}
