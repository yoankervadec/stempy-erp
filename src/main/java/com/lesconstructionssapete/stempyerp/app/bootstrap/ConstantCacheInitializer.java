package com.lesconstructionssapete.stempyerp.app.bootstrap;

import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.app.Dependencies;
import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantCache;

public class ConstantCacheInitializer {

  public static void initialize(Dependencies dependencies) {
    try {
      ConstantCache constantCache = ConstantCache.create(dependencies.constantService);

      constantCache.warmUp();

    } catch (SQLException e) {
      throw new RuntimeException("Failed to initialize Constant Cache :", e);
    }
  }
}
