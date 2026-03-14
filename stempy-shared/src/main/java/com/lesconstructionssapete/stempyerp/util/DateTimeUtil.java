package com.lesconstructionssapete.stempyerp.util;

import java.time.DayOfWeek;
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

/**
 * Utility class for date/time operations, formatting, parsing, and timezone
 * conversions.
 * Uses java.time API (Instant, LocalDateTime, ZonedDateTime, etc.) for modern
 * date/time handling.
 */
public final class DateTimeUtil {

  private DateTimeUtil() {
  }

  public static final ZoneId UTC = ZoneId.of("UTC");

  /**
   * ==========================
   * FORMATTING / PARSING
   * ==========================
   */

  // Format Instant using a timezone
  public static String format(Instant instant, String pattern, ZoneId zone) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(zone);
    return formatter.format(instant);
  }

  // Parse string to Instant using a timezone
  public static Instant parse(String dateTimeStr, String pattern, ZoneId zone) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    LocalDateTime ldt = LocalDateTime.parse(dateTimeStr, formatter);
    return ldt.atZone(zone).toInstant();
  }

  /**
   * ==========================
   * TIME ZONE CONVERSION
   * ==========================
   */

  // Convert Instant to LocalDateTime in zone
  public static LocalDateTime toLocalDateTime(Instant instant, ZoneId zone) {
    return LocalDateTime.ofInstant(instant, zone);
  }

  // Convert Instant to ZonedDateTime
  public static ZonedDateTime toZonedDateTime(Instant instant, ZoneId zone) {
    return instant.atZone(zone);
  }

  // Convert LocalDateTime in zone to Instant
  public static Instant toInstant(LocalDateTime dateTime, ZoneId zone) {
    return dateTime.atZone(zone).toInstant();
  }

  /**
   * ==========================
   * DATE HELPERS
   * ==========================
   */

  // Extract LocalDate from Instant
  public static LocalDate toDateOnly(Instant instant, ZoneId zone) {
    return instant.atZone(zone).toLocalDate();
  }

  // Start of day in zone → Instant
  public static Instant startOfDay(LocalDate date, ZoneId zone) {
    return date.atStartOfDay(zone).toInstant();
  }

  // End of day in zone → Instant
  public static Instant endOfDay(LocalDate date, ZoneId zone) {
    return date.plusDays(1).atStartOfDay(zone).minusNanos(1).toInstant();
  }

  /**
   * ==========================
   * INSTANT MANIPULATION
   * ==========================
   */

  public static Instant addDays(Instant instant, long days) {
    return instant.plusSeconds(days * 86400);
  }

  public static Instant subtractDays(Instant instant, long days) {
    return instant.minusSeconds(days * 86400);
  }

  /**
   * ==========================
   * RUN TIMES/DAYS HELPERS
   * ==========================
   */

  // Check if today (in zone) is in the list of days
  public static boolean isTodayIn(List<DayOfWeek> days, ZoneId zone) {
    DayOfWeek today = LocalDate.now(zone).getDayOfWeek();
    return days.contains(today);
  }

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
        String timeStr = part.trim().replace("\"", "");
        result.add(LocalTime.parse(timeStr));
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
        .map(t -> "\"" + t.toString() + "\"")
        .collect(Collectors.joining(",", "[", "]"));
  }

  // Parse string like [1,3,5] -> List<DayOfWeek> (1=Monday, 7=Sunday)
  public static List<DayOfWeek> parseRunDays(String json) {

    if (json == null || json.isBlank()) {
      return new ArrayList<>();
    }

    String cleaned = json.trim();

    if (cleaned.startsWith("["))
      cleaned = cleaned.substring(1);
    if (cleaned.endsWith("]"))
      cleaned = cleaned.substring(0, cleaned.length() - 1);

    List<DayOfWeek> result = new ArrayList<>();

    if (!cleaned.isBlank()) {
      String[] parts = cleaned.split(",");

      for (String part : parts) {
        int value = Integer.parseInt(part.trim());
        result.add(DayOfWeek.of(value));
      }
    }

    return result;
  }

  // Convert List<DayOfWeek> -> string like [1,3,5] (1=Monday, 7=Sunday)
  public static String toRunDaysJson(List<DayOfWeek> days) {

    if (days == null || days.isEmpty()) {
      return "[]";
    }

    return days.stream()
        .map(d -> Integer.toString(d.getValue()))
        .collect(Collectors.joining(",", "[", "]"));
  }
}
