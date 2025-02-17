//Libraries needed for window drawing
import javax.swing.*;
import java.awt.*;
// For Mouse Input, and position
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
 
public class PongGame extends JPanel implements MouseMotionListener{
// Sets up window dimension constants
    static final int WINDOW_WIDTH = 640, WINDOW_HEIGHT = 480;
// Sets up a Ball object to be created
    private Ball gameBall;
// Sets up player and CPU paddle
    private Paddle playerPaddle, cpuPaddle;
// Variable to track Y co-ord of mouse
    private int playerMouseY;
// Keeps track of score
    public static int playerScore, cpuScore;
// Score which player or cpu wins
    static final int WIN_SCORE = 5;
// Tracks the amount of times the ball has bounces
    private int bounceCount; 
// Variable to load Special font
    private Font game_font;
// Line seperating player and cpu player 
    private Line line;
// Paddle sound
    public Music ballNoise;
//Background Music
    private Music bgm;
// Sound when score increases
    private Music scoreUp;
     
    /*Method handling games physics , called each frame */
    public void gameLogic(){
        gameBall.moveBall();
    // check for edges to bounce ball off
        gameBall.bounceOffEdges(0, WINDOW_HEIGHT);
    // move the paddle towards the y Pos of the mouse 
        playerPaddle.moveTowards(playerMouseY);
    // Computer moves ball towards y pos of ball
        cpuPaddle.moveTowards(gameBall.getY());

        if(playerPaddle.checkCollision(gameBall) || cpuPaddle.checkCollision(gameBall)){
            ballNoise.playSound();
            gameBall.reverseX();
            bounceCount++;
        }

// after 5 bounces
    if (bounceCount == 5){
        // reset counter
        bounceCount = 0;
        gameBall.increaseSpeed();
    }

// check if cpu has scored
    if(gameBall.getX() < 0){
        //player has lost
        cpuScore++;
        scoreUp.playSound();
        gameBall.setBallColour(Color.YELLOW);
        reset();
    }
// check if player has score
    else if(gameBall.getX() > WINDOW_WIDTH){
        playerScore++;
        scoreUp.playSound();
        reset();
    }

    }
    // Creates constructor for PongGame
    public PongGame(){
        // Instantiates instances of needed game objects
        gameBall = new Ball(300, 200, 4, 4, 3, Color.WHITE, 10);
        playerPaddle = new Paddle(10,200,65,5,Color.WHITE);
        cpuPaddle = new Paddle(610,200,65,5,Color.WHITE);   

        // Draws line seperating player and cpu
        line = new Line(WINDOW_WIDTH / 2, 0, WINDOW_HEIGHT, 3, Color.WHITE);

        // Sets up ball sfx
        ballNoise = new Music("sfx/BallNoise.wav");

        // Sets up background music
        bgm = new Music("sfx/Alert.wav");
        bgm.playLoopedSound();

        // Sets up sound that plays when score increases
        scoreUp = new Music("sfx/Coin.wav");

        playerMouseY = 0;
        playerScore = 0; 
        cpuScore = 0;
        bounceCount = 0;

        // sets up an event listener for mouse on this object
        addMouseMotionListener(this);

        // Exception to handle loading font
        try {
            game_font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/MGS2MENU.TTF")).deriveFont(28f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(game_font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            game_font= new Font("Arial", Font.PLAIN, 20); // Fallback font
        }
    }

    /*Method to update and draw all graphics to screen*/
    @Override
    public void paintComponent(Graphics g){ 
        super.paintComponent(g);
        //Sets drawing colour to black
        g.setColor(Color.BLACK);

        /*draws black rectangle from (0,0) to (800,600),
        covering whole screen */ 
        g.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);

        // Draws a coloured ball 
        gameBall.paint(g);
        // Draws the paddles
        playerPaddle.paint(g);
        cpuPaddle.paint(g);
        // Draws line
        line.drawLine(g);      
        // update score
        g.setColor(Color.GREEN);
        g.setFont(game_font);

        // the drawString method needs a String to print, and a location to print it at.
        g.drawString(playerScore +"  "+ cpuScore,255 , 35);
    }

  // Overides built in methods for MouseEvent  
	@Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        playerMouseY = e.getY();
    }

    public void reset(){      
    // Pauses the game for 1 second 
    try{
        Thread.sleep(250);
    }
    catch(Exception e){
        e.printStackTrace();
    }

	// reset ball
    ballNoise.stopSound();
    gameBall.setX(300);
    gameBall.setY(200);
    gameBall.setDx(5);
    gameBall.setDy(5);
    gameBall.setSpeed(3);
    bounceCount = 0;
    }

    public void stopBGM(){
        this.bgm.stopSound();
    }

    public void stopScoreSound(){
        this.scoreUp.stopSound();
    }

    public void stopBallSound(){
        this.ballNoise.stopSound();
    }

    public void stopAllSounds(){
        stopBGM();
        stopBallSound();
        stopScoreSound();
    }
}
