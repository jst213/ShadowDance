import bagel.Font;
import bagel.Input;

import java.util.ArrayList;

/**
 * Represents a level in the game ShadowDance.
 * Provides basic functionalities and attributes that all levels share.
 * Specific level implementations should extend this class.
 */
public abstract class Level {
    /** Width of the game window. */
    private final static int WINDOW_WIDTH = 1024;

    /** Font used for level title. */
    private final Font TITLE_FONT = new Font(ShadowDance.FONT_FILE, TITLE_SIZE);

    /** Size of the title font. */
    private final static int TITLE_SIZE = 64;

    /** Font used for instructions. */
    private final Font INSTRUCTION_FONT = new Font(ShadowDance.FONT_FILE, INSTRUCTION_SIZE);

    /** Size of the instruction font. */
    private final static int INSTRUCTION_SIZE = 24;

    /** Message displayed when level is cleared. */
    private static final String CLEAR_MESSAGE = "CLEAR!";

    /** Message displayed when level is lost. */
    private static final String TRY_AGAIN_MESSAGE = "TRY AGAIN";

    /** Instructions to return to level selection. */
    private final static String RETURN_INSTRUCTIONS = "PRESS SPACE TO RETURN TO LEVEL SELECTION";

    /** Y-coordinate for the return instruction. */
    private final static int RETURN_INSTRUCTION_Y = 500;

    /** Y-coordinate for the win/loss message. */
    private final static int WIN_Y = 300;

    /** List of lanes in the level. */
    public ArrayList<Lane> lanes = new ArrayList<>();

    /** Current score in the level. */
    public int score = 0;

    /** Score required to win the level. */
    private final int target_score;

    /**
     * Constructs a new level with the given target score.
     * Initializes the level and reads the associated CSV file.
     *
     * @param target_score the score required to win the level.
     */
    public Level(int target_score) {
        this.target_score = target_score;
        ShadowDance.started = true;
        readCsv();
    }

    /**
     * Reads the CSV file associated with the level.
     * Implementation should be provided by the specific level subclass.
     */
    public abstract void readCsv();

    /**
     * Resets the game state.
     * Implementation should be provided by the specific level subclass.
     */
    public abstract void resetGame();

    /**
     * Updates the paused state based on user input.
     * Implementation should be provided by the specific level subclass.
     *
     * @param input the user's input.
     */
    public abstract void updatePausedState(Input input);

    /**
     * Updates the gameplay state based on user input.
     * Implementation should be provided by the specific level subclass.
     *
     * @param input the user's input.
     */
    public abstract void updateGameplay(Input input);

    /**
     * Checks if all the lanes in the level are finished.
     *
     * @return true if all lanes are finished, false otherwise.
     */
    public boolean checkFinished() {
        for (Lane lane : lanes) {
            if (!lane.isFinished()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Draws the ending screen with appropriate win/loss message
     * and return instructions.
     */
    public void drawEndingScreen() {
        // Determine win/loss message based on score and current level's target score
        String message = score >= target_score ? CLEAR_MESSAGE : TRY_AGAIN_MESSAGE;
        // Draw the win/loss message
        TITLE_FONT.drawString(message, (WINDOW_WIDTH - TITLE_FONT.getWidth(message)) / 2, WIN_Y);

        // Draw the instruction message
        INSTRUCTION_FONT.drawString(RETURN_INSTRUCTIONS, (WINDOW_WIDTH - INSTRUCTION_FONT.getWidth(RETURN_INSTRUCTIONS)) / 2, RETURN_INSTRUCTION_Y);
    }
}