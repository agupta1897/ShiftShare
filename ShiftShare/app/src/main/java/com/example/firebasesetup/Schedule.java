package com.example.firebasesetup;
import java.util.ArrayList;
import java.util.List;
public class Schedule {
    private String id;
    private String empl_id;
    private String Monday;
    private String Tuesday;
    private String Wednesday;
    private String Thursday;
    private String Friday;
    private String Saturday;
    private String Sunday;
    public Schedule() {
    }

    public Schedule(String id, String empl_id){
        this.id = id;
        this.empl_id = empl_id;
    }
    public String getId(){return this.id;}
    public String getempl_id(){return this.empl_id;}
    public void setMonday(String availability){this.Monday = availability;}
}