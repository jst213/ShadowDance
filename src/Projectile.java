import bagel.*;
import java.util.ArrayList;

/**
 * Represents a projectile in the game that moves towards a target.
 */
public class Projectile extends Entity {
    /** The direction component along the x-axis. */
    private final double directionX;

    /** The direction component along the y-axis. */
    private final double directionY;

    /** The path to the image that represents the projectile. */
    private final static String IMAGE_PATH = "res/arrow.png";

    /** The speed at which the projectile moves. */
    private final static int PROJECTILE_SPEED = 6;

    /** The distance at which a collision is detected. */
    private final static int COLLISION_DISTANCE = 62;

    /** Drawing options for rotating the projectile. */
    private final DrawOptions option;

    /**
     * Constructs a new Projectile aimed at a specific target enemy.
     *
     * @param x The x-coordinate of the starting position.
     * @param y The y-coordinate of the starting position.
     * @param targetEnemy The enemy entity the projectile is aimed at.
     */
    public Projectile(double x, double y, Enemy targetEnemy) {
        super(x, y, IMAGE_PATH);

        double deltaX = targetEnemy.getX() - x;
        double deltaY = targetEnemy.getY() - y;
        double magnitude = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        double rotationAngle = Math.atan2(deltaY, deltaX);
        option = new DrawOptions().setRotation(rotationAngle);

        this.directionX = deltaX / magnitude;
        this.directionY = deltaY / magnitude;
    }

    /**
     * Draws the projectile on the screen.
     */
    @Override
    public void draw() {
        getImage().draw(getX(), getY(), option);
    }

    /**
     * Updates the position of the projectile based on its direction and speed.
     * Also draws the updated projectile on the screen.
     */
    @Override
    public void update() {
        setX(getX() + directionX * PROJECTILE_SPEED);
        setY(getY() + directionY * PROJECTILE_SPEED);
        draw();
    }

    /**
     * Determines if the projectile is colliding with another entity.
     *
     * @param otherEntity The other entity to check for collision.
     * @return true if colliding, false otherwise.
     */
    public boolean isCollidingWith(Entity otherEntity) {
        double dx = otherEntity.getX() - this.getX();
        double dy = otherEntity.getY() - this.getY();
        double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

        return distance <= COLLISION_DISTANCE;
    }

    /**
     * Checks if the projectile is off the screen boundaries.
     *
     * @return true if the projectile is off the screen, false otherwise.
     */
    public boolean isOffScreen() {
        return getX() < 0 || getX() > Window.getWidth() || getY() < 0 || getY() > Window.getHeight();
    }

    /**
     * Determines if the projectile is colliding with any enemies from the given list.
     *
     * @param enemies A list of enemies to check for collisions.
     * @return true if colliding with any enemy, false otherwise.
     */
    public boolean isCollidingWithEnemies(ArrayList<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (isCollidingWith(enemy)) {
                return true;
            }
        }
        return false;
    }
}
