import bagel.Input;
import bagel.Keys;
import java.util.ArrayList;

/**
 * Represents a specialized note that speeds up the game when hit.
 * This class extends the SpecialNote class.
 */
public class SpeedUpNote extends SpecialNote {

    /** Message to display or handle when the note is hit. */
    private static final String MESSAGE = "Speed Up";

    /**
     * Constructs a SpeedUpNote with the specified direction and appearance frame.
     *
     * @param dir The direction or type of the note's image.
     * @param appearanceFrame The frame at which the note should appear.
     */
    public SpeedUpNote(String dir, int appearanceFrame) {
        super(dir, appearanceFrame);
    }

    /**
     * Evaluates the score of hitting the note based on its position, the target height,
     * and the key pressed by the player.
     *
     * @param input The game's input object to handle user input.
     * @param accuracy The accuracy object to evaluate the note's hit.
     * @param targetHeight The target height for hitting the note.
     * @param relevantKey The key that corresponds to the note.
     * @param notes The list of all notes in the current game level.
     * @return The score awarded for hitting or missing the note.
     */
    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey, ArrayList<Note> notes) {
        int score = 0;
        if (isActive()) {
            int effect = accuracy.evaluateEffect(getY(), targetHeight, input.wasPressed(relevantKey), MESSAGE);
            if (effect == Accuracy.EFFECT_ACTIVATED) {
                ShadowDance.speedUp();
                score = 15;
            }

            if (effect != Accuracy.NOT_SCORED) {
                deactivate();
                return score;
            }
        }
        return 0;
    }
}
