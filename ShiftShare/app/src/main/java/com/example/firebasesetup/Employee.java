package com.example.firebasesetup;

import java.util.LinkedList;
import java.util.List;

public class Employee {


    private String id;
    private String name;
    private String number;
    private String password;
    private String email;
    private List<String> business; //list of business ids

    public Employee() {

    }

    public Employee(String empl_id, String empl_name, String empl_number, String empl_password, String empl_email){

        this.id = empl_id;
        this.name = empl_name;
        this.number = empl_number;
        this.password = empl_password;
        this.email = empl_email;
        this.business = new LinkedList<>();

    }

    public String getId(){return this.id;}
    public String getName(){return this.name;}
    public String getEmail(){return this.email;}
    public String getNumber(){return this.number;}
    public String getPassword(){return this.password;}
    public void setName(String name){this.name = name;}
    public void setEmail(String email){this.email = email;}
    public void setNumber(String number){this.number = number;}
    public void setPassword(String password){this.password = password;}
    public void addBusiness(String id){this.business.add(id);}
    public void removeBusiness(String id){this.business.remove(id);}

}
