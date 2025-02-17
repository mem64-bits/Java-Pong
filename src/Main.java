// Library for making windows
import javax.swing.*;
// utility libraries for Timers, Colors, Input Handling and more
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static boolean transitionTriggered = false; // Flag to track if transition has been triggered

    public static void main(String[] args) {
        // Sets up java swing window object
        JFrame window = new JFrame("Java Pong");

        // Changes window icon to custom icon of choice
        ImageIcon icon = new ImageIcon("icons/ping-pong.png");
        window.setIconImage(icon.getImage());

        // Sets up window exiting
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /* The size of the game will be 480x640, the size of the JFrame needs to be slightly larger
           In computer graphics x is across, y is down */
        window.setSize(650, 495);

        // Instantiates PongGame object
        PongGame game = new PongGame();
        // Adds the extended PongGame class to JFrame
        window.add(game);

        // Shows the window
        window.setVisible(true);

        // Sets up timer to repeat at around 60 fps (60fps is 16.67 ms a second)
        Timer timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PongGame.playerScore == PongGame.WIN_SCORE && !transitionTriggered) {
                    transitionTriggered = true; // Set the flag to true to prevent multiple transitions
                    // stops all background and game sounds
                    game.stopAllSounds();
                    Transitions transitions = new Transitions();
                    window.setVisible(false);
                    window.remove(game);
                    window.add(transitions);
                    window.setVisible(true);
                    // pauses game briefly to give time to the transition
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    transitions.endScreen("You Win");
                    Music gameOverSound = new Music("sfx/SuperMetroidJingle.wav");
                    gameOverSound.playSound();
                }

                if (PongGame.cpuScore == PongGame.WIN_SCORE && !transitionTriggered) {
                    transitionTriggered = true; // Set the flag to true to prevent multiple transitions
                    // stops background sound
                    game.stopBGM();
                    Transitions transitions = new Transitions();
                    window.setVisible(false);
                    window.remove(game);
                    window.add(transitions);
                    window.setVisible(true);
                    // pauses game briefly to give time to the transition
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    transitions.endScreen("Game Over");
                    Music gameOverSound = new Music("sfx/Game Over.wav");
                    gameOverSound.playSound();
                }

                // Updates game logic and physics
                game.gameLogic();

                // Updates the screen 30 times a second
                game.repaint();
            }
        });

        // Starts the timer
        timer.start();
    }
}