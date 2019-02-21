package com.example.firebasesetup;

public class Business{

    private String id;
    private String name;
    private String number;
    private String street;
    private String city;
    private String state;
    private String zip;


    public Business(){}

    public Business(String bsn_id, String bsn_name, String bsn_number, String bsn_street, String bsn_city, String bsn_state, String bsn_zip){

        this.id = bsn_id;
        this.name = bsn_name;
        this.number = bsn_number;
        this.street = bsn_street;
        this.city = bsn_city;
        this.state = bsn_state;
        this.zip = bsn_zip;

    }

    public getId(){return this.id;}
    public getName(){return this.name;}
    public getNumber(){return this.number;}
    public getStreet(){return this.street;}
    public getCity(){return this.city;}
    public getState(){return this.state;}
    public getZip(){return this.zip;}

}