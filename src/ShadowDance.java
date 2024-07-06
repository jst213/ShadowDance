import bagel.*;

/**
 * Represents the main game class for the "Shadow Dance" game.
 * Manages the game loop, input handling, and rendering logic.
 * Inspired by Project 1 Solution
 *
 * @author Jeremy Santoso Tanasaleh
 */
public class ShadowDance extends AbstractGame {
    /** Current level of the game. */
    private Level level;

    /** Constants related to game window and visuals. */
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");

    /** Game states and settings. */
    public static boolean started = false;
    public static boolean finished = false;
    public static boolean paused = false;
    public static int speedChanger = 2;
    public static int currFrame = 0;

    /** Fonts and instructions to display on starting screen. */
    private final static int INSTRUCTION_SIZE = 24;
    public final static String FONT_FILE = "res/FSO8BITR.TTF";
    private final Font INSTRUCTION_FONT = new Font(FONT_FILE, INSTRUCTION_SIZE);
    private final Font TITLE_FONT = new Font(FONT_FILE, TITLE_SIZE);
    private final static int TITLE_SIZE = 64;
    private final static int TITLE_X = 220;
    private final static int TITLE_Y = 250;
    private final static String INSTRUCTIONS = "SELECT LEVELS WITH\nNUMBER KEYS\n\n    1       2       3";
    private final static int INS_X_OFFSET = 100;
    private final static int INS_Y_OFFSET = 190;

    /** Constructor initializes the game window. */
    ShadowDance() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * Main entry point for the program.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }

    /**
     * Handles game updates based on player input.
     *
     * @param input The current input from the player.
     */
    @Override
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        if (!started) {
            drawStartingScreen(input);
        } else if (finished) {
            level.drawEndingScreen();
            if (input.wasPressed(Keys.SPACE)) {
                level.resetGame();
            }
        } else {
            if (paused) {
                level.updatePausedState(input);
            } else {
                level.updateGameplay(input);
            }
        }
    }

    /**
     * Draws the starting screen and handles input for level selection.
     *
     * @param input The current input from the player.
     */
    private void drawStartingScreen(Input input) {
        TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
        INSTRUCTION_FONT.drawString(INSTRUCTIONS, TITLE_X + INS_X_OFFSET, TITLE_Y + INS_Y_OFFSET);

        if (input.wasPressed(Keys.NUM_1)) {
            level = new Level1();
        } else if (input.wasPressed(Keys.NUM_2)) {
            level = new Level2();
        } else if (input.wasPressed(Keys.NUM_3)) {
            level = new Level3();
        }
    }

    /** Decreases the speed by 1. */
    public static void slowDown() {
        speedChanger--;
    }

    /** Increases the speed by 1. */
    public static void speedUp() {
        speedChanger++;
    }

    /**
     * Retrieves the current game frame.
     *
     * @return The current frame of the game.
     */
    public static int getCurrFrame() {
        return currFrame;
    }
}
