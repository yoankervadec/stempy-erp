package com.lesconstructions.sapete.stempyerp.core.domain.base.user;

public class UserSimple {

  private String usernameLong;
  private String password;

  public UserSimple() {
  }

  public UserSimple(String usernameLong, String password) {
    this.usernameLong = usernameLong;
    this.password = password;
  }

  public String getUsernameLong() {
    return usernameLong;
  }

  public void setUsernameLong(String usernameLong) {
    this.usernameLong = usernameLong;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
