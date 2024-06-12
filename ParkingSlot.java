/**
 * The ParkingSlot class represents a parking slot in the car park management
 * system.
 * It tracks the slot's ID and whether it is occupied by a parked car.
 * 
 * Author: Shashank Chauhan
 * Student ID: 104168546
 * JDK version: 20.0.2
 * Program version: 1.0
 * Date Created: 28 August 2023
 */
public class ParkingSlot {
    private String slotId; // Holds the ID of the parking slot
    private Car parkedCar; // Holds the parked car in the parking slot

    /**
     * Constructor to create a new ParkingSlot instance with the provided slot ID.
     * Initializes the parking slot as unoccupied (no parked car).
     * 
     * @param slotId The ID of the parking slot.
     */
    public ParkingSlot(String slotId) {
        this.slotId = slotId;
        this.parkedCar = null;
    }

    /**
     * Gets the ID of the parking slot.
     * 
     * @return The ID of the parking slot.
     */
    public String getSlotId() {
        return slotId;
    }

    /**
     * Gets the car parked in the parking slot.
     * 
     * @return The parked car in the parking slot, or null if unoccupied.
     */
    public Car getParkedCar() {
        return parkedCar;
    }

    /**
     * Checks if the parking slot is occupied by a parked car.
     * 
     * @return True if the parking slot is occupied, false otherwise.
     */
    public boolean isOccupied() {
        return parkedCar != null;
    }

    /**
     * Parks a car in the parking slot if it is unoccupied.
     * 
     * @param car The car to be parked in the slot.
     * @return True if the car was parked successfully, false otherwise.
     */
    public boolean parkCar(Car car) {
        if (!isOccupied()) {
            parkedCar = car;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a car from the parking slot.
     * 
     * @return The car that was removed from the slot, or null if unoccupied.
     */
    public Car removeCar() {
        if (isOccupied()) {
            Car carToBeRemoved = parkedCar;
            parkedCar = null;
            System.out.println("Car removed from slot " + slotId + " successfully.");
            return carToBeRemoved;
        } else {
            System.out.println("Parking slot " + slotId + " is already empty.");
            return null;
        }
    }
}
