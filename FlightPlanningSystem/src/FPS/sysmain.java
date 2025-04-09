/* sysmain.java
 * Purpose: 
 * 
 * 
 */

package FPS;

import java.util.*;

public class sysmain {
    public static void main(String[] args) {
        // Welcomes user to FPS and takes user name
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

        // Main Menu
        System.out.println("\nWelcome " + user.getName() + " to the Flight Planning System! How could I help you today?");
        while (true) {
            System.out.println("\n1 Plan a flight\n2 Manage Airport Database\n3 Manage Airplane Database\n4 Close Application");
            System.out.print("\nEnter your choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    // Plan a flight
                    planFlight(user, input);
                    break;
                case "2":
                    // Manage Airport Database
                    manageAirportDatabase(input);
                    break;
                case "3":
                    // Manage Airplane Database
                    manageAirplaneDatabase(input);
                    break;
                case "4":
                    // Close Application
                    System.out.println("\nThank you for using the Flight Planning System. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    // Invalid input
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    private static void planFlight(Passenger user, Scanner input) {
        System.out.println("\nPlanning a flight...");
        List<String> destinations = new ArrayList<>();

        // Initialize destinations
        while (true) {
            System.out.print("Enter a destination (or type 'done' to finish): ");
            String destination = input.nextLine();
            if (destination.equalsIgnoreCase("done")) {
                break;
            }
            destinations.add(destination);
        }

        if (destinations.size() < 2) {
            System.out.println("You need to provide at least two destinations for departure and arrival.");
            return;
        }

        // Set departure airport
        String departureAirport = destinations.get(0);

        // Set arrival airports (all destinations with index > 0)
        List<String> arrivalAirports = destinations.subList(1, destinations.size());

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

        // Create a flight plan
        FlightPlan flightPlan = new FlightPlan(departureAirport, arrivalAirports, null, null, 0, 0, 0);

        System.out.println("\nFlight plan created successfully!");
        System.out.println("Passenger Name: " + user.getName());
        System.out.println("Departure Airport: " + flightPlan.getDepartureAirport());
        System.out.println("Arrival Airports: " + String.join(", ", arrivalAirports));
       //System.out.println("Selected Plane: " + selectedPlane);
    }

    private static void manageAirportDatabase(Scanner input) {
        AirportManager airportManager = new AirportManager(new Airports("", "", 0, 0, 0, ""));
        System.out.println("\nManaging Airport Database...");
        while (true) {
            System.out.println("\n1 Add an Airport\n2 Remove an Airport\n3 Modify an Airport\n4 Return to the menu\n5 View all Airports");
            System.out.print("\nEnter your choice: ");
            int choice = input.nextInt();
            input.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Add an Airport
                    System.out.print("\nEnter ICAO: ");
                    String icao = input.nextLine();
                    if (icao == null || icao.isEmpty() || icao.length() != 4) {
                        System.err.println("\nInvalid ICAO code. It must be a non-empty string of 4 characters.");
                        break;
                    }
                    System.out.print("Enter Name: ");
                    String name = input.nextLine();
                    if (name == null || name.isEmpty()) {
                        System.err.println("\nInvalid airport name. It must be a non-empty string.");
                        break;
                    }
                    System.out.print("Enter Latitude: ");
                    double latitude = input.nextDouble();
                    if (latitude < -90 || latitude > 90) {
                        System.err.println("\nInvalid latitude. It must be between -90 and 90.");
                        break;
                    }
                    System.out.print("Enter Longitude: ");
                    double longitude = input.nextDouble();
                    if (longitude < -180 || longitude > 180) {
                        System.err.println("\nInvalid longitude. It must be between -180 and 180.");
                        break;
                    }
                    input.nextLine(); // Consume newline
                    System.out.print("Enter Communication Frequencies: ");
                    double commFrequencies = input.nextDouble();
                    if (commFrequencies < 0) {
                        System.err.println("\nInvalid communication frequencies. It must be a non-negative value.");
                        break;
                    }
                    input.nextLine(); // Consume newline
                    System.out.print("Enter Fuel Types: ");
                    String fuelTypes = input.nextLine();
                    if (fuelTypes == null || fuelTypes.isEmpty()) {
                        System.err.println("\nInvalid fuel types. It must be a non-empty string.");
                        break;
                    }

                    //if (AirportManager.searchAirport(icao)) {
                     //   System.out.println("The airport already exists.");
                    //} else {
                    
                    airportManager.addAirport(icao, name, latitude, longitude, commFrequencies, fuelTypes);
                    System.out.println("Airport added successfully!");
                    break;
                case 2:
                   
                    // Remove an Airport
                    System.out.print("\nEnter the ICAO of the airport to remove: ");
                    String airportIcao = input.nextLine(); // Correctly read the ICAO
                    airportManager.removeAirport(airportIcao); // Use the correct variable
                    System.out.println("Airport removed successfully!");
                    break;
                   
                case 3:
                    // Modify an Airport 
                    int APindexToModify = -1;
                    while (true) {
                        System.out.println("\nEnter the icao of the airport to modify (or enter 'x' to cancel): ");
                        System.out.print("Enter icao: ");
                        String IcaoToModify = input.nextLine();
                        if (IcaoToModify.equals("x")) {
                            System.out.println("exiting...");
                            break;
                        }
                        APindexToModify = airportManager.searchAirport(IcaoToModify);
                        if (APindexToModify == -1) {
                            System.out.println("The airport does not exist. Please try again.");
                        }
                        else {
                            System.out.println("Airport found!");
                            break;
                        }

                    }
                    if (APindexToModify == -1) break;
                    // Modify an Airplane
                        System.out.print("\nEnter ICAO: ");
                        icao = input.nextLine();
                        if (icao == null || icao.isEmpty() || icao.length() != 4) {
                            System.err.println("Invalid ICAO code. It must be a non-empty string of 4 characters.");
                            break;
                        }
                        System.out.print("Enter Name: ");
                        name = input.nextLine();
                        if (name == null || name.isEmpty()) {
                            System.err.println("Invalid airport name. It must be a non-empty string.");
                            break;
                        }
                        System.out.print("Enter Latitude: ");
                        latitude = input.nextDouble();
                        if (latitude < -90 || latitude > 90) {
                            System.err.println("Invalid latitude. It must be between -90 and 90.");
                            break;
                        }
                        System.out.print("Enter Longitude: ");
                        longitude = input.nextDouble();
                        if (longitude < -180 || longitude > 180) {
                            System.err.println("Invalid longitude. It must be between -180 and 180.");
                            break;
                        }
                        input.nextLine(); // Consume newline
                        System.out.print("Enter Communication Frequencies: ");
                        commFrequencies = input.nextDouble();
                        input.nextLine(); // Consume newline
                        if (commFrequencies < 0) {
                            System.err.println("Invalid communication frequencies. It must be a non-negative value.");
                            break;
                        }
                        System.out.print("Enter Fuel Types: ");
                        fuelTypes = input.nextLine();
                        if (fuelTypes == null || fuelTypes.isEmpty()) {
                            System.err.println("Invalid fuel types. It must be a non-empty string.");
                            break;
                        }

                   airportManager.modifyAirport(icao, name, latitude, longitude, commFrequencies, fuelTypes, APindexToModify);
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
        // Initializes Airplane Manager
        AirplaneManager AirMan = new AirplaneManager(new Airplane("", "", "", 0, 0, 0));
        System.out.println("\nManaging Airplane Database...");
        // Airplane Manager Menu
        while (true) {
            System.out.println("\n1 Add an Airplane\n2 Remove an Airplane\n3 Modify an Airplane\n4 View all Airplanes\n5 Return to the menu");
            System.out.print("\nEnter your choice: ");
            String choice = input.nextLine();
            
            switch (choice) {
                case "1":
                    // Add an Airplane
                    while (true) {
                        System.out.print("\nEnter Make (or type 'cancel' to return to the menu): ");
                        String make = input.nextLine();
                        if (make.equalsIgnoreCase("cancel")) {
                            System.out.println("Returning to the menu...");
                            break;
                        }

                        System.out.print("Enter Model (or type 'cancel' to return to the menu): ");
                        String model = input.nextLine();
                        if (model.equalsIgnoreCase("cancel")) {
                            System.out.println("Returning to the menu...");
                            break;
                        }

                        // Validate the type
                        String type;
                        while (true) {
                            System.out.print("Enter Type (Jet, Prop, or Turboprop) (or type 'cancel' to return to the menu): ");
                            type = input.nextLine();
                            if (type.equalsIgnoreCase("cancel")) {
                                System.out.println("Returning to the menu...");
                                return; // Exit the function
                            }
                            if (type.equalsIgnoreCase("Jet") || type.equalsIgnoreCase("Prop") || type.equalsIgnoreCase("Turboprop")) {
                                break; // Valid type
                            } else {
                                System.out.println("Invalid type. Please enter 'Jet', 'Prop', or 'Turboprop'.");
                            }
                        }

                        double fuelCapacity = 0;
                        double cruiseSpeed = 0;
                        double fuelBurnRate = 0;

                        // Validate Fuel Capacity
                        while (true) {
                            try {
                                System.out.print("Enter Fuel Capacity (or type 'cancel' to return to the menu): ");
                                String inputFuelCapacity = input.nextLine();
                                if (inputFuelCapacity.equalsIgnoreCase("cancel")) {
                                    System.out.println("Returning to the menu...");
                                    return; // Exit the function
                                }
                                fuelCapacity = Double.parseDouble(inputFuelCapacity);
                                if (fuelCapacity < 0) {
                                    System.out.println("Fuel Capacity cannot be negative. Please try again.");
                                    continue;
                                }
                                break; // Valid input
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a numeric value for Fuel Capacity.");
                            }
                        }

                        // Validate Cruise Speed
                        while (true) {
                            try {
                                System.out.print("Enter Cruise Speed (or type 'cancel' to return to the menu): ");
                                String inputCruiseSpeed = input.nextLine();
                                if (inputCruiseSpeed.equalsIgnoreCase("cancel")) {
                                    System.out.println("Returning to the menu...");
                                    return; // Exit the function
                                }
                                cruiseSpeed = Double.parseDouble(inputCruiseSpeed);
                                if (cruiseSpeed < 0) {
                                    System.out.println("Cruise Speed cannot be negative. Please try again.");
                                    continue;
                                }
                                break; // Valid input
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a numeric value for Cruise Speed.");
                            }
                        }

                        // Validate Fuel Burn Rate
                        while (true) {
                            try {
                                System.out.print("Enter Fuel Burn Rate (or type 'cancel' to return to the menu): ");
                                String inputFuelBurnRate = input.nextLine();
                                if (inputFuelBurnRate.equalsIgnoreCase("cancel")) {
                                    System.out.println("Returning to the menu...");
                                    return; // Exit the function
                                }
                                fuelBurnRate = Double.parseDouble(inputFuelBurnRate);
                                if (fuelBurnRate < 0) {
                                    System.out.println("Fuel Burn Rate cannot be negative. Please try again.");
                                    continue;
                                }
                                break; // Valid input
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a numeric value for Fuel Burn Rate.");
                            }
                        }

                        // Check if the airplane already exists
                        if (AirMan.searchAirplane(make, model) != -1) {
                            System.out.println("The airplane already exists. Please try again.");
                            continue; // Restart the loop
                        }

                        // Add the airplane to the database
                        AirMan.addAirplane(make, model, type, fuelCapacity, cruiseSpeed, fuelBurnRate);
                        System.out.println("Airplane added successfully!");
                        break; // Exit the loop after successful addition
                    }
                    break;
                case "2":
                    // Remove an Airplane
                    System.out.print("\nEnter the make of the airplane to remove: ");
                    String airplaneMake = input.nextLine();
                    System.out.print("Enter the model of the airplane to remove: ");
                    String airplaneModel = input.nextLine();
                    int index = AirMan.searchAirplane(airplaneMake, airplaneModel);
                    AirMan.removeAirplane(index);

                    break;
                /*case "3": 
                    int indexToModify = -1;
                    while (true) {
                        System.out.println("\nEnter the make and model of the airplane to modify (or enter 'x' twice to cancel): ");
                        System.out.print("\nEnter make: ");
                        String airplaneMakeToModify = input.nextLine();
                        System.out.print("Enter model: ");
                        String airplaneModelToModify = input.nextLine();
                        if (airplaneMakeToModify.equals("x") && airplaneModelToModify.equals("x")) {
                            System.out.println("exiting");
                            break;
                        }
                        indexToModify = AirMan.searchAirplane(airplaneMakeToModify, airplaneModelToModify);
                        if (indexToModify == -1) {
                            System.out.println("The airplane does not exist. Please try again.");
                        }
                        else {
                            System.out.println("Airplane found!");
                            break;
                        }

                    }
                    if (indexToModify == -1) break;
                    
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

                        AirMan.modifyAirplane(make, model, type, fuelCapacity, cruiseSpeed, fuelBurnRate, indexToModify);
                        System.out.println("Airplane modified successfully!");
                        break;
                    */
                case "4":
                    // View all Airplanes
                    System.out.println("\nAll Airplanes in the Database:");
                    AirMan.displayAllAirplanes();
                case "5":
                    // Return to the menu
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
