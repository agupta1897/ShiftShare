package com.example.employeesignuppage;

public class Employee {

    private String id;
    private String name;
    private String number;
    private String password;


    public Employee(String empl_id, String empl_name, String empl_number, String empl_password){

        this.id = empl_id;
        this.name = empl_name;
        this.number = empl_number;
        this.password = empl_password;

    }

    public String getId(){return this.id;}
    public String getName(){return this.name;}
    public String getNumber(){return this.number;}
    public String getPassword(){return this.password;}
}
