package com.lesconstructionssapete.stempyerp.infrastructure.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.lesconstructionssapete.stempyerp.port.security.PasswordHashProvider;

public class PBKDF2PasswordProvider implements PasswordHashProvider {

  private static final int ITERATIONS = 65536;
  private static final int KEY_LENGTH = 256;
  private static final int SALT_LENGTH = 16;

  /**
   * Hash a password using PBKDF2 with a random salt.
   */
  @Override
  public String hashPassword(String password) {

    try {
      byte[] salt = generateSalt();

      byte[] hash = pbkdf2(password, salt);

      return encode(salt, hash);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException("Error hashing password", e);
    }
  }

  /**
   * Verify a password against a hashed value.
   */
  @Override
  public boolean verifyPassword(String password, String hashedPassword) {

    String[] parts = hashedPassword.split(":");
    if (parts.length != 2) {
      return false;
    }

    byte[] salt = Base64.getDecoder().decode(parts[0]);
    byte[] hash = Base64.getDecoder().decode(parts[1]);

    try {
      byte[] testHash = pbkdf2(password, salt);
      return slowEquals(hash, testHash);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException("Error verifying password", e);
    }
  }

  /**
   * Generate a PBKDF2 hash of the password using the provided salt.
   */
  private byte[] pbkdf2(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
    return skf.generateSecret(spec).getEncoded();
  }

  private byte[] generateSalt() {
    byte[] salt = new byte[SALT_LENGTH];
    new SecureRandom().nextBytes(salt);

    return salt;
  }

  private String encode(byte[] salt, byte[] hash) {
    return Base64.getEncoder().encodeToString(salt) + ":" +
        Base64.getEncoder().encodeToString(hash);
  }

  /**
   * Prevent timing attacks
   */
  private boolean slowEquals(byte[] a, byte[] b) {
    int diff = a.length ^ b.length;
    for (int i = 0; i < a.length && i < b.length; i++) {
      diff |= a[i] ^ b[i];
    }
    return diff == 0;
  }

}
