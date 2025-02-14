import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener{

    private KeyEvent e;

    public void setKeyEvent(KeyEvent e){
        this.e = e;
    }
    public void getInput(){

        if(e == null){
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W :
                System.out.println("A pressed");
                break;
        
            case KeyEvent.VK_S:
                System.out.println("S pressed");
                break;

            case KeyEvent.VK_UP:
                System.out.println("UP pressed");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("Down pressed");
                break;
        }
        
    }
     // Adds new functionality to bulit in Input handling methods
     
    @Override
    public void keyPressed(KeyEvent e) {
        setKeyEvent(e);
        getInput();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Optionally handle key release events
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Optionally handle key typed events
    }
}
