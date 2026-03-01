package com.lesconstructionssapete.stempyerp.core.domain.shared;

public class Address {

  private String address;
  private String city;
  private String province;
  private String postalCode;
  private String country;

  public Address() {
  }

  public Address(
      String address,
      String city,
      String province,
      String postalCode,
      String country) {
    this.address = address;
    this.city = city;
    this.province = province;
    this.postalCode = postalCode;
    this.country = country;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

}
