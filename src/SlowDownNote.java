import bagel.Input;
import bagel.Keys;
import java.util.ArrayList;

/**
 * Represents a specialized note that, when hit, slows down the game.
 * This class extends from SpecialNote and provides unique scoring behavior.
 */
public class SlowDownNote extends SpecialNote {

    /** Message to indicate the nature of this special note. */
    private static final String MESSAGE = "Slow Down";

    /**
     * Constructs a SlowDownNote with the specified direction and appearance frame.
     *
     * @param dir The direction for the note's image (used in parent class).
     * @param appearanceFrame The frame at which the note should appear.
     */
    public SlowDownNote(String dir, int appearanceFrame) {
        super(dir, appearanceFrame);
    }

    /**
     * Evaluates the score of this note based on its state and position.
     * If the note's effect is activated, it will slow down the game.
     *
     * @param input The user input.
     * @param accuracy The accuracy evaluator.
     * @param targetHeight The target y-coordinate for scoring.
     * @param relevantKey The key associated with this note.
     * @param notes The list of all active notes.
     * @return The score earned from the note.
     */
    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey, ArrayList<Note> notes) {
        int score = 0;
        if (isActive()) {
            int effect = accuracy.evaluateEffect(getY(), targetHeight, input.wasPressed(relevantKey), MESSAGE);

            if (effect == Accuracy.EFFECT_ACTIVATED) {
                ShadowDance.slowDown();
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
