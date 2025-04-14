package FPS;

import java.util.List;
import java.util.Map;

public class FlightPlan {

    private Flight flight;

    // Constructor
    public FlightPlan(Flight flight) {
        this.flight = flight;
    }

    // Display the full flight plan
    public void displayPlan() {
        System.out.println("------------------------------------------------------------");
        System.out.println("Flight Plan for " + flight.getAirplane().getMake() + " " + flight.getAirplane().getModel());
        System.out.println("Departure Airport: " + flight.getStart().getName() + " (" + flight.getStart().getIcao() + ")");
        System.out.println("Arrival Airport: " + flight.getEnd().getName() + " (" + flight.getEnd().getIcao() + ")");
        System.out.println("Total Distance: " + flight.getTotalDistance() + " km");
        System.out.println("------------------------------------------------------------");

        List<Map<String, Object>> legs = flight.getLegs();
        for (int i = 0; i < legs.size(); i++) {
            Map<String, Object> leg = legs.get(i);
            Airport start = (Airport) leg.get("start");
            Airport end = (Airport) leg.get("end");
            double distance = (double) leg.get("distance");
            double heading = (double) leg.get("heading");
            double time = (double) leg.get("time");

            System.out.println("Leg " + (i + 1) + ":");
            System.out.println("  Departure Airport: " + start.getName() + " (" + start.getIcao() + ")");
            System.out.println("  Communication Frequency: " + start.getCommFrequencies() + " MHz");
            System.out.println("  Arrival Airport: " + end.getName() + " (" + end.getIcao() + ")");
            System.out.println("  Communication Frequency: " + end.getCommFrequencies() + " MHz");
            System.out.println("  Distance: " + distance + " km");
            System.out.println("  Heading: " + heading + "°");
            System.out.println("  Time: " + time + " hours");
            System.out.println("------------------------------------------------------------");
        }

        double totalFuelRequired = calculateTotalFuelRequired();
        System.out.println("Total Fuel Required: " + totalFuelRequired + " liters");
        System.out.println("------------------------------------------------------------");
    }

    // Calculate the total fuel required for the flight
    private double calculateTotalFuelRequired() {
        double totalFuel = 0;
        List<Map<String, Object>> legs = flight.getLegs();
        for (Map<String, Object> leg : legs) {
            double distance = (double) leg.get("distance");
            double fuelBurnRate = flight.getAirplane().getFuelBurnrate();
            double cruiseSpeed = flight.getAirplane().getCruiseSpeed();
            double time = distance / cruiseSpeed;
            totalFuel += time * fuelBurnRate;
        }
        return totalFuel;
    }
}

//old code
/*public class FlightPlan {

    private String departureAirport;
    private List<String> arrivalAirports; // List of arrival airports
    private String departureTime;
    private String arrivalTime;
    private double distance; // in nautical miles
    private double estimatedFlightTime; // in hours
    private double fuelRequired; // in gallons
    private List<Airplane> airplanes; // list of airplanes available for the flight
    private List<Map<String, Object>> legs; // list of legs for the flight plan


    public FlightPlan(String departureAirport, List<String> arrivalAirports, String departureTime, String arrivalTime, double distance, double estimatedFlightTime, double fuelRequired) {
        this.departureAirport = departureAirport;
        this.arrivalAirports = arrivalAirports;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.distance = distance;
        this.estimatedFlightTime = estimatedFlightTime;
        this.fuelRequired = fuelRequired;
    }

    // Getters and Setters for each attribute (optional)
    public String getDepartureAirport() {
        return departureAirport;
    }   

    public List<String> getArrivalAirports() {
        return arrivalAirports;
    }

    public void setArrivalAirports(List<String> arrivalAirports) {
        this.arrivalAirports = arrivalAirports;
    }
} */