package com.lesconstructionssapete.stempyerp.security;

public interface PasswordHashProvider {

  String hashPassword(String password);

  boolean verifyPassword(String password, String hashedPassword);

}
