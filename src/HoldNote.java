import bagel.*;

import java.util.ArrayList;

/**
 * Represents a hold note in the game, which can be held for a period of time to score points.
 */
public class HoldNote extends Note {
    /** Image representing the hold note. */
    private final Image image;

    /** Offset used to determine the bottom height of the hold note. */
    private static final int HEIGHT_OFFSET = 82;

    /** Flag indicating if the hold action has started on the note. */
    private boolean holdStarted = false;

    /** Starting y-coordinate of the hold note. */
    private static final int START_Y = 24;

    /**
     * Constructs a new HoldNote with the given direction and appearance frame.
     *
     * @param dir the direction of the hold note ("Left" or "Right").
     * @param appearanceFrame the frame when the note appears.
     */
    public HoldNote(String dir, int appearanceFrame) {
        super(appearanceFrame);
        image = new Image("res/holdNote" + dir + ".png");
        setY(START_Y);
    }

    /**
     * Starts the hold action on this note.
     */
    public void startHold() {
        holdStarted = true;
    }

    /**
     * Evaluates the score based on the hold note's interaction with the given input.
     *
     * @param input the user's input.
     * @param accuracy the accuracy class used to evaluate the score.
     * @param targetHeight the target height to evaluate the score against.
     * @param relevantKey the key related to this note's column.
     * @param notes the list of notes in the current lane.
     * @return the score achieved for this interaction.
     */
    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey, ArrayList<Note> notes) {
        if (isActive() && !holdStarted) {
            int score = accuracy.evaluateScore(getBottomHeight(), targetHeight, input.wasPressed(relevantKey));

            if (score == Accuracy.MISS_SCORE) {
                deactivate();
                return score;
            } else if (score != Accuracy.NOT_SCORED) {
                startHold();
                return score;
            }
        } else if (isActive() && holdStarted) {
            int score = accuracy.evaluateScore(getTopHeight(), targetHeight, input.wasReleased(relevantKey));

            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score;
            } else if (input.wasReleased(relevantKey)) {
                deactivate();
                accuracy.setAccuracy(Accuracy.MISS);
                return Accuracy.MISS_SCORE;
            }
        }

        return 0;
    }

    /**
     * Gets the bottom height of the hold note.
     *
     * @return the bottom height value.
     */
    private int getBottomHeight() { return getY() + HEIGHT_OFFSET;}

    /**
     * Gets the top height of the hold note.
     *
     * @return the top height value.
     */
    private int getTopHeight() {return getY() - HEIGHT_OFFSET;}

    /**
     * Draws the hold note on the screen at the given x-coordinate.
     *
     * @param x the x-coordinate to draw the note at.
     */
    @Override
    public void draw(int x) {
        if (isActive()) {
            image.draw(x, getY());
        }
    }
}
