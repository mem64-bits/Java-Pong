import javax.sound.sampled.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {
    private final Map<String, Clip> soundCache = new HashMap<>();

    public void loadSound(String name, String path) {
        try {
            InputStream audioSrc = SoundPlayer.class.getResourceAsStream("/" + path);
            if (audioSrc == null) throw new IOException("Sound not found: " + path);
            BufferedInputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            soundCache.put(name, clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(String name) {
        Clip clip = soundCache.get(name);
        if (clip != null) {
            if (clip.isRunning()) clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void loop(String name) {
        Clip clip = soundCache.get(name);
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop(String name) {
        Clip clip = soundCache.get(name);
        if (clip != null) {
            clip.stop();
        }
    }

    public void stopAll() {
        for (Clip clip : soundCache.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }
}
