package com.lesconstructionssapete.stempyerp.port.security;

public interface PasswordHashProvider {

  String hashPassword(String password);

  boolean verifyPassword(String password, String hashedPassword);

}
