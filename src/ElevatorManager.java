import exceptions.InvalidRequestException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static java.util.stream.IntStream.*;


/**
 * Manager is the entity for managing the elevators in a building
 *
 *
 * @author Marko Regoda
 */
public class ElevatorManager {

    private final String name;
    private final Integer numberOfFloors;
    private final ArrayList<Elevator> elevators;
    private ExecutorService elevatorPool;

    public ElevatorManager(String name, Integer numberOfFloors, Integer numberOfElevators) {
        this.name = name;
        this.numberOfFloors = numberOfFloors;
        this.elevators = new ArrayList<>();
        this.createElevatorInstances(numberOfElevators);
    }

    /**
     * Creates a number of elevator instances
     * @param numberOfElevators to create
     */
    private void createElevatorInstances(Integer numberOfElevators) {
        range(0, numberOfElevators).forEach(counter ->
                elevators.add(new Elevator(counter + 1, numberOfFloors, 0))
        );
    }

    /**
     * Start the Elevator Manager
     */
    public void startManager() {
        System.out.println("STARTED " + this.name + "!");
        elevatorPool = Executors.newFixedThreadPool(elevators.size());
        elevators.forEach(elevator -> {
            System.out.println(name + " activating Elevator_" + elevator.getId() + "!");
            elevatorPool.execute(elevator);
        });
    }

    /**
     * Adds request to the elevators service request list
     * @param request to add
     */
    public void addRequest(Request request) throws InvalidRequestException {
        getElevator(request).addRequest(request);
    }

    /**
     * Estimates and returns the elevator that has the fastest response time to the request
     * @param request that needs to be serviced by the elevator
     * @return elevator with the fastest response time to request
     */
    private Elevator getElevator(Request request) {
        Elevator closestElevator = elevators.get(0);
        for (Elevator elevator : elevators) {
            if (elevator.estimateArrival(request) < closestElevator.estimateArrival(request)) {
                closestElevator = elevator;
            }
        }
        return closestElevator;
    }

    /**
     * Returns all available elevators
     * @return list of available elevators
     */
    public ArrayList<Elevator> getAvailableElevators() {
        ArrayList<Elevator> availableElevators = new ArrayList<>();
        elevators.forEach(elevator -> {
            if (elevator.getRequests().size() == 0 && elevator.getState() == ElevatorState.IDLE) {
                availableElevators.add(elevator);
            }
        });
        return availableElevators;
    }

    /**
     * Deactivate the Elevator Manager
     */
    public void shutDown() {
        elevatorPool.shutdown();
        elevators.forEach(Elevator::shutDown);
    }
}
