package com.lesconstructionssapete.stempyerp.bootstrap;

import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.port.cache.ConstantCache;

public class ConstantCacheInitializer {

  public static void initialize(ConstantCache cache) {
    try {
      cache.warmUp();

    } catch (SQLException e) {
      throw new RuntimeException("Failed to initialize Constant Cache :", e);
    }
  }
}
