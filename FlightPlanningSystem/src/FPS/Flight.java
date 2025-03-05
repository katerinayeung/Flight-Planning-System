package FPS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Flight {
    public static void main(String[] args) {
        List<Airplane> airplanes = readAirplanesFromFile(System.getProperty("user.dir") + "/FlightPlanningSystem/src/FPS/database/Airplanes.dat");
        for (Airplane airplane : airplanes) {
            airplane.displayInfo();
        }
    }

    public static List<Airplane> readAirplanesFromFile(String filePath) {
        List<Airplane> airplanes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 6) {
                    String make = fields[0];
                    String model = fields[1];
                    String type = fields[2];
                    double fuelCapacity = Double.parseDouble(fields[3]);
                    double cruiseSpeed = Double.parseDouble(fields[4]);
                    double fuelBurnrate = Double.parseDouble(fields[5]);
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