package com.example.firebasesetup;

public class Manager {

    private String id;
    private String name;
    private String email;
    private String password;
    private String contactNumber;
    private List<Business> business;

    public Manager(){

    }

    public Manager( String managerId, String managerName, String email, String password, String contactNumber)
    {
        this.id = managerId;
        this.name = managerName;
        this.contactNumber = contactNumber;
        this.password = password;
        this.email = email;
        this.business = new LinkedList<>();
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

    public List getBusiness(){
        return business;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void addBusiness(Business business){
        this.business.add(business);
    }

    public void removeBusiness(Business business){
        this.business.remove(business);
    }

}

