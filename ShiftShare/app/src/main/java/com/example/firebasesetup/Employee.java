package com.example.employeesignuppage;

public class Employee {

    private String id;
    private String name;
    private String email;
    private String number;
    private String password;

    public Employee(){}

    public Employee(String empl_id, String empl_name, String empl_email, String empl_number, String empl_password){

        this.id = empl_id;
        this.name = empl_name;
        this.email = empl_email;
        this.number = empl_number;
        this.password = empl_password;

    }

    public String getId(){return this.id;}
    public String getName(){return this.name;}
    public String getEmail(){return this.email;}
    public String getNumber(){return this.number;}
    public String getPassword(){return this.password;}
    public void setName(String name){this.name = name;}
    public void setNumber(String number){this.number = number;}
    public void setPassword(String pwd){this.password = pwd;}
}
