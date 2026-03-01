package com.lesconstructionssapete.stempyerp.bootstrap;

import java.sql.SQLException;

public class ConstantCacheInitializer {

  public static void initialize(Dependencies dependencies) {
    try {
      dependencies.constantCache.warmUp();

    } catch (SQLException e) {
      throw new RuntimeException("Failed to initialize Constant Cache :", e);
    }
  }
}
