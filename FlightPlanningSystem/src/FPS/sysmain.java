package FPS;

import java.util.*;

public class sysmain {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("***************************************************************************");
        System.out.println("THIS SOFTWARE IS NOT TO BE USED FOR FLIGHT PLANNING OR NAVIGATIONAL PURPOSE");
        System.out.println("***************************************************************************");

        System.out.print(
    "        ______\n" +
    "                _\\ _~-\\___\n" +
    "        =  = ==(____AA____D\n" +
    "                    \\_____\\___________________,-~~~~~~~`-.._\n" +
    "                    /     o O o o o o O O o o o o o o O o  |\\_\n" +
    "                    `~-.__        ___..----..                  )\n" +
    "                          `---~~\\___________/------------`````\n" +
    "                          =  ===(_________D\n"
);
        System.out.print("\n                Welcome to the Flight Planning System!\n\nPlease insert your name: ");

        String name = input.nextLine();
        Passenger user = new Passenger(name, new ArrayList<>(), null);

        System.out.println("\nWelcome " + user.getName() + " to the Flight Planning System! How could I help you today?");
        while (true) {
            System.out.println("\n1 Plan a flight\n2 Manage Airport Database\n3 Manage Airplane Database\n4 Close Application");
            System.out.print("\nEnter your choice: ");
            int choice = input.nextInt();
            input.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Plan a flight
                    planFlight(user, input);
                    break;
                case 2:
                    // Manage Airport Database
                    manageAirportDatabase(input);
                    break;
                case 3:
                    // Manage Airplane Database
                    manageAirplaneDatabase(input);
                    break;
                case 4:
                    // Close Application
                    System.out.println("\nThank you for using the Flight Planning System. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void planFlight(Passenger user, Scanner input) {
        System.out.println("\nPlanning a flight...");
        // Initialize destinations
        while (true) {
            System.out.print("Enter a destination (or type 'done' to finish): ");
            String destination = input.nextLine();
            if (destination.equalsIgnoreCase("done")) {
                break;
            }
            /*if (AirportManager.searchAirport(destination)) {
                user.getDestinations().add(destination);
                System.out.println("Destination added: " + destination);
            } else {
                System.out.println("The destination does not exist or is spelled incorrectly. Please try again.");
            }*/
        }

        // Initialize airplane
        while (true) {
            System.out.print("Enter the name of the plane you want to select: ");
            String plane = input.nextLine();
            /*if (AirplaneManager.searchAirplane(plane)) {
                user.setSelectedPlane(plane);
                System.out.println("Plane selected: " + plane);
                break;
            } else {
                System.out.println("The plane does not exist or is spelled incorrectly. Please try again.");
            }*/
            break;
        }

        System.out.println("\nFlight plan created successfully!");
        System.out.println("Passenger Name: " + user.getName());
        System.out.println("Destinations: " + user.getDestinations());
        System.out.println("Selected Plane: " + user.getSelectedPlane());
    }

    private static void manageAirportDatabase(Scanner input) {
        System.out.println("\nManaging Airport Database...");
        while (true) {
            System.out.println("\n1 Add an Airport\n2 Remove an Airport\n3 Modify an Airport\n4 Return to the menu");
            System.out.print("\nEnter your choice: ");
            int choice = input.nextInt();
            input.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Add an Airport
                    System.out.print("\nEnter ICAO: ");
                    String icao = input.nextLine();
                    System.out.print("Enter Name: ");
                    String name = input.nextLine();
                    System.out.print("Enter Latitude: ");
                    double latitude = input.nextDouble();
                    System.out.print("Enter Longitude: ");
                    double longitude = input.nextDouble();
                    input.nextLine(); // Consume newline
                    System.out.print("Enter Communication Frequencies: ");
                    String commFrequencies = input.nextLine();
                    System.out.print("Enter Fuel Types: ");
                    String fuelTypes = input.nextLine();

                    /*if (AirportManager.searchAirport(icao)) {
                        System.out.println("The airport already exists.");
                    } else {
                        AirportManager.addAirport(icao, name, latitude, longitude, commFrequencies, fuelTypes);
                        System.out.println("Airport added successfully!");
                    }*/
                    break;
                case 2:
                    // Remove an Airport
                    System.out.print("\nEnter the name of the airport to remove: ");
                    String airportName = input.nextLine();
                    /*if (AirportManager.searchAirport(airportName)) {
                        AirportManager.removeAirport(airportName);
                        System.out.println("Airport removed successfully!");
                    } else {
                        System.out.println("The airport does not exist.");
                    }*/
                    break;
                case 3:
                    // Modify an Airport
                    System.out.print("\nEnter ICAO: ");
                    icao = input.nextLine();
                    System.out.print("Enter Name: ");
                    name = input.nextLine();
                    System.out.print("Enter Latitude: ");
                    latitude = input.nextDouble();
                    System.out.print("Enter Longitude: ");
                    longitude = input.nextDouble();
                    input.nextLine(); // Consume newline
                    System.out.print("Enter Communication Frequencies: ");
                    commFrequencies = input.nextLine();
                    System.out.print("Enter Fuel Types: ");
                    fuelTypes = input.nextLine();

                   // AirportManager.modifyAirport(icao, name, latitude, longitude, commFrequencies, fuelTypes);
                    System.out.println("Airport modified successfully!");
                    break;
                case 4:
                    // Return to the menu
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageAirplaneDatabase(Scanner input) {
        AirplaneManager AirMan = new AirplaneManager(new Airplane("", "", "", 0, 0, 0));
        System.out.println("\nManaging Airplane Database...");
        while (true) {
            System.out.println("\n1 Add an Airplane\n2 Remove an Airplane\n3 Modify an Airplane\n4 Return to the menu");
            System.out.print("\nEnter your choice: ");
            int choice = input.nextInt();
            input.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Add an Airplane
                    System.out.print("\nEnter Make: ");
                    String make = input.nextLine();
                    System.out.print("Enter Model: ");
                    String model = input.nextLine();
                    System.out.print("Enter Type: ");
                    String type = input.nextLine();
                    System.out.print("Enter Fuel Capacity: ");
                    double fuelCapacity = input.nextDouble();
                    System.out.print("Enter Cruise Speed: ");
                    double cruiseSpeed = input.nextDouble();
                    System.out.print("Enter Fuel Burn Rate: ");
                    double fuelBurnRate = input.nextDouble();
                    input.nextLine(); // Consume newline

                    /*if (AirplaneManager.searchAirplane(make + " " + model)) {
                        System.out.println("The airplane already exists.");
                    } */
                    AirMan.addAirplane(make, model, type, fuelCapacity, cruiseSpeed, fuelBurnRate);
                    System.out.println("Airplane added successfully!");
                    
                    break;
                case 2:
                    // Remove an Airplane
                    System.out.print("\nEnter the name of the airplane to remove: ");
                    String airplaneName = input.nextLine();
                    /*if (AirplaneManager.searchAirplane(airplaneName)) {
                        AirplaneManager.removeAirplane(airplaneName);
                        System.out.println("Airplane removed successfully!");
                    } else {
                        System.out.println("The airplane does not exist.");
                    }*/
                    break;
                case 3:
                    // Modify an Airplane
                    System.out.print("\nEnter Make: ");
                    make = input.nextLine();
                    System.out.print("Enter Model: ");
                    model = input.nextLine();
                    System.out.print("Enter Type: ");
                    type = input.nextLine();
                    System.out.print("Enter Fuel Capacity: ");
                    fuelCapacity = input.nextDouble();
                    System.out.print("Enter Cruise Speed: ");
                    cruiseSpeed = input.nextDouble();
                    System.out.print("Enter Fuel Burn Rate: ");
                    fuelBurnRate = input.nextDouble();
                    input.nextLine(); // Consume newline

                    AirMan.modifyAirplane(make, model, type, fuelCapacity, cruiseSpeed, fuelBurnRate,3);
                    System.out.println("Airplane modified successfully!");
                    break;
                case 4:
                    // Return to the menu
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
