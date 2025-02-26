public class Airports{
    //attributes
    String icao = "";
	String name = "";
	double latitude = 0.0;
	double longitude = 0;
	double commFrequencies = 0.0;
    String fuelTypes = "";

    //constructor
    public Airports(String icao, String name, double latitude, double longitude, double commFrequencies, String fuelTypes){
        this.icao = icao;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.commFrequencies = commFrequencies;
        this.fuelTypes = fuelTypes;
    }

    public String getIcao(){
        return icao;
    }

    public String getName(){
        return name;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getCommFrequencies(){
        return commFrequencies;
    }

    public String getFuelTypes(){
        return fuelTypes;
    }

    public void setIcao(String icao){
        this.icao = icao;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public void setCommFrequencies(double commFrequencies){
        this.commFrequencies = commFrequencies;
    }

    public void setFuelTypes(String fuelTypes){
        this.fuelTypes = fuelTypes;
    }
	
    @Override
	public String toString() {
		return "ICAO: " + icao + "\n"
				+ "Name: " + name + "\n"
				+ "Latitude: " + latitude + "\n"
				+ "Longitude: " + longitude + "\n"
				+ "Fuel Type: " + fuelTypes;
    }   
}
