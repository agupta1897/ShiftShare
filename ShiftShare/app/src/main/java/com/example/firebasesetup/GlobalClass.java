package com.example.firebasesetup;

public class GlobalClass {

    public static Business business;
    public static Manager manager;
    public static Employee employee;

    public void clearGlobal(){

        business = new Business();
        manager = new Manager();
        employee = new Employee();

    }

}
