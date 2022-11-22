import exceptions.InvalidRequestException;
import java.util.ArrayList;
import java.util.Objects;
import static java.lang.Thread.sleep;


/**
 * Elevator is the entity that simulates a running elevator that can service one request at a time (no stops).
 *
 *
 * @author Marko Regoda
 */
public class Elevator implements Runnable {

    private Boolean active;
    private ElevatorState state;
    private final Integer id;
    private final Integer numberOfFloors;
    private Integer currentFloor;
    private Request currentRequest;
    private final ArrayList<Request> requests;

    public Elevator(Integer elevatorName, Integer numberOfFloors, Integer currentFloor) {
        this.active = true;
        this.state = ElevatorState.IDLE;
        this.id = elevatorName;
        this.numberOfFloors = numberOfFloors;
        this.currentFloor = currentFloor;
        this.currentRequest = null;
        this.requests = new ArrayList<>();
    }

    public Boolean getActive() { return active; }

    public ElevatorState getState() { return state; }

    public Integer getId() { return id; }

    public Integer getNumberOfFloors() { return numberOfFloors; }

    public Integer getCurrentFloor() { return currentFloor; }

    public Request getCurrentRequest() { return currentRequest; }

    public ArrayList<Request> getRequests() { return requests; }


    /**
     * Adds a request that is going to be serviced by this elevator instance
     * @param request that is added to the elevators service list
     */
    public void addRequest(Request request) throws InvalidRequestException {
        System.out.println("Elevator_" + id + " ---> ADDING REQUEST: " + request);
        if (request.isRequestValid(numberOfFloors)) {
            requests.add(request);
        } else {
            throw new InvalidRequestException("Invalid request!");
        }
    }

    /**
     * Move elevator towards the origin floor of the currently serviced request
     */
    private void moveToRequestFloor() throws InterruptedException {
        state = ElevatorState.INCOMING;
        String direction = currentFloor < currentRequest.getCurrentFloor() ? "UP" : "DOWN";
        while (!Objects.equals(currentFloor, currentRequest.getCurrentFloor())) {
            System.out.println("Elevator_" + id + " ---> INCOMING  -  location: " + currentFloor + "  destination: "
                    + currentRequest.getCurrentFloor() + "  direction: " + direction);
            moveToNextFloor(direction);
        }
        System.out.println("Elevator_" + id + " ---> INCOMING -> SERVICING - arrived at request location: " + currentRequest);
        moveToRequestDestination();
    }

    /**
     * Move elevator towards destination floor of the currently serviced request
     */
    private void moveToRequestDestination() throws InterruptedException {
        state = ElevatorState.SERVICING;
        while (!Objects.equals(currentFloor, currentRequest.getDestinationFloor())) {
            System.out.println("Elevator_" + id + " ---> SERVICING -  location: " + currentFloor + "  destination: "
                    + currentRequest.getDestinationFloor() + "  direction: " + currentRequest.getDirection());
            moveToNextFloor(currentRequest.getDirection());
        }
        System.out.println("Elevator_" + id + " ---> SERVICING -> IDLE - arrived at request destination: " + currentRequest);
        currentRequest = null;
        state = ElevatorState.IDLE;
    }

    /**
     * Move elevator to neighbouring floor
     * @param direction can be up or down
     */
    private void moveToNextFloor(String direction) throws InterruptedException {
        //////////////////
        sleep(2000);
        //////////////////
        if (direction.equals("UP")) {
            currentFloor++;
        } else if (direction.equals("DOWN")) {
            currentFloor--;
        }
    }

    /**
     * Estimate the number of floors that need to be visited before reaching the request
     * @param request is the reference point
     */
    public Integer estimateArrival(Request request) {
        int toReturn = 0;
        int helpFloor = 0;
        if (requests.size() < 0 && state == ElevatorState.IDLE) {
            return Math.abs(currentFloor - request.getCurrentFloor());
        }
        if (state == ElevatorState.INCOMING) {
            helpFloor = currentRequest.getDestinationFloor();
            toReturn = Math.abs(currentFloor - currentRequest.getCurrentFloor())
                    + Math.abs(currentRequest.getCurrentFloor() - currentRequest.getDestinationFloor())
                    + Math.abs(currentRequest.getDestinationFloor() - request.getCurrentFloor());
        }
        if (state == ElevatorState.SERVICING) {
            helpFloor = currentRequest.getDestinationFloor();
            toReturn = Math.abs(currentFloor - currentRequest.getDestinationFloor());
        }
        for (Request waitingRequests : requests) {
            toReturn += Math.abs(waitingRequests.getCurrentFloor() - helpFloor);
            toReturn += Math.abs(waitingRequests.getCurrentFloor() - waitingRequests.getDestinationFloor());
            helpFloor = waitingRequests.getDestinationFloor();
        }
        return toReturn;
    }

    /**
     * Deactivate the elevator
     */
    public void shutDown() { this.active = false; }


    @Override
    public void run() {
        while (active) {
            try {
                //////////////////
                sleep(1000);
                //////////////////
                if (requests.size() > 0 && state == ElevatorState.IDLE) {
                    currentRequest = requests.get(0);
                    requests.remove(currentRequest);
                    moveToRequestFloor();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
