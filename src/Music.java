import java.io.File; // Needed for file type
import java.io.IOException;// Exception handling for IO  
import javax.sound.sampled.*; // for playing sounds



public class Music {
    //Member variables to be assigned in constructor
    String file_path;
    private File file; 
    private AudioInputStream audioStream;
    private Clip clip;


    public Music(String file_path){

        // Attempts to open and load .wav file to be played later with .playSound()
        try{
            this.file_path = file_path;
            this.file = new File(file_path);
            this.audioStream = AudioSystem.getAudioInputStream(this.file);
            this.clip = AudioSystem.getClip();
            this.clip.open(audioStream);
        }
        // Needed Exception Handling for Audio and file Handling
        catch(UnsupportedAudioFileException | IOException | LineUnavailableException e){
            e.printStackTrace();
        }
        
    }

    public void playSound() {
        if (clip.isRunning()) {
            clip.stop(); // Stop the clip if it is already running
        }
        clip.setFramePosition(0); // Rewind to the beginning
        clip.start(); // Start playing the sound
    }

    public void playLoopedSound(){
        // Plays sound continuously in a loop until otherwise stopped
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // Closes and unloads sound
    public void stopSound(){
        clip.close();
    }
}

    
