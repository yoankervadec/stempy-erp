package com.lesconstructionssapete.stempyerp.core.domain.shared;

public class Contact {

  private String phoneNumber;
  private String phoneNumber2;
  private String email;

  public Contact() {
  }

  public Contact(
      String phoneNumber,
      String phoneNumber2,
      String email) {
    this.phoneNumber = phoneNumber;
    this.phoneNumber2 = phoneNumber2;
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getPhoneNumber2() {
    return phoneNumber2;
  }

  public void setPhoneNumber2(String phoneNumber2) {
    this.phoneNumber2 = phoneNumber2;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
