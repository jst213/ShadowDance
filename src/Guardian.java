import java.util.ArrayList;

/**
 * Represents a guardian entity in the game.
 * The guardian has the ability to detect the closest enemy and fire projectiles at it.
 */
public class Guardian extends Entity{
    /** X-coordinate of the guardian's position. */
    private final static double GUARDIAN_X = 800;

    /** Y-coordinate of the guardian's position. */
    private final static double GUARDIAN_Y = 600;

    /** Path to the image of the guardian. */
    private final static String IMAGE_PATH = "res/guardian.png";

    /**
     * Constructs a new Guardian at a predefined position.
     */
    public Guardian() {
        super(GUARDIAN_X, GUARDIAN_Y, IMAGE_PATH);
    }

    /**
     * Finds the closest enemy entity to this guardian from a list of enemies.
     *
     * @param enemies the list of enemies to search from
     * @return the closest Enemy object, or null if no enemies are provided
     */
    public Enemy findClosestEnemy(ArrayList<Enemy> enemies) {
        Enemy closestEnemy = null;
        double minDistance = Double.MAX_VALUE;
        for (Enemy enemy : enemies) {
            double dx = enemy.getX() - this.getX();
            double dy = enemy.getY() - this.getY();
            double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            if (distance < minDistance) {
                closestEnemy = enemy;
                minDistance = distance;
            }
        }
        return closestEnemy;
    }

    /**
     * Fires a projectile at the closest enemy.
     *
     * @param enemies the list of enemies
     * @return a new Projectile aimed at the closest enemy, or null if no enemies are found
     */
    public Projectile fireAtClosestEnemy(ArrayList<Enemy> enemies) {
        Enemy closestEnemy = findClosestEnemy(enemies);
        if (closestEnemy != null) {
            return new Projectile(GUARDIAN_X, GUARDIAN_Y, closestEnemy);

        }
        return null;
    }

    /**
     * Draws the guardian on the screen.
     */
    @Override
    public void draw() {
        getImage().draw(getX(), getY());
    }

    /**
     * Updates the state of the guardian.
     * This method is currently empty but can be populated with logic if needed in the future.
     */
    @Override
    public void update() {

    }

}
