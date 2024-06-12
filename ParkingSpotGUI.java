/**
 * The ParkingSpotGUI class provides a graphical user interface for the car park management system.
 * It extends the JFrame class to create a windowed application.
 * Author: Shashank Chauhan
 * Student ID: 104168546
 * JDK version: 20.0.2
 * Program version: 1.0
 * Date Created: 16 October 2023
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ParkingSpotGUI extends JFrame {
     // UI components for displaying parking grid and output messages
    private JPanel parkingGridPanel;
    private JTextArea outputArea;
    // Instance of the CarPark management system
    private CarPark carPark;
    /**
     * Default constructor for the ParkingSpotGUI class.
     * Initializes the car park and sets up the GUI components.
     */
    public ParkingSpotGUI() {
        carPark = new CarPark(); // Initialize the CarPark

        setTitle("Parking Spot System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the Exit button at the bottom
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Create the parking grid panel
        parkingGridPanel = new JPanel(new GridLayout(5, 5)); // 5x5 grid for demonstration
        for (ParkingSlot slot : carPark.getParkingSlots()) {
            parkingGridPanel.add(createParkingSlotButton(slot));
        }

        // Create the side panel with operations, Add Slot Button
        JPanel sidePanel = new JPanel(new BorderLayout());
        JButton addSlotButton = new JButton("Add Slot");
        addSlotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String slotId = JOptionPane.showInputDialog("Enter Slot ID:");
                if (!InputValidator.isValidSlotId(slotId)) {
                    outputArea.append(
                            "Invalid parking slot ID format. Please use an uppercase letter followed by 3 digits.\n");
                    return;
                }
                if (InputValidator.isSlotIdDuplicate(slotId, carPark.getParkingSlots())) {
                    outputArea.append("The slot ID is a duplicate. Please enter a unique slot ID.\n");
                    return;
                }
                if (slotId != null && !slotId.trim().isEmpty()) {
                    carPark.addParkingSlot(slotId);
                    refreshParkingGrid();
                    outputArea.append("Parking slot " + slotId + " added.\n");
                }
            }
        });

        // Delete Slot Button
        JButton deleteSlotButton = new JButton("Delete Slot");
        deleteSlotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String slotId = JOptionPane.showInputDialog("Enter the parking slot ID to delete:");
                if (slotId != null && !slotId.isEmpty()) {
                    ParkingSlot slotToDelete = carPark.findSlotById(slotId);
                    if (slotToDelete != null && slotToDelete.isOccupied()) {
                        JOptionPane.showMessageDialog(null, " Slot is occupied by a car and cannot be deleted.");
                        return;
                    }
            boolean result = carPark.deleteParkingSlot(slotId);
                    if (result) {
                        outputArea.append("Parking slot " + slotId + " deleted successfully.\n");
                        refreshParkingGrid();
                    } else {
                        outputArea.append("Failed to delete parking slot " + slotId + ". It might not exist.\n");
                    }
                }
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        Dimension maxDim = new Dimension(Integer.MAX_VALUE, addSlotButton.getPreferredSize().height);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Show All Slots Button
        JButton showAllSlotsButton = new JButton("Show All Slots");
        showAllSlotsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputArea.append("List of all parking slots:\n");
                for (ParkingSlot slot : carPark.getParkingSlots()) {
                    if (slot.isOccupied()) {
                        Car car = slot.getParkedCar();
                        String parkedDuration = TimeCalculator.calculateTimeDifference(car.getParkingTime(),
                                LocalDateTime.now());
                        outputArea.append(String.format("Slot ID: %s (Occupied by: %s, Parked for: %s)\n",
                                slot.getSlotId(), car.getRegistrationNumber(), parkedDuration));
                    } else {
                        outputArea.append("Slot ID: " + slot.getSlotId() + " (Unoccupied)\n");
                    }
                }
            }
        });

        // park car
        JButton parkCarButton = new JButton("Park Car");
        parkCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt for car details and slot ID
                String slotId = JOptionPane.showInputDialog("Enter Slot ID:");
                String regNumber = JOptionPane.showInputDialog("Enter Car Registration Number:");
                String make = JOptionPane.showInputDialog("Enter Car Make:");
                String model = JOptionPane.showInputDialog("Enter Car Model:");
                String year = JOptionPane.showInputDialog("Enter Car Year:");
                if (!InputValidator.isValidSlotId(slotId)) {
                    outputArea.append(
                            "Invalid parking slot ID format. Please use an uppercase letter followed by 3 digits.\n");
                    return;
                }
                if (!InputValidator.isValidRegistrationNumber(regNumber)) {
                    outputArea.append(
                            "Invalid Registration ID format. Please use an uppercase letter followed by 4 digits.\n");
                    return;
                }
                // New validation check to check if car with same registration number is already parked
                if (carPark.isCarAlreadyParked(regNumber)) {
                    outputArea.append(" Car with registration number " + regNumber + " is already parked in another slot.\n");
                    return;
                }                
                // New Validation to check if car with given slot Id exists already
                if (!carPark.doesSlotExist(slotId)) {
                    outputArea.append("No slot with ID " + slotId + " found.\n");
                    return;
                }

                // New validation check for missing details
                if (slotId == null || regNumber == null || make == null || model == null || year == null ||
                    slotId.trim().isEmpty() || regNumber.trim().isEmpty() || make.trim().isEmpty() || model.trim().isEmpty() || year.trim().isEmpty()) {
                    outputArea.append(" All details to park the car were not given or missing.\n");
                    return;
                }

                // New validation check for integer year
                try {
                    Integer.parseInt(year);
                } catch (NumberFormatException ex) {
                    outputArea.append("Please enter a valid year value.\n");
                    return;
                }
                // new validation to try to handle events where any parking details are missing
                if (slotId != null && regNumber != null && make != null && model != null && year != null) {
                    Car car = new Car(regNumber, make, model, Integer.parseInt(year));
                    boolean parkedSuccessfully = carPark.parkCar(slotId, car);
                    refreshParkingGrid();
                    if (parkedSuccessfully) {
                        outputArea.append("Car parked in " + slotId + " at " + car.getParkingTime() + "\n");
                    }
                    else {
                        outputArea.append("Parking slot " + slotId + " is already occupied.\n");
                    }
                }
            }
        });

        addSlotButton.setMaximumSize(maxDim);
        deleteSlotButton.setMaximumSize(maxDim);
        showAllSlotsButton.setMaximumSize(maxDim);
        parkCarButton.setMaximumSize(maxDim);
        buttonPanel.add(addSlotButton);
        buttonPanel.add(deleteSlotButton);
        buttonPanel.add(showAllSlotsButton);
        buttonPanel.add(parkCarButton);

        // Button to find a car by registration
        JButton findCarByRegButton = new JButton("Find Car by Registration");
        findCarByRegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String regNumber = JOptionPane.showInputDialog("Enter Car Registration Number:");
                if (regNumber != null && !regNumber.trim().isEmpty()) {
                    ParkingSlot slot = carPark.findCarByRegistration(regNumber);
                    if (slot != null) {
                        outputArea.append(
                                "Car with registration " + regNumber + " is parked in slot " + slot.getSlotId() + "\n");
                    } else {
                        outputArea.append("No car with registration " + regNumber + " is currently parked.\n");
                    }
                }
            }
        });

        // Button to find cars by make
        JButton findCarsByMakeButton = new JButton("Find Cars by Make");
        findCarsByMakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String make = JOptionPane.showInputDialog("Enter Car Make:");
                if (make != null && !make.trim().isEmpty()) {
                    ArrayList<ParkingSlot> slots = carPark.findCarsByMake(make);
                    if (!slots.isEmpty()) {
                        outputArea.append("Cars of make " + make + " are parked in the following slots:\n");
                        for (ParkingSlot slot : slots) {
                            Car car = slot.getParkedCar();
                            outputArea.append(car.toString() + "\n");
                        }
                    } else {
                        outputArea.append("No cars of make " + make + " are currently parked.\n");
                    }
                }
            }
        });

        // Button to remove a car by registration
        JButton removeCarByRegButton = new JButton("Remove Car by Registration");
        removeCarByRegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String regNumber = JOptionPane.showInputDialog("Enter Car Registration Number to Remove:");
                if (regNumber != null && !regNumber.trim().isEmpty()) {
                    if (carPark.removeCarByRegistration(regNumber)) {
                        refreshParkingGrid();
                        outputArea.append("Car with registration " + regNumber + " has been removed.\n");
                    } else {
                        outputArea.append("No car with registration " + regNumber + " is currently parked.\n");
                    }
                }
            }
        });

        // Add the new buttons to the side panel
        JPanel findAndRemovePanel = new JPanel(new GridLayout(3, 1));
        findAndRemovePanel.add(findCarByRegButton);
        findAndRemovePanel.add(findCarsByMakeButton);
        findAndRemovePanel.add(removeCarByRegButton);
        findAndRemovePanel.add(exitButton);
        sidePanel.add(findAndRemovePanel, BorderLayout.SOUTH);
        sidePanel.add(buttonPanel, BorderLayout.NORTH);

        // Create the output area for messages
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        // Add components to the main frame
        add(parkingGridPanel, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);
    }

    // Method to set the button color based on slot occupancy
    private void setButtonColorBasedOnOccupancy(ParkingSlot slot, JButton button) {
        if (slot.isOccupied()) {
            button.setBackground(Color.RED); // Red indicates occupied slot
        } else {
            button.setBackground(Color.GREEN); // Green indicates unoccupied slot
        }
    }

    // Method to handle the actions when a slot button is clicked
    private void handleSlotButtonClick(ParkingSlot slot, JButton button) {
        if (slot.isOccupied()) {
            Car parkedCar = slot.getParkedCar();
            int choice = JOptionPane.showOptionDialog(this,
                    "Car Details:\n" + parkedCar.toString(),
                    "Slot Occupied",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[] { "Remove Car", "Cancel" },
                    "default");

            if (choice == JOptionPane.YES_OPTION) {
                carPark.removeCarByRegistration(parkedCar.getRegistrationNumber());
                refreshParkingGrid();
                outputArea.append("Car removed from " + slot.getSlotId() + "\n");
            }

        } else {
            String regNumber = JOptionPane.showInputDialog("Enter Car Registration Number to Park:");
            String make = JOptionPane.showInputDialog("Enter Car Make:");
            String model = JOptionPane.showInputDialog("Enter Car Model:");
            String year = JOptionPane.showInputDialog("Enter Car Year:");
            String slotId = slot.getSlotId();
            if (!InputValidator.isValidSlotId(slotId)) {
                outputArea.append(
                        "Invalid parking slot ID format. Please use an uppercase letter followed by 3 digits.\n");
                return;
            }
            if (!InputValidator.isValidRegistrationNumber(regNumber)) {
                outputArea.append(
                        "Invalid Registration ID format. Please use an uppercase letter followed by 4 digits.\n");
                return;
            }
            // New validation check to check if car with same registration number is parked already
            if (carPark.isCarAlreadyParked(regNumber)) {
                outputArea.append("Car with registration number " + regNumber + " is already parked in another slot.\n");
                return;
            }
            // New validation check to check if car with given slot id exists
            if (!carPark.doesSlotExist(slotId)) {
                outputArea.append(" No slot with ID " + slotId + " found.\n");
                return;
            }

             // New validation check for missing details
             if (slotId == null || regNumber == null || make == null || model == null || year == null ||
             slotId.trim().isEmpty() || regNumber.trim().isEmpty() || make.trim().isEmpty() || model.trim().isEmpty() || year.trim().isEmpty()) 
             {
             outputArea.append(" All details to park the car were not given or missing.\n");
             return;
            }

            // New validation check for integer year
            try {
                Integer.parseInt(year);
            } catch (NumberFormatException ex) {
                outputArea.append("Please enter a valid year value.\n");
                return;
            }


            if (regNumber != null && !regNumber.trim().isEmpty()) {
                Car car = new Car(regNumber, make, model, Integer.parseInt(year));
                carPark.parkCar(slotId, car);
                refreshParkingGrid();
                outputArea.append("Car parked in " + slot.getSlotId() + " at " + car.getParkingTime() + "\n");
            }
        }
    }

    private JButton createParkingSlotButton(ParkingSlot slot) {
        JButton slotButton = new JButton(slot.getSlotId());
        setButtonColorBasedOnOccupancy(slot, slotButton);
        slotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSlotButtonClick(slot, slotButton);
            }
        });
        return slotButton;
    }

    // Method to refresh the parking grid based on the current state of the CarPark
    private void refreshParkingGrid() {
        parkingGridPanel.removeAll();
        for (ParkingSlot slot : carPark.getParkingSlots()) {
            parkingGridPanel.add(createParkingSlotButton(slot));
        }
        parkingGridPanel.revalidate();
        parkingGridPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ParkingSpotGUI gui = new ParkingSpotGUI();
            gui.setVisible(true);
        });
    }
}
