
//Libraries needed for window drawing
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;

public class PongGame extends JPanel implements MouseMotionListener {
    // Sets up window dimension constants
    static final int WINDOW_WIDTH = 1920, WINDOW_HEIGHT = 1200;

    // Sets up a Ball object to be created
    private Ball gameBall;
    // Sets up player and CPU paddle
    private Paddle playerPaddle, cpuPaddle;

    // Variable to track Y co-ord of mouse
    private int playerMouseY;

    // Keeps track of score for Player and Computer
    private int playerScore;
    private int cpuScore;

    // Score which player or cpu wins
    private final int WIN_SCORE = 3;
    // Tracks the amount of times the ball has bounced
    private int bounceCount;

    // Variable to load Special font
    private Font game_font;
    // Line seperating player and cpu player
    private Line line;

    // Sets up object that plays any given sound effect
    SoundPlayer sfx;
    // Sets up Win / Lose Screen
    private Transitions winLoseScreen;
    // Allows Parent window to be passed into constructor for transition
    private JFrame parentFrame;

    // Flag to track if transition has been triggered
    private boolean transitionTriggered = false;
    // Tracks the game state to stop game logic if score is met
    private boolean PLAYING;

    // Creates constructor for PongGame
    public PongGame(JFrame parentFrame) {
        // inherits JFrame needed to do transition on game window
        this.parentFrame = parentFrame;

        // Instantiates instances of needed game objects
        this.gameBall = new Ball((WINDOW_WIDTH / 2), (WINDOW_HEIGHT / 2), 4, 4, 3, Color.WHITE, 10);
        this.playerPaddle = new Paddle(10, WINDOW_HEIGHT / 2, 65, 6, Color.WHITE);
        this.cpuPaddle = new Paddle(WINDOW_WIDTH - 10 - 12, WINDOW_HEIGHT / 2, 65, 6, Color.WHITE);

        // Draws line seperating player and cpu
        this.line = new Line(WINDOW_WIDTH / 2, 0, WINDOW_HEIGHT, 3, Color.WHITE);

        // important variables to track different values in game
        this.playerMouseY = 0;
        this.playerScore = 0;
        this.cpuScore = 0;
        this.bounceCount = 0;
        // sets play state
        this.PLAYING = true;

        this.sfx = new SoundPlayer();
        // Preloads and caches all sounds to be played later
        sfx.loadSound("Alert", "sfx/Alert.wav");
        sfx.loadSound("Bounce", "sfx/Bounce.wav");
        sfx.loadSound("ScoreUp", "sfx/ScoreUp.wav");
        sfx.loadSound("GameOver", "sfx/GameOver.wav");
        sfx.loadSound("YouWin", "sfx/YouWin.wav");

        // sets up an event listener for mouse on this object
        addMouseMotionListener(this);

        // Exception to handle loading font
        try {
            InputStream fontStream = getClass().getResourceAsStream("fonts/MGS2MENU.TTF");
            game_font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(28f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(game_font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            game_font = new Font("Arial", Font.PLAIN, 20); // Fallback font
        }

        this.winLoseScreen = new Transitions();
        // starts background music
        sfx.play("Alert");
    }

    public int getPlayerScore() {
        return this.playerScore;
    }

    public int getCPUScore() {
        return this.cpuScore;
    }

    public void setPlayState(boolean playState) {
        this.PLAYING = playState;
    }

    public boolean getPlayingState() {
        return this.PLAYING;
    }

    int getWinScore() {
        return WIN_SCORE;
    }

    boolean getTransitionState() {
        return this.transitionTriggered;
    }

    void setTransitionState(boolean state) {
        this.transitionTriggered = state;
    }

    public Transitions getWinLoseScreen() {
        return winLoseScreen;
    }

    public void closeAfterDelay(int millis) {
        Timer closeTimer = new Timer(5000, e -> {
            this.parentFrame.dispose(); // Closes the game window
        });
        closeTimer.setRepeats(false);
        closeTimer.start();
    }

    /* Method handling games physics , called each frame */
    public void gameLogic() {
        if (!getPlayingState()) {
            return;
        }
        gameBall.moveBall();
        // check for edges to bounce ball off
        gameBall.bounceOffEdges(0, WINDOW_HEIGHT);
        // move the paddle towards the y Pos of the mouse
        playerPaddle.moveTowards(playerMouseY);
        // Computer moves ball towards y pos of ball
        cpuPaddle.moveTowards(gameBall.getY());

        if (playerPaddle.checkCollision(gameBall) || cpuPaddle.checkCollision(gameBall)) {
            sfx.play("Bounce");
            gameBall.reverseX();
            bounceCount++;
        }

        // after 5 bounces
        if (bounceCount == 5) {
            // reset counter
            bounceCount = 0;
            gameBall.increaseSpeed();
        }

        // check if cpu has scored
        if (gameBall.getX() < 0) {
            sfx.play("ScoreUp");
            this.cpuScore++;
            reset();
        }
        // check if player has scored
        else if (gameBall.getX() > WINDOW_WIDTH) {
            sfx.play("ScoreUp");
            this.playerScore++;
            reset();
        }

        if (getPlayerScore() == getWinScore() && !transitionTriggered) {
            sfx.stopAll();
            reset();
            setTransitionState(true);
            setPlayState(false);
            // pauses game briefly to give time to the transition
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            sfx.play("YouWin");
            winLoseScreen.endScreen("YOU WIN", this.parentFrame, this);
            closeAfterDelay(5000);
        }

        else if (getCPUScore() == getWinScore() && !transitionTriggered) {
            sfx.stopAll();
            reset();
            setTransitionState(true);
            setPlayState(false);

            // pauses game briefly to give time to the transition
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            // Plays game over music
            sfx.play("GameOver");
            // Displays MGS1 style game over transition
            winLoseScreen.endScreen("GamE OveR", this.parentFrame, this);
            closeAfterDelay(5000);
        }

    }

    /* Method to update and draw all graphics to screen */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable antialiasing for better rendering on scaled displays
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Calculate scaling factors based on design dimensions
        double scaleX = (double) getWidth() / WINDOW_WIDTH;
        double scaleY = (double) getHeight() / WINDOW_HEIGHT;

        // Save the original transform
        AffineTransform oldTransform = g2d.getTransform();

        // Apply scaling transform
        g2d.scale(scaleX, scaleY);

        // Now draw everything using the design resolution coordinates
        // Fill the background with black, for example
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Draw game objects (ball, paddles, line)
        gameBall.paint(g2d);
        playerPaddle.paint(g2d);
        cpuPaddle.paint(g2d);
        line.drawLine(g2d);

        // Draws the score text (adjust position based on design resolution)
        g2d.setColor(Color.GREEN);
        g2d.setFont(game_font);

        // Calculate equal distance from center line
        int scoreOffset = 60; // Distance from center line
        int centerX = WINDOW_WIDTH / 2;

        // Get font metrics to calculate text width for proper centering
        FontMetrics fm = g2d.getFontMetrics();
        String playerScoreStr = Integer.toString(playerScore);
        String cpuScoreStr = Integer.toString(cpuScore);

        // Calculate positions so text is equally spaced from center line
        int playerScoreWidth = fm.stringWidth(playerScoreStr);
        int playerScoreX = centerX - scoreOffset - playerScoreWidth; // Position so right edge is scoreOffset from
                                                                     // center
        int cpuScoreX = centerX + scoreOffset; // Position so left edge is scoreOffset from center

        // Draws score to window
        g2d.drawString(playerScoreStr, playerScoreX, 35);
        g2d.drawString(cpuScoreStr, cpuScoreX, 35);

        // Restore the original transform so other UI components are not affected
        g2d.setTransform(oldTransform);
    }

    // Overides built in methods for MouseEvent
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Scale mouse coordinates to match the design resolution
        double scaleY = (double) getHeight() / WINDOW_HEIGHT;
        playerMouseY = (int) (e.getY() / scaleY);
    }

    public void reset() {
        // Pauses the game for 1 second
        try {
            Thread.sleep(250);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // reset ball attributes
        gameBall.setX(WINDOW_WIDTH / 2);
        gameBall.setY(WINDOW_HEIGHT / 2);
        gameBall.setDx(5);
        gameBall.setDy(5);
        gameBall.setSpeed(3);
        bounceCount = 0;
    }
}
