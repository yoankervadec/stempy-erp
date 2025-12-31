package com.lesconstructionssapete.stempyerp.core.jwt;

import java.time.Duration;

public class JwtConfig {
  public static final String SECRET = "twelve-thousand-rats-dev-only-1234567890987654321";
  public static final long ACCESS_TOKEN_EXPIRATION = Duration.ofMinutes(30).toMillis(); // 30 minutes
  public static final long REFRESH_TOKEN_EXPIRATION = Duration.ofDays(7).toMillis(); // 7 days
}
