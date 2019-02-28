package com.example.firebasesetup;

import java.util.LinkedList;

public class Business{

    private String id;
    private String name;
    private String number;
    private String street;
    private String city;
    private String state;
    private String zip;
    private List<Manager> managers;
    private List<Employee> employees;


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

    public String getId(){return this.id;}
    public String getName(){return this.name;}
    public String getNumber(){return this.number;}
    public String getStreet(){return this.street;}
    public String getCity(){return this.city;}
    public String getState(){return this.state;}
    public String getZip(){return this.zip;}
    public List getManagers(){return this.managers;}
    public LIst getEmployees(){return this.employees;}
    public void setName(String name){this.name = name;}
    public void setNumber(String number){this.number = number;}
    public void setStreet(String street){this.street = street;}
    public void setCity(String city){this.city = city;}
    public void setState(String state){this.state = state;}
    public void setZip(String zip){this.zip = zip;}
    public void addManager(Manager manager){this.managers.add(manager);}
    public void addEmployee(Employee employee){this.employees.add(employee);}
    public void removeManager(Manager manager){this.managers.remove(manager);}
    public void removeEmployee(Employee employee){this.employees.remove(employee);}

}