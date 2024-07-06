import bagel.Input;
import bagel.Keys;

import java.util.ArrayList;

/**
 * Represents a special note in a rhythm game, specifically a "Double Score Note".
 * When activated, a Double Score Note can double the score of subsequent notes for a period.
 */
public class DoubleScoreNote extends SpecialNote {
    /** Time duration (in frames) for which the double score effect lasts. */
    public static final int TIME_EFFECT = 480;

    /** Message displayed when the DoubleScoreNote effect is activated. */
    private static final String MESSAGE = "Double Score";

    /**
     * Constructs a new DoubleScoreNote.
     *
     * @param dir             the direction of the note
     * @param appearanceFrame the frame at which the note appears
     */
    public DoubleScoreNote(String dir, int appearanceFrame) {
        super(dir, appearanceFrame);
    }

    /**
     * Checks the score based on user input and activates the DoubleScoreNote's special effect if conditions are met.
     *
     * @param input         the input representing user actions
     * @param accuracy      the accuracy system to determine how well the note was hit
     * @param targetHeight  the intended height for hitting the note
     * @param relevantKey   the key corresponding to this note's lane
     * @param notes         the list of notes present in the game
     * @return always returns 0 for DoubleScoreNote as it does not contribute to the score directly
     */
    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey, ArrayList<Note> notes) {
        if (isActive()) {
            int effect = accuracy.evaluateEffect(getY(), targetHeight, input.wasPressed(relevantKey), MESSAGE);
            if (effect == Accuracy.EFFECT_ACTIVATED) {
                accuracy.doubleScore();
            }

            if (effect != Accuracy.NOT_SCORED) {
                deactivate();
                return 0;
            }
        }
        return 0;
    }
}
