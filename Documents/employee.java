package com.example.firebasesetup;

public class Employee{

    private String id;
    private String name;
    private String number;
    private String password;


    public Employee(){}

    public Employee(String empl_id, String empl_name, String empl_number, String empl_password){

        this.id = empl_id;
        this.name = empl_name;
        this.number = empl_number;
        this.password = empl_password;

    }

    public getId(){return this.id;}
    public getName(){return this.name;}
    public getNumber(){return this.number;}
    public getPassword(){return this.password;}
}