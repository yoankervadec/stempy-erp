package com.lesconstructionssapete.stempyerp.app.bootstrap;

import java.sql.Connection;

import com.lesconstructionssapete.stempyerp.app.Dependencies;
import com.lesconstructionssapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantCache;

public class ConstantCacheInitializer {

  public static void initialize(Dependencies dependencies) {
    try (Connection con = ConnectionPool.getConnection()) {

      ConstantCache constantCache = ConstantCache.create(dependencies.constantRepository);

      constantCache.warmUp(con);
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize Constant Cache :", e);
    }
  }
}
