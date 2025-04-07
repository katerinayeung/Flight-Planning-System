package FPS;

import java.util.List;

public class Passenger {
    private String name;
    private List<String> destinations;
    private String selectedPlane;

    public Passenger(String name, List<String> destinations, String selectedPlane) {
        this.name = name;
        this.destinations = destinations;
        this.selectedPlane = selectedPlane;
    }

    public String getName() {
        return name;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public String getSelectedPlane() {
        return selectedPlane;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDestinations(List<String> destinations) {
        this.destinations = destinations;
    }

    public void setSelectedPlane(String selectedPlane) {
        this.selectedPlane = selectedPlane;
    }
}