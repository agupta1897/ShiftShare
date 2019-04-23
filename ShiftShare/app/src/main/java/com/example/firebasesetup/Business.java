package com.example.firebasesetup;

import java.util.LinkedList;
import java.util.List;

public class Business{

    private String id;
    private String name;
    private String number;
    private String street;
    private String city;
    private String state;
    private String zip;
    private List<String> managers; //list of manager ids
    private List<String> employees; //list of employee ids

    public Business(){}

    public Business(String bsn_id, String bsn_name, String bsn_number, String bsn_street, String bsn_city, String bsn_state, String bsn_zip){

        this.id = bsn_id;
        this.name = bsn_name;
        this.number = bsn_number;
        this.street = bsn_street;
        this.city = bsn_city;
        this.state = bsn_state;
        this.zip = bsn_zip;
        this.managers = new LinkedList<>();
        this.employees = new LinkedList<>();

    }

    //getters
    public String getId(){return this.id;}
    public String getName(){return this.name;}
    public String getNumber(){return this.number;}
    public String getStreet(){return this.street;}
    public String getCity(){return this.city;}
    public String getState(){return this.state;}
    public String getZip(){return this.zip;}
    public List getManagers(){return this.managers;}
    public List getEmployees(){return this.employees;}

    //setters
    public void setName(String name){this.name = name;}
    public void setNumber(String number){this.number = number;}
    public void setStreet(String street){this.street = street;}
    public void setCity(String city){this.city = city;}
    public void setState(String state){this.state = state;}
    public void setZip(String zip){this.zip = zip;}

    //list management
    public void addManager(String id){
        if(this.managers == null) this.managers = new LinkedList<>();
        this.managers.add(id);
    }
    public void addEmployee(String id){
        if(this.employees == null) this.employees = new LinkedList<>();
        this.employees.add(id);
    }
    public void removeManager(String id){this.managers.remove(id);}
    public void removeEmployee(String id){this.employees.remove(id);}

}