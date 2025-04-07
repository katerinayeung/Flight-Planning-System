package FPS;

public class Flight {
    public static void main(String[] args) {
        AirplaneManager AirMan = new AirplaneManager(new Airplane());
        // Creates a list of airplane objects from the Airplanes.dat file
        Airplane airplane = AirMan.readAirplaneFromFile(1);
        if (airplane != null) {
            System.out.println("Make: " + airplane.getMake());
            System.out.println("Model: " + airplane.getModel());
            System.out.println("Type: " + airplane.getType());
            System.out.println("Fuel Capacity: " + airplane.getFuelCapacity());
            System.out.println("Cruise Speed: " + airplane.getCruiseSpeed());
            System.out.println("Fuel Burnrate: " + airplane.getFuelBurnrate());
        } else {
            System.out.println("Airplane not found or error reading file.");
        }
    }

    // method to read airplanes from file
    /* public static Airplane readAirplaneFromFile(int index) {
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
    } */
}