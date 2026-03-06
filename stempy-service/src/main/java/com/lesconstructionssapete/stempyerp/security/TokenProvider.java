package com.lesconstructionssapete.stempyerp.security;

public interface TokenProvider {

  String generateAccessToken(String userNo);

  String generateRefreshToken(String userNo);

  String validateAccessTokenAndGetUserNo(String token);

  String validateRefreshTokenAndGetUserNo(String token);

  long getRefreshTokenExpirationMillis();

}
