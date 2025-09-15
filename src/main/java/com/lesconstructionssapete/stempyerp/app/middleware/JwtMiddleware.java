package com.lesconstructionssapete.stempyerp.app.middleware;

import com.lesconstructionssapete.stempyerp.core.exception.api.UnauthorizedException;
import com.lesconstructionssapete.stempyerp.core.jwt.JwtUtil;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.JwtException;

public class JwtMiddleware implements Handler {

  @Override
  public void handle(Context ctx) throws Exception {
    String authHeader = ctx.header("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new UnauthorizedException(null, null);
    }

    String token = authHeader.substring(7);
    try {
      String userNo = JwtUtil.validateTokenAndGetUserNo(token);

      if (userNo == null) {
        throw new UnauthorizedException(null, null);
      }
      ctx.attribute("userNo", userNo);

    } catch (JwtException e) {
      throw new UnauthorizedException(null, null);
    }
  }
}
