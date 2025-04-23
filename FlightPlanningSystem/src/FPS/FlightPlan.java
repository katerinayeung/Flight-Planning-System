/* FlightPlan.java
   This class uses the Flight class to display the full flight plan for a given flight.
   It includes the departure and arrival airports, total distance, and each leg of the flight with its details. */
package FPS;

import java.util.List;
import java.util.Map;

public class FlightPlan {

    private Flight flight;

    // Constructor
    public FlightPlan(Flight flight) {
        this.flight = flight;
    }

    // Displays the full formatted flight plan
    public void displayPlan() {
        System.out.println("------------------------------------------------------------");
        System.out.println("Flight Plan for " + flight.getAirplane().getMake() + " " + flight.getAirplane().getModel());
        System.out.println("Departure Airport: " + flight.getStart().getName() + " (" + flight.getStart().getIcao() + ")");
        System.out.println("Arrival Airport: " + flight.getEnd().getName() + " (" + flight.getEnd().getIcao() + ")");
        System.out.println("Total Distance: " + String.format("%.2f", flight.getTotalDistance()) + " km");
        System.out.println("------------------------------------------------------------");

        List<Map<String, Object>> legs = flight.getLegs();
        for (int i = 0; i < legs.size(); i++) {
            Map<String, Object> leg = legs.get(i);
            Airport start = (Airport) leg.get("start");
            Airport end = (Airport) leg.get("end");
            double distance = (double) leg.get("distance");
            double heading = (double) leg.get("heading");
            // Calculate time in hours and minutes
            double timeHours = ((double) leg.get("time")) % 60;
            double timeMinutes = ((double) leg.get("time")) * 60 % 60;
            // prints the leg information
            System.out.println("Leg " + (i + 1) + ":");
            System.out.println("  Departure Airport: " + start.getName() + " (" + start.getIcao() + ")");
            System.out.println("  Communication Frequency: " + String.format("%.2f", start.getCommFrequencies()) + " MHz");
            System.out.println("  Arrival Airport: " + end.getName() + " (" + end.getIcao() + ")");
            System.out.println("  Communication Frequency: " + String.format("%.2f", end.getCommFrequencies()) + " MHz");
            System.out.println("  Distance: " + String.format("%.2f", distance) + " km");
            System.out.println("  Heading: " + String.format("%.2f", heading) + "°");
            System.out.println("  Estimated Time: " + String.format("%.0f", timeHours) + " hours " + String.format("%.0f", timeMinutes) + " minutes");
            System.out.println("------------------------------------------------------------");
        }
    
        double totalFuelRequired = calculateTotalFuelRequired();
        System.out.println("Total Fuel Required: " + String.format("%.2f", totalFuelRequired) + " liters");
        System.out.println("------------------------------------------------------------");
    }

    // Calculate the total fuel required for the flight (helper method)
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