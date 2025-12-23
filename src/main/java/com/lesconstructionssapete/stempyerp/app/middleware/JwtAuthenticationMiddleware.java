package com.lesconstructionssapete.stempyerp.app.middleware;

import com.lesconstructionssapete.stempyerp.core.exception.api.UnauthorizedException;
import com.lesconstructionssapete.stempyerp.core.jwt.JwtUtil;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.JwtException;

public class JwtAuthenticationMiddleware implements Handler {

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
      String userNo = JwtUtil.validateAccessTokenAndGetUserNo(token);

      ctx.attribute("AUTHENTICATED_USER_NO", userNo);

    } catch (JwtException e) {
      throw new UnauthorizedException(null, "Invalid or expired token");
    }
  }

}
