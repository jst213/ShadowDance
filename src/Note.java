import bagel.*;

import java.util.ArrayList;

/**
 * Represents an abstract note in the game.
 * Defines the basic behavior and properties of a note, with specific behaviors to be defined by subclasses.
 */
public abstract class Note {
    /** The frame number at which the note should begin to appear. */
    private final int appearanceFrame;

    /** The speed at which the note moves on the screen. */
    private int speed = 2;

    /** The current vertical position (y-coordinate) of the note. */
    private int y;

    /** Indicates whether the note is currently active on the screen. */
    private boolean active = false;

    /** Indicates whether the note has been interacted with (completed) or not. */
    private boolean completed = false;

    /**
     * Constructs a new Note with the specified appearance frame.
     *
     * @param appearanceFrame the frame number on which the note should begin to appear.
     */
    public Note(int appearanceFrame) {
        this.appearanceFrame = appearanceFrame;
    }

    /**
     * Sets the speed of the note.
     *
     * @param speed the speed to set.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Checks if the note is currently active on the screen.
     *
     * @return true if the note is active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Checks if the note has been completed.
     *
     * @return true if completed, false otherwise.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Marks the note as inactive and completed.
     */
    public void deactivate() {
        active = false;
        completed = true;
    }

    /**
     * Updates the note's state based on its current status and the game's frame count.
     */
    public void update() {
        if (active) {
            y += speed;
        }

        if (ShadowDance.getCurrFrame() >= appearanceFrame && !completed) {
            active = true;
        }
    }

    /**
     * Draws the note on the screen at the specified x-coordinate.
     *
     * @param x the x-coordinate where the note should be drawn.
     */
    public abstract void draw(int x);

    /**
     * Checks the score for the note based on its current state and the player's input.
     *
     * @param input         the user's input.
     * @param accuracy      the accuracy evaluator.
     * @param targetHeight  the target height for the note to be pressed.
     * @param relevantKey   the key associated with this note.
     * @param notes         the list of all notes in the lane.
     * @return the score evaluated for this note.
     */
    public abstract int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey, ArrayList<Note> notes);

    /**
     * Sets the y-coordinate of the note.
     *
     * @param y the y-coordinate to set.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Retrieves the y-coordinate of the note.
     *
     * @return the current y-coordinate of the note.
     */
    public int getY() {
        return y;
    }
}
