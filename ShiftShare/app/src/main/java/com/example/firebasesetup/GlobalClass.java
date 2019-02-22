package com.example.firebasesetup;

public class GlobalClass {

    public static Business business;
    public static Manager manager;

    public void clearGlobal(){

        business = new Business();
        manager = new Manager();

    }

}
