package com.example.firebasesetup;

import android.app.Application;

public class GlobalClass extends Application {

    private static Business business;
    private static Manager manager;
    private static Employee employee;

    public static Business getBusiness(){
        return business == null ? new Business() : business;
    }

    public static Manager getManager(){
        return manager == null ? new Manager() : manager;
    }

    public static Employee getEmployee(){
        return employee == null ? new Employee() : employee;
    }

    public static void setBusiness(Business b){
        business = b;
    }

    public static void setManager(Manager m){
        manager = m;
    }

    public static void setEmployee(Employee e){
        employee = e;
    }

    public static void clearGlobal(){

        business = new Business();
        manager = new Manager();
        employee = new Employee();

    }

}
