import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.ArrayList;

/**
 * Represents a lane in the game where notes fall down.
 */
public class Lane {
    /** Height of the lane. */
    private static final int HEIGHT = 384;

    /** Target height for scoring. */
    private static final int TARGET_HEIGHT = 657;

    /** Type of the lane, which can be "Left", "Right", etc. */
    private final String type;

    /** Image representing the lane. */
    private final Image image;

    /** List of notes in the lane. */
    private final ArrayList<Note> notes = new ArrayList<>();

    /** Key associated with this lane. */
    private Keys relevantKey;

    /** Location of the lane on screen. */
    private final int location;

    /** Index of the current note to be processed. */
    private int currNote = 0;

    public ArrayList<Note> getNotes() {
        return notes;
    }

    /**
     * Constructs a new lane with the given direction and location.
     *
     * @param dir the direction/type of the lane.
     * @param location the on-screen location of the lane.
     */
    public Lane(String dir, int location) {
        this.type = dir;
        this.location = location;
        image = new Image("res/lane" + dir + ".png");
        switch (dir) {
            case "Left":
                relevantKey = Keys.LEFT;
                break;
            case "Right":
                relevantKey = Keys.RIGHT;
                break;
            case "Up":
                relevantKey = Keys.UP;
                break;
            case "Down":
                relevantKey = Keys.DOWN;
                break;
            case "Special":
                relevantKey = Keys.SPACE;
                break;
        }
    }

    /**
     * Returns the list of notes in this lane.
     *
     * @return the notes in this lane.
     */
    public int getLocation() {
        return location;
    }

    /**
     * Returns the location of the lane on screen.
     *
     * @return the location of the lane.
     */
    public String getType() {
        return type;
    }

    /**
     * Updates all the notes in the lane based on user input.
     *
     * @param input the user's input.
     * @param accuracy the accuracy class used for score evaluation.
     * @return the score achieved for this update.
     */
    public int update(Input input, Accuracy accuracy) {
        draw();

        for (Note note : notes) {
            note.update();
        }

        if (currNote < notes.size()) {
            int score = notes.get(currNote).checkScore(input, accuracy, TARGET_HEIGHT, relevantKey, notes);
            if (notes.get(currNote).isCompleted()) {
                currNote++;
                return score;
            }
            return score;
        }

        return Accuracy.NOT_SCORED;
    }

    /**
     * Adds a new note to the lane.
     *
     * @param n the note to add.
     */
    public void addNote(Note n) {
        notes.add(n);
    }

    /**
     * Checks if all the notes in the lane have been pressed or missed.
     *
     * @return true if all notes are completed, false otherwise.
     */
    public boolean isFinished() {
        for (Note note : notes) {
            if (!note.isCompleted()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Draws the lane and all its notes on the screen.
     */
    public void draw() {
        image.draw(location, HEIGHT);

        for (Note note : notes) {
            note.draw(location);
        }
    }
}
