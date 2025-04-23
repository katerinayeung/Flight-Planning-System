/*  AirplaneManager.java
    This class governs all methods regarding manipulating airplanes in the database 
   this includes adding, removing, modifying, displaying, and searching for airplanes */
package FPS;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AirplaneManager extends Airplane {
    private Airplane airplane;

    public AirplaneManager(Airplane airplane) {
        this.airplane = airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }
    // returns an airplane object for use by seaching for it based on its index in the database
    public Airplane getAirplane(int index) {
        String filePath = System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/Airplanes.dat";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentIndex = 0;

            // Read the file line by line
            while ((line = br.readLine()) != null) {
                if (currentIndex == index) {
                    String[] fields = line.split(",");
                    if (fields.length == 6) {
                        return new Airplane(fields[0], fields[1], (fields[2]), Double.parseDouble(fields[3]), Double.parseDouble(fields[4]), Double.parseDouble(fields[5]));
                    }
                }
                currentIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Return null if no airplane is found at the specified index
    }
    // Adds a new airplane inot the database using the parameters provided by the user
    public void addAirplane(String make, String model, String type, double fuelCapacity, double cruiseSpeed, double fuelBurnrate) {
        String filePath = System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/Airplanes.dat";
        List<String> airplanes = new ArrayList<>();
    
        // Read all lines into a list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Remove leading and trailing whitespace
                if (line.isEmpty()) {
                    continue; // Skip empty lines while reading
                }
                airplanes.add(line); // Add the existing line to the list
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    
        // Add the new airplane to the list
        airplanes.add(make + "," + model + "," + type + "," + fuelCapacity + "," + cruiseSpeed + "," + fuelBurnrate);
    
        // Remove any remaining empty lines from the list
        airplanes.removeIf(String::isEmpty);
    
        // Rewrite the file with the updated list
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String airplane : airplanes) {
                bw.write(airplane);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        System.out.println("Airplane added successfully!");
    }
    // Removes an airplane from the database using its index
   public void removeAirplane(int index) {
    String filePath = System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/Airplanes.dat";
    List<String> airplanes = new ArrayList<>();

    // Read all lines into a list
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            airplanes.add(line);
        }
    } catch (IOException e) {
        e.printStackTrace();
        return;
    }

    // Check if the index is valid
    if (index < 0 || index >= airplanes.size()) {
        System.out.println("\n This plane does not exist.");
        return;
    }

    // Remove the airplane at the specified index
    if (index < airplanes.size() - 1) {
        // Move the last airplane to the removed airplane's position
        String lastAirplane = airplanes.remove(airplanes.size() - 1);
        airplanes.set(index, lastAirplane);
    } else {
        // If it's the last airplane, just remove it
        airplanes.remove(index);
    }

    // Rewrite the file with the updated list
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
        for (String airplane : airplanes) {
            bw.write(airplane);
            bw.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    System.out.println("\nAirplane removed and list updated successfully.");
    }
    // Updates an airplane in the database using the parameters provided by the user and the index of the airplane to be updated
    public static void modifyAirplane(String make, String model, String type, double fuelCapacity, double cruiseSpeed, double fuelBurnrate, int index) {
        String filePath = System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/Airplanes.dat";
        StringBuilder updatedContent = new StringBuilder();
    
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentIndex = 0;
    
            // Read the file line by line
            while ((line = br.readLine()) != null) {
                if (currentIndex == index) {
                    // Modify the specific line
                    line = make + "," + model + "," + type + "," + fuelCapacity + "," + cruiseSpeed + "," + fuelBurnrate;
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
        System.out.println("Airplane modified successfully!");
    }
    // Search method used by airplane manager to find an airplane in the database using its make and model
    // Returns the index of the airplane if found, otherwise returns -1 (only used by the airplane manager as a helper method)
    public int searchAirplane(String make, String model) {
        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/Airplanes.dat"))) {
            String line;
            int index = 0;
    
            // Iterate through the file line by line
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 6) {
                    String currentMake = fields[0];
                    String currentModel = fields[1];
                    if (currentMake.equalsIgnoreCase(make) && currentModel.equalsIgnoreCase(model)) {
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
    // Display all airplanes in the database in a table format
    public void displayAllAirplanes() {
        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/Airplanes.dat"))) {
            String line;
            int index = 0;
    
            // Print table header
            System.out.printf("%-15s %-15s %-10s %-15s %-20s %-15s%n", "Make", "Model", "Type", "Fuel Cap. (L)", "Cruise Speed (kts)", "Fuel Burn (L/hr)");
            System.out.println("-----------------------------------------------------------------------------------------------");
    
            // Iterate through the file line by line
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Remove leading and trailing whitespace
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }
    
                String[] fields = line.split(",");
                if (fields.length == 6) {
                    System.out.printf("%-15s %-15s %-10s %-15s %-20s %-15s%n",
                            fields[0], // Make
                            fields[1], // Model
                            fields[2], // Type
                            fields[3], // Fuel Capacity
                            fields[4], // Cruise Speed
                            fields[5]  // Fuel Burn Rate
                    );
                } else {
                    System.out.println("Invalid data format on line " + index);
                }
                index++; // Increment the index for each line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Display a single airplane in the database using its index in a table format
    public void displayAirplane(int index) {
        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/Airplanes.dat"))) {
            String line;
            int currentIndex = 0;

            // Print table header
            System.out.printf("%-15s %-15s %-10s %-15s %-20s %-15s%n", "Make", "Model", "Type", "Fuel Cap. (L)", "Cruise Speed (kts)", "Fuel Burn (L/hr)");
            System.out.println("-----------------------------------------------------------------------------------------------");

            // Read the file line by line
            while ((line = br.readLine()) != null) {
                if (currentIndex == index) {
                    line = line.trim(); // Remove leading and trailing whitespace
                    if (line.isEmpty()) {
                        System.out.println("No airplane found at the specified index.");
                        return;
                    }

                    String[] fields = line.split(",");
                    if (fields.length == 6) {
                        System.out.printf("%-15s %-15s %-10s %-15s %-20s %-15s%n",
                                fields[0], // Make
                                fields[1], // Model
                                fields[2], // Type
                                fields[3], // Fuel Capacity
                                fields[4], // Cruise Speed
                                fields[5]  // Fuel Burn Rate
                        );
                    } else {
                        System.out.println("Invalid data format at the specified index.");
                    }
                    return; // Exit after displaying the airplane
                }
                currentIndex++;
            }

            // If the index is not found
            System.out.println("No airplane found at the specified index.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
