/*Manages all Airplane objects. 
 *Provides methods to add, remove, modify, and search for airports in the database.
 */

package FPS;
import java.io.*;
import java.util.*;

public class AirportManager {
    
    private Airports airport;
    private final String DATABASE_FILE = "airport.dat";  // Simulating a simple file-based DB

    // Constructor
    public AirportManager(Airports airport) {
        this.airport = airport;
    }

    // Getter
    public Airports getAirport() {
        return airport;
    }

    // Setter
    public void setAirport(Airports airport) {
        this.airport = airport;
    }

// Add airport to the database
   public void addAirport(String icao, String name, double latitude, double longitude, double commFrequencies, String fuelTypes) {
    // Validate input parameters
    if (icao == null || icao.isEmpty() || icao.length() != 4) {
        System.err.println("Invalid ICAO code. It must be a non-empty string of 4 characters.");
        return;
    }
    if (name == null || name.isEmpty()) {
        System.err.println("Invalid airport name. It must be a non-empty string.");
        return;
    }
    if (latitude < -90 || latitude > 90) {
        System.err.println("Invalid latitude. It must be between -90 and 90.");
        return;
    }
    if (longitude < -180 || longitude > 180) {
        System.err.println("Invalid longitude. It must be between -180 and 180.");
        return;
    }
    if (commFrequencies < 0) {
        System.err.println("Invalid communication frequencies. It must be a non-negative value.");
        return;
    }
    if (fuelTypes == null || fuelTypes.isEmpty()) {
        System.err.println("Invalid fuel types. It must be a non-empty string.");
        return;
    }

    // Create a new Airports object
    Airports newAirport = new Airports(icao, name, latitude, longitude, commFrequencies, fuelTypes);

    // Check if the airport already exists in the index
    if (airportIndex.containsKey(icao.toLowerCase()) || airportIndex.containsKey(name.toLowerCase())) {
        System.err.println("Airport with the same ICAO or name already exists.");
        return;
    }

    // Add the new airport to the index
    airportIndex.put(icao.toLowerCase(), newAirport);
    airportIndex.put(name.toLowerCase(), newAirport);

    // Append the new airport to the database file
    String airportData = String.format("%s,%s,%.6f,%.6f,%.2f,%s%n", icao, name, latitude, longitude, commFrequencies, fuelTypes);
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_FILE, true))) {
        writer.write(airportData);
    } catch (IOException e) {
        System.err.println("Error adding airport to database: " + e.getMessage());
    }
}

// Remove airport from the database
    public void removeAirport(String icao) {
    // Check if the airport exists in the index
    Airports airportToRemove = airportIndex.get(icao.toLowerCase());
    if (airportToRemove == null) {
        System.out.println("Airport with ICAO " + icao + " not found.");
        return;
    }

    // Remove the airport from the index
    airportIndex.remove(icao.toLowerCase());
    airportIndex.remove(airportToRemove.getName().toLowerCase()); // Remove by name as well

    // Write the updated index back to the file
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_FILE))) {
        for (Airports airport : new HashSet<>(airportIndex.values())) { // Avoid duplicates
            String airportData = String.format("%s,%s,%.6f,%.6f,%.2f,%s%n",
                airport.getIcao(),
                airport.getName(),
                airport.getLatitude(),
                airport.getLongitude(),
                airport.getCommFrequencies(),
                airport.getFuelTypes()
            );
            writer.write(airportData);
        }
    } catch (IOException e) {
        System.err.println("Error removing airport: " + e.getMessage());
    }
}

// Modify airport information in the database
        public void modifyAirport(String initialIcao, String icao, String name, double latitude, double longitude, double commFrequencies, String fuelTypes) {
            // Validate input parameters
        if (icao == null || icao.isEmpty() || icao.length() != 4) {
            System.err.println("Invalid ICAO code. It must be a non-empty string of 4 characters.");
            return;
        }
        if (name == null || name.isEmpty()) {
            System.err.println("Invalid airport name. It must be a non-empty string.");
            return;
        }
        if (latitude < -90 || latitude > 90) {
            System.err.println("Invalid latitude. It must be between -90 and 90.");
            return;
        }
        if (longitude < -180 || longitude > 180) {
            System.err.println("Invalid longitude. It must be between -180 and 180.");
            return;
        }
        if (commFrequencies < 0) {
            System.err.println("Invalid communication frequencies. It must be a non-negative value.");
            return;
        }
        if (fuelTypes == null || fuelTypes.isEmpty()) {
            System.err.println("Invalid fuel types. It must be a non-empty string.");
            return;
        }
        File inputFile = new File(DATABASE_FILE);
        StringBuilder fileContent = new StringBuilder();
        boolean found = false;

        String newAirportData = String.format("%s,%s,%.6f,%.6f,%.2f,%s%n", icao, name, latitude, longitude, commFrequencies, fuelTypes);

        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith(initialIcao + ",")) {
                    fileContent.append(newAirportData);  // Replace with new data
                    found = true;
                } else {
                    fileContent.append(line).append("\n");
                }
            }

            if (!found) {
                System.out.println("Airport with ICAO " + initialIcao + " not found.");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_FILE))) {
                writer.write(fileContent.toString());
            }
        } catch (IOException e) {
            System.err.println("Error modifying airport: " + e.getMessage());
        }
    }

// Search for an airport by ICAO or name
    private Map<String, Airports> airportIndex = new HashMap<>();

    public void loadAirportIndex() {
        File inputFile = new File(DATABASE_FILE);
        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] airportData = line.split(",");
                Airports airport = new Airports(
                    airportData[0], // ICAO
                    airportData[1], // Name
                    Double.parseDouble(airportData[2]), // Latitude
                    Double.parseDouble(airportData[3]), // Longitude
                    Double.parseDouble(airportData[4]), // Communication Frequencies
                    airportData[5] // Fuel Types
                );
                airportIndex.put(airportData[0].toLowerCase(), airport); // Index by ICAO
                airportIndex.put(airportData[1].toLowerCase(), airport); // Index by Name
            }
        } catch (IOException e) {
            System.err.println("Error loading airport index: " + e.getMessage());
        }
    }

    public Airports searchAirport(String icao, String name) {
        return airportIndex.getOrDefault(icao.toLowerCase(), airportIndex.get(name.toLowerCase()));
    }
}




