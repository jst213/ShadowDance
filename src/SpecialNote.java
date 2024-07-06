import bagel.*;

/**
 * Represents a specialized note that provides different behaviors or effects
 * when hit compared to normal notes. This class extends the Note class and serves
 * as a foundation for more specific types of special notes.
 */
public abstract class SpecialNote extends Note {

    /** The image representing the special note. */
    private final Image image;

    /** The y-coordinate at which the note should begin to appear. */
    private final static int STARTING_Y = 100;

    /**
     * Constructs a SpecialNote with the specified direction and appearance frame.
     *
     * @param dir The direction or type of the note's image.
     * @param appearanceFrame The frame at which the note should appear.
     */
    public SpecialNote(String dir, int appearanceFrame) {
        super(appearanceFrame);

        if (dir.equalsIgnoreCase("DoubleScore")) {
            image = new Image("res/note2x.png");
        } else {
            image = new Image("res/note" + dir + ".png");
        }

        setY(STARTING_Y);
    }

    /**
     * Draws the special note on the screen if it is active.
     *
     * @param x The x-coordinate where the note should be drawn.
     */
    @Override
    public void draw(int x) {
        if (isActive()) {
            image.draw(x, getY());
        }
    }
}
