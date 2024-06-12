
/**
 * The CarPark class represents a car park management system.
 * It manages parking slots and provides functionality to add, delete, list,
 * park, find, and remove cars.
 * 
 * Author: Shashank Chauhan
 * Student ID: 104168546
 * JDK version: 20.0.2
 * Program version: 1.0
 * Date Created: 28 August 2023
 */

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CarPark {
    private ArrayList<ParkingSlot> parkingSlots; // Holds the list of parking slots

    /**
     * Constructor to create a new CarPark instance.
     * Initializes the list of parking slots.
     */
    public CarPark() {
        parkingSlots = new ArrayList<>();
    }

    /**
     * Adds a parking slot to the car park.
     * 
     * @param slotId The ID of the parking slot to add.
     */
    public void addParkingSlot(String slotId) {
        // Validate and add the parking slot
        if (!InputValidator.isValidSlotId(slotId)) {
            return;
        }

        if (InputValidator.isSlotIdDuplicate(slotId, parkingSlots)) {
            return;
        }

        ParkingSlot newSlot = new ParkingSlot(slotId);
        parkingSlots.add(newSlot);
        
    }

    /**
     * Deletes a parking slot from the car park.
     * 
     * @param slotId The ID of the parking slot to delete.
     */
    public boolean deleteParkingSlot(String slotId) {
        ParkingSlot slotToDelete = findSlotById(slotId);
        if (slotToDelete != null) {
            parkingSlots.remove(slotToDelete);
            return true; // Slot was found and deleted
        }
        return false; // Slot was not found
    }

    /**
     * Lists all parking slots and their occupancy status.
     */
    public void listAllSlots() {
        System.out.println("List of all parking slots:");
        for (ParkingSlot slot : parkingSlots) {
            System.out.print("Slot ID: " + slot.getSlotId());
            if (slot.isOccupied()) {
                LocalDateTime parkedTime = slot.getParkedCar().getParkingTime();
                LocalDateTime parkedAt = parkedTime;
                String timeDifference = TimeCalculator.calculateTimeDifference(parkedAt, LocalDateTime.now());
                System.out.println(" (Occupied by: " + slot.getParkedCar().getRegistrationNumber() + ", Parked for: "
                        + timeDifference + ")");
            } else {
                System.out.println(" (Unoccupied)");
            }
        }
    }

    /**
     * Parks a car in a specific parking slot.
     * 
     * @param slotId The ID of the parking slot.
     * @param car    The car to be parked.
     * @return True if the car was parked successfully, false otherwise.
     */
    public boolean parkCar(String slotId, Car car) {
        // Validate car registration
        if (!InputValidator.isValidRegistrationNumber(car.getRegistrationNumber())) {
            System.out.println("Invalid car registration format.");
            return false;
        }
        ParkingSlot targetSlot = null;
        for (ParkingSlot slot : parkingSlots) {
            if (slot.getSlotId().equals(slotId)) {
                if (!slot.isOccupied()) {
                    targetSlot = slot;
                    break;
                } else {;
                    return false;
                }
            }
        }
        if (targetSlot != null) {
            targetSlot.parkCar(car);
            return true;
        } else {
            return false;
        }
    }
    //check for same registration number
    public boolean isCarAlreadyParked(String regNumber) {
        for (ParkingSlot slot : parkingSlots) {
            Car car = slot.getParkedCar();
            if (car != null && car.getRegistrationNumber().equals(regNumber)) {
                return true;  // Car with the given registration number is already parked
            }
        }
        return false;
    }
    

    /**
     * Finds a car in the car park by its registration number.
     * 
     * @param regNumber The registration number of the car to find.
     * @return The found car or null if not found.
     */
    public ParkingSlot findCarByRegistration(String regNumber) {
        for (ParkingSlot slot : parkingSlots) {
            Car car = slot.getParkedCar();
            if (car != null && car.getRegistrationNumber().equals(regNumber)) {
                return slot; // Return the parking slot where the car is parked
            }
        }
        return null; // Return null if the car is not found
    }

    /**
     * Removes a car from the car park by its registration number.
     * 
     * @param regNumber The registration number of the car to remove.
     * @return True if the car was removed successfully, false otherwise.
     */
    public boolean removeCarByRegistration(String regNumber) {
        for (ParkingSlot slot : parkingSlots) {
            Car car = slot.getParkedCar();
            if (car != null && car.getRegistrationNumber().equals(regNumber)) {
                slot.removeCar(); // Remove the car from the parking slot
                return true; // Indicate successful removal
            }
        }
        return false; // Indicate that the car was not found
    }

    /**
     * Finds cars in the car park by their make.
     * 
     * @param make The make of the cars to find.
     * @return A list of found cars or null if none found.
     */
    public ArrayList<ParkingSlot> findCarsByMake(String make) {
        ArrayList<ParkingSlot> slotsWithGivenMake = new ArrayList<>();
        for (ParkingSlot slot : parkingSlots) {
            Car car = slot.getParkedCar();
            if (car != null && car.getMake().equalsIgnoreCase(make)) {
                slotsWithGivenMake.add(slot); // Add the parking slot to the list
            }
        }
        return slotsWithGivenMake; // Return the list of parking slots
    }

    public ParkingSlot findSlotById(String slotId) {
        for (ParkingSlot slot : parkingSlots) {
            if (slot.getSlotId().equals(slotId)) {
                return slot;
            }
        }
        return null; // If slot with the given ID is not found
    }

    public boolean doesSlotExist(String slotId) {
        for (ParkingSlot slot : parkingSlots) {
            if (slot.getSlotId().equals(slotId)) {
                return true;  // Slot with the given ID exists
            }
        }
        return false;
    }
    
    public ArrayList<ParkingSlot> getParkingSlots() {
        return parkingSlots;
    }

}
