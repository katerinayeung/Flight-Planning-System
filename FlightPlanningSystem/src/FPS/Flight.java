package FPS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Flight {
    private Airport start;
    private Airport end;
    private Airplane airplane;
    private List<Airport> layovers;
    private List<Map<String, Object>> legs;
    private double totalDistance;

    // Constructor
    public Flight(Airport start, Airport end, Airplane airplane) {
        this.start = start;
        this.end = end;
        this.airplane = airplane;
        this.layovers = new ArrayList<>();
        this.legs = new ArrayList<>();
    }

    // Calculate total distance between two airports (flat Earth assumption with latitude scaling)
    public double calculateDistance(Airport a, Airport b) {
        final double KM_PER_DEGREE_LATITUDE = 111.0; // Approximate distance in km for 1 degree of latitude
        double avgLatitude = Math.toRadians((a.getLatitude() + b.getLatitude()) / 2.0);

        // Scale longitude difference by the cosine of the average latitude
        double latDiff = (b.getLatitude() - a.getLatitude()) * KM_PER_DEGREE_LATITUDE;
        double lonDiff = (b.getLongitude() - a.getLongitude()) * KM_PER_DEGREE_LATITUDE * Math.cos(avgLatitude);

        // Use the Pythagorean theorem to calculate the distance
        return Math.sqrt((latDiff * latDiff) + (lonDiff * lonDiff));
    }

    // Check if airport supports airplane's fuel type
    public boolean hasCorrectFuel(Airport airport, String airplaneType) {
        if (airplaneType.equalsIgnoreCase("Jet") || airplaneType.equalsIgnoreCase("Turboprop")) {
            return airport.getFuelTypes().contains("JA-a");
        } else {
            return airport.getFuelTypes().contains("AvGas");
        }
    }

    // Determine layovers needed and populate layovers list
    public List<Airport> determineLayovers(List<Airport> allAirports) throws Exception {
        Airport currentAirport = start;
        double remainingDistance = calculateDistance(start, end);
        totalDistance = remainingDistance;

        while (remainingDistance > airplane.calculateRange()) {
            // Get all airports within the airplane's range
            List<Airport> possibleStops = new ArrayList<>();
            for (Airport airport : allAirports) {
                if (!airport.equals(currentAirport) &&
                    hasCorrectFuel(airport, airplane.getType()) &&
                    calculateDistance(currentAirport, airport) <= airplane.calculateRange()) {
                    possibleStops.add(airport);
                }
            }

            if (possibleStops.isEmpty()) {
                throw new Exception("Flight impossible: No refueling airports available.");
            }

            // Select the airport that minimizes the remaining distance to the destination
            Airport bestStop = null;
            double minRemainingDistance = Double.MAX_VALUE;
            for (Airport airport : possibleStops) {
                double distanceToEnd = calculateDistance(airport, end);
                if (distanceToEnd < minRemainingDistance) {
                    minRemainingDistance = distanceToEnd;
                    bestStop = airport;
                }
            }

            // Add the selected airport as a layover
            layovers.add(bestStop);
            currentAirport = bestStop;
            remainingDistance = calculateDistance(currentAirport, end);
        }

        return layovers;
    }

    // Generate flight legs (start → layovers → end)
    public void generateLegs() {
        List<Airport> allStops = new ArrayList<>();
        allStops.add(start);
        allStops.addAll(layovers);
        allStops.add(end);

        for (int i = 0; i < allStops.size() - 1; i++) {
            Airport legStart = allStops.get(i);
            Airport legEnd = allStops.get(i + 1);
            double distance = calculateDistance(legStart, legEnd);
            double heading = calculateHeading(legStart, legEnd);
            double time = (distance / airplane.getCruiseSpeed());

            // Create a leg map
            Map<String, Object> leg = new HashMap<>();
            leg.put("start", legStart);
            leg.put("end", legEnd);
            leg.put("distance", distance);
            leg.put("heading", heading);
            leg.put("time", time);

            legs.add(leg);
        }
    }

    // Calculate heading between two airports (simplified for flat Earth)
    public double calculateHeading(Airport a, Airport b) {
        double latDiff = b.getLatitude() - a.getLatitude();
        double lonDiff = b.getLongitude() - a.getLongitude();
        return Math.toDegrees(Math.atan2(lonDiff, latDiff));
    }

    // Getters
    public List<Map<String, Object>> getLegs() {
        return legs;
    }

    public double getTotalDistance() {
        return totalDistance;
    }
    public Airport getStart() {
        return start;
    }
    public Airplane getAirplane() {
        return airplane;
    }
    public Airport getEnd() {
        return end;
    }
}