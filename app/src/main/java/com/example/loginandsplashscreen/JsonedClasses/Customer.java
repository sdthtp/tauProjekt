package com.example.loginandsplashscreen.JsonedClasses;

public class Customer {
    private String id;
    private String name;
    private String mail;
    private String password;
    private String status;
    private boolean canGetFreeItem;
    private double balanceMensa;
    private double balanceShuttle;


    public Customer(String id, String name, String mail, String password, String status, boolean canGetFreeItem, double balanceMensa, double balanceShuttle) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.status = status;
        this.canGetFreeItem = canGetFreeItem;
        this.balanceMensa = balanceMensa;
        this.balanceShuttle = balanceShuttle;
    }

    public Customer(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCanGetFreeItem() {
        return canGetFreeItem;
    }

    public void setCanGetFreeItem(boolean canGetFreeItem) {
        this.canGetFreeItem = canGetFreeItem;
    }

    public double getBalanceMensa() {
        return balanceMensa;
    }

    public void setBalanceMensa(double balanceMensa) {
        this.balanceMensa = balanceMensa;
    }

    public double getBalanceShuttle() {
        return balanceShuttle;
    }

    public void setBalanceShuttle(double balanceShuttle) {
        this.balanceShuttle = balanceShuttle;
    }

    public void setBalanceMensa(int balanceMensa) {
        this.balanceMensa = balanceMensa;
    }


    public void setBalanceShuttle(int balanceShuttle) {
        this.balanceShuttle = balanceShuttle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + mail + " " + status + " " + canGetFreeItem + " " + balanceMensa + " " + balanceShuttle;
    }
}

