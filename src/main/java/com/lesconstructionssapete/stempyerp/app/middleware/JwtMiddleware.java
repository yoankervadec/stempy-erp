package com.lesconstructionssapete.stempyerp.app.middleware;

import com.lesconstructionssapete.stempyerp.core.exception.api.UnauthorizedException;
import com.lesconstructionssapete.stempyerp.core.jwt.JwtUtil;

import io.javalin.http.Context;
import io.jsonwebtoken.JwtException;

public class JwtMiddleware {

  public String authenticate(Context ctx) {
    String authHeader = ctx.header("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new UnauthorizedException(null, "Invalid or missing Authorization header");
    }

    String token = authHeader.substring(7);

    try {
      String userNo = JwtUtil.validateTokenAndGetUserNo(token);

      if (userNo == null) {
        throw new UnauthorizedException(null, "Invalid or expired token");
      }
      return userNo;

    } catch (JwtException e) {
      throw new UnauthorizedException(null, null);
    }
  }
}
