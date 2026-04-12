package com.lesconstructionssapete.stempyerp.context;

public final class SecurityContext {

  private SecurityContext() {
  }

  private static final ThreadLocal<Long> CURRENT_USER = new ThreadLocal<>();

  public static void setUserId(Long userId) {
    CURRENT_USER.set(userId);
  }

  public static Long getUserId() {
    Long userId = CURRENT_USER.get();
    if (userId == null) {
      throw new IllegalStateException("No user ID set in SecurityContext");
    }
    return userId;
  }

  public static void clear() {
    CURRENT_USER.remove();
  }

}
