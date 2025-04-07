package FPS;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

    public void addAirplane(String make, String model, String type, double fuelCapacity, double cruiseSpeed, double fuelBurnrate) {
        
        // Add airplane to database (e.g., write to file)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/Airplanes.dat", true))) {
            writer.newLine();
            writer.write(make + "," + model + "," + type + "," + fuelCapacity + "," + cruiseSpeed + "," + fuelBurnrate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void removeAirplane(Airplane airplane) {
        // Remove airplane from database
    }

    public static void modifyAirplane(String make, String model, String type, double fuelCapacity, double cruiseSpeed, double fuelBurnrate, int index) {
        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/Airplanes.dat"))) {
            String line = null;
    
            // Skip lines until the desired index
            for (int i = 0; i <= index; i++) {
                line = br.readLine();
                if (line == null) {
                    throw new IOException("Reached end of file before finding the specified line.");
                }
            }
    
            // Process the specific line
            String[] fields = line.split(",");
            if (fields.length == 6) {
                fields[0] = make;
                fields[1] = model;
                fields[2] = type;
                fields[3] = String.valueOf(fuelCapacity);
                fields[4] = String.valueOf(cruiseSpeed);
                fields[5] = String.valueOf(fuelBurnrate);
            } else {
                throw new IOException("Invalid data format on the specified line.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        System.out.println("Airplane not found in the database.");
        return -1; // Return -1 if no match is found
    }

    public static Airplane readAirplaneFromFile(int index) {
        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/Airplanes.dat"))) {
            String line = null;
    
            // Skip lines until the desired index
            for (int i = 0; i <= index; i++) {
                line = br.readLine();
                if (line == null) {
                    throw new IOException("Reached end of file before finding the specified line.");
                }
            }
    
            // Process the specific line
            String[] fields = line.split(",");
            if (fields.length == 6) {
                String make = fields[0];
                String model = fields[1];
                String type = fields[2];
                double fuelCapacity = Double.parseDouble(fields[3]);
                double cruiseSpeed = Double.parseDouble(fields[4]);
                double fuelBurnrate = Double.parseDouble(fields[5]);
    
                // Create and return the Airplane object
                return new Airplane(make, model, type, fuelCapacity, cruiseSpeed, fuelBurnrate);
            } else {
                throw new IOException("Invalid data format on the specified line.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return null; // Return null if an error occurs
    }
}
