public class Passenger {
    private String name;
    private String destination;
    private String selectedPlane;

    public Passenger(String name, String destination, String selectedPlane) {
        this.name = name;
        this.destination = destination;
        this.selectedPlane = selectedPlane;
    }

    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSelectedPlane() {
        return selectedPlane;
    }

    public void setSelectedPlane(String selectedPlane) {
        this.selectedPlane = selectedPlane;
    }
}