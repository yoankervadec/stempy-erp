package com.lesconstructionssapete.stempyerp.middleware;

import com.lesconstructionssapete.stempyerp.exception.UnauthorizedException;
import com.lesconstructionssapete.stempyerp.port.security.TokenProvider;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.JwtException;

public class JwtAuthenticationMiddleware implements Handler {

  private final TokenProvider tokenProvider;

  public JwtAuthenticationMiddleware(
      TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  public void handle(Context ctx) {

    String authHeader = ctx.header("Authorization");

    if (authHeader == null) {
      throw new UnauthorizedException(null, "Missing Authorization header");
    }

    String[] parts = authHeader.split(" ");
    if (parts.length != 2 || !parts[0].equalsIgnoreCase("Bearer")) {
      throw new UnauthorizedException(null, "Invalid Authorization header format");
    }

    String token = parts[1];

    try {
      String userNo = tokenProvider.validateAccessTokenAndGetUserNo(token);

      ctx.attribute("AUTHENTICATED_USER_NO", userNo);

    } catch (JwtException e) {
      throw new UnauthorizedException(null, "Invalid or expired token");
    }
  }

}
