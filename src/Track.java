import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * Manages background music tracks. This class extends Thread and can be used to play, pause,
 * or stop music tracks.
 */
public class Track extends Thread {

    /** Stream representing the audio input. */
    private AudioInputStream stream;

    /** The Clip object responsible for playing the audio. */
    private Clip clip;

    /**
     * Constructs a new Track with the specified audio file.
     *
     * @param file Path to the audio file to be loaded and played.
     */
    public Track(String file) {
        try {
            stream = AudioSystem.getAudioInputStream(new java.io.File(file));
            clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, stream.getFormat()));
            clip.open(stream);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Pauses the currently playing track.
     */
    public void pause() {
        try {
            clip.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Starts or resumes the audio playback. This method is called when the thread is started.
     */
    @Override
    public void run() {
        try {
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
