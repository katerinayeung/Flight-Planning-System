package FPS;

public class AirplaneManager extends Airplane {
    private Airplane airplane;

    public AirplaneManager(Airplane airplane) {
        this.airplane = airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void addAirplane(Airplane airplane) {
        // Add airplane to database
    }

    public void removeAirplane(Airplane airplane) {
        // Remove airplane from database
    }

    public void updateAirplane(Airplane airplane) {
        // Update airplane in database
    }
}
