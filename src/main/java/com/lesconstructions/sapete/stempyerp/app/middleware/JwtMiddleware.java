package com.lesconstructions.sapete.stempyerp.app.middleware;

import com.lesconstructions.sapete.stempyerp.core.auth.JwtUtil;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class JwtMiddleware implements Handler {

  @Override
  public void handle(Context ctx) throws Exception {
    String authHeader = ctx.header("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      ctx.status(401).result("Missing token");
      return;
    }

    String token = authHeader.substring(7);
    try {
      String userNo = JwtUtil.validateTokenAndGetUserNo(token);
      if (userNo != null) {
        ctx.attribute("userNo", userNo);
      }
    } catch (Exception e) {
      ctx.status(401).result("Invalid or expired token");
    }

  }
}
