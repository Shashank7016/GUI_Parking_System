import java.time.LocalDateTime;

/**
 * The Car class represents a car with its registration details and parking
 * information.
 * It provides methods to get and set car attributes, as well as an overridden
 * toString method.
 * 
 * Author: Shashank Chauhan
 * Student ID: 104168546
 * JDK version: 20.0.2
 * Program version: 1.0
 * Date Created: 28 August 2023
 */
public class Car {
    private String registrationNumber; // Holds the registration number of the car
    private String make; // Holds the make (manufacturer) of the car
    private String model; // Holds the model of the car
    private int year; // Holds the manufacturing year of the car
    private LocalDateTime parkingTime; // Holds the parking time of the car

    /**
     * Constructor to create a new Car instance with the provided attributes.
     * The parking time is automatically set to the current time when the car is
     * created.
     * 
     * @param registrationNumber The registration number of the car.
     * @param make               The make (manufacturer) of the car.
     * @param model              The model of the car.
     * @param year               The manufacturing year of the car.
     */
    public Car(String registrationNumber, String make, String model, int year) {
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.parkingTime = LocalDateTime.now();
    }

    /**
     * Gets the registration number of the car.
     * 
     * @return The registration number of the car.
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Gets the make (manufacturer) of the car.
     * 
     * @return The make of the car.
     */
    public String getMake() {
        return make;
    }

    /**
     * Gets the model of the car.
     * 
     * @return The model of the car.
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets the manufacturing year of the car.
     * 
     * @return The manufacturing year of the car.
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the parking time of the car.
     * 
     * @return The parking time of the car.
     */
    public LocalDateTime getParkingTime() {
        return parkingTime;
    }

    /**
     * Sets the parking time of the car to the current time.
     */
    public void setParkingTime() {
        this.parkingTime = LocalDateTime.now();
    }

    /**
     * Overrides the toString method to provide a string representation of the Car.
     * 
     * @return A string representation of the Car object.
     */
    @Override
    public String toString() {
        String parkedDuration = TimeCalculator.calculateTimeDifference(parkingTime, LocalDateTime.now());
        return String.format(
                "Car[Registration Number: %s, Make: %s, Model: %s, Year: %d, Parked for: %s, Parking Time: %s]",
                registrationNumber, make, model, year, parkedDuration, parkingTime);
    }
}
