import bagel.*;

import java.util.ArrayList;

/**
 * Represents a standard note in the game.
 * This type of note has a basic behavior and appearance as opposed to special types of notes.
 */
public class NormalNote extends Note{
    /** The image representing the normal note. */
    private final Image image;

    /** The y-coordinate at which the note should begin to appear. */
    private final static int STARTING_Y = 100;

    /**
     * Constructs a new NormalNote with the specified direction and the frame it should appear on.
     *
     * @param dir             the direction of the note, used to determine the image.
     * @param appearanceFrame the frame number on which the note should begin to appear.
     */
    public NormalNote(String dir, int appearanceFrame) {
        super(appearanceFrame);
        image = new Image("res/note" + dir + ".png");
        setY(STARTING_Y);
    }

    /**
     * Checks the score for this note based on its current state and the player's input.
     *
     * @param input         the user's input to check against.
     * @param accuracy      the accuracy evaluator.
     * @param targetHeight  the target height for the note to be pressed.
     * @param relevantKey   the key associated with this note.
     * @param notes         the list of all notes in the lane.
     * @return the score evaluated for this note.
     */
    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey, ArrayList<Note> notes) {
        if (isActive()) {
            int score = accuracy.evaluateScore(getY(), targetHeight, input.wasPressed(relevantKey));

            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score;
            }
        }
        return 0;
    }

    /**
     * Draws the normal note on the screen if it is active.
     *
     * @param x the x-coordinate where the note should be drawn.
     */
    @Override
    public void draw(int x) {
        if (isActive()) {
            image.draw(x, getY());
        }
    }
}
