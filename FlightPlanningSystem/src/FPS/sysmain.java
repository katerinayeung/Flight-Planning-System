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
                // ADD AN AIRPORT
                System.out.println("\nYou can type 'cancel' at any time to return to the Airport Database menu.\n");
                while (true) {
                    // ICAO
                    System.out.print("Enter the ICAO code of the airport: ");
                    String icao = input.nextLine();
                    if (icao.equalsIgnoreCase("cancel")) {
                        System.out.println("\nReturning to the Airport Database menu...");
                        cancel = true;
                        break;
                    }
                    if (icao.isEmpty() || icao.length() != 4 || !icao.matches("[A-Za-z]+")) {
                        System.out.println("*** Invalid ICAO code. It must be a non-empty string of 4 alphabetic characters.");
                        continue;
                    }
                    if (airportManager.searchAirport(icao) != -1) {
                        System.out.println("*** The airport already exists in the database. Please try again.");
                        continue;
                    }

                    // Name
                    System.out.print("Enter the name of the airport: ");
                    String name = input.nextLine();
                    if (name.equalsIgnoreCase("cancel")) {
                        System.out.println("\nReturning to the Airport Database menu...");
                        cancel = true;
                        break;
                    }
                    if (name.isEmpty()) {
                        System.out.println("*** Invalid airport name. It must be a non-empty string.");
                        continue;
                    }

                    // Latitude
                    double latitude = 0;
                    while (true) {
                        try {
                            System.out.print("Enter the latitude of the airport: ");
                            String inputLatitude = input.nextLine();
                            if (inputLatitude.equalsIgnoreCase("cancel")) {
                                System.out.println("\nReturning to the Airport Database menu...");
                                cancel = true;
                                break;
                            }
                            latitude = Double.parseDouble(inputLatitude);
                            if (latitude < -90 || latitude > 90) {
                                System.out.println("*** Invalid latitude. It must be between -90 and 90.");
                                continue;
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
                                break;
                            }
                            longitude = Double.parseDouble(inputLongitude);
                            if (longitude < -180 || longitude > 180) {
                                System.out.println("*** Invalid longitude. It must be between -180 and 180.");
                                continue;
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
                                break;
                            }
                            commFrequencies = Double.parseDouble(inputCommFreq);
                            if (commFrequencies < 0) {
                                System.out.println("*** Invalid communication frequency. It must be a non-negative value.");
                                continue;
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
                            break;
                        }
                        if (fuelTypes.equalsIgnoreCase("JA-a") || fuelTypes.equalsIgnoreCase("AvGas")) {
                            break; // Valid input
                        } else {
                            System.out.println("*** Invalid fuel type. Please enter 'JA-a' or 'AvGas'.");
                        }
                    }
                    if (cancel) break;

                    // Add the airport to the database
                    airportManager.addAirport(icao, name, latitude, longitude, commFrequencies, fuelTypes);
                    System.out.println("Airport added successfully!");
                    break; // Exit the loop after successful addition
                }
                if (cancel) break;
                break;

            case "2":
                // REMOVE AN AIRPORT
                System.out.println("\nYou can type 'cancel' at any time to return to the Airport Database menu.");
                while (true) {
                    System.out.print("\nEnter the ICAO code of the airport to remove: ");
                    String icaoToRemove = input.nextLine();
                    if (icaoToRemove.equalsIgnoreCase("cancel")) {
                        System.out.println("\nReturning to the Airport Database menu...");
                        cancel = true;
                        break;
                    }
                    if (icaoToRemove.isEmpty() || icaoToRemove.length() != 4 || !icaoToRemove.matches("[A-Za-z]+")) {
                        System.out.println("*** Invalid ICAO code. It must be a non-empty string of 4 alphabetic characters.");
                        continue;
                    }
                    int indexToRemove = airportManager.searchAirport(icaoToRemove);
                    if (indexToRemove == -1) {
                        System.out.println("*** The airport does not exist in the database. Please try again.");
                        continue;
                    }

                    airportManager.removeAirport(indexToRemove);
                    System.out.println("Airport removed successfully!");
                    break;
                }
                if (cancel) break;
                break;

            case "3":
                // MODIFY AN AIRPORT
                System.out.println("\nYou can type 'cancel' at any time to return to the Airport Database menu.");
                int indexToModify = -1;
                while (true) {
                    System.out.print("\nEnter the ICAO code of the airport to modify: ");
                    String icaoToModify = input.nextLine();
                    if (icaoToModify.equalsIgnoreCase("cancel")) {
                        System.out.println("\nReturning to the Airport Database menu...");
                        cancel = true;
                        break;
                    }
                    if (icaoToModify.isEmpty() || icaoToModify.length() != 4 || !icaoToModify.matches("[A-Za-z]+")) {
                        System.out.println("*** Invalid ICAO code. It must be a non-empty string of 4 alphabetic characters.");
                        continue;
                    }
                    indexToModify = airportManager.searchAirport(icaoToModify);
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
                System.out.print("Enter the new ICAO code: ");
                String newIcao = input.nextLine();
                if (newIcao.equalsIgnoreCase("cancel")) {
                    System.out.println("\nReturning to the Airport Database menu...");
                    break;
                }
                if (newIcao.isEmpty() || newIcao.length() != 4 || !newIcao.matches("[A-Za-z]+")) {
                    System.out.println("*** Invalid ICAO code. It must be a non-empty string of 4 alphabetic characters.");
                    break;
                }

                System.out.print("Enter the new name of the airport: ");
                String newName = input.nextLine();
                if (newName.equalsIgnoreCase("cancel")) {
                    System.out.println("\nReturning to the Airport Database menu...");
                    break;
                }
                if (newName.isEmpty()) {
                    System.out.println("*** Invalid airport name. It must be a non-empty string.");
                    break;
                }

                double newLatitude = 0;
                while (true) {
                    try {
                        System.out.print("Enter the new latitude of the airport: ");
                        String inputLatitude = input.nextLine();
                        if (inputLatitude.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airport Database menu...");
                            cancel = true;
                            break;
                        }
                        newLatitude = Double.parseDouble(inputLatitude);
                        if (newLatitude < -90 || newLatitude > 90) {
                            System.out.println("*** Invalid latitude. It must be between -90 and 90.");
                            continue;
                        }
                        break; // Valid input
                    } catch (NumberFormatException e) {
                        System.out.println("*** Invalid input. Please enter a numeric value for latitude.");
                    }
                }
                if (cancel) break;

                double newLongitude = 0;
                while (true) {
                    try {
                        System.out.print("Enter the new longitude of the airport: ");
                        String inputLongitude = input.nextLine();
                        if (inputLongitude.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airport Database menu...");
                            cancel = true;
                            break;
                        }
                        newLongitude = Double.parseDouble(inputLongitude);
                        if (newLongitude < -180 || newLongitude > 180) {
                            System.out.println("*** Invalid longitude. It must be between -180 and 180.");
                            continue;
                        }
                        break; // Valid input
                    } catch (NumberFormatException e) {
                        System.out.println("*** Invalid input. Please enter a numeric value for longitude.");
                    }
                }
                if (cancel) break;

                double newCommFrequencies = 0;
                while (true) {
                    try {
                        System.out.print("Enter the new communication frequency of the airport: ");
                        String inputCommFreq = input.nextLine();
                        if (inputCommFreq.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airport Database menu...");
                            cancel = true;
                            break;
                        }
                        newCommFrequencies = Double.parseDouble(inputCommFreq);
                        if (newCommFrequencies < 0) {
                            System.out.println("*** Invalid communication frequency. It must be a non-negative value.");
                            continue;
                        }
                        break; // Valid input
                    } catch (NumberFormatException e) {
                        System.out.println("*** Invalid input. Please enter a numeric value for communication frequency.");
                    }
                }
                if (cancel) break;

                String newFuelTypes;
                while (true) {
                    System.out.print("Enter the new fuel types of the airport (JA-a or AvGas): ");
                    newFuelTypes = input.nextLine();
                    if (newFuelTypes.equalsIgnoreCase("cancel")) {
                        System.out.println("\nReturning to the Airport Database menu...");
                        cancel = true;
                        break;
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
                break;

            case "4":
                // VIEW A SINGLE AIRPORT
                System.out.println("\nYou can type 'cancel' at any time to return to the Airport Database menu.");
                while (true) {
                    System.out.print("\nEnter the ICAO code of the airport to view: ");
                    String icaoToView = input.nextLine();
                    if (icaoToView.equalsIgnoreCase("cancel")) {
                        System.out.println("\nReturning to the Airport Database menu...");
                        cancel = true;
                        break;
                    }
                    if (icaoToView.isEmpty() || icaoToView.length() != 4 || !icaoToView.matches("[A-Za-z]+")) {
                        System.out.println("*** Invalid ICAO code. It must be a non-empty string of 4 alphabetic characters.");
                        continue;
                    }
                    int indexToView = airportManager.searchAirport(icaoToView);
                    if (indexToView == -1) {
                        System.out.println("*** The airport does not exist in the database. Please try again.");
                        continue;
                    }

                    airportManager.displayAirport(indexToView);
                    break;
                }
                if (cancel) break;
                break;

            case "5":
                // VIEW ALL AIRPORTS
                System.out.println("\n--- All Airports in the Database ---");
                airportManager.displayAllAirports();
                System.out.println("\nReturning to the Airport Database menu...");
                break;

            case "6":
                // RETURN TO MAIN MENU
                System.out.println("Returning to the Main Menu...");
                return;

            default:
                System.out.println("*** Invalid choice. Please enter a valid option.");
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
                    System.out.println("\nYou can type 'cancel' at any time to return to the Airplane Database menu.\n");
                    while (true) {
                        // Make
                    String make;
                    while (true) {
                        System.out.print("Enter the make of the airplane: ");
                        make = input.nextLine();
                        if (make.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break;
                        }
                        if (make.isEmpty()) {
                            System.out.println("*** Invalid make. It must be a non-empty string. Please try again.");
                            continue;
                        }
                        break; // Valid input
                    }
                    if (make.equalsIgnoreCase("cancel")) break;

                    // Model
                    String model;
                    while (true) {
                        System.out.print("Enter the model of the airplane: ");
                        model = input.nextLine();
                        if (model.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break;
                        }
                        if (model.isEmpty()) {
                            System.out.println("*** Invalid model. It must be a non-empty string. Please try again.");
                            continue;
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
                    System.out.println("\nYou can type 'cancel' at any time to return to the Airplane Database menu.\n");
                    int indexToModify = -1;
                    while (true) {
                        System.out.print("Enter the make and model of the airplane to modify (e.g., 'make' 'model'): ");
                        String airplaneInput = input.nextLine();
                        if (airplaneInput.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break;
                        }

                        String[] airplaneParts = airplaneInput.split(" ", 2);
                        if (airplaneParts.length < 2) {
                            System.out.println("*** Invalid input. Please provide both make and model. Try again.");
                            continue; // Prompt the user again
                        }

                        String airplaneMakeToModify = airplaneParts[0];
                        String airplaneModelToModify = airplaneParts[1];
                        indexToModify = AirMan.searchAirplane(airplaneMakeToModify, airplaneModelToModify);
                        if (indexToModify == -1) {
                            System.out.println("*** The airplane does not exist in the database. Please try again.");
                        } else {
                            System.out.println("Airplane found!");
                            break;
                        }
                    }
                    if (indexToModify == -1) break;

                    // Make
                    String make;
                    while (true) {
                        System.out.print("Enter the make of the airplane: ");
                        make = input.nextLine();
                        if (make.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break;
                        }
                        if (make.isEmpty()) {
                            System.out.println("*** Invalid make. It must be a non-empty string. Please try again.");
                            continue;
                        }
                        break; // Valid input
                    }
                    if (make.equalsIgnoreCase("cancel")) break;

                    // Model
                    String model;
                    while (true) {
                        System.out.print("Enter the model of the airplane: ");
                        model = input.nextLine();
                        if (model.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break;
                        }
                        if (model.isEmpty()) {
                            System.out.println("*** Invalid model. It must be a non-empty string. Please try again.");
                            continue;
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
                            if (fuelCapacity <= 0) {
                                System.out.println("*** Fuel capacity must be greater than 0. Please try again.");
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
                            if (cruiseSpeed <= 0) {
                                System.out.println("*** Cruise speed must be greater than 0. Please try again.");
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
                            if (fuelBurnRate <= 0) {
                                System.out.println("*** Fuel burn rate must be greater than 0. Please try again.");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("*** Invalid input. Please enter a numeric value for fuel burn rate.");
                        }
                    }
                    if (fuelBurnRate == 0) break;

                    // Modify the airplane
                    AirplaneManager.modifyAirplane(make, model, type, fuelCapacity, cruiseSpeed, fuelBurnRate, indexToModify);
                    System.out.println("Airplane modified successfully!");
                    break;

                case "4":
                    // View a Single Airplane
                    System.out.println("\nYou can type 'cancel' at any time to return to the Airplane Database menu.\n");
                    while (true) {
                        System.out.print("Enter the make and model of the airplane to view (e.g., 'make' 'model'): ");
                        String airplaneInput = input.nextLine();
                        if (airplaneInput.equalsIgnoreCase("cancel")) {
                            System.out.println("\nReturning to the Airplane Database menu...");
                            break;
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
                    break;

                case "6":
                    // Return to the Main Menu
                    System.out.println("\nReturning to the Main Menu...");
                    return;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
