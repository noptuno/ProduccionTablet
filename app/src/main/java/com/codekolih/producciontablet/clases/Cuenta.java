package com.codekolih.producciontablet.clases;

public class Cuenta {

  String UserName;
  String Password;
  String MacAddress;

    public Cuenta(String userName, String password, String macAddress) {
        UserName = userName;
        Password = password;
        MacAddress = macAddress;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMacAddress() {
        return MacAddress;
    }

    public void setMacAddress(String macAddress) {
        MacAddress = macAddress;
    }
}
