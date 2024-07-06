import bagel.Font;
import bagel.Window;

/**
 * Represents the accuracy of pressing notes in a rhythm game.
 * The accuracy is determined based on how close the note press is to the intended target.
 */
public class Accuracy {
    /** Score for a perfect note hit. */
    private static final int PERFECT_SCORE = 10;

    /** Score for a good note hit. */
    private static final int GOOD_SCORE = 5;

    /** Score for a bad note hit. */
    private static final int BAD_SCORE = -1;

    /** Score when a note is missed. */
    public static final int MISS_SCORE = -5;

    /** Represents a note that hasn't been scored yet. */
    public static final int NOT_SCORED = 0;

    /** Indicates that an effect has been activated. */
    public static final int EFFECT_ACTIVATED = 1;

    /** Indicates that an effect has not been activated. Used for internal calculations. */
    private static final int EFFECT_NOT_ACTIVATED = -1;

    /** String representation for a perfect note hit. */
    private static final String PERFECT = "PERFECT";

    /** String representation for a good note hit. */
    private static final String GOOD = "GOOD";

    /** String representation for a bad note hit. */
    private static final String BAD = "BAD";

    /** String representation for a missed note. */
    public static final String MISS = "MISS";

    /** Pixel radius for determining a perfect note hit. */
    private static final int PERFECT_RADIUS = 15;

    /** Pixel radius for determining a good note hit. */
    private static final int GOOD_RADIUS = 50;

    /** Pixel radius for determining a bad note hit. */
    private static final int BAD_RADIUS = 100;

    /** Pixel radius for determining a missed note. */
    private static final int MISS_RADIUS = 200;

    /** Pixel radius for determining the activation of an effect. */
    private static final int EFFECT_RADIUS = 50;

    /** Font used for rendering the accuracy display. */
    private static final Font ACCURACY_FONT = new Font(ShadowDance.FONT_FILE, 40);

    /** Number of frames to render the accuracy display. */
    private static final int RENDER_FRAMES = 30;

    /** Current accuracy status. */
    private String currAccuracy = null;

    /** Counter for the number of frames rendered. */
    private int frameCount = 0;

    /** Multiplier effect for scoring. */
    private int doubleEffect = 1;

    /** Counter for the number of effects applied. */
    private int effectCount = 0;

    /** Indicates if the double score effect is active. */
    private boolean doubleScore = false;

    /**
     * Sets the current accuracy.
     *
     * @param accuracy the accuracy to set (e.g., "PERFECT", "GOOD", etc.)
     */
    public void setAccuracy(String accuracy) {
        currAccuracy = accuracy;
        frameCount = 0;
    }

    /** Activates double score effect. */
    public void doubleScore() {
        doubleScore = true;
        doubleEffect *= 2;
        effectCount = DoubleScoreNote.TIME_EFFECT;
    }

    /**
     * Evaluates the effect based on height, target height, and other conditions.
     *
     * @param height       current height of the note
     * @param targetHeight target height for the note
     * @param triggered    indicates whether the note was triggered (pressed) or not
     * @param message      the accuracy message to be displayed
     * @return an integer representing the result (e.g., EFFECT_ACTIVATED, NOT_SCORED, etc.)
     */
    public int evaluateEffect(int height, int targetHeight, boolean triggered, String message) {
        int distance = Math.abs(height - targetHeight);
        if (triggered) {
            if (distance <= EFFECT_RADIUS) {
                setAccuracy(message);
                effectCount = 0;
                return EFFECT_ACTIVATED;
            }
        } else if (height >= (Window.getHeight())) {
            return EFFECT_NOT_ACTIVATED;
        }
        return NOT_SCORED;
    }

    /**
     * Evaluates the score based on height, target height, and other conditions.
     *
     * @param height       current height of the note
     * @param targetHeight target height for the note
     * @param triggered    indicates whether the note was triggered (pressed) or not
     * @return the score based on the evaluation
     */
    public int evaluateScore(int height, int targetHeight, boolean triggered) {
        int distance = Math.abs(height - targetHeight);

        if (triggered) {
            if (distance <= PERFECT_RADIUS) {
                setAccuracy(PERFECT);
                return PERFECT_SCORE * doubleEffect;
            } else if (distance <= GOOD_RADIUS) {
                setAccuracy(GOOD);
                return GOOD_SCORE * doubleEffect;
            } else if (distance <= BAD_RADIUS) {
                setAccuracy(BAD);
                return BAD_SCORE * doubleEffect;
            } else if (distance <= MISS_RADIUS) {
                setAccuracy(MISS);
                return MISS_SCORE * doubleEffect;
            }

        } else if (height >= (Window.getHeight())) {
            setAccuracy(MISS);
            return MISS_SCORE * doubleEffect;
        }

        return NOT_SCORED;

    }

    /**
     * Setter method for the current accuracy.
     *
     * @param currAccuracy the accuracy to set
     */
    public void setCurrAccuracy(String currAccuracy) {
        this.currAccuracy = currAccuracy;
    }

    /**
     * Updates the accuracy display and related effects.
     */
    public void update() {
        frameCount++;
        if (currAccuracy != null && frameCount < RENDER_FRAMES) {
            ACCURACY_FONT.drawString(currAccuracy,
                    (double) Window.getWidth() /2 - ACCURACY_FONT.getWidth(currAccuracy)/2,
                    (double) Window.getHeight() /2);
        }

        if (effectCount > 0 && doubleScore) {
            effectCount--;
        } else {
            doubleEffect = 1;
        }
    }
}
