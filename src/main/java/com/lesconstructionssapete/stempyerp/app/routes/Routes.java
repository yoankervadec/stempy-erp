package com.lesconstructionssapete.stempyerp.app.routes;

import io.javalin.Javalin;
import io.javalin.http.Handler;

/**
 * Utility class for defining API routes.
 * 
 * Provides methods to create standardized API paths and register HTTP method
 * handlers.
 * 
 */

public final class Routes {

  private Routes() {
  }

  public static String api(String version, String path) {
    return "/api/" + version + normalize(path);
  }

  public static String normalize(String path) {
    if (path == null || path.isBlank())
      return "";
    return path.startsWith("/") ? path : "/" + path;
  }

  public static void get(Javalin app, String path, Handler handler) {
    app.get(path, handler);
  }

  public static void post(Javalin app, String path, Handler handler) {
    app.post(path, handler);
  }

  public static void put(Javalin app, String path, Handler handler) {
    app.put(path, handler);
  }

  public static void delete(Javalin app, String path, Handler handler) {
    app.delete(path, handler);
  }

}
