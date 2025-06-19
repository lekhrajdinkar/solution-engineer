package java8.DateTime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

public class DateTime_1
{
    public static void main(String[] args)
    {
        // Current date and time
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Custom date and time
        LocalDate customDate = LocalDate.of(2023, Month.NOVEMBER, 18);
        LocalTime customTime = LocalTime.of(14, 30, 45);
        LocalDateTime customDateTime = LocalDateTime.of(2023, Month.NOVEMBER, 18, 14, 30);

        // Formatting and parsing
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);
        LocalDateTime parsedDateTime = LocalDateTime.parse("18-11-2024 14:30", formatter);

        // Operations on date and time
        LocalDate nextWeek = currentDate.plus(1, ChronoUnit.WEEKS);
        LocalDate previousMonth = currentDate.minusMonths(1);
        Period period = Period.between(customDate, currentDate);

        // Time zones
        ZoneId zoneId = ZoneId.of("America/New_York");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(currentDateTime, zoneId);
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();

        // Duration and Period
        Duration duration = Duration.between(currentTime, customTime);
        Period difference = Period.between(customDate, currentDate);

        // Instant for timestamps
        Instant currentInstant = Instant.now();
        Instant instantFromEpoch = Instant.ofEpochSecond(1633024800);

        // Adjusters
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        LocalDate nextSunday = currentDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        // Clock
        Clock clock = Clock.systemUTC();
        LocalDateTime dateTimeWithClock = LocalDateTime.now(clock);

        // Printing examples
        System.out.println("Current Date: " + currentDate);
        System.out.println("Formatted DateTime: " + formattedDateTime);
        System.out.println("Zoned DateTime: " + zonedDateTime);
        System.out.println("Period Difference: " + period);
        System.out.println("Available Time Zones: " + availableZoneIds.size());


        // ====================================
        // Get the current date and time with the system default time zone
        ZonedDateTime currentZonedDateTime = ZonedDateTime.now();
        System.out.println("Current ZonedDateTime: " + currentZonedDateTime);

        // Create a ZonedDateTime for a specific date, time, and time zone
        ZonedDateTime customZonedDateTime = ZonedDateTime.of(2024, 11, 18, 14, 30, 0, 0, ZoneId.of("America/New_York"));
        System.out.println("Custom ZonedDateTime (America/New_York): " + customZonedDateTime);

        // Convert the current ZonedDateTime to another time zone
        ZonedDateTime convertedZonedDateTime = currentZonedDateTime.withZoneSameInstant(ZoneId.of("Europe/London"));
        System.out.println("Converted ZonedDateTime (Europe/London): " + convertedZonedDateTime);

        // Formatting ZonedDateTime
         formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm z");
        String formattedZonedDateTime = customZonedDateTime.format(formatter);
        System.out.println("Formatted Custom ZonedDateTime: " + formattedZonedDateTime);

        // Parsing a ZonedDateTime from a string
        String dateTimeString = "18-11-2024 14:30 PST";
        DateTimeFormatter parseFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm z");
        ZonedDateTime parsedZonedDateTime = ZonedDateTime.parse(dateTimeString, parseFormatter);
        System.out.println("Parsed ZonedDateTime: " + parsedZonedDateTime);
    }
}

