/**
 * Represents a standard lane in which notes fall down.
 * This is a specific type of lane, extending the general Lane class.
 */
public class NormalLane extends Lane{
    /**
     * Constructs a new NormalLane with the specified direction and location.
     *
     * @param dir      the direction of the lane.
     * @param location the location of the lane.
     */
    public NormalLane(String dir, int location) {
        super(dir, location);
    }
}
