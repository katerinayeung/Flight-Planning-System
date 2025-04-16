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
        // Initializes Airport and Airplane Managers
        AirportManager airportManager = new AirportManager(new Airport("", "", 0, 0, 0, ""));
        AirplaneManager airplaneManager = new AirplaneManager(new Airplane("", "", "", 0, 0, 0));
        System.out.println("\nPlanning a flight...");
    
        // Select a starting airport
        System.out.print("Enter the ICAO code of the starting airport: ");
        String startIcao = input.nextLine();
        int startIndex = airportManager.searchAirport(startIcao);
        if (startIndex == -1) {
            System.out.println("The starting airport does not exist. Returning to the main menu.");
            return;
        }
        Airport startAirport = airportManager.getAirport(startIndex);
    
        // Select destination airports
        List<Airport> destinationAirports = new ArrayList<>();
        while (true) {
            System.out.print("Enter the ICAO code of a destination airport (or type 'done' to finish): ");
            String destinationIcao = input.nextLine();
            if (destinationIcao.equalsIgnoreCase("done")) {
                break;
            }
            int destinationIndex = airportManager.searchAirport(destinationIcao);
            if (destinationIndex == -1) {
                System.out.println("The destination airport does not exist. Please try again.");
            } else {
                destinationAirports.add(airportManager.getAirport(destinationIndex));
            }
        }
    
        if (destinationAirports.isEmpty()) {
            System.out.println("No destination airports were selected. Returning to the main menu.");
            return;
        }
    
        // Select an airplane
        System.out.print("Enter the make and model of the airplane you want to use (e.g., 'Boeing 747'): ");
        String airplaneInput = input.nextLine();
        String[] airplaneParts = airplaneInput.split(" ", 2);
        if (airplaneParts.length < 2) {
            System.out.println("Invalid airplane input. Returning to the main menu.");
            return;
        }
        String airplaneMake = airplaneParts[0];
        String airplaneModel = airplaneParts[1];
        int airplaneIndex = airplaneManager.searchAirplane(airplaneMake, airplaneModel);
        if (airplaneIndex == -1) {
            System.out.println("The airplane does not exist. Returning to the main menu.");
            return;
        }
        Airplane selectedAirplane = airplaneManager.getAirplane(airplaneIndex);
    
        // Create and process the flight
        try {
            Flight flight = new Flight(startAirport, destinationAirports.get(destinationAirports.size() - 1), selectedAirplane);
            flight.determineLayovers(destinationAirports);
            flight.generateLegs();
    
            // Create and display the flight plan
            FlightPlan flightPlan = new FlightPlan(flight);
            flightPlan.displayPlan();
        } catch (Exception e) {
            System.out.println("An error occurred while planning the flight: " + e.getMessage());
        }
    
        System.out.println("Returning to the main menu...");
    }

    private static void manageAirportDatabase(Scanner input) {
        AirportManager airportManager = new AirportManager(new Airport("", "", 0, 0, 0, ""));
        System.out.println("\nManaging Airport Database...");
        while (true) {
            System.out.println("\n1 Add an Airport\n2 Remove an Airport\n3 Modify an Airport\n4 View Single Airport\n5 View all Airports\n6 Return to the menu");
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
                    if (airportManager.searchAirport(icao) != -1) {
                        // Check if the airport already exists
                        System.out.println("The airport already exists. Please try again.");
                        break; // Restart the loop
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
                    
                    airportManager.addAirport(icao, name, latitude, longitude, commFrequencies, fuelTypes);
                    System.out.println("Airport added successfully!");
                    break;
                case 2:
                   
                    // Remove an Airport
                    System.out.print("\nEnter the ICAO of the airport to remove: ");
                    String airportIcao = input.nextLine(); // Correctly read the ICAO
                    if (airportIcao == null || airportIcao.isEmpty() || airportIcao.length() != 4) {
                        System.err.println("\nInvalid ICAO code. It must be a non-empty string of 4 characters.");
                        break;
                    }
                    int indexToRemove = airportManager.searchAirport(airportIcao);
                    if (indexToRemove == -1) {
                        System.out.println("The airport does not exist. Please try again.");
                        break;
                    }
                    airportManager.removeAirport(indexToRemove); // Use the correct variable
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
                    // View single Airport
                    System.out.print("\nEnter the ICAO of the airport to view: ");
                    String airportIcaoToView = input.nextLine();
                    if (airportIcaoToView == null || airportIcaoToView.isEmpty() || airportIcaoToView.length() != 4) {
                        System.err.println("\nInvalid ICAO code. It must be a non-empty string of 4 characters.");
                        break;
                    }
                    int airportIndex = airportManager.searchAirport(airportIcaoToView);
                    if (airportIndex == -1) {
                        System.out.println("The airport does not exist. Please try again.");
                        break;
                    }
                    airportManager.displayAirport(airportIndex);
                    break;
                case 5:
                    // View all Airports
                    System.out.println("\nAll Airports in the Database:");
                    airportManager.displayAllAirports();
                    break;
                case 6:
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
        // Airplane Manager Menu
        while (true) {
            System.out.println("\n--- Managing Airplane Database ---");
            System.out.println("\n1. Add an Airplane");
            System.out.println("2. Remove an Airplane");
            System.out.println("3. Modify an Airplane");
            System.out.println("4. View a Single Airplane");
            System.out.println("5. View All Airplanes");
            System.out.println("6. Return to the Main Menu");
            System.out.print("\nEnter your choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    // Add an Airplane
                    System.out.println("\nYou can type 'cancel' at any time to return to the Airplane Database menu.");
                    while (true) {
                        System.out.print("\nEnter the make of the airplane: ");
                        String make = input.nextLine();
                        if (make.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break;
                        }

                        System.out.print("Enter the model of the airplane: ");
                        String model = input.nextLine();
                        if (model.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break;
                        }

                        // Validate the type
                        String type;
                        while (true) {
                            System.out.print("Enter the type of the airplane (Jet, Prop, or Turboprop): ");
                            type = input.nextLine();
                            if (type.equalsIgnoreCase("cancel")) {
                                System.out.println("\nReturning to the Airplane Database menu...");
                                break;
                            }
                            if (type.equalsIgnoreCase("Jet") || type.equalsIgnoreCase("Prop") || type.equalsIgnoreCase("Turboprop")) {
                                break; // Valid type
                            } else {
                                System.out.println("*** Invalid type. Please enter 'Jet', 'Prop', or 'Turboprop'.");
                            }
                        }
                        if (type.equalsIgnoreCase("cancel")) break;

                        double fuelCapacity = 0, cruiseSpeed = 0, fuelBurnRate = 0;

                        // Validate Fuel Capacity
                        while (true) {
                            try {
                                System.out.print("Enter the fuel capacity of the airplane: ");
                                String inputFuelCapacity = input.nextLine();
                                if (inputFuelCapacity.equalsIgnoreCase("cancel")) {
                                    System.out.println("\nReturning to the Airplane Database menu...");
                                    break;
                                }
                                fuelCapacity = Double.parseDouble(inputFuelCapacity);
                                if (fuelCapacity < 0) {
                                    System.out.println("*** Fuel capacity cannot be negative. Please try again.");
                                    continue;
                                }
                                break; // Valid input
                            } catch (NumberFormatException e) {
                                System.out.println("*** Invalid input. Please enter a numeric value for fuel capacity.");
                            }
                        }
                        if (fuelCapacity == 0) break;

                        // Validate Cruise Speed
                        while (true) {
                            try {
                                System.out.print("Enter the cruise speed of the airplane: ");
                                String inputCruiseSpeed = input.nextLine();
                                if (inputCruiseSpeed.equalsIgnoreCase("cancel")) {
                                    System.out.println("\nReturning to the Airplane Database menu...");
                                    break;
                                }
                                cruiseSpeed = Double.parseDouble(inputCruiseSpeed);
                                if (cruiseSpeed < 0) {
                                    System.out.println("*** Cruise speed cannot be negative. Please try again.");
                                    continue;
                                }
                                break; // Valid input
                            } catch (NumberFormatException e) {
                                System.out.println("*** Invalid input. Please enter a numeric value for cruise speed.");
                            }
                        }
                        if (cruiseSpeed == 0) break;

                        // Validate Fuel Burn Rate
                        while (true) {
                            try {
                                System.out.print("Enter the fuel burn rate of the airplane: ");
                                String inputFuelBurnRate = input.nextLine();
                                if (inputFuelBurnRate.equalsIgnoreCase("cancel")) {
                                    System.out.println("\nReturning to the Airplane Database menu...");
                                    break;
                                }
                                fuelBurnRate = Double.parseDouble(inputFuelBurnRate);
                                if (fuelBurnRate < 0) {
                                    System.out.println("*** Fuel burn rate cannot be negative. Please try again.");
                                    continue;
                                }
                                break; // Valid input
                            } catch (NumberFormatException e) {
                                System.out.println("*** Invalid input. Please enter a numeric value for fuel burn rate.");
                            }
                        }
                        if (fuelBurnRate == 0) break;

                        // Check if the airplane already exists
                        if (AirMan.searchAirplane(make, model) != -1) {
                            System.out.println("The airplane already exists in the database. Please try again.");
                            continue; // Restart the loop
                        }

                        // Add the airplane to the database
                        AirMan.addAirplane(make, model, type, fuelCapacity, cruiseSpeed, fuelBurnRate);
                        break; // Exit the loop after successful addition
                    }
                    break;

                case "2":
                    // Remove an Airplane
                    System.out.println("\nYou can type 'cancel' at any time to return to the Airplane Database menu.");
                    while (true) {
                        System.out.print("\nEnter the make of the airplane to remove: ");
                        String airplaneMake = input.nextLine();
                        if (airplaneMake.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break;
                        }

                        System.out.print("Enter the model of the airplane to remove: ");
                        String airplaneModel = input.nextLine();
                        if (airplaneModel.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break;
                        }

                        int index = AirMan.searchAirplane(airplaneMake, airplaneModel);
                        if (index == -1) {
                            System.out.println("The airplane does not exist in the database. Please try again.");
                            continue;
                        }

                        AirMan.removeAirplane(index);
                        break;
                    }
                    break;

                case "3":
                    // Modify an Airplane
                    System.out.println("\nYou can type 'cancel' at any time to return to the Airplane Database menu.");
                    int indexToModify = -1;
                    while (true) {
                        System.out.print("\nEnter the make of the airplane to modify: ");
                        String airplaneMakeToModify = input.nextLine();
                        if (airplaneMakeToModify.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break;
                        }

                        System.out.print("Enter the model of the airplane to modify: ");
                        String airplaneModelToModify = input.nextLine();
                        if (airplaneModelToModify.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break;
                        }

                        indexToModify = AirMan.searchAirplane(airplaneMakeToModify, airplaneModelToModify);
                        if (indexToModify == -1) {
                            System.out.println("The airplane does not exist in the database. Please try again.");
                        } else {
                            System.out.println("Airplane found!");
                            break;
                        }
                    }
                    if (indexToModify == -1) break;

                    // Modify the airplane
                    System.out.print("\nEnter the new make of the airplane: ");
                    String make = input.nextLine();
                    if (make.equalsIgnoreCase("cancel")) {
                        System.out.println("\nReturning to the Airplane Database menu...");
                        break;
                    }

                    System.out.print("Enter the new model of the airplane: ");
                    String model = input.nextLine();
                    if (model.equalsIgnoreCase("cancel")) {
                        System.out.println("\nReturning to the Airplane Database menu...");
                        break;
                    }

                    String type;
                    while (true) {
                        System.out.print("Enter the new type of the airplane (Jet, Prop, or Turboprop): ");
                        type = input.nextLine();
                        if (type.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break;
                        }
                        if (type.equalsIgnoreCase("Jet") || type.equalsIgnoreCase("Prop") || type.equalsIgnoreCase("Turboprop")) {
                            break; // Valid type
                        } else {
                            System.out.println("*** Invalid type. Please enter 'Jet', 'Prop', or 'Turboprop'.");
                        }
                    }
                    if (type.equalsIgnoreCase("cancel")) break;

                    double fuelCapacity = 0, cruiseSpeed = 0, fuelBurnRate = 0;

                    // Validate Fuel Capacity
                    while (true) {
                        try {
                            System.out.print("Enter the new fuel capacity of the airplane: ");
                            String inputFuelCapacity = input.nextLine();
                            if (inputFuelCapacity.equalsIgnoreCase("cancel")) {
                                System.out.println("\nReturning to the Airplane Database menu...");
                                break;
                            }
                            fuelCapacity = Double.parseDouble(inputFuelCapacity);
                            if (fuelCapacity < 0) {
                                System.out.println("*** Fuel capacity cannot be negative. Please try again.");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("*** Invalid input. Please enter a numeric value for fuel capacity.");
                        }
                    }
                    if (fuelCapacity == 0) break;

                    // Validate Cruise Speed
                    while (true) {
                        try {
                            System.out.print("Enter the new cruise speed of the airplane: ");
                            String inputCruiseSpeed = input.nextLine();
                            if (inputCruiseSpeed.equalsIgnoreCase("cancel")) {
                                System.out.println("\nReturning to the Airplane Database menu...");
                                break;
                            }
                            cruiseSpeed = Double.parseDouble(inputCruiseSpeed);
                            if (cruiseSpeed < 0) {
                                System.out.println("*** Cruise speed cannot be negative. Please try again.");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("*** Invalid input. Please enter a numeric value for cruise speed.");
                        }
                    }
                    if (cruiseSpeed == 0) break;

                    // Validate Fuel Burn Rate
                    while (true) {
                        try {
                            System.out.print("Enter the new fuel burn rate of the airplane: ");
                            String inputFuelBurnRate = input.nextLine();
                            if (inputFuelBurnRate.equalsIgnoreCase("cancel")) {
                                System.out.println("\nReturning to the Airplane Database menu...");
                                break;
                            }
                            fuelBurnRate = Double.parseDouble(inputFuelBurnRate);
                            if (fuelBurnRate < 0) {
                                System.out.println("*** Fuel burn rate cannot be negative. Please try again.");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("*** Invalid input. Please enter a numeric value for fuel burn rate.");
                        }
                    }
                    if (fuelBurnRate == 0) break;

                    // Modify the airplane
                    AirMan.modifyAirplane(make, model, type, fuelCapacity, cruiseSpeed, fuelBurnRate, indexToModify);
                    break;

                case "4":
                    // View a Single Airplane
                    System.out.println("\nYou can type 'cancel' at any time to return to the Airplane Database menu.");
                    System.out.print("\nEnter the make and model of the airplane to view (e.g., 'Boeing 747'): ");
                    String airplaneInput = input.nextLine();
                    if (airplaneInput.equalsIgnoreCase("cancel")) {
                        System.out.println("\nReturning to the Airplane Database menu...");
                        break;
                    }
                    String[] airplaneParts = airplaneInput.split(" ", 2);
                    if (airplaneParts.length < 2) {
                        System.out.println("Invalid airplane input. Returning to the menu.");
                        break;
                    }
                    String airplaneMakeToView = airplaneParts[0];
                    String airplaneModelToView = airplaneParts[1];
                    int airplaneIndex = AirMan.searchAirplane(airplaneMakeToView, airplaneModelToView);
                    if (airplaneIndex == -1) {
                        System.out.println("The airplane does not exist. Please try again.");
                        break;
                    }
                    AirMan.displayAirplane(airplaneIndex);
                    break;

                case "5":
                    // View All Airplanes
                    System.out.println("\n--- All Airplanes in the Database ---");
                    AirMan.displayAllAirplanes();
                    System.out.println("\nReturning to the Airplane Database menu...");
                    break;

                case "6":
                    // Return to the Main Menu
                    System.out.println("Returning to the Main Menu...");
                    return;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
