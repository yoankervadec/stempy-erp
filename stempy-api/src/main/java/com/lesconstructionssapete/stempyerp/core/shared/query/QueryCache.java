package com.lesconstructionssapete.stempyerp.core.shared.query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.lesconstructionssapete.stempyerp.core.exception.domain.QueryLoadException;

/**
 * Reads the .sql file from "resources/" (defined in Query enum) then returns
 * the query as a String and caches it if required.
 * 
 * Usage: String queryString = QueryCache.get(Query.SELECT_QUERY);
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
    try (InputStream inputStream = QueryCache.class.getResourceAsStream(path);
        BufferedReader reader = inputStream != null
            ? new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
            : null) {

      if (inputStream == null) {
        throw new QueryLoadException(path, new IllegalArgumentException("File not found in resources"));
      }

      return reader.lines().collect(Collectors.joining("\n"));

    } catch (IOException e) {
      throw new QueryLoadException(path, e);
    }
  }
}