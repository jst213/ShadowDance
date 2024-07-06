import bagel.Input;
import bagel.Keys;

import java.util.ArrayList;

/**
 * Represents a special note in a rhythm game, specifically a "Bomb Note".
 * When activated, a Bomb Note can clear an entire lane of notes.
 */
public class BombNote extends SpecialNote{
    /** Message displayed when the BombNote effect is activated. */
    private static final String MESSAGE = "Lane Clear";

    /**
     * Constructs a new BombNote.
     *
     * @param dir             the direction of the note
     * @param appearanceFrame the frame at which the note appears
     */
    public BombNote(String dir, int appearanceFrame) {
        super(dir, appearanceFrame);
    }

    /**
     * Checks the score based on user input and activates the BombNote's special effect if conditions are met.
     *
     * @param input         the input representing user actions
     * @param accuracy      the accuracy system to determine how well the note was hit
     * @param targetHeight  the intended height for hitting the note
     * @param relevantKey   the key corresponding to this note's lane
     * @param notes         the list of notes present in the game
     * @return always returns 0 for BombNote as it does not contribute to the score directly
     */
    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey, ArrayList<Note> notes) {
        if (isActive()) {
            int effect = accuracy.evaluateEffect(getY(), targetHeight, input.wasPressed(relevantKey), MESSAGE);
            if (effect == Accuracy.EFFECT_ACTIVATED) {
                clearLane(notes);
            }

            if (effect != Accuracy.NOT_SCORED) {
                deactivate();
                return 0;
            }
        }
        return 0;
    }

    /**
     * Clears all active notes in a given lane.
     *
     * @param notes the list of notes in the lane
     */
    private void clearLane(ArrayList<Note> notes) {
        for (Note note : notes) {
            if (note.isActive()) {
                note.deactivate();
            }
        }
    }
}
