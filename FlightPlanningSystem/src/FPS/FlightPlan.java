package FPS;

import java.util.*;

public class FlightPlan {

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
}