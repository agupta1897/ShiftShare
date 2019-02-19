package com.example.firebasesetup;

public class Manager {

    private String id;
    private String name;
    private String email;
    private String password;
    private String contactNumber;


    public Manager(){

    }

    public Manager( String managerId, String managerName, String email, String password, String contactNumber)
    {
        this.id = managerId;
        this.name = managerName;
        this.contactNumber = contactNumber;
        this.password = password;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getContactNumber() {
        return contactNumber;
    }





}

