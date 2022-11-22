# DC Tower Elevator Challenge

The DC Tower Elevator Manager is tasked with servicing elevator transport 
requests by assigning them to the right elevator instance. It finds the elevator with 
the fastest estimated response time and assigns the request to it. Furthermore, 
each elevator services one request at a time (no stops).

## Test

To test the DC Tower Elevator Manager implementation simply run the main method of the Test.class
. This will run two tests/simulations, one small example and a bigger simulation representing the DC Tower.

```java
public class Test {

    public static void main(String[] args) throws InvalidRequestException, InterruptedException {
        testSimulationExample();
        testDcTowerManager();
    }
    ...
}
```

## Output
The output of the elevator that is responding to and servicing a request will look like this.
In this particular instance a request ([current floor: 3, destination floor: 0, trip direction: DOWN]) is assigned to elevator 1 that now needs to travel 
from the ground floor to the 3rd floor to pick up potential passengers and to take them to 
the ground floor (destination floor). The information that is displayed in the output includes the current state 
or state transition of the elevator, as well as current floor, destination floor and 
current trip direction. An elevator can have three states: 

IDLE - when the elevator has no pending requests.

INCOMING - elevator is moving to request location.

SERVICING - elevator is servicing a request.


```bash
Elevator_1 ---> ADDING REQUEST: [current floor: 3, destination floor: 0, trip direction: DOWN]
Elevator_1 ---> INCOMING  -  location: 0  destination: 3  direction: UP
Elevator_1 ---> INCOMING  -  location: 1  destination: 3  direction: UP
Elevator_1 ---> INCOMING  -  location: 2  destination: 3  direction: UP
Elevator_1 ---> INCOMING -> SERVICING - arrived at request location: [current floor: 3, destination floor: 0, trip direction: DOWN]
Elevator_1 ---> SERVICING -  location: 3  destination: 0  direction: DOWN
Elevator_1 ---> SERVICING -  location: 2  destination: 0  direction: DOWN
Elevator_1 ---> SERVICING -  location: 1  destination: 0  direction: DOWN
Elevator_1 ---> SERVICING -> IDLE - arrived at request destination: [current floor: 3, destination floor: 0, trip direction: DOWN]
```
