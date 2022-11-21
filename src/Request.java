import java.util.Objects;


/**
 * Request is the entity used for representing an elevator trip
 *
 *
 * @author Marko Regoda
 */
public class Request {

    private final Integer currentFloor;
    private final Integer destinationFloor;
    private final String direction;

    public Request(Integer currentFloor, Integer destinationFloor, String direction) {
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
        this.direction = direction;
    }

    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public Integer getDestinationFloor() {
        return destinationFloor;
    }

    public String getDirection() {
        return direction;
    }

    public String toString() {
        return "[" + "current floor: " + currentFloor +
                ", destination floor: " + destinationFloor +
                ", trip direction: " + direction + "]";
    }

    /**
     * Determines if the request is valid
     * @param numberOfFloors number of floors that the request should support
     * @return true if the request is valid (correct direction, number of floors, etc.)
     */
    public boolean isRequestValid(Integer numberOfFloors) {
        return currentFloor <= destinationFloor ? Objects.equals(direction, "UP") : Objects.equals(direction, "DOWN")
                && !Objects.equals(currentFloor, destinationFloor)
                && currentFloor == 0 || destinationFloor == 0
                && currentFloor >= 0  && destinationFloor >= 0
                && destinationFloor <= numberOfFloors && currentFloor <= numberOfFloors;
    }
}
