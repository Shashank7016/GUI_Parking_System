
/**
 * The InputValidator class provides static methods for validating input data related to the car park management system.
 * It includes methods for checking the validity of parking slot IDs and car registration numbers.
 * 
 * Author: Shashank Chauhan
 * Student ID: 104168546
 * JDK version: 20.0.2
 * Program version: 1.0
 * Date Created: 28 August 2023
 */
import java.util.ArrayList;

public class InputValidator {

    /**
     * Checks if a given parking slot ID follows the required format of an uppercase
     * letter followed by 3 digits.
     * 
     * @param slotId The parking slot ID to validate.
     * @return True if the slot ID is valid, false otherwise.
     */
    public static boolean isValidSlotId(String slotId) {
        // Check if the slotId follows the required format (uppercase letter followed by
        // 3 digits)
        return slotId.matches("^[A-Z]\\d{3}$");
    }

    /**
     * Checks if a given car registration number follows the required format of an
     * uppercase letter followed by 4 digits.
     * 
     * @param regNumber The car registration number to validate.
     * @return True if the registration number is valid, false otherwise.
     */
    public static boolean isValidRegistrationNumber(String regNumber) {
        // Check if the regNumber follows the required format (uppercase letter followed
        // by 4 digits)
        return regNumber.matches("^[A-Z]\\d{4}$");
    }

    /**
     * Checks if a given parking slot ID is a duplicate in the provided list of
     * parking slots.
     * 
     * @param slotId       The parking slot ID to check for duplicates.
     * @param parkingSlots The list of parking slots to search for duplicates.
     * @return True if the slot ID is a duplicate, false otherwise.
     */
    public static boolean isSlotIdDuplicate(String slotId, ArrayList<ParkingSlot> parkingSlots) {
        for (ParkingSlot slot : parkingSlots) {
            if (slot.getSlotId().equals(slotId)) {
                return true; // Slot ID already exists
            }
        }
        return false; // Slot ID is not a duplicate
    }
}
