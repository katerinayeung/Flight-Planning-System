/*Manages all Airplane objects. 
 *Provides methods to add, remove, modify, and search for airports in the database.
 */

package FPS;
import java.io.*;
import java.util.*;

public class AirportManager {
    
    private Airports airport;
    private final String DATABASE_FILE = "airports.dat";  // Simulating a simple file-based DB

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
            
    // Add airport to database (e.g., write to file)
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/airports.dat", true))) {
            writer.newLine();
            writer.write(icao + "," + name + "," + latitude + "," + longitude + "," + commFrequencies + "," + fuelTypes);
        } catch (IOException e) {
            e.printStackTrace();
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
        public void modifyAirport(String icao, String name, double latitude, double longitude, double commFrequencies, String fuelTypes, int index) {
            String filePath = System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/airports.dat";
        StringBuilder updatedContent = new StringBuilder();
    
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentIndex = 0;
    
            // Read the file line by line
            while ((line = br.readLine()) != null) {
                if (currentIndex == index) {
                    // Modify the specific line
                    line = icao + "," + name + "," + latitude + "," + longitude + "," + commFrequencies + "," + fuelTypes;
                }
                updatedContent.append(line).append(System.lineSeparator());
                currentIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    
        // Write the updated content back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(updatedContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

            // Validate input parameters
        
        
        
        
        
        
        


    }

// Search for an airport by ICAO or name
    public int searchAirport(String icao) {
        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/airports.dat"))) {
            String line;
            int index = 0;

            // Iterate through the file line by line
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 6) {
                    String currentIcao = fields[0];
                    if (currentIcao.equalsIgnoreCase(icao)) {
                        return index; // Return the index if a match is found
                    }
                }
                index++; // Increment the index for each line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nAirport not found in the database.");
        return -1; // Return -1 if no match is found
    }
}





