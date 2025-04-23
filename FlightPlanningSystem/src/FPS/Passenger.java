// Passenger.java
// This class stores all of the user information used for a flight plan
// It includes the passenger name, list of destinations, starting airport, and selected plane
package FPS;

import java.util.List;

public class Passenger {
    // Attributes
    private String name;
    private List<String> destinations;
    private String selectedPlane;

    // Constructor
    public Passenger(String name, List<String> destinations, String selectedPlane) {
        this.name = name;
        this.destinations = destinations;
        this.selectedPlane = selectedPlane;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<String> destinations) {
        this.destinations = destinations;
    }

    public String getSelectedPlane() {
        return selectedPlane;
    }

    public void setSelectedPlane(String selectedPlane) {
        this.selectedPlane = selectedPlane;
    }

    // Method to get all arrival airports (destinations with index > 0)
    public List<String> getArrivalAirports() {
        if (destinations == null || destinations.size() <= 1) {
            return List.of(); // Return an empty list if no valid arrivals
        }
        return destinations.subList(1, destinations.size());
    }
}