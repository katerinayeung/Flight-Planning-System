/* Airplane.java
   This class contains the properties and methods for an airplane object
   It includes the make, model, type, fuel capacity, cruise speed, and fuel burn rate
   It also includes methods to calculate the range of the airplane and display its information */
package FPS;
public class Airplane {
    //attributes
    private String make;
    private String model;
    private String type;
    private double fuelCapacity;
    private double cruiseSpeed;
    private double fuelBurnrate;
    //constructor
    public Airplane(String make, String model, String type, double fuelCapacity, double cruiseSpeed, double fuelBurnrate) {
        this.make = make;
        this.model = model;
        this.type = type;
        this.fuelCapacity = fuelCapacity;
        this.cruiseSpeed = cruiseSpeed;
        this.fuelBurnrate = fuelBurnrate;
    }
    //default constructor
    public Airplane() {
        this.make = "";
        this.model = "";
        this.type = "";
        this.fuelCapacity = 0;
        this.cruiseSpeed = 0;
        this.fuelBurnrate = 0;
    }
    //getters and setters
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public double getCruiseSpeed() {
        return cruiseSpeed;
    }

    public void setCruiseSpeed(double cruiseSpeed) {
        this.cruiseSpeed = cruiseSpeed;
    }

    public double getFuelBurnrate() {
        return fuelBurnrate;
    }

    public void setFuelBurnrate(double fuelBurnrate) {
        this.fuelBurnrate = fuelBurnrate;
    }
    //calculates range of plane based on fuel capacity, cruise speed, and fuel burn rate
    public double calculateRange() {
        return (fuelCapacity / fuelBurnrate)*cruiseSpeed;
        
    }
    // Prints the information of the airplane to the console
    public void displayInfo() {
        System.out.println("Make: " + make);
        System.out.println("Model: " + model);
        System.out.println("Type: " + type);
        System.out.println("Fuel Capacity: " + fuelCapacity);
        System.out.println("Cruise Speed: " + cruiseSpeed);
        System.out.println("Fuel Burnrate: " + fuelBurnrate);
    }
    @Override
    public String toString() {
        return "Airplane{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", fuelCapacity=" + fuelCapacity +
                ", cruiseSpeed=" + cruiseSpeed +
                ", fuelBurnrate=" + fuelBurnrate +
                '}';
    }


}