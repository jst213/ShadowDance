import bagel.*;

/**
 * Represents a general game entity with an associated image and position.
 * This abstract class provides a foundational structure for game entities,
 * offering position management and rendering capabilities.
 */
public abstract class Entity {

    /** The visual representation of the entity. */
    private final Image image;

    /** The x-coordinate of the entity's position. */
    private double x;

    /** The y-coordinate of the entity's position. */
    private double y;

    /**
     * Constructs a new Entity with a specified position and image.
     *
     * @param x The initial x-coordinate.
     * @param y The initial y-coordinate.
     * @param imagePath The path to the image file representing the entity.
     */
    public Entity(double x, double y, String imagePath) {
        this.image = new Image(imagePath);
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the entity on the screen.
     * Implementation specifics are determined by the subclasses.
     */
    public abstract void draw();

    /**
     * Updates the state and properties of the entity.
     * Implementation specifics are determined by the subclasses.
     */
    public abstract void update();

    /**
     * Retrieves the x-coordinate of the entity's position.
     *
     * @return The x-coordinate of the entity.
     */
    public double getX() {
        return x;
    }

    /**
     * Retrieves the y-coordinate of the entity's position.
     *
     * @return The y-coordinate of the entity.
     */
    public double getY() {
        return y;
    }

    /**
     * Updates the x-coordinate of the entity's position.
     *
     * @param x The new x-coordinate for the entity.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Updates the y-coordinate of the entity's position.
     *
     * @param y The new y-coordinate for the entity.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Retrieves the image representation of the entity.
     *
     * @return The image of the entity.
     */
    public Image getImage() {
        return image;
    }
}
