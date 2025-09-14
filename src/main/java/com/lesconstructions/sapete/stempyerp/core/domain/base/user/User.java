package com.lesconstructions.sapete.stempyerp.core.domain.base.user;

import java.time.LocalDateTime;
import java.util.List;

import com.lesconstructions.sapete.stempyerp.core.domain.base.constant.TaxRegion;
import com.lesconstructions.sapete.stempyerp.core.domain.base.constant.UserAction;
import com.lesconstructions.sapete.stempyerp.core.domain.base.constant.UserRole;

/*
 * Full user object
 */

public class User extends UserReference {

  private String usernameLong;
  private UserRole userRole;
  private String password;
  private String pin;
  private List<UserAction> userActions;
  private TaxRegion userTaxRegion;
  private boolean isEnabled;

  public User(
      Long userSeq,
      String userNo,
      String usernameShort,
      String usernameLong,
      UserRole userRole,
      String password,
      String pin,
      List<UserAction> userActions,
      TaxRegion userTaxRegion,
      boolean isEnabled,
      long createdByUserSeq,
      LocalDateTime createdAt) {
    super(
        userSeq,
        userNo,
        usernameShort,
        createdByUserSeq);
    this.usernameLong = usernameLong;
    this.userRole = userRole;
    this.password = password;
    this.pin = pin;
    this.userActions = userActions;
    this.userTaxRegion = userTaxRegion;
    this.isEnabled = isEnabled;
    this.setCreatedAt(createdAt);
  }

  public String getUsernameLong() {
    return usernameLong;
  }

  public void setUsernameLong(String usernameLong) {
    this.usernameLong = usernameLong;
  }

  public UserRole getUserRole() {
    return userRole;
  }

  public void setUserRole(UserRole userRole) {
    this.userRole = userRole;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPin() {
    return pin;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  public List<UserAction> getUserActions() {
    return userActions;
  }

  public void setUserActions(List<UserAction> userActions) {
    this.userActions = userActions;
  }

  public TaxRegion getUserTaxRegion() {
    return userTaxRegion;
  }

  public void setUserTaxRegion(TaxRegion userTaxRegion) {
    this.userTaxRegion = userTaxRegion;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

}
