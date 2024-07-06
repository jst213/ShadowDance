import java.util.ArrayList;
import java.util.Random;

/**
 * Represents an enemy entity in the game. An enemy can collide with certain types of notes,
 * removing them from lanes when there's a collision.
 */
public class Enemy extends Entity {
    /** Speed of the enemy. */
    private final static int ENEMY_SPEED = 1;

    /** Path to the image of the enemy. */
    private final static String IMAGE_PATH = "res/enemy.PNG";

    /** Current direction of movement of the enemy. */
    private int direction;

    /** Distance threshold for a collision between the enemy and a note. */
    private final static int COLLISION_DISTANCE = 104;

    /** Maximum random X-coordinate for initializing the enemy's position. */
    private final static int RANDOM_X = 801;

    /** Maximum random Y-coordinate for initializing the enemy's position. */
    private final static int RANDOM_Y = 401;

    /** Starting position for both X and Y coordinates. */
    private final static int START_X_Y = 100;

    /** Lower limit for X coordinate. */
    private final static int X_LOWER_LIMIT = 100;

    /** Upper limit for X coordinate. */
    private final static int X_UPPER_LIMIT = 900;

    /**
     * Constructs a new Enemy at a random position within specified bounds.
     */
    public Enemy() {
        super(new Random().nextInt(RANDOM_X) + START_X_Y, new Random().nextInt(RANDOM_Y) + START_X_Y, IMAGE_PATH);
        this.direction = new Random().nextInt(2) == 0? -ENEMY_SPEED : ENEMY_SPEED;
    }

    /**
     * Checks for collisions between this enemy and notes in the provided lanes.
     * Removes the notes from the lanes if there's a collision.
     *
     * @param lanes the lanes to check for collisions with
     */
    public void isCollidingWith(ArrayList<Lane> lanes) {
        for (Lane lane : lanes) {
            ArrayList<Note> notesToRemove = new ArrayList<>();
            for (Note note : lane.getNotes()) {
                if (note instanceof NormalNote && note.isActive()) {
                    double dx = getX() - lane.getLocation();
                    double dy = getY() - note.getY();
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    if (distance <= COLLISION_DISTANCE) {
                        notesToRemove.add(note);
                    }
                }
            }
            lane.getNotes().removeAll(notesToRemove);
        }
    }

    /**
     * Draws the enemy on the screen.
     */
    @Override
    public void draw() {
        getImage().draw(getX(), getY());

    }

    /**
     * Updates the state and position of the enemy, and redraws it.
     */
    @Override
    public void update() {

        setX(getX() + direction);

        if (getX() <= X_LOWER_LIMIT || getX() >= X_UPPER_LIMIT) {
            direction = -direction;
        }
        draw();
    }
}
