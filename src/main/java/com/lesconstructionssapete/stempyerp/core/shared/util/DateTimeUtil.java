package com.lesconstructionssapete.stempyerp.core.shared.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Utility class for working with dates and times.
 */
public final class DateTimeUtil {

  private DateTimeUtil() {
  }

  /**
   * ==========================
   * FORMATTING / PARSING
   * ==========================
   */

  // Format LocalDateTime into string with given pattern
  public static String format(LocalDateTime dateTime, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

    return dateTime.format(formatter);
  }

  // Parse string into LocalDateTime with given pattern
  public static LocalDateTime parse(String dateTimeStr, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDateTime.parse(dateTimeStr, formatter);
  }

  /**
   * ==========================
   * TIME ZONE HELPERS
   * ==========================
   */

  // Convert LocalDateTime from one timezone to another
  public static LocalDateTime convertZone(LocalDateTime dateTime, ZoneId fromZone, ZoneId toZone) {
    ZonedDateTime zoned = dateTime.atZone(fromZone);
    return zoned.withZoneSameInstant(toZone).toLocalDateTime();
  }

  // Get current time in a specific timezone
  public static LocalDateTime nowInZone(ZoneId zone) {
    return LocalDateTime.now(zone);
  }

  /**
   * ==========================
   * DATE MANIPULATION
   * ==========================
   */

  // Remove time part (midnight)
  public static LocalDate toDateOnly(LocalDateTime dateTime) {
    return dateTime.toLocalDate();
  }

  // Truncate to start of day
  public static LocalDateTime startOfDay(LocalDateTime dateTime) {
    return dateTime.toLocalDate().atStartOfDay();
  }

  // Truncate to end of day
  public static LocalDateTime endOfDay(LocalDateTime dateTime) {
    return dateTime.toLocalDate().atTime(LocalTime.MAX);
  }

  // Add days
  public static LocalDateTime addDays(LocalDateTime dateTime, int days) {
    return dateTime.plusDays(days);
  }

  // Subtract days
  public static LocalDateTime subtractDays(LocalDateTime dateTime, int days) {
    return dateTime.minusDays(days);
  }

  /**
   * ==========================
   * CONVERSIONS
   * ==========================
   */

  // Convert LocalDateTime to Instant (UTC)
  public static Instant toInstant(LocalDateTime dateTime, ZoneId zone) {
    return dateTime.atZone(zone).toInstant();
  }

  // Convert Instant to LocalDateTime in a given zone
  public static LocalDateTime fromInstant(Instant instant, ZoneId zone) {
    return LocalDateTime.ofInstant(instant, zone);
  }

  /**
   * ==========================
   * RUN TIMES HELPERS
   * ==========================
   */

  // Parse string like ["08:00:00","12:30:00"] -> List<LocalTime>
  public static List<LocalTime> parseRunTimes(String json) {
    if (json == null || json.isBlank()) {
      return new ArrayList<>();
    }
    String cleaned = json.trim();
    if (cleaned.startsWith("["))
      cleaned = cleaned.substring(1);
    if (cleaned.endsWith("]"))
      cleaned = cleaned.substring(0, cleaned.length() - 1);

    List<LocalTime> result = new ArrayList<>();
    if (!cleaned.isBlank()) {
      String[] parts = cleaned.split(",");
      for (String part : parts) {
        String timeStr = part.trim().replace("\"", ""); // remove quotes
        result.add(LocalTime.parse(timeStr)); // expects HH:mm or HH:mm:ss
      }
    }
    return result;
  }

  // Convert List<LocalTime> -> string like ["08:00:00","12:30:00"]
  public static String toRunTimesJson(List<LocalTime> runTimes) {
    if (runTimes == null || runTimes.isEmpty()) {
      return "[]";
    }
    return runTimes.stream()
        .map(t -> "\"" + t.toString() + "\"") // LocalTime.toString() -> "HH:mm:ss"
        .collect(Collectors.joining(",", "[", "]"));
  }
}
