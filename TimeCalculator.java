
/**
 * The TimeCalculator class provides static methods for calculating the time difference between two LocalDateTime instances.
 * It calculates the difference in hours, minutes, and seconds.
 * 
 * Author: Shashank Chauhan
 * Student ID: 104168546
 * JDK version: 20.0.2
 * Program version: 1.0
 * Date Created: 28 August 2023
 */
import java.time.Duration;
import java.time.LocalDateTime;

public class TimeCalculator {

    /**
     * Calculates the time difference between two LocalDateTime instances.
     * 
     * @param startTime The start time.
     * @param endTime   The end time.
     * @return A formatted string indicating the time difference in hours, minutes,
     *         and seconds.
     */
    public static String calculateTimeDifference(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        return hours + " hours " + minutes + " minutes " + seconds + " seconds";
    }
}
