package com.example.mrdr.doctor;

public class Doctorclass {
    private String dname;
    private String dspecific;
    private String dphone;

    public String getDname() {
        return dname;
    }

    public String getDspecific() {
        return dspecific;
    }

    public String getDphone() {
        return dphone;
    }

    public Doctorclass(String dname, String dspecific, String dphone){
        this.dname = dname;
        this.dspecific = dspecific;
        this.dphone = dphone;


    }
}
