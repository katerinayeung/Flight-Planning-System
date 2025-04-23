/* sysmain.java
 * Contains the main method for the Flight Planning System (FPS).
 * This class serves as the entry point for the application and handles user interactions.
 * It allows users to plan flights, manage airport and airplane databases, and view created flight plans.
 * Selections are made through a console menu system.
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
        System.out.println("\nWelcome to the Flight Planning System! How could I help you today " + user.getName() + "?");
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
                    System.out.println("\nThank you for using the Flight Planning System. Goodbye " + user.getName() + "!");
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
        String startIcao = "";
        Airport startAirport = null; // Declare startAirport
        while (true) {
            // Searches for the starting airport in the database using inputted ICAO
            System.out.print("Enter the ICAO code of the starting airport: ");
            startIcao = input.nextLine();
            int startIndex = airportManager.searchAirport(startIcao);
            if (startIcao.isEmpty() || startIcao.length() != 4 || ! startIcao.matches("[A-Za-z]+")) {
                System.out.println("*** Invalid ICAO code. It must be a non-empty string of 4 alphabetic characters.");
                continue;
            }
            if (startIndex == -1) {
                System.out.println("*** The starting airport does not exist. Please try again.");
                continue; // Ask the user again
            }
            
            startAirport = airportManager.getAirport(startIndex); // Initialize startAirport
            System.out.println("Starting airport selected: " + startAirport.getName() + " (" + startAirport.getIcao() + ")");
            break; // Valid input, exit the loop
        }
    
        // Select destination airports
        List<Airport> destinationAirports = new ArrayList<>();
        while (true) {
            // Searches for the destination airports in the database using inputted ICAO
            System.out.print("Enter the ICAO code of a destination airport (or type 'done' to finish): ");
            String destinationIcao = input.nextLine();
            // Escape condition for 'done'
            if (destinationIcao.equalsIgnoreCase("done")) {
                break;
            }
            if (destinationIcao.isEmpty() || destinationIcao.length() != 4 || ! destinationIcao.matches("[A-Za-z]+")) {
                System.out.println("*** Invalid ICAO code. It must be a non-empty string of 4 alphabetic characters.");
                continue; // Ask the user again
            }
            // Check if the destination airport already exists in the list
            int destinationIndex = airportManager.searchAirport(destinationIcao);
            if (destinationIndex == -1) {
                System.out.println("The destination airport does not exist. Please try again.");
                continue;
            }
            // Add the destination airport to the list
            destinationAirports.add(airportManager.getAirport(destinationIndex));
            System.out.println("Destination airport added: " + destinationAirports.get(destinationAirports.size() - 1).getName() + " (" + destinationIcao + ") ");
            
        }
        // If no destination airports were selected, return to the main menu
        if (destinationAirports.isEmpty()) {
            System.out.println("No destination airports were selected. Returning to the main menu.");
            return;
        }
    
        // Select an airplane
        Airplane selectedAirplane = null; // Declare the variable
        while (true) {
            System.out.print("Enter the make and model of the airplane you want to use (e.g., 'Boeing 737'): ");
            String airplaneInput = input.nextLine();
            String[] airplaneParts = airplaneInput.split(" ", 2);
            if (airplaneParts.length < 2) {
                System.out.println("*** Invalid airplane input. Please provide both make and model. Try again.");
                continue; // Prompt the user again
            }
            String airplaneMake = airplaneParts[0];
            String airplaneModel = airplaneParts[1];
            int airplaneIndex = airplaneManager.searchAirplane(airplaneMake, airplaneModel);
            if (airplaneIndex == -1) {
                System.out.println("*** The airplane does not exist in the database. Please try again.");
                continue; // Prompt the user again
            }
            selectedAirplane = airplaneManager.getAirplane(airplaneIndex); // Initialize the variable
            System.out.println("Airplane selected: " + selectedAirplane.getMake() + " " + selectedAirplane.getModel());
            break; // Valid input, exit the loop
        }
    
        // Create and process the flight
        try {
            Flight flight = new Flight(startAirport, destinationAirports.get(destinationAirports.size() - 1), selectedAirplane);
            flight.determineLayovers(destinationAirports);
            flight.generateLegs();
    
            // Create and display the flight plan
            FlightPlan flightPlan = new FlightPlan(flight);
            flightPlan.displayPlan();
        } catch (Exception e) {
            // Handle any exceptions that occur during flight planning
            System.out.println("\nAn error occurred while planning the flight: " + e.getMessage());
        }
    
        System.out.println("\nReturning to the main menu...");
    }
    // Uses AirportManager to manage the airport database
    private static void manageAirportDatabase(Scanner input) {
    // Initializes Airport Manager
    AirportManager airportManager = new AirportManager(new Airport("", "", 0, 0, 0, ""));
    // Airport Manager Menu
    while (true) {
        System.out.println("\n--- Managing Airport Database ---");
        System.out.println("\n1. Add an Airport");
        System.out.println("2. Remove an Airport");
        System.out.println("3. Modify an Airport");
        System.out.println("4. View a Single Airport");
        System.out.println("5. View All Airports");
        System.out.println("6. Return to the Main Menu");
        System.out.print("\nEnter your choice: ");
        String choice = input.nextLine();
        boolean cancel = false; // Flag to handle "cancel" cases

        switch (choice) {
            case "1":
                // Add an airport
                System.out.println("\nYou can type 'cancel' at any time to return to the Airport Database menu.\n");
                while (true) {
                    // ICAO
                    System.out.print("Enter the ICAO code of the airport: ");
                    String icao = input.nextLine();
                    if (icao.equalsIgnoreCase("cancel")) {
                        System.out.println("\nReturning to the Airport Database menu...");
                        cancel = true;
                        break; // Exit the loop
                    }
                    if (icao.isEmpty() || icao.length() != 4 || !icao.matches("[A-Za-z]+")) {
                        System.out.println("*** Invalid ICAO code. It must be a non-empty string of 4 alphabetic characters.");
                        continue; // Prompt the user again
                    }
                    if (airportManager.searchAirport(icao) != -1) {
                        System.out.println("*** The airport already exists in the database. Please try again.");
                        continue; // Prompt the user again
                    }

                    // Name
                    String name = "";
                    while (true) {
                        System.out.print("Enter the name of the airport: ");
                        name = input.nextLine().trim();
                        if (name.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airport Database menu...");
                            cancel = true; 
                            break; // Exit the loop
                        }
                        if (name.isEmpty()) {
                            System.out.println("*** Invalid airport name. It must be a non-empty string. Please try again.");
                            continue; // Prompt the user again
                        }
                        break; 
                    }
                    if (cancel) break;

                    // Latitude
                    double latitude = 0;
                    while (true) {
                        try {
                            System.out.print("Enter the latitude of the airport: ");
                            String inputLatitude = input.nextLine();
                            if (inputLatitude.equalsIgnoreCase("cancel")) {
                                System.out.println("\nReturning to the Airport Database menu...");
                                cancel = true;
                                break; // Exit the loop
                            }
                            latitude = Double.parseDouble(inputLatitude);
                            if (latitude < -90 || latitude > 90) {
                                System.out.println("*** Invalid latitude. It must be between -90 and 90.");
                                continue; // Prompt the user again
                            }
                            break; // Valid input
                        } catch (NumberFormatException e) {
                            System.out.println("*** Invalid input. Please enter a numeric value for latitude.");
                        }
                    }
                    if (cancel) break;

                    // Longitude
                    double longitude = 0;
                    while (true) {
                        try {
                            System.out.print("Enter the longitude of the airport: ");
                            String inputLongitude = input.nextLine();
                            if (inputLongitude.equalsIgnoreCase("cancel")) {
                                System.out.println("\nReturning to the Airport Database menu...");
                                cancel = true;
                                break; // Exit the loop
                            }
                            longitude = Double.parseDouble(inputLongitude);
                            if (longitude < -180 || longitude > 180) {
                                System.out.println("*** Invalid longitude. It must be between -180 and 180.");
                                continue; // Prompt the user again
                            }
                            break; // Valid input
                        } catch (NumberFormatException e) {
                            System.out.println("*** Invalid input. Please enter a numeric value for longitude.");
                        }
                    }
                    if (cancel) break;

                    // Communication Frequencies
                    double commFrequencies = 0;
                    while (true) {
                        try {
                            System.out.print("Enter the communication frequency of the airport: ");
                            String inputCommFreq = input.nextLine();
                            if (inputCommFreq.equalsIgnoreCase("cancel")) {
                                System.out.println("\nReturning to the Airport Database menu...");
                                cancel = true;
                                break; // Exit the loop
                            }
                            commFrequencies = Double.parseDouble(inputCommFreq);
                            if (commFrequencies < 0) {
                                System.out.println("*** Invalid communication frequency. It must be a non-negative value.");
                                continue; // Prompt the user again
                            }
                            break; // Valid input
                        } catch (NumberFormatException e) {
                            System.out.println("*** Invalid input. Please enter a numeric value for communication frequency.");
                        }
                    }
                    if (cancel) break;

                    // Fuel Types
                    String fuelTypes;
                    while (true) {
                        System.out.print("Enter the fuel types of the airport (JA-a or AvGas): ");
                        fuelTypes = input.nextLine();
                        if (fuelTypes.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airport Database menu...");
                            cancel = true;
                            break; // Exit the loop
                        }
                        if (fuelTypes.equalsIgnoreCase("JA-a") || fuelTypes.equalsIgnoreCase("AvGas")) {
                            break; // Valid input
                        } else {
                            System.out.println("*** Invalid fuel type. Please enter 'JA-a' or 'AvGas'.");
                        }
                    }
                    if (cancel) break;

                    // Add the airport to the database with the provided information
                    airportManager.addAirport(icao, name, latitude, longitude, commFrequencies, fuelTypes);
                    System.out.println("Airport added successfully!");
                    break; // Exit the loop after successful addition
                }
                if (cancel) break;
                break;

            case "2":
                // Remove an airport
                System.out.println("\nYou can type 'cancel' at any time to return to the Airport Database menu.");
                while (true) {
                    System.out.print("\nEnter the ICAO code of the airport to remove: ");
                    String icaoToRemove = input.nextLine();
                    if (icaoToRemove.equalsIgnoreCase("cancel")) {
                        System.out.println("\nReturning to the Airport Database menu...");
                        cancel = true;
                        break; // Exit the loop
                    }
                    if (icaoToRemove.isEmpty() || icaoToRemove.length() != 4 || !icaoToRemove.matches("[A-Za-z]+")) {
                        System.out.println("*** Invalid ICAO code. It must be a non-empty string of 4 alphabetic characters.");
                        continue; // Prompt the user again
                    }
                    int indexToRemove = airportManager.searchAirport(icaoToRemove);
                    if (indexToRemove == -1) {
                        System.out.println("*** The airport does not exist in the database. Please try again.");
                        continue; // Prompt the user again
                    }

                    airportManager.removeAirport(indexToRemove);
                    System.out.println("Airport removed successfully!");
                    break; // Exit the loop after successful removal
                }
                if (cancel) break;
                break;

            case "3":
                // Modify an airport
                System.out.println("\nYou can type 'cancel' at any time to return to the Airport Database menu.");
                int indexToModify = -1;
                while (true) {
                    System.out.print("\nEnter the ICAO code of the airport to modify: ");
                    String icaoToModify = input.nextLine();
                    if (icaoToModify.equalsIgnoreCase("cancel")) {
                        System.out.println("\nReturning to the Airport Database menu...");
                        cancel = true;
                        break; // Exit the loop
                    }
                    if (icaoToModify.isEmpty() || icaoToModify.length() != 4 || !icaoToModify.matches("[A-Za-z]+")) {
                        System.out.println("*** Invalid ICAO code. It must be a non-empty string of 4 alphabetic characters.");
                        continue; // Prompt the user again
                    }
                    indexToModify = airportManager.searchAirport(icaoToModify);
                    // checks if airport exists in the database
                    if (indexToModify == -1) {
                        System.out.println("*** The airport does not exist in the database. Please try again.");
                    } else {
                        System.out.println("Airport found!");
                        break;
                    }
                }
                if (cancel || indexToModify == -1) break;

                // Input the modified information
                System.out.println("\nEnter the new details for the airport:");
                //new ICAO code
               String newIcao = "";
               while (true) {
                    System.out.print("Enter the new ICAO code: ");
                    newIcao = input.nextLine();
                    if (newIcao.equalsIgnoreCase("cancel")) {
                        System.out.println("\nReturning to the Airport Database menu...");
                        cancel = true;
                        break; // Exit the loop
                    }
                    if (newIcao.isEmpty() || newIcao.length() != 4 || !newIcao.matches("[A-Za-z]+")) {
                        System.out.println("*** Invalid ICAO code. It must be a non-empty string of 4 alphabetic characters. Please try again.");
                        continue; // Prompt the user again
                    }
                    break; // Valid input
                }
                if (cancel) break;

                //Name
                String newName = "";
                while (true) {
                    System.out.print("Enter the new name of the airport: ");
                    newName = input.nextLine().trim();
                    if (newName.equalsIgnoreCase("cancel")) {
                        System.out.println("\nReturning to the Airport Database menu...");
                        cancel= true;
                        break; // Exit the loop
                    }
                    if (newName.isEmpty()) {
                        System.out.println("*** Invalid airport name. It must be a non-empty string. Please try again.");
                        continue; // Prompt the user again
                    }
                    break; // Valid input
                }
                if (cancel) break;
                
                // Latitude and Longitude
                double newLatitude = 0;
                while (true) {
                    try {
                        System.out.print("Enter the new latitude of the airport: ");
                        String inputLatitude = input.nextLine();
                        if (inputLatitude.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airport Database menu...");
                            cancel = true;
                            break; // Exit the loop
                        }
                        newLatitude = Double.parseDouble(inputLatitude);
                        if (newLatitude < -90 || newLatitude > 90) {
                            System.out.println("*** Invalid latitude. It must be between -90 and 90.");
                            continue; // Prompt the user again
                        }
                        break; // Valid input
                    } catch (NumberFormatException e) {
                        System.out.println("*** Invalid input. Please enter a numeric value for latitude.");
                    }
                }
                if (cancel) break;

                // Longitude
                double newLongitude = 0;
                while (true) {
                    try {
                        System.out.print("Enter the new longitude of the airport: ");
                        String inputLongitude = input.nextLine();
                        if (inputLongitude.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airport Database menu...");
                            cancel = true; // Exit the loop
                            break;
                        }
                        newLongitude = Double.parseDouble(inputLongitude);
                        if (newLongitude < -180 || newLongitude > 180) {
                            System.out.println("*** Invalid longitude. It must be between -180 and 180.");
                            continue; // Prompt the user again
                        }
                        break; // Valid input
                    } catch (NumberFormatException e) {
                        System.out.println("*** Invalid input. Please enter a numeric value for longitude.");
                    }
                }
                if (cancel) break;

                // Communication Frequencies
                double newCommFrequencies = 0;
                while (true) {
                    try {
                        System.out.print("Enter the new communication frequency of the airport: ");
                        String inputCommFreq = input.nextLine();
                        if (inputCommFreq.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airport Database menu...");
                            cancel = true;
                            break; // Exit the loop
                        }
                        newCommFrequencies = Double.parseDouble(inputCommFreq);
                        if (newCommFrequencies < 0) {
                            System.out.println("*** Invalid communication frequency. It must be a non-negative value.");
                            continue; // Prompt the user again
                        }
                        break; // Valid input
                    } catch (NumberFormatException e) {
                        System.out.println("*** Invalid input. Please enter a numeric value for communication frequency.");
                    }
                }
                if (cancel) break;

                // Fuel Types
                String newFuelTypes;
                while (true) {
                    System.out.print("Enter the new fuel types of the airport (JA-a or AvGas): ");
                    newFuelTypes = input.nextLine();
                    if (newFuelTypes.equalsIgnoreCase("cancel")) {
                        System.out.println("\nReturning to the Airport Database menu...");
                        cancel = true;
                        break; // Exit the loop
                    }
                    if (newFuelTypes.equalsIgnoreCase("JA-a") || newFuelTypes.equalsIgnoreCase("AvGas")) {
                        break; // Valid input
                    } else {
                        System.out.println("*** Invalid fuel type. Please enter 'JA-a' or 'AvGas'.");
                    }
                }
                if (cancel) break;

                // Modify the airport in the database
                airportManager.modifyAirport(newIcao, newName, newLatitude, newLongitude, newCommFrequencies, newFuelTypes, indexToModify);
                System.out.println("Airport modified successfully!");
                break; // Exit the loop after successful modification

            case "4":
                // View a single airport
                System.out.println("\nYou can type 'cancel' at any time to return to the Airport Database menu.");
                while (true) {
                    System.out.print("\nEnter the ICAO code of the airport to view: ");
                    String icaoToView = input.nextLine();
                    if (icaoToView.equalsIgnoreCase("cancel")) {
                        System.out.println("\nReturning to the Airport Database menu...");
                        cancel = true;
                        break; // Exit the loop
                    }
                    if (icaoToView.isEmpty() || icaoToView.length() != 4 || !icaoToView.matches("[A-Za-z]+")) {
                        System.out.println("*** Invalid ICAO code. It must be a non-empty string of 4 alphabetic characters.");
                        continue; // Prompt the user again
                    }
                    // Seaches for the airport in the database using inputted ICAO and retrieves the index
                    int indexToView = airportManager.searchAirport(icaoToView);
                    if (indexToView == -1) {
                        System.out.println("*** The airport does not exist in the database. Please try again.");
                        continue; // Prompt the user again
                    }
                    // Displays the airport information using the index
                    airportManager.displayAirport(indexToView);
                    break; // Exit the loop after displaying the airport information
                }
                if (cancel) break;
                break;

            case "5":
                // View all airports
                System.out.println("\n--- All Airports in the Database ---");
                airportManager.displayAllAirports();
                System.out.println("\nReturning to the Airport Database menu...");
                break; // Exit the loop after displaying all airports

            case "6":
                // Return to the main menu
                System.out.println("Returning to the Main Menu...");
                return;

            default:
                // Invalid input
                System.out.println("*** Invalid choice. Please enter a valid option.");
        }
    }
}
    // Uses AirplaneManager to manage the airplane database
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
                    System.out.println("\nYou can type 'cancel' at any time to return to the Airplane Database menu.\n");
                    while (true) {
                        // Make
                    String make;
                    while (true) {
                        System.out.print("Enter the make of the airplane: ");
                        make = input.nextLine().trim();
                        if (make.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break; // Exit the loop
                        }
                        if (make.isEmpty()) {
                            System.out.println("*** Invalid make. It must be a non-empty string. Please try again.");
                            continue;
                        }
                        break; // Valid input
                    }
                    // Check if the user wants to cancel
                    if (make.equalsIgnoreCase("cancel")) break;

                    // Model
                    String model;
                    while (true) {
                        System.out.print("Enter the model of the airplane: ");
                        model = input.nextLine().trim();
                        if (model.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break; // Exit the loop
                        }
                        if (model.isEmpty()) {
                            System.out.println("*** Invalid model. It must be a non-empty string. Please try again.");
                            continue; // Prompt the user again
                        }
                        break; // Valid input
                    }
                    if (model.equalsIgnoreCase("cancel")) break;

                        // Check if the airplane already exists
                        if (AirMan.searchAirplane(make, model) != -1) {
                            System.out.println("*** The airplane already exists in the database. Please try again.");
                            continue; // Loop back to prompt for a new make and model
                        }

                        // Validate the type
                        String type;
                        while (true) {
                            System.out.print("Enter the type of the airplane (Jet, Prop, or Turboprop): ");
                            type = input.nextLine();
                            if (type.equalsIgnoreCase("cancel")) {
                                System.out.println("\nReturning to the Airplane Database menu...");
                                break; // Exit the loop
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
                                    break; // Exit the loop
                                }
                                fuelCapacity = Double.parseDouble(inputFuelCapacity);
                                if (fuelCapacity < 0) {
                                    System.out.println("*** Fuel capacity cannot be negative. Please try again.");
                                    continue; // Prompt the user again
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
                                    break; // Exit the loop
                                }
                                cruiseSpeed = Double.parseDouble(inputCruiseSpeed);
                                if (cruiseSpeed < 0) {
                                    System.out.println("*** Cruise speed cannot be negative. Please try again.");
                                    continue; // Prompt the user again
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
                                    break; // Exit the loop
                                }
                                fuelBurnRate = Double.parseDouble(inputFuelBurnRate);
                                if (fuelBurnRate < 0) {
                                    System.out.println("*** Fuel burn rate cannot be negative. Please try again.");
                                    continue; // Prompt the user again
                                }
                                break; // Valid input
                            } catch (NumberFormatException e) {
                                System.out.println("*** Invalid input. Please enter a numeric value for fuel burn rate.");
                            }
                        }
                        if (fuelBurnRate == 0) break;

                        // Add the airplane to the database
                        AirMan.addAirplane(make, model, type, fuelCapacity, cruiseSpeed, fuelBurnRate);
                        System.out.println("Airplane added successfully!");
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
                            break; // Exit the loop
                        }

                        System.out.print("Enter the model of the airplane to remove: ");
                        String airplaneModel = input.nextLine();
                        if (airplaneModel.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break; // Exit the loop
                        }

                        int index = AirMan.searchAirplane(airplaneMake, airplaneModel);
                        if (index == -1) {
                            System.out.println("The airplane does not exist in the database. Please try again.");
                            continue; // Prompt the user again
                        }

                        AirMan.removeAirplane(index);
                        break; // Exit the loop after successful removal
                    }
                    break;

                case "3":
                    // Modify an Airplane
                    System.out.println("\nYou can type 'cancel' at any time to return to the Airplane Database menu.\n");
                    int indexToModify = -1;
                    while (true) {
                        System.out.print("Enter the make and model of the airplane to modify (e.g., 'make' 'model'): ");
                        String airplaneInput = input.nextLine();
                        if (airplaneInput.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break; // Exit the loop
                        }

                        String[] airplaneParts = airplaneInput.split(" ", 2);
                        if (airplaneParts.length < 2) {
                            System.out.println("*** Invalid input. Please provide both make and model. Try again.");
                            continue; // Prompt the user again
                        }

                        String airplaneMakeToModify = airplaneParts[0];
                        String airplaneModelToModify = airplaneParts[1];
                        // Search for the airplane in the database using the provided make and model and retrieve the index
                        indexToModify = AirMan.searchAirplane(airplaneMakeToModify, airplaneModelToModify);
                        if (indexToModify == -1) {
                            System.out.println("*** The airplane does not exist in the database. Please try again.");
                        } else {
                            System.out.println("Airplane found!");
                            break; // Exit the loop
                        }
                    }
                    // if the airplane is not found, exit the loop
                    if (indexToModify == -1) break;

                    // Make
                    String make;
                    while (true) {
                        System.out.print("Enter the new make of the airplane: ");
                        make = input.nextLine().trim();
                        if (make.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break; // Exit the loop
                        }
                        if (make.isEmpty()) {
                            System.out.println("*** Invalid make. It must be a non-empty string. Please try again.");
                            continue; // Prompt the user again
                        }
                        break; // Valid input
                    }
                    if (make.equalsIgnoreCase("cancel")) break;

                    // Model
                    String model;
                    while (true) {
                        System.out.print("Enter the new model of the airplane: ");
                        model = input.nextLine().trim();
                        if (model.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break; // Exit the loop
                        }
                        if (model.isEmpty()) {
                            System.out.println("*** Invalid model. It must be a non-empty string. Please try again.");
                            continue; // Prompt the user again
                        }
                        break; // Valid input
                    }
                    if (model.equalsIgnoreCase("cancel")) break;

                    String type;
                    while (true) {
                        System.out.print("Enter the new type of the airplane (Jet, Prop, or Turboprop): ");
                        type = input.nextLine();
                        if (type.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break; // Exit the loop
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
                                break; // Exit the loop
                            }
                            fuelCapacity = Double.parseDouble(inputFuelCapacity);
                            if (fuelCapacity <= 0) {
                                System.out.println("*** Fuel capacity must be greater than 0. Please try again.");
                                continue; // Prompt the user again
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
                                break; // Exit the loop
                            }
                            cruiseSpeed = Double.parseDouble(inputCruiseSpeed);
                            if (cruiseSpeed <= 0) {
                                System.out.println("*** Cruise speed must be greater than 0. Please try again.");
                                continue; // Prompt the user again
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
                                break; // Exit the loop
                            }
                            fuelBurnRate = Double.parseDouble(inputFuelBurnRate);
                            if (fuelBurnRate <= 0) {
                                System.out.println("*** Fuel burn rate must be greater than 0. Please try again.");
                                continue; // Prompt the user again
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("*** Invalid input. Please enter a numeric value for fuel burn rate.");
                        }
                    }
                    if (fuelBurnRate == 0) break;

                    // Modify the airplane
                    AirplaneManager.modifyAirplane(make, model, type, fuelCapacity, cruiseSpeed, fuelBurnRate, indexToModify);
                    break; // Exit the loop after successful modification

                case "4":
                    // View a Single Airplane
                    System.out.println("\nYou can type 'cancel' at any time to return to the Airplane Database menu.\n");
                    while (true) {
                        System.out.print("Enter the make and model of the airplane to view (e.g., 'make' 'model'): ");
                        String airplaneInput = input.nextLine();
                        if (airplaneInput.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break; // Exit the loop
                        }
                
                        String[] airplaneParts = airplaneInput.split(" ", 2);
                        if (airplaneParts.length < 2) {
                            System.out.println("*** Invalid input. Please provide both make and model. Try again.");
                            continue; // Prompt the user again
                        }
                
                        String airplaneMakeToView = airplaneParts[0];
                        String airplaneModelToView = airplaneParts[1];
                        int airplaneIndex = AirMan.searchAirplane(airplaneMakeToView, airplaneModelToView);
                        if (airplaneIndex == -1) {
                            System.out.println("*** The airplane does not exist in the database. Please try again.");
                            continue; // Prompt the user again
                        }
                
                        // Display the airplane details
                        System.out.println();
                        AirMan.displayAirplane(airplaneIndex);
                        break; // Exit the loop after successfully displaying the airplane
                    }
                    break;

                case "5":
                    // View All Airplanes
                    System.out.println("\n--- All Airplanes in the Database ---");
                    AirMan.displayAllAirplanes();
                    System.out.println("\nReturning to the Airplane Database menu...");
                    break; // Exit the loop after displaying all airplanes

                case "6":
                    // Return to the Main Menu
                    System.out.println("\nReturning to the Main Menu...");
                    return; 

                // Default case for invalid input
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
