package com.lesconstructions.sapete.stempyerp.core.shared.query;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Reads the .sql file from "resources/" (defined in Query enum) then returns
 * the query as a String and caches it if required.
 * 
 * Usage: String queryString = Querycache.get(Query.SELECT_QUERY);
 */

public class QueryCache {

  private static final Map<String, String> CACHE = new ConcurrentHashMap<>();

  public static String get(Query query) {
    if (query.isCacheEnabled()) {
      return CACHE.computeIfAbsent(query.getFilePath(), QueryCache::loadFromClasspath);
    } else {
      return loadFromClasspath(query.getFilePath());
    }
  }

  private static String loadFromClasspath(String path) {
    try (var inputStream = QueryCache.class.getResourceAsStream(path);
        var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

      return reader.lines().collect(Collectors.joining("\n"));
    } catch (Exception e) {
      throw new RuntimeException("Failed to load SQL file: " + path, e);
    }
  }

}
