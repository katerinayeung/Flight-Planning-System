/*Manages all Airplane objects. 
 *Provides methods to add, remove, modify, and search for airports in the database.
 */

package FPS;
import java.io.*;
import java.util.*;

public class AirportManager {
    
    private Airport airport;

    // Constructor
    public AirportManager(Airport airport) {
        this.airport = airport;
    }

    // Getter
    public Airport getAirport(int index) {
        String filePath = System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/airports.dat";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentIndex = 0;

            // Read the file line by line
            while ((line = br.readLine()) != null) {
                if (currentIndex == index) {
                    String[] fields = line.split(",");
                    if (fields.length == 6) {
                        return new Airport(fields[0], fields[1], Double.parseDouble(fields[2]), Double.parseDouble(fields[3]), Double.parseDouble(fields[4]), fields[5]);
                    }
                }
                currentIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Return null if no airport is found at the specified index
    }

    // Setter
    public void setAirport(Airport airport) {
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
public void removeAirport(int index) {
    String filePath = System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/airports.dat";
    List<String> airports = new ArrayList<>();

    // Read all lines into a list
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            airports.add(line);
        }
    } catch (IOException e) {
        e.printStackTrace();
        return;
    }

    // Check if the index is valid
    if (index < 0 || index >= airports.size()) {
        System.out.println("Invalid index. No airport removed.");
        return;
    }

    // Remove the airport at the specified index
    if (index < airports.size() - 1) {
        // Move the last airport to the removed airport's position
        String lastAirport = airports.remove(airports.size() - 1);
        airports.set(index, lastAirport);
    } else {
        // If it's the last airport, just remove it
        airports.remove(index);
    }

    // Rewrite the file with the updated list
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
        for (String airport : airports) {
            bw.write(airport);
            bw.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    System.out.println("Airport removed and list updated successfully.");
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
        return -1; // Return -1 if no match is found
    }
    // Display all airports in the database
    public void displayAllAirports() {
        String filePath = System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/airports.dat";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Print table header with extended space for the "Name" column
            System.out.println("List of Airports:");
            System.out.println("------------------------------------------------------------");
            System.out.printf("%-10s %-30s %-10s %-10s %-15s %-10s%n", "ICAO", "Name", "Latitude", "Longitude", "Comm Freq", "Fuel Types");
            System.out.println("------------------------------------------------------------");

            // Read and display each line
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Remove leading and trailing whitespace
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }

                String[] fields = line.split(",");
                if (fields.length == 6) {
                    String icao = fields[0];
                    String name = fields[1].replace("International", "Intl"); // Abbreviate "International"
                    double latitude = Double.parseDouble(fields[2]);
                    double longitude = Double.parseDouble(fields[3]);
                    double commFrequencies = Double.parseDouble(fields[4]);
                    String fuelTypes = fields[5];

                    // Split the name into multiple lines if necessary
                    List<String> nameLines = splitNameIntoLines(name, 30);

                    // Display the first line with the rest of the data
                    System.out.printf("%-10s %-30s %-10.4f %-10.4f %-15.2f %-10s%n", icao, nameLines.get(0), latitude, longitude, commFrequencies, fuelTypes);

                    // Display additional lines for the name, if any
                    for (int i = 1; i < nameLines.size(); i++) {
                        System.out.printf("%-10s %-30s%n", "", nameLines.get(i));
                    }
                }
            }
            System.out.println("------------------------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Return a list of all airports in the database
    public List<Airport> getAllAirports() {
        List<Airport> airports = new ArrayList<>();
        String filePath = System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/airports.dat";
    
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
    
            // Read each line and create Airport objects
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 6) {
                    Airport airport = new Airport(fields[0], fields[1], Double.parseDouble(fields[2]), Double.parseDouble(fields[3]), Double.parseDouble(fields[4]), fields[5]);
                    airports.add(airport);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return airports;
    }
    // Display a specific airport by index
    public void displayAirport(int index) {
        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/airports.dat"))) {
            String line;
            int currentIndex = 0;

            // Print table header with extended space for the "Name" column
            System.out.printf("%-10s %-30s %-10s %-10s %-15s %-10s%n", "ICAO", "Name", "Latitude", "Longitude", "Comm Freq", "Fuel Types");
            System.out.println("------------------------------------------------------------");

            // Read the file line by line
            while ((line = br.readLine()) != null) {
                if (currentIndex == index) {
                    line = line.trim(); // Remove leading and trailing whitespace
                    if (line.isEmpty()) {
                        System.out.println("No airport found at the specified index.");
                        return;
                    }

                    String[] fields = line.split(",");
                    if (fields.length == 6) {
                        String icao = fields[0];
                        String name = fields[1].replace("International", "Intl"); // Abbreviate "International"
                        double latitude = Double.parseDouble(fields[2]);
                        double longitude = Double.parseDouble(fields[3]);
                        double commFrequencies = Double.parseDouble(fields[4]);
                        String fuelTypes = fields[5];

                        // Split the name into multiple lines if necessary
                        List<String> nameLines = splitNameIntoLines(name, 30);

                        // Display the first line with the rest of the data
                        System.out.printf("%-10s %-30s %-10.4f %-10.4f %-15.2f %-10s%n", icao, nameLines.get(0), latitude, longitude, commFrequencies, fuelTypes);

                        // Display additional lines for the name, if any
                        for (int i = 1; i < nameLines.size(); i++) {
                            System.out.printf("%-10s %-30s%n", "", nameLines.get(i));
                        }
                    } else {
                        System.out.println("Invalid data format at the specified index.");
                    }
                    return; // Exit after displaying the airport
                }
                currentIndex++;
            }

            // If the index is not found
            System.out.println("No airport found at the specified index.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Helper method to split a name into multiple lines without splitting words
    private List<String> splitNameIntoLines(String name, int maxLength) {
        List<String> lines = new ArrayList<>();
        String[] words = name.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (currentLine.length() + word.length() + 1 > maxLength) {
                // Add the current line to the list and start a new line
                lines.add(currentLine.toString().trim());
                currentLine = new StringBuilder();
            }
            currentLine.append(word).append(" ");
        }

        // Add the last line to the list
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString().trim());
        }

        return lines;
    }
}






