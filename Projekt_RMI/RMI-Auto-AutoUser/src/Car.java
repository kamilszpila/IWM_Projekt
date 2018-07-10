import java.io.IOException;
import java.io.Serializable;

public class Car implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int carID;
    private int year;
    private String brand;
    private String model;
    private String VIN;
    private double mileage;
    private int driver;
    private double fuelLevel;
    private int maxFuelLevel;
    private static int carCounter = 0;
    
    public Car(int year, String brand, String model, String VIN, double mileage, int maxFuelLevel) throws IOException{
        
    	carCounter++;
    	this.setID(carCounter);
    	this.setYear(year);
        this.setBrand(brand);
        this.setModel(model);
        this.setVIN(VIN);
        this.setMileage(mileage);
        this.setDriver(0);
        this.setFuelLevel(maxFuelLevel);
        this.setMaxFuelLevel(maxFuelLevel);
        
        
    }
    
    //set methods
    public void setID(int ID) {
        this.carID = ID;}

    public void setYear(int year) {
        this.year = year;}

    public void setBrand(String brand) {
        this.brand = brand;}

    public void setModel(String model) {
        this.model = model;}
    
    public void setVIN(String VIN) {
        this.VIN = VIN;}

    public void setMileage(double mileage) {
        this.mileage = mileage;}

    public void setDriver(int driver) {
        this.driver = driver;}

    public void setFuelLevel(double FuelLevel) {
    		this.fuelLevel = FuelLevel;}
    
    public void setMaxFuelLevel(int maxFuelLevel) {
        this.maxFuelLevel = maxFuelLevel;}
    
    public static void setCounter(int counter) {
        Car.carCounter = counter;}

    
    //get methods
    public int getID() {
    	return this.carID;}
    
    public int getYear() {
        return this.year;}

    public String getBrand() {
        return this.brand;}

    public String getModel() {
        return this.model;}

    public String getVIN() {
        return this.VIN;}

    public double getMileage() {
        return this.mileage;}

    public int getDriver() {
        return this.driver;}

    public double getFuelLevel() {
    	return this.fuelLevel;}
    
    public int getMaxFuelLevel() {
    	return this.maxFuelLevel;}
    
    public static int getCounter() {
    	return carCounter;}


    public void sumJourney(double distance, double fuel) {
    	setMileage((getMileage()+distance));
    	setFuelLevel((getFuelLevel()-fuel));
    	setDriver(0);
    }
    
    public String toString() {
        return "ID: " + this.carID + ", Year: " + this.year 
        		+ ", Brand: " +  this.brand 
        		+ ", Model: " + this.model 
        		+ ", VIN: " + this.VIN 
        		+ ", Mileage: " +  this.mileage 
        		+ ", Driver: " + this.driver
        		+ ", Fuel level: " + this.fuelLevel;
    }    
    

}
