/**
 * Represents a special type of lane in which notes fall down.
 * This is a distinct subclass of the general Lane class to handle specific behaviors.
 */
public class SpecialLane extends Lane{
    /**
     * Constructs a new SpecialLane with the specified direction and location.
     *
     * @param dir      the direction of the lane.
     * @param location the location of the lane.
     */
    public SpecialLane(String dir, int location) {
        super(dir, location);
    }
}
