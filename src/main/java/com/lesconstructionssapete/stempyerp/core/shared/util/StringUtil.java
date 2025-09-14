package com.lesconstructionssapete.stempyerp.core.shared.util;

import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

public final class StringUtil {

  private StringUtil() {
  }

  /**
   * Replace the given marker if found, otherwise append the replacement
   * to the end of the string.
   */
  public static String replaceOrAppend(String input, String marker, String replacement) {
    Objects.requireNonNull(input);
    Objects.requireNonNull(marker);
    Objects.requireNonNull(replacement);

    if (input.contains(marker)) {
      return input.replace(marker, replacement);
    }
    return input + " " + replacement;
  }

  /**
   * Replace the given marker if found, otherwise insert the replacement
   * BEFORE the fallback marker.
   */
  public static String replaceOrInsertBefore(String input, String marker, String replacement, String fallbackMarker) {
    Objects.requireNonNull(input);
    if (input.contains(marker)) {
      return input.replace(marker, replacement);
    }
    int idx = input.indexOf(fallbackMarker);
    if (idx != -1) {
      return input.substring(0, idx) + replacement + " " + input.substring(idx);
    }
    return input + " " + replacement;
  }

  /**
   * Replace the given marker if found, otherwise insert the replacement
   * AFTER the fallback marker.
   */
  public static String replaceOrInsertAfter(String input, String marker, String replacement, String fallbackMarker) {
    Objects.requireNonNull(input);
    if (input.contains(marker)) {
      return input.replace(marker, replacement);
    }
    int idx = input.indexOf(fallbackMarker);
    if (idx != -1) {
      return input.substring(0, idx + fallbackMarker.length()) + " " + replacement + " "
          + input.substring(idx + fallbackMarker.length());
    }
    return input + " " + replacement;
  }

  /**
   * Join non-null, non-empty strings with a delimiter.
   * Useful for building comma-separated or AND-separated lists.
   */
  public static String joinNonEmpty(String delimiter, Collection<String> parts) {
    Objects.requireNonNull(delimiter);
    if (parts == null || parts.isEmpty())
      return "";
    StringJoiner joiner = new StringJoiner(delimiter);
    for (String part : parts) {
      if (part != null && !part.isBlank()) {
        joiner.add(part.trim());
      }
    }
    return joiner.toString();
  }

  /**
   * Trim and collapse multiple spaces into one.
   */
  public static String normalizeWhitespace(String input) {
    if (input == null)
      return "";
    return input.trim().replaceAll("\\s+", " ");
  }

  /**
   * Parse an entityNo into a Long
   *
   * @param entityNo           the entity number string to parse
   * @param removeLeadingZeros if true, remove leading zeros before parsing
   * @return the parsed Long
   */
  public static Long parseEntityNo(String entityNo, boolean removeLeadingZeros) {
    if (entityNo != null && !entityNo.isEmpty()) {

      // Remove non-digit characters
      String numericPart = entityNo.replaceAll("\\D+", "");

      if (numericPart.isEmpty()) {
        throw new IllegalArgumentException("Failed to parse: entityNo does not contain a numeric part");
      }

      if (removeLeadingZeros) {
        numericPart = numericPart.replaceFirst("^0+", "");
        if (numericPart.isEmpty()) {
          numericPart = "0"; // handle case like "0000"
        }
      }

      return Long.valueOf(numericPart);
    }

    throw new IllegalArgumentException("Failed to parse: entityNo cannot be null");
  }

}
