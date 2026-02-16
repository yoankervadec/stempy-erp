package com.lesconstructionssapete.stempyerp.app.bootstrap;

import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.app.Dependencies;

public class ConstantCacheInitializer {

  public static void initialize(Dependencies dependencies) {
    try {
      dependencies.constantCache.warmUp();

    } catch (SQLException e) {
      throw new RuntimeException("Failed to initialize Constant Cache :", e);
    }
  }
}
