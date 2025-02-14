import javax.swing.*;

public class Main {
    public static void main(String[] args){

        // Sets up java swing window object
        JFrame window  = new JFrame("Java Pong");

        // Sets up window exiting
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		//the size of the game will be 480x640, the size of the JFrame needs to be slightly larger
        window.setSize(800,600);

		// shows the window
        window.setVisible(true);
    }
}
