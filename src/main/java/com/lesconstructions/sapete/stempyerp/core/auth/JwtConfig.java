package com.lesconstructions.sapete.stempyerp.core.auth;

public class JwtConfig {
  public static final String SECRET = "twelve-thousand-rats-dev-only-1234567890987654321"; // TODO Figure out how to
                                                                                           // pull from .env
  public static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // 15 min
  public static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000; // 7 days
}
