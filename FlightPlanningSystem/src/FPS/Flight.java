package FPS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Flight {
    public static void main(String[] args) {
        // Creates a list of airplane objects from the Airplanes.dat file
        List<Airplane> airplanes = readAirplanesFromFile(System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/Airplanes.dat");
        //displays the information of each airplane (temporary)
        for (Airplane airplane : airplanes) {
            airplane.displayInfo();
        }
    }

    // method to read airplanes from file
    public static List<Airplane> readAirplanesFromFile(String filePath) {
        List<Airplane> airplanes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                //splits the line into fields based on commas
                String[] fields = line.split(",");
                //assigns the fields to airplane attributes
                if (fields.length == 6) {
                    String make = fields[0];
                    String model = fields[1];
                    String type = fields[2];
                    double fuelCapacity = Double.parseDouble(fields[3]);
                    double cruiseSpeed = Double.parseDouble(fields[4]);
                    double fuelBurnrate = Double.parseDouble(fields[5]);
                    //creates a new airplane object with the fields and adds it to the list
                    Airplane airplane = new Airplane(make, model, type, fuelCapacity, cruiseSpeed, fuelBurnrate);
                    airplanes.add(airplane);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return airplanes;
    }
}