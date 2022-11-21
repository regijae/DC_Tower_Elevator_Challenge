import exceptions.InvalidRequestException;

import static java.lang.Thread.sleep;

/**
 * Test is the entity we will be using for testing the example and dc_tower simulations
 *
 *
 * @author Marko Regoda
 */
public class Test {

    public static void main(String[] args) throws InvalidRequestException, InterruptedException {
        testSimulationExample();
        testDcTowerManager();
    }

    public static void testSimulationExample() throws InvalidRequestException, InterruptedException {
        System.out.println("*************************************************");
        System.out.println("*         EXAMPLE_SIMULATION_STARTED!           *");
        System.out.println("*************************************************");
        ElevatorManager exampleManager = new ElevatorManager("Example_Manager",10, 3);
        Request requestOne = new Request(3,0, "DOWN");
        Request requestTwo = new Request(5,0, "DOWN");
        Request requestThree = new Request(0,9, "UP");
        Request requestFour = new Request(0,7, "UP");
        Request requestFive = new Request(9,0, "DOWN");
        exampleManager.startManager();
        System.out.println("*************************************************");
        exampleManager.addRequest(requestOne);
        sleep(5000);
        exampleManager.addRequest(requestTwo);
        sleep(2000);
        exampleManager.addRequest(requestThree);
        sleep(6000);
        exampleManager.addRequest(requestFour);
        exampleManager.addRequest(requestFive);
        exampleManager.shutDown();
    }

    public static void testDcTowerManager() throws InvalidRequestException, InterruptedException {
        System.out.println("*************************************************");
        System.out.println("*         DC_TOWER_SIMULATION STARTED           *");
        System.out.println("*************************************************");
        ElevatorManager dcTowerManager = new ElevatorManager("DC_Tower_Elevator_Manager",55,7);
        Request request1 = new Request(0,10, "UP");
        Request request2 = new Request(0,7, "UP");
        Request request3 = new Request(0,30, "UP");
        Request request4 = new Request(0,23, "UP");
        Request request5 = new Request(35,0, "DOWN");
        Request request6 = new Request(51,0, "DOWN");
        Request request7 = new Request(0,6, "UP");
        Request request8 = new Request(21,0, "DOWN");
        Request request9 = new Request(0,27, "UP");
        Request request10 = new Request(55,0, "DOWN");
        Request request11 = new Request(0,17, "UP");
        Request request12 = new Request(7,0, "DOWN");
        dcTowerManager.startManager();
        System.out.println("*************************************************");
        dcTowerManager.addRequest(request1);
        dcTowerManager.addRequest(request2);
        dcTowerManager.addRequest(request3);
        dcTowerManager.addRequest(request4);
        dcTowerManager.addRequest(request5);
        sleep(5000);
        assert(dcTowerManager.getAvailableElevators().size() == 2);
        dcTowerManager.addRequest(request6);
        assert(dcTowerManager.getAvailableElevators().size() == 1);
        dcTowerManager.addRequest(request7);
        dcTowerManager.addRequest(request8);
        sleep(14000);
        dcTowerManager.addRequest(request9);
        sleep(2000);
        dcTowerManager.addRequest(request10);
        dcTowerManager.addRequest(request11);
        dcTowerManager.addRequest(request12);
        sleep(4000);
        dcTowerManager.shutDown();
    }
}
